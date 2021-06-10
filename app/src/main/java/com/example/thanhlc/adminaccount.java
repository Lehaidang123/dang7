package com.example.thanhlc;

import android.os.Build;
import android.os.Bundle;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adminaccount extends AppCompatActivity {
GridView gridView;
    ArrayList<Helperclass> listHinhAnh = new ArrayList<>();
    accountadapter adapter;
    DatabaseReference Mdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminaccount);
        gridView = findViewById(R.id.adminxoa);
        DatafromFirebase();
        adapter = new accountadapter(this,listHinhAnh);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void DatafromFirebase(){
        listHinhAnh = new ArrayList<>();
        Mdata= FirebaseDatabase.getInstance().getReference().child("account");
        Mdata.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    //  Log.d("abc", "onDataChange: vao day");
                    String key = ds.getKey();

                    //

                    String user = ds.child("usernamae").getValue(String.class);
                    String ten = ds.child("ten").getValue(String.class);
                    String sdt = ds.child("sdt").getValue(String.class);
                    String pass = ds.child("pass").getValue(String.class);
                    String loaitk = ds.child("loaitk").getValue(String.class);

                        if(loaitk.equals("Khách Hàng")) {

                            Helperclass ha = new Helperclass(user, pass, sdt, ten, loaitk);
                            listHinhAnh.add(ha);
                        }


                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}