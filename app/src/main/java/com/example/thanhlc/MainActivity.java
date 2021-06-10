package com.example.thanhlc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button dangky, dangnhap;
    TextView quenmk,link;
    EditText tk, mk;
    Helperclass helperclass;
    FirebaseDatabase root;
    ArrayList<Helperclass> helperclasses = null;
    DatabaseReference reference;
    public static String sdt;
    public static String ten;
    public static String username;
    public static String passs;
    public static String sanpham;
    public static String loaitk;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tk = (EditText) findViewById(R.id.tk);
        mk = (EditText) findViewById(R.id.mk);
        dangky = (Button) findViewById(R.id.dangky);
            link = findViewById(R.id.tv_createText);



        dangnhap = (Button) findViewById(R.id.dangnhap);
          quenmk = (TextView) findViewById(R.id.quenmk);
        quenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity4.class);
                startActivity(intent);
            }
        });


        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mh2 = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(mh2);
            }
        });
    }

    public void dangnhapn(View view) {
        String tai = tk.getText().toString().trim();
        String khau = mk.getText().toString().trim();
        Query user = FirebaseDatabase.getInstance().getReference("account").orderByChild("usernamae").equalTo(tai);

        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String passcheck = snapshot.child(tai).child("pass").getValue(String.class);
                    try {
                        if (!passcheck.equals(md5(khau))) {
                            Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        } else {
                           sdt = snapshot.child(tai).child("sdt").getValue(String.class);
                           ten =snapshot.child(tai).child("ten").getValue(String.class);
                           username=snapshot.child(tai).child("usernamae").getValue(String.class);
                           passs =snapshot.child(tai).child("pass").getValue(String.class);
                           loaitk =snapshot.child(tai).child("loaitk").getValue(String.class);
                       //  sessionManager.SetLogin(true);
                            startActivity(new Intent(MainActivity.this, MainActivity7.class));

                            Toast.makeText(MainActivity.this, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                } else {

                    Toast.makeText(MainActivity.this, "Vui lòng điền đầy đủ thông tin!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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