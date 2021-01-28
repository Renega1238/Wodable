package mx.tec.wodable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class UserActivity_Michel extends AppCompatActivity {

    private static final int DAILYSTEPS_CODE = 2;
    private static final int NEWRACE_CODE = 3;

    // Main Rene
    Button user, myprofile, dailysteps;
    TextView saludo;
    ImageButton newRace;
    // Main Michel
    TextView emailTextView, nombreTextView;
    MaterialButton logoutButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    @Override
    public void onBackPressed(){
        FirebaseAuth.getInstance().signOut();
        FirebaseFirestore.getInstance().terminate();
        Toast.makeText(UserActivity_Michel.this, "¡Has cerrado sesión exitosamente!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UserActivity_Michel.this, LoginActivityMichel.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_michel);
        nombreTextView = findViewById(R.id.nombreTextView);
        emailTextView = findViewById(R.id.emailTextView);
        logoutButton = findViewById(R.id.logoutButton);
        // atr rene
        myprofile = findViewById(R.id.myprofilebutton);
        dailysteps = findViewById(R.id.ActivityDailySteps);
        newRace = findViewById(R.id.MainActivityNewRace);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
        DocumentReference documentReference = fStore.collection("usuarios").document(userId);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                String nombre = documentSnapshot.getString("nombre_completo");
                String correo = documentSnapshot.getString("correo");

                nombreTextView.setText(nombre);
                emailTextView.setText(correo);
            }
        });
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                FirebaseFirestore.getInstance().terminate();
                Toast.makeText(UserActivity_Michel.this, "¡Has cerrado sesión exitosamente!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(UserActivity_Michel.this, LoginActivityMichel.class);
                startActivity(intent);
                finish();
            }
        });

    }
    // Botones Rene
    // Image botton
    public void beginRace(View v){

        Intent newRace = new Intent(this, RaceActivity.class);

        startActivityForResult(newRace, NEWRACE_CODE);

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

        if(requestCode == 2 && resultCode == Activity.RESULT_OK && data != null){

            int x = Integer.parseInt(data.getStringExtra("meta")) / 2;

            if(Integer.parseInt(data.getStringExtra("pasos")) < x){
                Toast.makeText(this,"No has llegado a la mitad", Toast.LENGTH_SHORT).show();
            }else if(Integer.parseInt(data.getStringExtra("pasos")) < Integer.parseInt(data.getStringExtra("meta"))){
                int p = Integer.parseInt(data.getStringExtra("meta")) - Integer.parseInt(data.getStringExtra("pasos"));
                Toast.makeText(this, "Felicidades ya solo te faltan " + String.valueOf(p) + " pasos para tu meta de hoy", Toast.LENGTH_SHORT).show();
            }else if(Integer.parseInt(data.getStringExtra("pasos")) == Integer.parseInt(data.getStringExtra("meta"))){
                Toast.makeText(this, "FELICIDADES, LO HAS LOGRADO", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "No se que hicicste", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void dailySteps(View v){

        Intent dailysteps = new Intent(this, DailyStepsActivity.class);
        startActivityForResult(dailysteps, DAILYSTEPS_CODE);
    }
}