package mx.tec.wodable;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoadingScreen extends AppCompatActivity {

    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar = findViewById(R.id.progress_bar);
        textView = findViewById(R.id.loadingnumber);

        progressBar.setMax(100);
        progressBar.setScaleY(3f);
        progressBarAnimation();



    }

    public void progressBarAnimation(){

        ProgressBarAnimation bar = new ProgressBarAnimation(this, progressBar, textView, 0f, 100f);
        bar.setDuration(5000); // 5 Segundos
        progressBar.setAnimation(bar);
    }
}