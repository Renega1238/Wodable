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
    private DBHelperSQLite db_test;
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

        db_test = new DBHelperSQLite(this);

        prefs = getSharedPreferences(ARCHIVO_PREFS, MODE_PRIVATE);

        user = findViewById(R.id.userinfo);
        saludo = findViewById(R.id.saludohomepage);
        myprofile = findViewById(R.id.myprofilebutton);

        saludo.setText(prefs.getString(NOMBRE_KEY, " ") + " " + prefs.getString(APELLIDO_KEY, " "));


        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarDatos(v);
            }
        });
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

    public void cargarDatos(View v){
        String timestamp = ADT_Recorridos.getCurrentTimeStamp();
        String birthDay = ADT_AtributosFisicos.getBirthDayToday();


        ADT_Usuarios usuario1 = new ADT_Usuarios("UsuarioTest1", "usuario1", "user1@gmail.com", "nombreUser1", "apellidoUser1");
        ADT_Usuarios usuario2 = new ADT_Usuarios("UsuarioTest2", "usuario2", "user2@gmail.com", "nombreUser2", "apellidoUser2");
        ADT_Usuarios usuario3 = new ADT_Usuarios("UsuarioTest3", "usuario3", "user3@gmail.com", "nombreUser3", "apellidoUser3");
        ADT_Usuarios usuario4 = new ADT_Usuarios("UsuarioTest4", "usuario4", "user4@gmail.com", "nombreUser4", "apellidoUser4");
        ADT_Usuarios usuario5 = new ADT_Usuarios("UsuarioTest5", "usuario5", "user5@gmail.com", "nombreUser5", "apellidoUser5");


        ADT_Recorridos recorrido1 = new ADT_Recorridos("2021-01-10 06:20:14","2021-01-10 07:20:10",10000,500,1);
        ADT_Recorridos recorrido2 = new ADT_Recorridos("2021-01-11 07:20:14","2021-01-11 08:20:10",20000,1000,1);
        ADT_Recorridos recorrido3 = new ADT_Recorridos("2021-01-12 09:20:14","2021-01-12 10:20:10",30000,2000,1);
        ADT_Recorridos recorrido4 = new ADT_Recorridos("2021-01-13 11:20:14","2021-01-13 12:20:10",40000,3000,2);
        ADT_Recorridos recorrido5 = new ADT_Recorridos("2021-01-14 13:20:14","2021-01-14 14:20:10",50000,4000,2);
        ADT_Recorridos recorrido6 = new ADT_Recorridos("2021-01-14 13:20:14","2021-01-14 14:20:10",60000,5000,3);
        ADT_Recorridos recorrido7 = new ADT_Recorridos("2021-01-14 15:20:14","2021-01-14 16:20:10",70000,6000,3);
        ADT_Recorridos recorrido8 = new ADT_Recorridos("2021-01-14 17:20:14","2021-01-14 18:20:10",80000,7000,3);
        ADT_Recorridos recorrido9 = new ADT_Recorridos("2021-01-14 17:20:14","2021-01-14 18:20:10",90000,8000,4);
        ADT_Recorridos recorrido10 = new ADT_Recorridos("2021-01-14 11:20:14","2021-01-14 12:20:10",100000,9000,5);
        ADT_Recorridos recorrido11 = new ADT_Recorridos("2021-01-14 13:20:14","2021-01-14 14:20:10",110000,10000,5);
        ADT_Recorridos recorrido12 = new ADT_Recorridos("2021-01-14 15:20:14","2021-01-14 16:20:10",120000,11000,5);



        ADT_AtributosFisicos atributo1 = new ADT_AtributosFisicos("1991-01-11", (float)71.500, (float)1.71, "Hombre", 1);
        ADT_AtributosFisicos atributo2 = new ADT_AtributosFisicos("1992-02-12", (float)72.500, (float)1.72, "Mujer", 2);
        ADT_AtributosFisicos atributo3 = new ADT_AtributosFisicos("1993-03-13", (float)73.500, (float)1.73, "Hombre", 3);
        ADT_AtributosFisicos atributo4 = new ADT_AtributosFisicos("1994-04-14", (float)74.500, (float)1.74, "Mujer", 4);
        ADT_AtributosFisicos atributo5 = new ADT_AtributosFisicos("1995-05-15", (float)75.500, (float)1.75, "Hombre", 5);



        db_test.agregarUsuario(usuario1);
        db_test.agregarUsuario(usuario2);
        db_test.agregarUsuario(usuario3);
        db_test.agregarUsuario(usuario4);
        db_test.agregarUsuario(usuario5);

        db_test.agregarRecorrido(recorrido1);
        db_test.agregarRecorrido(recorrido2);
        db_test.agregarRecorrido(recorrido3);
        db_test.agregarRecorrido(recorrido4);
        db_test.agregarRecorrido(recorrido5);
        db_test.agregarRecorrido(recorrido6);
        db_test.agregarRecorrido(recorrido7);
        db_test.agregarRecorrido(recorrido8);
        db_test.agregarRecorrido(recorrido9);
        db_test.agregarRecorrido(recorrido10);
        db_test.agregarRecorrido(recorrido11);
        db_test.agregarRecorrido(recorrido12);

        db_test.agregarAtributo(atributo1);
        db_test.agregarAtributo(atributo2);
        db_test.agregarAtributo(atributo3);
        db_test.agregarAtributo(atributo4);
        db_test.agregarAtributo(atributo5);



        List<ADT_Usuarios> usuarios = db_test.getAllUsers();
        List<ADT_Recorridos> recorridos = db_test.getAllRecorridos();
        List<ADT_AtributosFisicos> atributos = db_test.getAllAtributos();

        for (ADT_Usuarios usuario: usuarios) {
            Toast.makeText(this, usuario.getNombre(), Toast.LENGTH_SHORT).show();

        }

        for (ADT_Recorridos recorrido: recorridos) {
            Toast.makeText(this, recorrido.getPasos(), Toast.LENGTH_SHORT).show();
        }

        for (ADT_AtributosFisicos atributo: atributos) {
            Toast.makeText(this, "" +atributo.getAltura(), Toast.LENGTH_SHORT).show();
        }

    }

    public int validarUsuario(String username){

        List<ADT_Usuarios> usuarios = db_test.getAllUsers();

        for(ADT_Usuarios user: usuarios){
            if(username.equals(user.getUsername())){
                    return usuarios.indexOf(user);
            }
        }
        return -1;
    }

    public ADT_Usuarios validarContrasena(String username, String password){

        try{
            List<ADT_Usuarios> usuarios = db_test.getAllUsers();
            int user_id = validarUsuario(username);

            ADT_Usuarios  cuenta = usuarios.get(user_id);
            if(cuenta.getPassword().equals(password))
                return cuenta;

        } catch(NullPointerException e)
        {
            Log.d("My activity", "Error in validarContrasena " + e);
        }

        return null;
    }

    public List<ADT_Recorridos> recorridosByUserName(String username){

        List<ADT_Recorridos> recorridos = db_test.getAllRecorridos();
        List<ADT_Recorridos>  recorridos_del_usuario = new ArrayList<>();

        int user_id = validarUsuario(username);

        try{
            for(ADT_Recorridos recorrido: recorridos){
                if(user_id==recorrido.getId_usuario()){
                    recorridos_del_usuario.add(recorrido);
                }
            }

            return recorridos_del_usuario;

        } catch(NullPointerException e)
        {
            Log.d("My activity", "Error in duracionDistanciaUltmoRecorrido " + e);
        }

        return null;
    }

}