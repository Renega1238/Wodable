package mx.tec.wodable;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentA#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentA extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // NOTE: Vars to work with Strings
    ListView lv;
    SearchView sv;
    ArrayAdapter<String> adapter;
    String[] data = {"R1","R2","R3"};

    // NOTE: Vars to work with Fragments
    ArrayList<RaceCardFragment> myRaceCards = new ArrayList<RaceCardFragment>();
    int racesFound = 3;



    public FragmentA() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentA.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentA newInstance(String param1, String param2) {
        FragmentA fragment = new FragmentA();
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

        View v = inflater.inflate(R.layout.fragment_a,container,false);
        lv = (ListView) v.findViewById(R.id.fragmentList);
        // NOTE: WORKING WITH STRINGS ONLY
            // INSERT INTO DATA ARRAY
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,data);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),"Selected Race: "+i,Toast.LENGTH_SHORT).show();
            }
        });
        lv.setAdapter(adapter);

        /*
        //NOTE: Testing with RaceCardFragment
        Context ctx = getActivity();
        for (int i = 0;i<racesFound;i++){
            RaceCardFragment newCard = RaceCardFragment.newInstance("5.5","11/01/2021","30:30");
            myRaceCards.add(newCard);
        }
        */
        // Inflate the layout for this fragment
        return v;

    }
}