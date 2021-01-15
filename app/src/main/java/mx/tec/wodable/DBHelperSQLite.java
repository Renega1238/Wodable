package mx.tec.wodable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelperSQLite extends SQLiteOpenHelper {

    // TAG
    private static final String TAG = "MyActivity: ";

    // Database Info
    private static final String DATABASE_NAME = "WodableDB15.db";
    private static final int DATABASE_VERSION = 1;
    // Table Names
    private static final String TABLE_USUARIOS = "usuarios";
    private static final String TABLE_RECORRIDOS = "recorridos";
    private static final String TABLE_ATRIBUTOS_FISICOS = "atributos_fisicos";
    // usuarios Table Columns
    private static final String KEY_USER_ID = "id_usuario";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_CORREO = "correo";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_APELLIDO = "apellido";
    // recorridos Table Columns
    private static final String KEY_ID_CARRERA = "id_carrera";
    private static final String KEY_TIMESTAMP_INICIO = "timestamp_inicio";
    private static final String KEY_TIMESTAMP_FINAL = "timestamp_final";
    private static final String KEY_DISTANCIA = "distancia";
    private static final String KEY_PASOS = "pasos";
    private static final String KEY_ID_USUARIO = "id_usuario";
    // atributos_fisicos Table Columns
    private static final String KEY_ID_ATRIBUTOS = "id_atributos";
    private static final String KEY_FECHA_NACIMIENTO = "fecha_nacimiento";
    private static final String KEY_PESO = "peso";
    private static final String KEY_ALTURA = "altura";
    private static final String KEY_GENERO = "genero";
    private static final String KEY_ID_USUARIO_ATRIBUTOS = "id_usuario";

    private static DBHelperSQLite sInstance;

    public static synchronized DBHelperSQLite getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DBHelperSQLite(context.getApplicationContext());
        }
        return sInstance;
    }

    public DBHelperSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        SQLiteDatabase db = getReadableDatabase();
    }


    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USUARIOS_TABLE = "CREATE TABLE " + TABLE_USUARIOS +
                "(" +
                KEY_USER_ID + " INTEGER PRIMARY KEY," +
                KEY_USERNAME + " TEXT," +
                KEY_PASSWORD + " TEXT," +
                KEY_CORREO + " TEXT," +
                KEY_NOMBRE + " TEXT," +
                KEY_APELLIDO + " TEXT" +
                ")";


        String CREATE_RECORRIDOS_TABLE = "CREATE TABLE " + TABLE_RECORRIDOS +
                "(" +
                KEY_ID_CARRERA + " INTEGER PRIMARY KEY," +
                KEY_TIMESTAMP_INICIO + " TEXT," +
                KEY_TIMESTAMP_FINAL + " TEXT," +
                KEY_DISTANCIA + " TEXT," +
                KEY_PASOS + " TEXT," +
                KEY_ID_USUARIO + " INTEGER"+
                ")";

        String CREATE_ATRIBUTOS_FISICOS_TABLE = "CREATE TABLE " + TABLE_ATRIBUTOS_FISICOS +
                "(" +
                KEY_ID_ATRIBUTOS + " INTEGER PRIMARY KEY," +
                KEY_FECHA_NACIMIENTO + " TEXT," +
                KEY_PESO + " FLOAT," +
                KEY_ALTURA + " FLOAT," +
                KEY_GENERO + " TEXT," +
                KEY_ID_USUARIO_ATRIBUTOS + " INTEGER" +
                ")";



        db.execSQL(CREATE_USUARIOS_TABLE);
        db.execSQL(CREATE_RECORRIDOS_TABLE);
        db.execSQL(CREATE_ATRIBUTOS_FISICOS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORRIDOS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATRIBUTOS_FISICOS);

            onCreate(db);
        }
    }



    // Funcion que registra usuarios
    public void agregarUsuario(ADT_Usuarios usuario){

        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            // Valores para usuario
            //values.put(KEY_USER_ID , usuario.getId_usuario());
            values.put(KEY_USERNAME , usuario.getUsername());
            values.put(KEY_PASSWORD , usuario.getPassword());
            values.put(KEY_CORREO , usuario.getCorreo());
            values.put(KEY_NOMBRE , usuario.getNombre());
            values.put(KEY_APELLIDO  , usuario.getApellido());

            db.insertOrThrow(TABLE_USUARIOS, null, values);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add user to database");
        } finally {
            db.endTransaction();
        }
    }


    public void agregarRecorrido(ADT_Recorridos recorrido){
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            //Valores de recorrido
            values.put(KEY_TIMESTAMP_INICIO  , recorrido.getTimestamp_inicio());
            values.put(KEY_TIMESTAMP_FINAL  , recorrido.getTimestamp_final());
            values.put(KEY_DISTANCIA  , recorrido.getDistancia());
            values.put(KEY_PASOS   , recorrido.getPasos());
            values.put(KEY_ID_USUARIO    , recorrido.getId_usuario());

            db.insertOrThrow(TABLE_RECORRIDOS, null, values);

            Log.d(TAG, "Se agrego recorrido?");
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add recorrido " + e);
        } finally {
            db.endTransaction();
        }

    }

    public void agregarAtributo(ADT_AtributosFisicos atributo){

        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();

            //Valores de recorrido
            // Valores de atributos fisicos
            //values.put(KEY_ID_ATRIBUTOS   , atributo.());
            values.put(KEY_FECHA_NACIMIENTO   , atributo.getFecha_nacimiento());
            values.put(KEY_PESO   , atributo.getPeso());
            values.put(KEY_ALTURA    , atributo.getGenero());
            values.put(KEY_GENERO    , atributo.getGenero());
            values.put(KEY_ID_USUARIO_ATRIBUTOS     , atributo.getId_usuario());

            db.insertOrThrow(TABLE_ATRIBUTOS_FISICOS, null, values);

            Log.d(TAG, "Se agrego atributo?");
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add atributo " + e);
        } finally {
            db.endTransaction();
        }

    }


    // Get all users in the database
    public List<ADT_Usuarios> getAllUsers() {
        List<ADT_Usuarios> usuarios = new ArrayList<ADT_Usuarios>();


        String USERS_SELECT_QUERY =
                String.format("SELECT * FROM %s",
                        TABLE_USUARIOS);


        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USERS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    ADT_Usuarios usuario = new ADT_Usuarios();
                    String id=cursor.getString(cursor.getColumnIndex(KEY_USER_ID ));
                    usuario.setId_usuario(Integer.parseInt(id));
                    usuario.setUsername(cursor.getString(cursor.getColumnIndex(KEY_PASSWORD   )));
                    usuario.setPassword(cursor.getString(cursor.getColumnIndex(KEY_CORREO  )));
                    usuario.setCorreo(cursor.getString(cursor.getColumnIndex(KEY_NOMBRE  )));
                    usuario.setNombre(cursor.getString(cursor.getColumnIndex(KEY_APELLIDO  )));

                    usuarios.add(usuario);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get users from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return usuarios;
    }


    // Get all recorridos in the database
    public List<ADT_Recorridos> getAllRecorridos() {
        List<ADT_Recorridos> recorridos = new ArrayList<ADT_Recorridos>();


        String USERS_RECORRIDOS_QUERY =
                String.format("SELECT * FROM %s",
                        TABLE_RECORRIDOS);


        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USERS_RECORRIDOS_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    ADT_Recorridos recorrido = new ADT_Recorridos();
                    String id = cursor.getString(cursor.getColumnIndex(KEY_ID_CARRERA));
                    String id2 = cursor.getString(cursor.getColumnIndex(KEY_ID_USUARIO));

                    recorrido.setId_carrera(Integer.parseInt(id));
                    recorrido.setTimestamp_inicio(cursor.getString(cursor.getColumnIndex(KEY_TIMESTAMP_INICIO)));
                    recorrido.setTimestamp_final(cursor.getString(cursor.getColumnIndex(KEY_TIMESTAMP_FINAL)));
                    recorrido.setDistancia(cursor.getInt(cursor.getColumnIndex(KEY_DISTANCIA)));
                    recorrido.setPasos((cursor.getInt(cursor.getColumnIndex(KEY_PASOS))));
                    recorrido.setId_usuario(Integer.parseInt(id2));
                    recorridos.add(recorrido);

                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get recorridos from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return recorridos;
    }

    // Get all Atributos Fisicos in the database
    public List<ADT_AtributosFisicos> getAllAtributos() {
        List<ADT_AtributosFisicos> atributos = new ArrayList<ADT_AtributosFisicos>();


        String ATRIBUTOS_FISICOS_QUERY =
                String.format("SELECT * FROM %s",
                        TABLE_ATRIBUTOS_FISICOS);


        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(ATRIBUTOS_FISICOS_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    ADT_AtributosFisicos atributo = new ADT_AtributosFisicos();

                    String id = cursor.getString(cursor.getColumnIndex(KEY_ID_ATRIBUTOS  ));

                    atributo.setId_atributos(Integer.parseInt(id));
                    atributo.setFecha_nacimiento(cursor.getString(cursor.getColumnIndex(KEY_FECHA_NACIMIENTO )));
                    atributo.setPeso(cursor.getFloat(cursor.getColumnIndex(KEY_PESO )));
                    atributo.setGenero(cursor.getString(cursor.getColumnIndex(KEY_GENERO )));
                    atributo.setId_atributos((cursor.getInt(cursor.getColumnIndex(KEY_ID_USUARIO_ATRIBUTOS ))));

                    atributos.add(atributo);

                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get atributos from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return atributos;
    }


}
