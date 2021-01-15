package mx.tec.wodable;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class edit_info_personal extends AppCompatActivity {

    EditText fecha,genero,peso,altura;
    Button actualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info_personal);

        fecha=findViewById(R.id.editTextDate);
        genero=findViewById(R.id.editTextGenero);
        peso=findViewById(R.id.editTextPeso);
        altura=findViewById(R.id.editTextAltura);
        actualizar=findViewById(R.id.buttonAct);
    }

    public void actualizarDatos(View v){

        Intent cambiar=new Intent();
        cambiar.putExtra("fecha",fecha.getText().toString());
        cambiar.putExtra("genero",genero.getText().toString());
        cambiar.putExtra("peso",peso.getText().toString());
        cambiar.putExtra("altura",altura.getText().toString());


        Toast.makeText(this,"Datos actualizados",Toast.LENGTH_SHORT).show();

        setResult(Activity.RESULT_OK,cambiar);

        finish();
    }
}