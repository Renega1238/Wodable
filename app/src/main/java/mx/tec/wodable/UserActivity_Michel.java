package mx.tec.wodable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
}