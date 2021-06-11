package com.example.thanhlc.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.thanhlc.MainActivity;
import com.example.thanhlc.MainActivitycapnhat;
import com.example.thanhlc.MainActivityqlsp;
import com.example.thanhlc.R;
import com.example.thanhlc.doimatkhau;
import com.example.thanhlc.userdata;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link thontinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class thontinFragment extends Fragment {
    TextView btnBack,qltk,hotro;
    String userId;
    TextView user,qlsp,hoten;
    TextView txtsdt,doimk;
    ImageView profileImage;
    StorageReference storageReference;
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
        hotro = (TextView)view.findViewById(R.id.hotro);
        doimk=(TextView) view.findViewById(R.id.txtdoimk);
        profileImage = (ImageView) view.findViewById(R.id.profileImage);
        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://thanh-l-c.appspot.com");

//        userId = fAuth.getUid();
        // userFB = fAuth.getCurrentUser();
        StorageReference profileRef = storageReference.child("users/"+MainActivity.sdt);//Tạo một thư mục để mỗi người có thể tải ảnh của mình lên và không thể truy cập vào thư mục của người khác
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(getContext()).load(uri).into(profileImage);
            }//hiển thị hình ảnh từ một nguồn bên ngoài
        });

        hotro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotro .setText("https://www.facebook.com/profile.php?id=100006421546739");
                Linkify.addLinks(hotro , Linkify.WEB_URLS);
            }
        });
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