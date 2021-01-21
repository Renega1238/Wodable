package mx.tec.wodable;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RouteMapFragment extends Fragment {

    EditText source, destination;
    Button set;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RouteMapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RouteMapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RouteMapFragment newInstance(String param1, String param2) {
        RouteMapFragment fragment = new RouteMapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View reference
        View v = inflater.inflate(R.layout.fragment_route_map, container,false);

        source = v.findViewById(R.id.RouteMapSource);
        destination = v.findViewById(R.id.RouteMapDestination);

        set = v.findViewById(R.id.RouteMapTrack);

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenemos los valores del edittext
                String sSource = source.getText().toString().trim();
                String dDestination = destination.getText().toString().trim();

                // Vemos que no esta vacio
                if(sSource.equals("") && dDestination.equals("")){
                    Toast.makeText(getContext(), "Ingrese la ruta", Toast.LENGTH_SHORT).show();

                }else{
                    DisplayTrack(sSource, dDestination);
                }
            }
        });
        return v;
    }

    public void DisplayTrack(String sSource, String dDestination){

        // Verificamos que tenga MAPS
        try{
            // Si google maps existe
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + sSource + "/" + dDestination);

            // Hacemos un intent, es como una actividad pero con el mapa  o pagina que escogimos
            Intent maps = new Intent(Intent.ACTION_VIEW, uri);

            maps.setPackage("com.google.android.apps.maps");

            // Flag
            maps.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // empezamos
            startActivity(maps);
        }catch (ActivityNotFoundException e){
            // Cuando no hay mapas
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");

            // intetn con esto
            Intent fail = new Intent(Intent.ACTION_VIEW, uri);

            //flag
            fail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(fail);
        }
    }
}