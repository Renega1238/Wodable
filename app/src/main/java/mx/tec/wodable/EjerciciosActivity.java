package mx.tec.wodable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class EjerciciosActivity extends AppCompatActivity {
    private ImageView mi_perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios);
        mi_perfil = findViewById(R.id.mi_perfil);
        mi_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarAMiPerfil(v);
            }
        });
    }


    public void cambiarAMiPerfil(View v){
        Intent i = new Intent(this, ProfileInfoActivity.class);
        startActivity(i);
    }



}