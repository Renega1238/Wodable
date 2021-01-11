package mx.tec.wodable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static final int USERINFO_CODE = 1;

    // Vamos a crear el archivo local
    // shared prefs
    private static final String ARCHIVO_PREFS = "prefs";
    private static final String NOMBRE_KEY = "usernombre";
    private static final String APELLIDO_KEY = "userapellido";
    // shared prefs
    private SharedPreferences prefs;

    Button user;
    TextView saludo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences(ARCHIVO_PREFS, MODE_PRIVATE);

        user = findViewById(R.id.userinfo);
        saludo = findViewById(R.id.saludohomepage);

        saludo.setText(prefs.getString(NOMBRE_KEY, " ") + " " + prefs.getString(APELLIDO_KEY, " "));

    }

    public void user(View v ){

        Intent user = new Intent(this, UserInfo.class);

        // Esperamos retorno
        startActivityForResult(user, USERINFO_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == Activity.RESULT_OK && data != null){

           SharedPreferences.Editor editor = prefs.edit();

           editor.putString(NOMBRE_KEY, data.getStringExtra("nombreUser"));
           editor.putString(APELLIDO_KEY, data.getStringExtra("apellidoUser"));

           editor.commit();

            Toast.makeText(this, " " +
                    prefs.getString(NOMBRE_KEY, "No hay saludo") + " " +
                    prefs.getString(APELLIDO_KEY, "No hay saludo"), Toast.LENGTH_SHORT).show();
        }
    }

}