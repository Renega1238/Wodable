package mx.tec.wodable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

// logout
public class EjerciciosActivity extends AppCompatActivity {
    private ImageView mi_perfil, mis_carreras, mis_pasosdiarios, mis_cronometro, mis_ejercicios ;

    private static final int DAILYSTEPS_CODE = 2;
    private static final int NEWRACE_CODE = 3;

    private String userId;
    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;

    protected static Hashtable<String, Recorrido> HT_Recorridos;

    private boolean nuevoUsarioRecorridos = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios);
        mi_perfil = findViewById(R.id.mi_perfil);
        mis_carreras = findViewById(R.id.mis_carreras);
        mis_pasosdiarios = findViewById(R.id.mis_pasosdiarios);
        mis_cronometro = findViewById(R.id.mi_cronometro);
        mis_ejercicios = findViewById(R.id.mi_ejercicios);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        HT_Recorridos = new Hashtable<>();

        mi_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarAMiPerfil(v);
            }
        });

        mis_carreras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Recorrido recorrido = new Recorrido(1, "20", "20", "20", "20", "20");
                //HT_Recorridos.put("0",recorrido);
                if(nuevoUsarioRecorridos == true){
                    AlertDialog.Builder builder = new AlertDialog.Builder(EjerciciosActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Usuario nuevo");
                    builder.setMessage("No tienes recorridos que mostrar.");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                    return;
                }
                cambiarAMisCarreras(v);
            }
        });

        mis_pasosdiarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarAMisPasosDiarios(v);
            }
        });

        mis_cronometro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarAMiCronometro(v);
            }
        });


        mis_ejercicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarAMisEjercicios(v);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        fStore = FirebaseFirestore.getInstance();
        if(mAuth.getCurrentUser() == null){
            FirebaseAuth.getInstance().signOut();
            FirebaseFirestore.getInstance().terminate();
            Intent i = new Intent(EjerciciosActivity.this, LoginActivityMichel.class);
            startActivity(i);
            finish();
            return;
        }

        userId = mAuth.getCurrentUser().getUid();

        actualizarRecorridos();

    }

    public void actualizarRecorridos() {
        DocumentReference docRef = fStore.collection("recorridos").document(userId);
        Log.wtf("UserID: ", userId+ "");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.getData() == null || document.getData().size() <= 0){
                        nuevoUsarioRecorridos = true;
                        return;
                    }
                    if(document != null){
                        //Map<String,Object> lista_recorridos = document.getData();
                        for(int i=0;i<document.getData().size();i++){
                            String index = String.valueOf(i);
                            Recorrido recorridousuario = new Recorrido();

                            ArrayList<Object> lista_recorridos = (ArrayList) document.getData().get(index);
                            Log.wtf("Tamaño lista X", lista_recorridos.size()+"");

                            int indice=0;
                            recorridousuario.setId_recorrido(Integer.parseInt(index));
                            for (Object item: lista_recorridos) {
                                Log.wtf("Objetos lista X", lista_recorridos.toString()+"");
                                if(indice == 0){
                                    recorridousuario.setTiempoInicioTotal(item.toString());
                                }else if(indice == 1){
                                    recorridousuario.setTiempoFinalTotal(item.toString());
                                }else if(indice == 2){
                                    recorridousuario.setDistancia(item.toString());
                                }else if(indice == 3){
                                    recorridousuario.setPasos(item.toString());
                                }else if(indice == 4){
                                    recorridousuario.setTiempo_carrera(item.toString());
                                }
                                indice++;

                                Log.wtf("Recorrido del usuario", "\n Inicio total: "
                                        + recorridousuario.getTiempoInicioTotal() + "\n"
                                        + recorridousuario.getTiempoFinalTotal() + "\n"
                                        + recorridousuario.getDistancia() + "\n"
                                        + recorridousuario.getPasos() + "\n"
                                        + recorridousuario.getTiempo_carrera());
                            }

                            // Agregar HT
                            EjerciciosActivity.HT_Recorridos.put(index, recorridousuario);
                        }

                        for (Map.Entry<String,Recorrido> entry : EjerciciosActivity.HT_Recorridos.entrySet()){
                            Log.wtf("Lista de recorridos despues: ","Key = " + entry.getKey() + ", Value = " + entry.getValue());
                        }
                        nuevoUsarioRecorridos = false;
                        //Log.wtf("Size hashtable despues de dos for", EjerciciosActivity.HT_Recorridos.size()+"");
                        //Recorrido a = EjerciciosActivity.HT_Recorridos.get("0");

                        /*
                        Log.wtf("Objeto A",a.getDistancia());
                        Log.wtf("Objeto A",a.getPasos());
                        Log.wtf("Objeto A",a.getTiempo_carrera());
                        Log.wtf("Objeto A",a.getTiempoFinalTotal());
                        Log.wtf("Objeto A",a.getId_recorrido()+"");

                        Recorrido b = EjerciciosActivity.HT_Recorridos.get("1");

                        Log.wtf("Objeto B",b.getDistancia());
                        Log.wtf("Objeto B",b.getPasos());
                        Log.wtf("Objeto B",b.getTiempo_carrera());
                        Log.wtf("Objeto B",b.getTiempoFinalTotal());
                        Log.wtf("Objeto B",b.getId_recorrido()+"");

                        Recorrido c = EjerciciosActivity.HT_Recorridos.get("2");

                        Log.wtf("Objeto C",c.getDistancia());
                        Log.wtf("Objeto C",c.getPasos());
                        Log.wtf("Objeto C",c.getTiempo_carrera());
                        Log.wtf("Objeto C",c.getTiempoFinalTotal());
                        Log.wtf("Objeto C",c.getId_recorrido()+"");

                         */

                    }else{
                        Log.wtf("Lista de recorridos: ", "Está vacía");
                    }
                }else{
                    Log.wtf("Error con firebase: ", task.getException());
                }
            }
        });
    }
    private void cambiarAMisEjercicios(View v) {
        Intent misejercicios = new Intent(this, Rene_Ejercicios.class);
        startActivity(misejercicios);
    }

    private void cambiarAMiCronometro(View v) {
        Intent newRace = new Intent(this, Rene_MapsActivity.class);
        startActivityForResult(newRace, NEWRACE_CODE);
    }


    private void cambiarAMisPasosDiarios(View v) {
        Intent dailysteps = new Intent(this, DailyStepsActivity.class);
        startActivityForResult(dailysteps, DAILYSTEPS_CODE);
    }

    private void cambiarAMisCarreras(View v) {
        Intent i = new Intent(this, RecyclerActivityRecorridos.class);
        startActivity(i);
    }


    public void cambiarAMiPerfil(View v){
        Intent i = new Intent(this, ProfileInfoActivity.class);
        startActivity(i);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == Activity.RESULT_OK && data != null){

            int x = Integer.parseInt(data.getStringExtra("meta")) / 2;

            if(Integer.parseInt(data.getStringExtra("pasos")) < x){
                Toast.makeText(this,"No has llegado a la mitad", Toast.LENGTH_SHORT).show();
            }else if(Integer.parseInt(data.getStringExtra("pasos")) < Integer.parseInt(data.getStringExtra("meta"))){
                int p = Integer.parseInt(data.getStringExtra("meta")) - Integer.parseInt(data.getStringExtra("pasos"));
                Toast.makeText(this, "Ya solo te faltan " + String.valueOf(p) + " pasos para tu meta de hoy", Toast.LENGTH_SHORT).show();
            }else if(Integer.parseInt(data.getStringExtra("pasos")) == Integer.parseInt(data.getStringExtra("meta"))){
                Toast.makeText(this, "FELICIDADES, LO HAS LOGRADO", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "No se que hicicste", Toast.LENGTH_LONG).show();
            }
        }
    }
}