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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LogUserActivity extends AppCompatActivity {

    TextView bienvenido;
    ImageView fotito;
    EditText nombreentry, contrasenaentry;
    Button registrarse, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_user);

        Log.d("My activity:" ,"Cargando datos...");
        Log.d("My final activity","Cargando datos final...");


        bienvenido = findViewById(R.id.Bienvenido);
        fotito = findViewById(R.id.fotito);
        registrarse = findViewById(R.id.registrarsebutton);
        contrasenaentry = findViewById(R.id.logincontrasena);
        nombreentry = findViewById(R.id.loginnombreentry);
        login = findViewById(R.id.iniciarsesionbutton);





    }

    public void iniciarSesion(View v){

        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }



    public void loginStart(View v){

       String username = this.nombreentry.getText().toString();
        String password = this.contrasenaentry.getText().toString();

        try{


            /*Intent intentMain = new Intent(this, MainActivity.class);
            intentMain.putExtra("usuario", usuario.getUsername());
            intentMain.putExtra("correo", usuario.getCorreo());
            intentMain.putExtra("nombre", usuario.getNombre());
            intentMain.putExtra("apellido", usuario.getApellido());
            intentMain.putExtra("id_usuario", usuario.getId_usuario());

            startActivity(intentMain);*/

        }catch(NullPointerException e){
            Toast.makeText(this, "Datos incorrectos",Toast.LENGTH_SHORT ).show();
        }


    }






}