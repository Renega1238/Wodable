package mx.tec.wodable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LogUserActivity extends AppCompatActivity {

    TextView bienvenido;
    ImageView fotito;
    EditText nombreentry, apellidoentry;
    Button registrarse, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_user);

        bienvenido = findViewById(R.id.Bienvenido);
        fotito = findViewById(R.id.fotito);
        nombreentry = findViewById(R.id.loginnombreentry);
        apellidoentry = findViewById(R.id.loginapellidoentry);
        registrarse = findViewById(R.id.registrarsebutton);
        login = findViewById(R.id.iniciarsesionbutton);

    }

    public void iniciarSesion(View v){

        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }
}