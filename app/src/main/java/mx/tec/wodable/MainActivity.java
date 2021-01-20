package mx.tec.wodable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int USERINFO_CODE = 1;
    //private DBHelperSQLite db_test;
    // Vamos a crear el archivo local
    // shared prefs
    private static final String ARCHIVO_PREFS = "prefs";
    private static final String NOMBRE_KEY = "usernombre";
    private static final String APELLIDO_KEY = "userapellido";
    // shared prefs
    private SharedPreferences prefs;

    Button user, myprofile;
    TextView saludo;

    @Override
    //Actividad principal
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //db_test = new DBHelperSQLite(this);

        prefs = getSharedPreferences(ARCHIVO_PREFS, MODE_PRIVATE);

        user = findViewById(R.id.userinfo);
        saludo = findViewById(R.id.saludohomepage);
        myprofile = findViewById(R.id.myprofilebutton);


        Intent intento = getIntent();
        saludo.setText("Hola "+intento.getStringExtra("NAME")+"!");

    }

    public void beginRace(View v){
        Toast.makeText(this,"Podometer unavailable",Toast.LENGTH_SHORT);
    }

    public void user(View v ){

        Intent user = new Intent(this, UserInfo.class);
        // Esperamos retorno
        startActivityForResult(user, USERINFO_CODE);

    }

    public void myProfile(View v){

        Intent myprofile = new Intent(this, ProfileInfoActivity.class);

        startActivity(myprofile);
    }
    
    public void myRaces(View v){
        Intent myRaces = new Intent(this, RecentActivity.class);
        startActivity(myRaces);
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
        saludo.setText(prefs.getString(NOMBRE_KEY, " ") + " " + prefs.getString(APELLIDO_KEY, " "));
    }


}