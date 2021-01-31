package mx.tec.wodable;

import androidx.annotation.NonNull;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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

    // Bases de datos
    private String userId;
    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;

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

        // Para base de datos
        // Referencia a la DB de usuarios autenticados
        mAuth = FirebaseAuth.getInstance();

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

    @Override
    protected void onStart() {
        super.onStart();
        fStore = FirebaseFirestore.getInstance();
        if(mAuth.getCurrentUser() == null){
            FirebaseAuth.getInstance().signOut();
            FirebaseFirestore.getInstance().terminate();
            Intent i = new Intent(DailyStepsActivity.this, LoginActivityMichel.class);
            startActivity(i);
            finish();
            return;
        }
        userId = mAuth.getCurrentUser().getUid();
        bandera = true;
    }

    private static boolean success = false;
    public void actualizarDatos(String pasos, String distancia){
        DocumentReference documentReference = fStore.collection("dailySteps").document(userId);
        Map<String, Object> dailySteps = new HashMap<>();
        dailySteps.put("pasos", pasos);
        dailySteps.put("distancia", distancia);
        documentReference.set(dailySteps).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(DailyStepsActivity.this,"Datos actualizados",Toast.LENGTH_SHORT).show();
                Log.wtf("Atributos creados (Atributos): ", "Datos actualizados");
                success = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DailyStepsActivity.this,"¡Algo pasó!",Toast.LENGTH_SHORT).show();
                Log.d("Algo pasó (Atributos): ", "onFailure: " + e.toString());
                success = false;
            }
        });

        // Cuando se actualicen los datos llevar a otra pantalla
        Intent i = new Intent(DailyStepsActivity.this, EjerciciosActivity.class);
        startActivity(i);
        finish();
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
        float distanciaFinal = Distance(Integer.parseInt(pasos.getText().toString()));

        String r = String.valueOf(pasosdado);
        String p = String.valueOf(distanciaFinal);
        actualizarDatos(r,p);
        // Guardar en sharedprefs
        SharedPreferences.Editor editor = pasosprefs.edit();
        // guardamos los pasos que hayamos dado

        flag = 1;

        editor.putInt(PASOSALFINAL, Integer.parseInt(pasos.getText().toString()));
        editor.putInt(PASOSANTERIORES, 0);
        editor.putInt(METAANTERIOR, 0);
        editor.commit();


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