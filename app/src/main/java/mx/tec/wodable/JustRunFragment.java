package mx.tec.wodable;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

// We would like to thank https://youtu.be/7QVr5SgpVog for the timer help

public class JustRunFragment extends Fragment {

    // Elementos
    TextView tiempo, pasos, distancia;
    Button reset, stop, start, exit;

    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;

    boolean timeStarted = false;

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
        //tiempo = v.findViewById(R.id.JustRunFragmentTiempo);
        pasos = v.findViewById(R.id.JustRunFragmentPasos);
        distancia = v.findViewById(R.id.JustRunFragmentDistancia);


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

                    startTimer();

                }else{
                    timeStarted = false;
                    start.setText("START");

                    timerTask.cancel();
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                            timeStarted = false;
                            // Llamamos a poner el tiempo
                            tiempo.setText("00:00:00");
                        }
                    }
                });
                resetAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // no hace nada
                        dialog.cancel();
                    }
                });
                resetAlert.show();
            }
        });
        return v;
    }

    public void startTimer(){

        /*timerTask = new TimerTask() {
            @Override
            public void run() {
                time++;
                tiempo.setText(getTimerText());
            }
        };
         */
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        time++;
                        tiempo.setText(getTimerText(time));
                    }
                    //no se puede olvidar el start o no corre
                }).start();
            }
        };
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


        return h + ":" + m + ":" + s;
    }
    private String formatTime(int segundos, int minutos, int horas){
        //String f =
        return String.format(Locale.US, "%02d", horas) + ":" + String.format(Locale.US, "%02d", minutos) + ":" + String.format(Locale.US, "%02d", segundos);
    }

}