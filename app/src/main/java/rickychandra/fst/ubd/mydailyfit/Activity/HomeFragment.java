package rickychandra.fst.ubd.mydailyfit.Activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import rickychandra.fst.ubd.mydailyfit.R;


public class HomeFragment extends Fragment {
    TextView txtCategory;
    TextView txtCalories;


    String kategori = "", kalori = "";

    public HomeFragment() {

    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        txtCategory = (TextView) view.findViewById(R.id.textKategori);
        txtCalories = (TextView) view.findViewById(R.id.textKalori);

        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            kategori = extras.getString("KATEGORI_TUBUH");
            kalori = extras.getString("KEBUTUHAN_KALORI");
        }

        if (kategori.equals("Kurus")) {
            kategori = getResources().getString(R.string.kurus);
        } else if (kategori.equals("Gemuk")) {
            kategori = getResources().getString(R.string.gemuk);
        } else {
            kategori = getResources().getString(R.string.normal);
        }

        txtCategory.setText(kategori);
        txtCalories.setText(kalori);

        return view;
    }
}
