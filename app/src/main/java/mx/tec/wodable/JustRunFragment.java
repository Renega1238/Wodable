package mx.tec.wodable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.sql.Time;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static androidx.core.content.ContextCompat.getSystemService;

// We would like to thank https://youtu.be/7QVr5SgpVog for the timer help

public class JustRunFragment extends Fragment implements SensorEventListener, StepListener{

    // Elementos
    TextView tiempo, pasos, distancia;
    Button reset, stop, start, exit;

    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;

    boolean timeStarted = false;

    // sensores etc step counter
    private SensorManager sensorManager;
    private Sensor accel;
    private StepDetector stepDetector;

    public int pasosdado = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public JustRunFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JustRunFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JustRunFragment newInstance(String param1, String param2) {
        JustRunFragment fragment = new JustRunFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        timer = new Timer();

        // View reference
        View v = inflater.inflate(R.layout.fragment_just_run, container,false);

        // Botones
        reset = v.findViewById(R.id.JustRunFragmentResetButton);
        stop = v.findViewById(R.id.JustRunFragmentStopButton);
        start = v.findViewById(R.id.JustRunFragmentStartButton);
        exit = v.findViewById(R.id.JustRunFragmentExitButton);

        // Text views
        tiempo = v.findViewById(R.id.JustRunFragmentTiempo);
        pasos = v.findViewById(R.id.JustRunFragmentPasos);
        distancia = v.findViewById(R.id.JustRunFragmentDistancia);


        // En los fragmentes siempre ocupa el get activity
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // Java class
        stepDetector = new StepDetector();
        stepDetector.registerListener(this);
        // Contador de pasoos
        pasosdado = 0;

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // dejar de contar cuando sale el pop
                sensorManager.unregisterListener(JustRunFragment.this);

                timerTask.cancel();

                float distanciaFinal = Distance(pasosdado);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);
                builder.setTitle("Espera!");
                builder.setMessage("Estas seguro que deseas parar? Has corrido:  " + String.valueOf(distanciaFinal) + " Km");

                // Dialog alert

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        timeStarted = true;
                        start.setText("PAUSE");
                        // seguimo scontadno cuando sael
                        sensorManager.registerListener(JustRunFragment.this,accel, SensorManager.SENSOR_DELAY_FASTEST);
                        startTimer();
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("Seguro!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sensorManager.unregisterListener(JustRunFragment.this);
                        pasos.setText("0");
                        distancia.setText("0 Km");
                        time = 0.0;
                        pasosdado = 0;
                        timeStarted = false;
                        // Llamamos a poner el tiempo
                        start.setText("START");
                        tiempo.setText("00:00:00");
                    }
                });
                builder.show();
            }
        });
        // Exit button
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                Fragment f = manager.findFragmentById(R.id.JustRunFragment);
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(f);
                transaction.commit();
            }
        });

        // Start pause botton
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timeStarted == false){
                    timeStarted = true;
                    start.setText("PAUSE");

                    distancia.setText("Calculando....");
                    // Step counter
                    sensorManager.registerListener(JustRunFragment.this,accel, SensorManager.SENSOR_DELAY_FASTEST);

                    startTimer();

                }else{

                    timeStarted = false;
                    start.setText("START");
                    timerTask.cancel();
                    sensorManager.unregisterListener(JustRunFragment.this);

                    float f = Distance(pasosdado);
                    distancia.setText(String.valueOf(f) + "Km");

                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sensorManager.unregisterListener(JustRunFragment.this);
                timerTask.cancel();

                AlertDialog.Builder resetAlert = new AlertDialog.Builder(getContext());
                resetAlert.setCancelable(true);
                resetAlert.setTitle("Reset Timer!");
                resetAlert.setMessage("¿Seguro que deseas empezar de nuevo?");
                // Botones en la alerta
                resetAlert.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(timerTask != null){
                            timerTask.cancel();
                            time = 0.0;
                            timeStarted = true;
                            pasosdado = 0;
                            pasos.setText("0");
                            distancia.setText("0 Km");
                            // Llamamos a poner el tiempo
                            start.setText("PAUSE");
                            tiempo.setText("00:00:00");
                            distancia.setText("Calculando....");
                            sensorManager.registerListener(JustRunFragment.this,accel, SensorManager.SENSOR_DELAY_FASTEST);
                            startTimer();
                        }
                    }
                });
                resetAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // no hace nada
                        sensorManager.registerListener(JustRunFragment.this,accel, SensorManager.SENSOR_DELAY_FASTEST);
                        startTimer();
                        dialog.cancel();
                    }
                });
                resetAlert.show();
            }
        });
        return v;
    }

    public void startTimer(){

        timerTask = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        tiempo.setText(getTimerText(time));
                    }
                });
            }
        };
        //timer.scheduleAtFixedRate(timerTask,0,1000);
        timer.scheduleAtFixedRate(timerTask,0,1000);
    }

    public String getTimerText(double time){

        int rounded = (int) Math.round(time);

        int horas = 0;
        int minutos = 0;
        int segundos = 0;

        segundos = ((rounded % 86400) % 3600) % 60;
        minutos = ((rounded % 86400) % 3600) / 60;
        horas = ((rounded % 86400) / 3600);

        String h = String.valueOf(horas);
        String m = String.valueOf(minutos);
        String s = String.valueOf(segundos);


        return formatTime(segundos, minutos, horas);
    }
    private String formatTime(int segundos, int minutos, int horas){

        //String f =
        return String.format(new Locale("es","MX"), "%02d", horas) + ":" + String.format(new Locale("es","MX"), "%02d", minutos) + ":" + String.format(new Locale("es","MX"), "%02d", segundos);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            stepDetector.updateAccel(event.timestamp, event.values[0], event.values[1], event.values[2]);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void step(long timeNS) {
        pasosdado++;
        pasos.setText(String.valueOf(pasosdado));
    }

    public float Distance(int pasosdados){

        float distancia = 0;

        // Buscamos las siguientes aproximaciones en Google
        distancia = (float) (pasosdados*78) / (float) 100000; // es 100000 porque esta en cm y pasamos a km

        return distancia;
    }
}