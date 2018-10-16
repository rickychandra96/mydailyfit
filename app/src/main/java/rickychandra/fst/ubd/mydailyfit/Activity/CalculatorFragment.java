package rickychandra.fst.ubd.mydailyfit.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import rickychandra.fst.ubd.mydailyfit.CalorieNeeds;
import rickychandra.fst.ubd.mydailyfit.R;

public class CalculatorFragment extends Fragment {
    TextView editBerat;
    TextView editTinggi;
    TextView editUmur;
    ImageView imgL;
    ImageView imgP;
    CheckBox chkL;
    CheckBox chkP;
    CheckBox chkSangatRingan;
    CheckBox chkRingan;
    CheckBox chkSedang;
    CheckBox chkBerat;
    ImageView btnKalkulator;

    private int umur=0,berat=0,tinggi=0;
    private char gender=0;
    private String aktivitas="";
    private String kategoriTubuh="";
    private String kebutuhanKalori="";


    public CalculatorFragment() {

    }

    public static CalculatorFragment newInstance() {
        CalculatorFragment fragment = new CalculatorFragment();
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
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);

        editBerat = (EditText) view.findViewById(R.id.editWeight);
        editTinggi = (EditText) view.findViewById(R.id.editHeight);
        editUmur = (EditText) view.findViewById(R.id.editAge);
        imgL = (ImageView) view.findViewById(R.id.imgL);
        imgP = (ImageView) view.findViewById(R.id.imgP);
        chkSangatRingan = (CheckBox) view.findViewById(R.id.chkSangatRingan);
        chkRingan = (CheckBox) view.findViewById(R.id.chkRingan);
        chkSedang = (CheckBox) view.findViewById(R.id.chkSedang);
        chkBerat = (CheckBox) view.findViewById(R.id.chkBerat);
        chkP = (CheckBox) view.findViewById(R.id.chkP);
        chkL = (CheckBox) view.findViewById(R.id.chkL);
        btnKalkulator = (ImageView) view.findViewById(R.id.btnKalkulator);


        imgL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chkL.setVisibility(View.VISIBLE);
                chkP.setVisibility(View.INVISIBLE);
                gender = 'L';
            }
        });

        imgP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chkP.setVisibility(View.VISIBLE);
                chkL.setVisibility(View.INVISIBLE);
                gender = 'P';
            }
        });

        chkSangatRingan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chkSangatRingan.setVisibility(View.VISIBLE);
                chkRingan.setVisibility(View.INVISIBLE);
                chkSedang.setVisibility(View.INVISIBLE);
                chkBerat.setVisibility(View.INVISIBLE);
                aktivitas="sangat ringan";
            }
        });

        chkRingan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chkSangatRingan.setVisibility(View.INVISIBLE);
                chkRingan.setVisibility(View.VISIBLE);
                chkSedang.setVisibility(View.INVISIBLE);
                chkBerat.setVisibility(View.INVISIBLE);
                aktivitas="ringan";
            }
        });

        chkSedang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chkSangatRingan.setVisibility(View.INVISIBLE);
                chkRingan.setVisibility(View.INVISIBLE);
                chkSedang.setVisibility(View.VISIBLE);
                chkBerat.setVisibility(View.INVISIBLE);
                aktivitas="sedang";
            }
        });

        chkBerat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chkSangatRingan.setVisibility(View.INVISIBLE);
                chkRingan.setVisibility(View.INVISIBLE);
                chkSedang.setVisibility(View.INVISIBLE);
                chkBerat.setVisibility(View.VISIBLE);
                aktivitas="berat";
            }
        });

        btnKalkulator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valid()){
                    CalorieNeeds CN = new CalorieNeeds(berat,tinggi,umur,gender,aktivitas);
                    kategoriTubuh=CN.getBodyCategory();
                    kebutuhanKalori=CN.getCalorieNeed()+" KKal";
                }else{
                    //TODO make warning dialog number exception

                    return;
                }


                Intent intent = new Intent(getActivity(), MainActivity.class);
                Bundle extras = new Bundle();
                extras.putString("KATEGORI_TUBUH",kategoriTubuh);
                extras.putString("KEBUTUHAN_KALORI",kebutuhanKalori);
                intent.putExtras(extras);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

    private boolean valid(){
        if (editUmur.getText().toString().equals("")||
                editBerat.getText().toString().equals("")||
                editTinggi.getText().toString().equals("")||
                gender==0||aktivitas.equals("")
                ){
            return false;
        }

        try {
            berat = Integer.parseInt(editBerat.getText().toString());
            tinggi = Integer.parseInt(editTinggi.getText().toString());
            umur = Integer.parseInt(editUmur.getText().toString());
        }catch (NumberFormatException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
