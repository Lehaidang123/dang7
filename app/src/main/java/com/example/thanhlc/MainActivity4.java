package com.example.thanhlc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {
        EditText user,sdt,pass;
        Button laymk;
    DatabaseReference Mdata;
    public static String ten;
    public static final String TAG = "TAG";
    //Khởi tạo -------------------------------------------------
    EditText mFullName,mEmail,mPassword,mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    ProgressBar progressBar;
    //firebase-----------------------------------------

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        anhxa();

    }
    public void anhxa()
    {
        user =findViewById(R.id.et_fullName);
        sdt=findViewById(R.id.et_Email);
        pass=findViewById(R.id.et_password);
    }
    public void load(View view)
    {
        String tai = user.getText().toString().trim();
        String khau = sdt.getText().toString().trim();
        String password = pass.getText().toString().trim();
        Query user = FirebaseDatabase.getInstance().getReference("account").orderByChild("usernamae").equalTo(tai);
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String passcheck = snapshot.child(tai).child("sdt").getValue(String.class);
                    if (passcheck.equals(khau)) {
                        ten =snapshot.child(tai).child("ten").getValue(String.class);
                        Mdata= FirebaseDatabase.getInstance().getReference().child("account");
                        Helperclass helperclass = new Helperclass(tai,password,khau,ten);





                        //Toast.makeText(MainActivitycapnhat.this,"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                        Mdata.child(tai).setValue(helperclass);
                        Toast.makeText(MainActivity4.this, "Lấy Mật Khẩu Thành Công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity4.this, "username hoặc sdt không đúng", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(MainActivity4.this, "Vui lòng điền đầy đủ thông tin!!!", Toast.LENGTH_SHORT).show();
                }
            }




            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

}