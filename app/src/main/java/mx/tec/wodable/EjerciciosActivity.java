package mx.tec.wodable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

// logout
public class EjerciciosActivity extends AppCompatActivity {
    private ImageView mi_perfil, mis_carreras, mis_pasosdiarios, mis_cronometro, mis_ejercicios ;

    private static final int DAILYSTEPS_CODE = 2;
    private static final int NEWRACE_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios);
        mi_perfil = findViewById(R.id.mi_perfil);
        mis_carreras = findViewById(R.id.mis_carreras);
        mis_pasosdiarios = findViewById(R.id.mis_pasosdiarios);
        mis_cronometro = findViewById(R.id.mi_cronometro);
        mis_ejercicios = findViewById(R.id.mi_ejercicios);

        mi_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarAMiPerfil(v);
            }
        });

        mis_carreras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarAMisCarreras(v);
            }
        });

        mis_pasosdiarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarAMisPasosDiarios(v);
            }
        });

        mis_cronometro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarAMiCronometro(v);
            }
        });
    }

    private void cambiarAMiCronometro(View v) {
        Intent newRace = new Intent(this, Rene_MapsActivity.class);

        startActivityForResult(newRace, NEWRACE_CODE);
    }

    private void cambiarAMisPasosDiarios(View v) {
        Intent dailysteps = new Intent(this, DailyStepsActivity.class);
        startActivityForResult(dailysteps, DAILYSTEPS_CODE);
    }

    private void cambiarAMisCarreras(View v) {
        Intent i = new Intent(this, RecyclerActivityRecorridos.class);
        startActivity(i);
    }


    public void cambiarAMiPerfil(View v){
        Intent i = new Intent(this, ProfileInfoActivity.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == Activity.RESULT_OK && data != null){

            int x = Integer.parseInt(data.getStringExtra("meta")) / 2;

            if(Integer.parseInt(data.getStringExtra("pasos")) < x){
                Toast.makeText(this,"No has llegado a la mitad", Toast.LENGTH_SHORT).show();
            }else if(Integer.parseInt(data.getStringExtra("pasos")) < Integer.parseInt(data.getStringExtra("meta"))){
                int p = Integer.parseInt(data.getStringExtra("meta")) - Integer.parseInt(data.getStringExtra("pasos"));
                Toast.makeText(this, "Ya solo te faltan " + String.valueOf(p) + " pasos para tu meta de hoy", Toast.LENGTH_SHORT).show();
            }else if(Integer.parseInt(data.getStringExtra("pasos")) == Integer.parseInt(data.getStringExtra("meta"))){
                Toast.makeText(this, "FELICIDADES, LO HAS LOGRADO", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "No se que hicicste", Toast.LENGTH_LONG).show();
            }
        }
    }
}