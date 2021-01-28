package mx.tec.wodable;

import android.content.Context;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProgressBarAnimation extends Animation {

    private Context context;
    private ProgressBar progressBar;
    private TextView textView;
    private float from;
    private float to;

    public ProgressBarAnimation(Context context, ProgressBar progressBar, TextView textView, float from, float to){

        this.context = context;
        this.progressBar = progressBar;
        this.textView = textView;
        this.from = from;
        this.to = to;

    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        float valor = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) valor);
        textView.setText((int)valor + "% ");

        if(valor == to){

                Intent main = new Intent(context, LoginActivityMichel.class);
                context.startActivity(main);

            // Esto es para las sesiones, si un usuario ya está conectado regresa a
            // la pantall de User Activity en vez de Login Activity
           // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
            //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(MainActivity.this);

            /*if(user != null && account != null){
                Intent intent = new Intent(context, UserActivity_Michel.class);
                context.startActivity(intent);
            }else{
                Intent intent = new Intent(context, LoginActivityMichel.class);
                context.startActivity(intent);
            }*/

        }





    }
}
