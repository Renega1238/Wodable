package mx.tec.wodable;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

// Thanks to Ansu Pilar for helping us with the podemter

public class DailyStepsActivity extends AppCompatActivity implements SensorEventListener, StepListener {
    //para progress bar
    private Handler handler = new Handler();


    //Elementos
    ProgressBar progressBar;
    TextView pasos, meta, question, distancia;
    EditText update;
    Button newSteps, continueSteps, finishSteps, endSteps;

    // sensores etc step counter
    private SensorManager sensorManager;
    private Sensor accel;
    private StepDetector stepDetector;

    public int pasosdado = 0;
    boolean bandera = false;

    //Keys
    private static final String PASOS_PREFS = "pasosprefs";
    private static final String PASOSANTERIORES = "pasosanteriores";
    private static final String METAANTERIOR = "metaanterior";
    private static final String DISTANCIAGUARDADA = "distancia";
    // Guardar los pasos que se dieron cuando el usario termine el día
    private static final String PASOSALFINAL = "pasosalfinal";
    private SharedPreferences pasosprefs;

    // bandera
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_steps);

        // ID's
        progressBar = findViewById(R.id.DailyStepsProgressBar);
        pasos = findViewById(R.id.DailyStepsPasos);
        meta = findViewById(R.id.DailyStepsTotal);
        question = findViewById(R.id.DailyStepsQuestion);
        update = findViewById(R.id.DailyStepsEntry);
        newSteps = findViewById(R.id.DailyStepsUpdateButton);
        continueSteps = findViewById(R.id.DailyStepsContinueButton);
        finishSteps = findViewById(R.id.DailyStepsFinishButton);
        distancia = findViewById(R.id.DailyStepsFinalDistance);
        //Boton nuevo
        endSteps  = findViewById(R.id.DailyStepsEndButton);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // Java class
        stepDetector = new StepDetector();
        stepDetector.registerListener(this);

        pasosprefs = getSharedPreferences(PASOS_PREFS, MODE_PRIVATE);

        pasos.setText(String.valueOf(pasosprefs.getInt(PASOSANTERIORES, 0)));
        meta.setText(String.valueOf(pasosprefs.getInt(METAANTERIOR, 0)));
        distancia.setText("Tu último recorrido fue de: " + String.valueOf(pasosprefs.getFloat(DISTANCIAGUARDADA, 0 )) + " Km");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (Integer.parseInt(pasos.getText().toString()) <= Integer.parseInt(meta.getText().toString())){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(Integer.parseInt(pasos.getText().toString()));  //pasos
                            progressBar.setMax(Integer.parseInt(meta.getText().toString()));
                        }
                    });
                    try{
                        Thread.sleep(100);
                    }catch (InterruptedException e){

                    }
                }
            }
        }).start();

    }

    public boolean isDigit(String number){
        try{
            if(TextUtils.isEmpty(number) || Integer.parseInt(number) <= 0){
                return false;
            }else{
                return TextUtils.isDigitsOnly(number);
            }
        }catch (Exception e){
            return false;
        }

    }
    public void newStep(View v){

        if(!isDigit(update.getText().toString())){
            Toast.makeText(this, "Porfavor ingresa un numero entero mayor a 0", Toast.LENGTH_SHORT).show();
        }else{

            // Flag para saber si sumar o reemplazar
            distancia.setText("Calculando....");
            flag = 1;
            //progressBar.setMax(Integer.parseInt(meta.getText().toString()));
            meta.setText(update.getText().toString());
            pasos.setText("0");
            //Guardamos la meta anterior
            SharedPreferences.Editor editor = pasosprefs.edit();
            // Guardamos en shared prefs la nueva meta
            editor.putInt(METAANTERIOR, Integer.parseInt(meta.getText().toString()));
            editor.commit();
            pasosdado = 0;
            //progressBar.setProgress(Integer.parseInt(meta.getText().toString()));
            sensorManager.registerListener(DailyStepsActivity.this,accel, SensorManager.SENSOR_DELAY_FASTEST);

            bandera = false;
        }
    }

    public void continuestep(View v){
        // Si ya completo la meta, ya no puede seguir
        if(Integer.parseInt(pasos.getText().toString()) == Integer.parseInt(meta.getText().toString())){

            AlertDialog.Builder builder = new AlertDialog.Builder(DailyStepsActivity.this);
            builder.setCancelable(true);
            builder.setTitle("Espera!");
            builder.setMessage("Ya has completado tu meta: " + meta.getText().toString() + " pasos, porfavor define una nueva");

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();

        }else{
            if(bandera == true){
                flag = 2;
                distancia.setText("Calculando....");
                //progressBar.setMax(Integer.parseInt(meta.getText().toString()));
                pasos.setText(String.valueOf(pasosprefs.getInt(PASOSANTERIORES, 0)));
                meta.setText(String.valueOf(pasosprefs.getInt(METAANTERIOR, 0)));
                pasosdado = pasosprefs.getInt(PASOSANTERIORES,0);
                sensorManager.registerListener(DailyStepsActivity.this,accel, SensorManager.SENSOR_DELAY_FASTEST);
                bandera = false;
            }else{

            }


        }
    }
    public void stop(View v){

        sensorManager.unregisterListener(DailyStepsActivity.this);

        flag = 1;
        // Guardar en sharedprefs
        SharedPreferences.Editor editor = pasosprefs.edit();
        // guardamos los pasos que hayamos dado
        editor.putInt(PASOSANTERIORES, Integer.parseInt(pasos.getText().toString()));
        editor.commit();

        float distanciaFinal = Distance(Integer.parseInt(pasos.getText().toString()));

        distancia.setText("Has recorrido un total de: " + String.valueOf(distanciaFinal) + " Km");

        bandera = true;
    }

    public void endSteps(View v){
        // Detenemos la cuenta
        sensorManager.unregisterListener(DailyStepsActivity.this);

        // Obtener los pasos que se dieron cuando el usuario lo finaliza aqui
        // Conectar a fire base

        // Guardar en sharedprefs
        SharedPreferences.Editor editor = pasosprefs.edit();
        // guardamos los pasos que hayamos dado

        flag = 1;

        editor.putInt(PASOSALFINAL, Integer.parseInt(pasos.getText().toString()));
        editor.putInt(PASOSANTERIORES, 0);
        editor.putInt(METAANTERIOR, 0);
        editor.commit();


        float distanciaFinal = Distance(Integer.parseInt(pasos.getText().toString()));

        pasos.setText("0");
        meta.setText("0");

        distancia.setText("Tu último recorrido fue de: " + String.valueOf(distanciaFinal) + " Km");

        bandera = false;
    }
    // Volver
    public void home(View v){
        Intent retorno = new Intent();

        //Intent info
        retorno.putExtra("pasos", pasos.getText().toString());
        retorno.putExtra("meta", meta.getText().toString());

        setResult(Activity.RESULT_OK, retorno);

        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            stepDetector.updateAccel(event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    // metodo de interface
    @Override
    public void step(long timeNs){
        pasosdado++;
        pasos.setText(String.valueOf(pasosdado));
        compare(pasosdado);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // Pop alert when reached the goal
    public void compare(int pasosdados){

        if(pasosdados == Integer.parseInt(meta.getText().toString())){

            // primero paramos la cuetna
            stop(finishSteps);

            AlertDialog.Builder builder = new AlertDialog.Builder(DailyStepsActivity.this);
            builder.setCancelable(true);
            builder.setTitle("FELICIDADES");
            builder.setMessage("Has logrado tu meta de hoy: " + meta.getText().toString() + " pasos!");

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.setPositiveButton("Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
    }

    public float Distance(int pasos){

        // Guardar en sharedprefs
        SharedPreferences.Editor editor = pasosprefs.edit();

        float distancia = 0;

        if(flag == 1){
            // Buscamos las siguientes aproximaciones en Google
            distancia = (float) (pasos*78) / (float) 100000; // es 100000 porque esta en cm y pasamos a km

            // guardamos los pasos que hayamos dado
            editor.putFloat(DISTANCIAGUARDADA, distancia);

            editor.commit();

        }else if (flag == 2){
            float x = (float) (pasos*78) / (float) 100000;
            distancia = (float) pasosprefs.getFloat(DISTANCIAGUARDADA, 0) + x;

            editor.putFloat(DISTANCIAGUARDADA, distancia);

            editor.commit();
        }

        return distancia;
    }

}