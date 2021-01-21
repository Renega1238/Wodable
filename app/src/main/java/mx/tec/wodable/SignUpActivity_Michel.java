package mx.tec.wodable;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpActivity_Michel extends AppCompatActivity {

    TextView nuevoUsuario, bienvenidoLabel, continuarLabel;
    ImageView signUpImageView;
    TextInputLayout usuarioSignUpTextField, contrasenaTextField, nameTextField;
    MaterialButton inicioSesion;
    String userID;


    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;

    TextInputEditText emailEditText, passwordEditText, confirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_michel);

        signUpImageView = findViewById(R.id.signUpImageView);
        bienvenidoLabel = findViewById(R.id.bienvenidoLabel);
        continuarLabel = findViewById(R.id.continuarLabel);
        usuarioSignUpTextField = findViewById(R.id.usuarioSignUpTextField);
        contrasenaTextField = findViewById(R.id.contrasenaTextField);
        inicioSesion = findViewById(R.id.inicioSesion);
        nuevoUsuario = findViewById(R.id.nuevoUsuario);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        nameTextField = findViewById(R.id.nameTextField);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        nuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transitionBack();
            }
        });

            inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });



    }

    public void validate(){
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String nombre = nameTextField.getEditText().getText().toString();

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Correo inválido");
            Toast.makeText(SignUpActivity_Michel.this, "Correo inválido", Toast.LENGTH_SHORT).show();

            return;
        }else{
            emailEditText.setError(null);
        }

        if(password.isEmpty() || password.length() < 6){
            passwordEditText.setError("Se necesitan más de 6 carácteres");
            Toast.makeText(SignUpActivity_Michel.this, "Se necesitan más de 6 carácteres de contraseña", Toast.LENGTH_SHORT).show();
        }else if(!Pattern.compile("[0-9]").matcher(password).find()){
            passwordEditText.setError("La contraseña necesita al menos un número");
            Toast.makeText(SignUpActivity_Michel.this, "La contraseña necesita al menos un número", Toast.LENGTH_SHORT).show();
            return;
        }else{
            passwordEditText.setError(null);
        }

        if(!confirmPassword.equals(password)){
            confirmPasswordEditText.setError("Deben ser iguales");
            Toast.makeText(SignUpActivity_Michel.this, "Las contraseñas deben ser iguales", Toast.LENGTH_SHORT).show();
            return;
        }else{
            registrar(email, password,nombre);
        }
    }


    public void registrar(String email, String password, String nombre){
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignUpActivity_Michel.this, "Registro completo", Toast.LENGTH_LONG).show();
                                userID = mAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore
                                        .collection("usuarios").document(userID);

                                Map<String, Object> user = new HashMap<>();
                                user.put("nombre_completo", nombre);
                                user.put("correo", email);
                                user.put("password", password);

                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                                    Log.wtf("Usuario creado - ", "onSuccess: user Profile is created for " + user);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("Error usuario:", "onFailure: " + e.toString());
                                    }
                                });

                                Intent intent = new Intent(SignUpActivity_Michel.this, UserActivity_Michel.class);
                                startActivity(intent);
                                finish();
                            }else{

                                Toast.makeText(SignUpActivity_Michel.this, "Fallo en registrarse", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
    }


    @Override
    public void onBackPressed(){
        transitionBack();
    }

    public void transitionBack(){

        // La clase en la que estamos, y la clase a la que queremos ir
        Intent intent = new Intent(SignUpActivity_Michel.this, LoginActivityMichel.class);

        // Arreglo de animaciones
        Pair[] pairs = new Pair[7];

        pairs[0] = new Pair<View, String>(signUpImageView, "LogoImageTrans");
        pairs[1] = new Pair<View, String>(bienvenidoLabel, "textTrans");
        pairs[2] = new Pair<View, String>(continuarLabel, "iniciaSesionTextTrans");
        pairs[3] = new Pair<View, String>(usuarioSignUpTextField, "emailInputTextTrans");
        pairs[4] = new Pair<View, String>(contrasenaTextField, "passwordInputTextTrans");
        pairs[5] = new Pair<View, String>(inicioSesion, "buttonSignInTrans");
        pairs[6] = new Pair<View, String>(nuevoUsuario, "newUserTrans");

            // Hace las animaciones si la versión es mayor a lollipop o igual
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUpActivity_Michel.this, pairs);
            startActivity(intent, options.toBundle());
        }else{
            startActivity(intent);
            finish();
        }




    }
}