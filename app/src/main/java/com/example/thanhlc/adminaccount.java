package com.example.thanhlc;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adminaccount extends AppCompatActivity {
RecyclerView recyclerView;
EditText editText;
    ArrayList<Helperclass> listHinhAnh ;
    seachadmin adapter;
    DatabaseReference Mdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminaccount);
        recyclerView = findViewById(R.id.adminxoa);
        editText=findViewById(R.id.a);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        DatafromFirebase();

//        adapter.notifyDataSetChanged();
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
                    String user = ds.child("usernamae").getValue(String.class);
                    String sdt = ds.child("sdt").getValue(String.class);
                    String pass = ds.child("pass").getValue(String.class);
                    String ten = ds.child("ten").getValue(String.class);
                    String loaitk = ds.child("loaitk").getValue(String.class);
                    if(loaitk.equals("Khách Hàng")) {
                        Helperclass ha = new Helperclass(user, pass, sdt, ten, loaitk);


                        listHinhAnh.add(ha);
                    }
                }
               // recyclerView= findViewById(R.id.listimkiem);
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(adminaccount.this);
                adapter = new seachadmin(adminaccount.this, listHinhAnh);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}