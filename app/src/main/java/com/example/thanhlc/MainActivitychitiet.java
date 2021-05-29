package com.example.thanhlc;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivitychitiet extends AppCompatActivity {
        ImageView imageView;
        TextView ten,gia,noidung,sdt,tinhtrang;
        sanphamAdapter adapter;
        DatabaseReference Mdata;
        String tens;
    FloatingActionButton mail;
        ArrayList<Hinhanh> hinhanhArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activitychitiet);
        imageView= findViewById(R.id.chitiet);
        ten=findViewById(R.id.chiietten);
        sdt=findViewById(R.id.chitietsdt);
        gia=findViewById(R.id.chitietgia);
        mail=findViewById(R.id.fab);
        tinhtrang=findViewById(R.id.chitiettinhtrang);
        noidung=findViewById(R.id.chitietnoidung);
        loadData();
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivitychitiet.this,acticchat.class);

                intent.putExtra("sdt",sdt.getText());
                startActivity(intent);
            }
        });
    }


    private void loadData(){
        Intent intent = getIntent();
        ten.setText(intent.getStringExtra("ten"));
        gia.setText(intent.getStringExtra("gia"));
         noidung.setText(intent.getStringExtra("noidung"));
        tinhtrang.setText(intent.getStringExtra("tinhtrang"));
            sdt.setText(intent.getStringExtra("sdt"));

         String url = intent.getStringExtra("hinh");
        Picasso.with(this).load(url).into(imageView);
    }
}