package mx.tec.wodable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView bienvenida;
    EditText usuario, contrasena;
    Button registro,entrar;

    private static final int ACTIVITY2_CODE=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bienvenida=findViewById(R.id.textView);
        usuario=findViewById(R.id.Plainusuario);
        contrasena=findViewById(R.id.Plaincontrasena);
        registro=findViewById(R.id.button1);
        entrar=findViewById(R.id.button2);
    }

    public void interaccion(View v){
        Toast.makeText(this,"Ingresando",Toast.LENGTH_SHORT).show();
        Intent intento=new Intent(this,ProfileInfoActivity.class);
        intento.putExtra("usuario",usuario.getText().toString());
        startActivityForResult(intento,ACTIVITY2_CODE);
    }


}