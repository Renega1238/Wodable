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
    private DBHelperSQLite db_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_user);

        db_test = new DBHelperSQLite(this);
        Log.d("My activity:" ,"Cargando datos...");
        //cargarDatos();
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

    /*public void interaccion(View v){
        Toast.makeText(this,"Ingresando",Toast.LENGTH_SHORT).show();
        Intent intento=new Intent(this,ProfileInfoActivity.class);
        intento.putExtra("usuario",usuario.getText().toString());
        startActivityForResult(intento,ACTIVITY2_CODE);
    }*/

    public void cargarDatos(){
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

        /*for (ADT_Usuarios usuario: usuarios) {
            Toast.makeText(this, usuario.getNombre(), Toast.LENGTH_SHORT).show();

        }

        for (ADT_Recorridos recorrido: recorridos) {
            Toast.makeText(this, recorrido.getPasos(), Toast.LENGTH_SHORT).show();
        }

        for (ADT_AtributosFisicos atributo: atributos) {
            Toast.makeText(this, "" +atributo.getAltura(), Toast.LENGTH_SHORT).show();
        }*/

    }

    public void loginStart(View v){

       String username = this.nombreentry.getText().toString();
        String password = this.contrasenaentry.getText().toString();

        try{
            ADT_Usuarios usuario = validarContrasena(username,password);

            Intent intentMain = new Intent(this, MainActivity.class);
            intentMain.putExtra("usuario", usuario.getUsername());
            intentMain.putExtra("correo", usuario.getCorreo());
            intentMain.putExtra("nombre", usuario.getNombre());
            intentMain.putExtra("apellido", usuario.getApellido());
            intentMain.putExtra("id_usuario", usuario.getId_usuario());

            startActivity(intentMain);

        }catch(NullPointerException e){
            Toast.makeText(this, "Datos incorrectos",Toast.LENGTH_SHORT ).show();
        }





    }

    public int validarUsuario(String username){

        List<ADT_Usuarios> usuarios = db_test.getAllUsers();

        List<String> lista_nombres = new ArrayList<>();

        for(ADT_Usuarios user : usuarios ){
            lista_nombres.add(user.getUsername());
        }

        int pos = lista_nombres.indexOf(username);
        int found = -1;

        if(lista_nombres.indexOf(username) != -1){
            found = usuarios.get(pos).getId_usuario();
            return found;
        }

       return found;
    }

    public ADT_Usuarios validarContrasena(String username, String password){

        ADT_Usuarios c = new ADT_Usuarios();
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

        return c;
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