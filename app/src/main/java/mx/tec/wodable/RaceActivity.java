package mx.tec.wodable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class RaceActivity extends AppCompatActivity {

    // Keys
    private static final String TAG_FRAGMENTO = "fragmento";

    // Elementos
    Button justrun, route;
    JustRunFragment justRunFragment;
    RouteMapFragment routeMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race);

        // Id's
        justrun = findViewById(R.id.RaceJustRun);
        route = findViewById(R.id.RaceRoute);

        // Fragment
        justRunFragment = new JustRunFragment();
        routeMapFragment = new RouteMapFragment();

    }


    // Para cambiar Fragment
    public void cambiarFragmento(Fragment nuevo){

        //Como obtener referencia a un fragmento ya agregado
        FragmentManager manager = getSupportFragmentManager();
        //Siermpre el fragment android x
        Fragment f = manager.findFragmentByTag(TAG_FRAGMENTO);
        FragmentTransaction transaction = manager.beginTransaction();

        // Si al fragmento es el que ya esta, pues que no haga nada
        if (nuevo == f){
            return;
        }

        // Si no es null agregamos
        if(f != null){
            transaction.remove(f);
        }

        //justrun.setVisibility(View.GONE);
        //route.setVisibility(View.GONE);
        transaction.add(R.id.JustRunFragment, nuevo, TAG_FRAGMENTO);
        transaction.commit();
    }

    public void JustRun(View v){

        cambiarFragmento(justRunFragment);

    }

    public void RouteMap(View v){

        cambiarFragmento(routeMapFragment);
    }
}