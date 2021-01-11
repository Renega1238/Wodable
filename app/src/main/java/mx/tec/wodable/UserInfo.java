package mx.tec.wodable;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserInfo extends AppCompatActivity {



    // Oojetos
    Button home, safe;
    EditText nombre, apellido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        // Ids
        home = findViewById(R.id.userinofhomebutton);
        safe = findViewById(R.id.userinfosafe);

        nombre = findViewById(R.id.UserInfoNombreEntry);
        apellido = findViewById(R.id.UserInfoApellidoEntry);

   }

   public void home(View v){
        finish();
    }

    // Safe
    public void safe(View v){

        Intent retorno = new Intent();

        //Intent info
        retorno.putExtra("nombreUser", nombre.getText().toString());
        retorno.putExtra("apellidoUser", apellido.getText().toString());

        setResult(Activity.RESULT_OK, retorno);

        finish();
    }
}