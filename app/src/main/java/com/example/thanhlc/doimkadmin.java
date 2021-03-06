package com.example.thanhlc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class doimkadmin extends AppCompatActivity {
EditText user,pass,newpass,repass;
Button button;
    DatabaseReference Mdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doimkadmin);
        anhxa();
        load();
    }
    public void load()
    {
        user.setText(MainActivity.username);
    }
    public void doimatkhauad(View view)
    {
        String tai = user.getText().toString().trim();
        String khau = pass.getText().toString().trim();
        String password = newpass.getText().toString().trim();
        String newpass= repass.getText().toString().trim();
        String loaitk="admin";
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
                                        helperclass = new Helperclass(tai, md5(newpass), MainActivity.sdt, MainActivity.ten,loaitk);
                                    } catch (NoSuchAlgorithmException e) {
                                        e.printStackTrace();
                                    }


                                    //Toast.makeText(MainActivitycapnhat.this,"????ng k?? th??nh c??ng",Toast.LENGTH_SHORT).show();
                                    Mdata.child(tai).setValue(helperclass);


                                    Toast.makeText(doimkadmin.this, "????i M???t Kh???u Th??nh C??ng", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(doimkadmin.this, MainActivity.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(doimkadmin.this, "Password ph???i tr??n 8 ki t???", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(doimkadmin.this, "Pass Ho???c pass m???i kh??ng ????ng", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(doimkadmin.this, "Pass Ho???c pass m???i kh??ng ????ng", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                } else {

                    Toast.makeText(doimkadmin.this, "Vui l??ng ??i???n ?????y ????? th??ng tin!!!", Toast.LENGTH_SHORT).show();
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
    public void anhxa()
    {
        user=findViewById(R.id.tenadmin);
        pass=findViewById(R.id.nhappassadmin);
        newpass=findViewById(R.id.etpassmoiadmin);
        repass=findViewById(R.id.newpassadmin);
        button=findViewById(R.id.btndoimkadmin);
    }
}