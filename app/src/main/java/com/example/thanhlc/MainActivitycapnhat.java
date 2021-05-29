package com.example.thanhlc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivitycapnhat extends AppCompatActivity {
TextView ten,sdt;
Button capnhat;
    DatabaseReference Mdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activitycapnhat);
        anhxa();
        loadData();

    }
    private void anhxa()
    {
        ten=findViewById(R.id.capnhatten);
        sdt=findViewById(R.id.capnhansdt);
        capnhat=findViewById(R.id.btncapnhat);
    }
    private void loadData() {
        Intent intent = getIntent();
       ten.setText(MainActivity.ten);
       sdt.setText(MainActivity.sdt);
    }
    public void cap(View view)
    {

        String tai = ten.getText().toString().trim();
        String khau = sdt.getText().toString().trim();

        Mdata= FirebaseDatabase.getInstance().getReference().child("account");
        Helperclass helperclass = new Helperclass(MainActivity.username, MainActivity.passs, khau, tai);
        Toast.makeText(MainActivitycapnhat.this, "Cập nhật thành công Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();




                    //Toast.makeText(MainActivitycapnhat.this,"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                    Mdata.child(MainActivity.username).setValue(helperclass);



                  //  String passcheck = snapshot.child(tai).child("pass").getValue(String.class);


    }
}