package mx.tec.wodable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Pattern;


public class LoginActivityMichel extends AppCompatActivity {

    TextView bienvenidoLabel, continuarLabel, nuevoUsuario, olvidasteContrasena;
    ImageView loginImageView;
    TextInputLayout usuarioTextField, contrasenaTextField;
    MaterialButton inicioSesion;
    TextInputEditText emailEditText, passwordEditText;

    private FirebaseAuth mAuth;

    // Google
   // SignInButton signInButton;
    //GoogleSignInClient mGoogleSignInClient;
    //public static final int RC_SIGN_IN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_michel);

        loginImageView = findViewById(R.id.loginImageView);
        bienvenidoLabel = findViewById(R.id.bienvenidoLabel);
        continuarLabel = findViewById(R.id.continuarLabel);
        usuarioTextField = findViewById(R.id.usuarioTextField);
        contrasenaTextField = findViewById(R.id.contrasenaTextField);
        inicioSesion = findViewById(R.id.inicioSesion);
        nuevoUsuario = findViewById(R.id.nuevoUsuario);

        olvidasteContrasena = findViewById(R.id.olvidasteContra);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        mAuth = FirebaseAuth.getInstance();

        nuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // La clase en la que estamos, y la clase a la que queremos ir
               Intent intent = new Intent(LoginActivityMichel.this, SignUpActivity_Michel.class);
                startActivity(intent);



                // Arreglo de animaciones
                Pair[] pairs = new Pair[7];

                pairs[0] = new Pair<View, String>(loginImageView, "LogoImageTrans");
                pairs[1] = new Pair<View, String>(bienvenidoLabel, "textTrans");
                pairs[2] = new Pair<View, String>(continuarLabel, "iniciaSesionTextTrans");
                pairs[3] = new Pair<View, String>(usuarioTextField, "emailInputTextTrans");
                pairs[4] = new Pair<View, String>(contrasenaTextField, "passwordInputTextTrans");
                pairs[5] = new Pair<View, String>(inicioSesion, "buttonSignInTrans");
                pairs[6] = new Pair<View, String>(nuevoUsuario, "newUserTrans");

                // Hace las animaciones si la versión es mayor a lollipop o igual
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivityMichel.this, pairs);
                    startActivity(intent, options.toBundle());
                }else{
                    startActivity(intent);
                    finish();
                }

            }
        });


        olvidasteContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivityMichel.this, ForgotPasswordMichel.class);

                startActivity(intent);
                finish();


            }
        });

        inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });


        // Google Sign-In
        //signInButton = findViewById(R.id.loginGoogle);

        /*signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
            }
        });*/

        /*GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);*/

    }


    /*private void signInWithGoogle(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        
        super.onActivityResult(requestCode, resultCode, data);

        /*if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            }catch(ApiException e){
                Log.e("Fallo por?",""+ e);
                Toast.makeText(LoginActivityMichel.this, "Fallo Google", Toast.LENGTH_SHORT).show();
            }
        }*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        return;
    }

/* private void firebaseAuthWithGoogle(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(LoginActivityMichel.this, UserActivity_Michel.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(LoginActivityMichel.this, "Fallo en iniciar sesión", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
    }*/



    public void validate(){
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();


        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Correo inválido");
            Toast.makeText(LoginActivityMichel.this, "Correo inválido", Toast.LENGTH_SHORT).show();

            return;
        }else{
            emailEditText.setError(null);
        }

        if(password.isEmpty() || password.length() < 6){
            passwordEditText.setError("Se necesitan más de 6 carácteres");
            Toast.makeText(LoginActivityMichel.this, "Se necesitan más de 6 carácteres de contraseña", Toast.LENGTH_SHORT).show();
            return;

        }else if(!Pattern.compile("[0-9]").matcher(password).find()){
            passwordEditText.setError("La contraseña necesita al menos un número");
            Toast.makeText(LoginActivityMichel.this, "La contraseña necesita al menos un número", Toast.LENGTH_SHORT).show();

            return;
        }else{
            passwordEditText.setError(null);
        }
        iniciarSesion(email, password);
    }

    public void iniciarSesion(String email, String password){

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivityMichel.this, "¡Has iniciado sesión correctamente!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivityMichel.this, UserActivity_Michel.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(LoginActivityMichel.this, "Credenciales equivocadas, intenta de nuevo.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}