package com.example.thanhlc.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.example.thanhlc.Danhmuc;
import com.example.thanhlc.MainActivitytimkiem;
import com.example.thanhlc.MainActivityxeco;
import com.example.thanhlc.R;
import com.example.thanhlc.adapterdanhmuc;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;


public class danhmucFragment extends Fragment {
    FirebaseStorage storage = FirebaseStorage.getInstance();
    DatabaseReference Mdata;
    TextView txtseach,testnoithat;
    GridView tt;
    EditText editText;
    adapterdanhmuc adapterdanhmuc;
    ArrayList<Danhmuc> danhmucs;
    String tenDM;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Mdata= FirebaseDatabase.getInstance().getReference();

        View view = inflater.inflate(R.layout.fragment_danhmuc,container,false);
        txtseach =(TextView)view.findViewById(R.id.txtSearch);
        tt =(GridView)view.findViewById(R.id.grid_danhmuc);
        DatafromFirebase();
            adapterdanhmuc=new adapterdanhmuc(getContext(),R.layout.dongdanhmuc,danhmucs);
            tt.setAdapter(adapterdanhmuc);
       tt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(getActivity(),MainActivityxeco.class);
               intent.putExtra("tendm",danhmucs.get(position).getTen());
               startActivity(intent);
           }
       });
        txtseach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(), MainActivitytimkiem.class);
                startActivity(intent);
            }
        });
        return view;
    }
    private void DatafromFirebase(){
        danhmucs = new ArrayList<>();
        Mdata= FirebaseDatabase.getInstance().getReference().child("danhmuc");
        Mdata.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    Log.d("abc", "onDataChange: vao day");
                    String ten = ds.child("tendm").getValue(String.class);
                    String hinh = ds.child("hinh").getValue(String.class);
                    Danhmuc ha = new Danhmuc(ten,hinh);
                    danhmucs.add(ha);
                }
                adapterdanhmuc.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}