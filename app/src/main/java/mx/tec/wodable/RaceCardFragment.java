package mx.tec.wodable;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RaceCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RaceCardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "distance";
    private static final String ARG_PARAM2 = "date";
    private static final String ARG_PARAM3 = "time";

    // TODO: Rename and change types of parameters
    private String distance;
    private String date;
    private String time;

    public RaceCardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param distance is given in meters, so divide over 1000 & stringify with 2 decimals
     * @param date must reformat dd/mm/yy
     * @param time expects a string --:--
     * @return A new instance of fragment RaceCardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RaceCardFragment newInstance(String distance, String date, String time) {
        RaceCardFragment fragment = new RaceCardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, distance);
        args.putString(ARG_PARAM2, date);
        args.putString(ARG_PARAM3, time);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            distance = getArguments().getString(ARG_PARAM1);
            date = getArguments().getString(ARG_PARAM2);
            time = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_race_card,container,false);

        TextView showDistance = v.findViewById(R.id.raceCardDistance);
        TextView showDate = v.findViewById(R.id.raceCardDate);
        TextView showTime = v.findViewById(R.id.raceCardTime);

        showDistance.setText(distance);
        showDate.setText(date);
        showTime.setText(time);

        return v;
    }
}