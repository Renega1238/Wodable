package mx.tec.wodable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileInfoActivity extends AppCompatActivity {

    private static final String ARCHIVO_PREFS = "prefs";
    private static final String NOMBRE_KEY = "usernombre";
    private static final String APELLIDO_KEY = "userapellido";
    private SharedPreferences prefs;

    TextView Saludo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);

        Saludo = findViewById(R.id.textView2);

        prefs = getSharedPreferences(ARCHIVO_PREFS, MODE_PRIVATE);

        Saludo.setText(prefs.getString(NOMBRE_KEY, " ") + " " + prefs.getString(APELLIDO_KEY, " "));

    }
}