package mx.tec.wodable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mx.tec.wodable.Recorrido;

public class RecyclerActivityRecorridos extends AppCompatActivity implements View.OnClickListener {
    /*
     *  Variables bases de datos
     */
    private String userId;
    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;

    private Hashtable<String, Recorrido> HT_Recorridos;
    private RecyclerView recyclerViewRecorridos;
    private RecorridosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_recorridos);
        recyclerViewRecorridos = findViewById(R.id.recycler_recorridos);
        mAuth = FirebaseAuth.getInstance();
        HT_Recorridos = new Hashtable<>();

    }

    @Override
    protected void onStart() {
        super.onStart();
        fStore = FirebaseFirestore.getInstance();
        if(mAuth.getCurrentUser() == null){
            FirebaseAuth.getInstance().signOut();
            FirebaseFirestore.getInstance().terminate();
            Intent i = new Intent(RecyclerActivityRecorridos.this, LoginActivityMichel.class);
            startActivity(i);
            finish();
            return;
        }

        userId = mAuth.getCurrentUser().getUid();
        actualizarRecorridos();
        /*this.adapter = new RecorridosAdapter(HT_Recorridos, this);
        Log.wtf("onCreate", HT_Recorridos.size()+ "");
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewRecorridos.setLayoutManager(llm);
        recyclerViewRecorridos.setAdapter(this.adapter);*/
    }


    public void actualizarRecorridos() {
        DocumentReference docRef = fStore.collection("recorridos").document(userId);
        Log.wtf("UserID: ", userId+ "");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document != null){
                        //Map<String,Object> lista_recorridos = document.getData();



                    for(int i=0;i<document.getData().size();i++){

                        String index = String.valueOf(i);
                        Recorrido recorridousuario = new Recorrido();

                        ArrayList<Object> lista_recorridos = (ArrayList) document.getData().get(index);
                        Log.wtf("Tamaño lista X", lista_recorridos.size()+"");

                        for (Object item: lista_recorridos) {
                            int indice=0;
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
                            recorridousuario.setId_recorrido(indice);


                            Log.wtf("Recorrido del usuario", "\n Inicio total: "
                                    + recorridousuario.getTiempoInicioTotal() + "\n"
                                    + recorridousuario.getTiempoFinalTotal() + "\n"
                                    + recorridousuario.getDistancia() + "\n"
                                    + recorridousuario.getPasos() + "\n"
                                    + recorridousuario.getTiempo_carrera());
                        }

                        // Agregar HT
                        HT_Recorridos.put(index, recorridousuario);
                    }

                        for (Map.Entry<String,Recorrido> entry : HT_Recorridos.entrySet()){
                            Log.wtf("Lista de recorridos despues: ","Key = " + entry.getKey() + ", Value = " + entry.getValue());
                        }



                    /*
                        HashMap<String, String> lista_recorridos = new HashMap<String, String>();

                        Log.wtf("Objecto", x.toString());
                        for(int i=0;i<document.getData().size();i++){
                            String indice = String.valueOf(i);

                            document.getData().get(indice);


                            for (Map.Entry<String,String> entry : lista_recorridos.entrySet()){
                                Log.wtf("Lista de recorridos","Key = " + entry.getKey() + ", Value = " + entry.getValue());
                            }
                        }*/
                        /*for (Map.Entry<String,Object> entry : lista_recorridos.entrySet()){
                            Log.wtf("Lista de recorridos","Key = " + entry.getKey() + ", Value = " + entry.getValue());
                        }*/

                        /*for(Object key: lista_recorridos.keySet()){
                            for(Object value: lista_recorridos.get(key)){

                            }
                        }*/

                        //Iterator<Map.Entry<String, Object>> itr = lista_recorridos.entrySet().iterator();

                        //List<Object> lista = new ArrayList<>();

                        /*while(itr.hasNext())
                        {
                            Map.Entry<String, Object> entry = itr.next();
                            Log.wtf("Lista de recorridos con iterator: ","Key = " + entry.getKey() +
                                    ", Value = " + entry.getValue());

                            lista.add(entry.getValue());
                        }*/





                    }else{
                        Log.wtf("Lista de recorridos: ", "Está vacía");
                    }
                }else{
                    Log.wtf("Error con firebase: ", task.getException());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Log.wtf("Tamano onclick", HT_Recorridos.size()+"");
        int position = recyclerViewRecorridos.getChildLayoutPosition(v);

        /*String pos = String.valueOf((position+1));
        String nombre = HT_Recorridos.get(pos).getNombre();
        String hobby = HT_Recorridos.get(pos).getHobby();
        String edad = HT_Recorridos.get(pos).getEdad();
        String telefono = HT_Recorridos.get(pos).getTelefono();
        String direccion = HT_Recorridos.get(pos).getDireccion();*/
    }
}