package com.example.thanhlc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.thanhlc.MainActivity;
import com.example.thanhlc.MainActivitycapnhat;
import com.example.thanhlc.MainActivityqlsp;
import com.example.thanhlc.R;
import com.example.thanhlc.doimatkhau;
import com.example.thanhlc.userdata;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link thontinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class thontinFragment extends Fragment {
    TextView btnBack,qltk;
    String userId;
    TextView user,qlsp,hoten;
    TextView txtsdt,doimk;
    public  static String u;
    public  static String y;

userdata userdata;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public thontinFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment thontinFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static thontinFragment newInstance(String param1, String param2) {
        thontinFragment fragment = new thontinFragment();
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
        View view = inflater.inflate(R.layout.fragment_thontin, container, false);
        btnBack = (TextView) view.findViewById(R.id.btnDangXuat);
        user =(TextView) view.findViewById(R.id.txtuser);
        qlsp=(TextView) view.findViewById(R.id.txtQLsp);
        hoten=(TextView)view.findViewById(R.id.txthoten);
        qltk=(TextView)view.findViewById(R.id.txtQLTK);
        doimk=(TextView) view.findViewById(R.id.txtdoimk);
        doimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(), doimatkhau.class);
                startActivity(intent);
            }
        });
        qltk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(), MainActivitycapnhat.class);
                startActivity(intent);
            }
        });
        qlsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(), MainActivityqlsp.class);
                startActivity(intent);
            }
        });
        txtsdt=(TextView)view.findViewById(R.id.txtsodienthoai);
      //
        backMain();
        // Inflate the layout for this fragment
      
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(getContext().getApplicationContext(), MainActivity.class);
                startActivity(intent);


            }
        });
fra();
        return view;
    }

    public void backMain() {

        txtsdt.setText(MainActivity.sdt
        );
        hoten.setText(MainActivity.ten);
        u = MainActivity.username;
    }
    public void fra()
    {
        if(MainActivity.loaitk.equals("admin"))
        {
            admin t= new admin();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.conterner,t);
            fragmentTransaction.commit();
        }
        else {
            if (MainActivity.loaitk.equals(""))
            {
                thontinFragment p = new thontinFragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.conterner, p);
            fragmentTransaction.commit();
        }
        }
    }
}