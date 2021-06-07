package com.example.thanhlc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class doimatkhau extends AppCompatActivity {
    DatabaseReference Mdata;
    TextView ten,pass, newpass, nhaplai;
    Button lay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doimatkhau);
        anhxa();
        load();
    }
    public void anhxa()
    {
        ten=findViewById(R.id.tennew);
        pass=findViewById(R.id.nhappass);
        newpass=findViewById(R.id.etpassmoi);
        nhaplai=findViewById(R.id.newpass);
        lay=findViewById(R.id.btndoimk);
    }
    public void load()
    {
        ten.setText(MainActivity.username);
    }
    public void doimatkhau(View view)
    {
        String tai = ten.getText().toString().trim();
        String khau = pass.getText().toString().trim();
        String password = newpass.getText().toString().trim();
        String newpass= nhaplai.getText().toString().trim();
        Query user = FirebaseDatabase.getInstance().getReference("account").orderByChild("usernamae").equalTo(tai);
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String passcheck = snapshot.child(tai).child("pass").getValue(String.class);
                    try {
                        if (passcheck.equals(md5(khau))) {
                           if(newpass.equals(password)) {
                               if(newpass.length()>=8) {
                                   // ten =snapshot.child(tai).child("ten").getValue(String.class);
                                   Mdata = FirebaseDatabase.getInstance().getReference().child("account");
                                   Helperclass helperclass = null;
                                   try {
                                       helperclass = new Helperclass(tai, md5(newpass), MainActivity.sdt, MainActivity.ten);
                                   } catch (NoSuchAlgorithmException e) {
                                       e.printStackTrace();
                                   }


                                   //Toast.makeText(MainActivitycapnhat.this,"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                                   Mdata.child(tai).setValue(helperclass);


                                   Toast.makeText(doimatkhau.this, "Đôi Mật Khẩu Thành Công", Toast.LENGTH_SHORT).show();
                                   Intent intent = new Intent(doimatkhau.this, MainActivity.class);
                                   startActivity(intent);
                               }else {
                                   Toast.makeText(doimatkhau.this, "Password phải trên 8 ki tự", Toast.LENGTH_SHORT).show();
                               }
                           }
                           else {
                               Toast.makeText(doimatkhau.this, "Pass Hoặc pass mới không đúng", Toast.LENGTH_SHORT).show();
                           }
                        } else {
                            Toast.makeText(doimatkhau.this, "Pass Hoặc pass mới không đúng", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                } else {

                    Toast.makeText(doimatkhau.this, "Vui lòng điền đầy đủ thông tin!!!", Toast.LENGTH_SHORT).show();
                }
            }




            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    public static String md5(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(text.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b : result) {
                int number = b & 0xff;
                String hex = Integer.toHexString(number);
                if (hex.length() == 1) {
                    sb.append("0" + hex);

                } else {
                    sb.append(hex);
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}