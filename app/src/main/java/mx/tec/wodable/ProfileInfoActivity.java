package mx.tec.wodable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileInfoActivity extends AppCompatActivity {

    private static final String ARCHIVO_PREFS = "prefs";
    private static final String NOMBRE_KEY = "usernombre";
    private static final String APELLIDO_KEY = "userapellido";
    private SharedPreferences prefs;
    private static final int ACTIVITY10_CODE=0;

    TextView Saludo,Fnacimiento,genero,peso,altura,imc;
    Button editarDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);

        Saludo = findViewById(R.id.textView2);
        Fnacimiento = findViewById(R.id.textView4);
        genero = findViewById(R.id.textView5);
        altura = findViewById(R.id.textView6);
        peso = findViewById(R.id.textView7);
        imc = findViewById(R.id.textView8);
        editarDatos = findViewById(R.id.buttonEditarDatos);

        prefs = getSharedPreferences(ARCHIVO_PREFS, MODE_PRIVATE);

        Saludo.setText(prefs.getString(NOMBRE_KEY, " ") + " " + prefs.getString(APELLIDO_KEY, " "));
        Intent cambio=getIntent();
        String FechaNac=cambio.getStringExtra("fecha");
        String gen=cambio.getStringExtra("genero");
        String pes=cambio.getStringExtra("peso");
        String alt=cambio.getStringExtra("altura");


        Fnacimiento.setText(FechaNac);
        genero.setText(gen);
        altura.setText(pes);
        peso.setText(alt);
        imc.setText("Indice de masa corporal");

    }

    public void editar(View v){
        Intent editar=new Intent(this,edit_info_personal.class);
        startActivityForResult(editar,ACTIVITY10_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}