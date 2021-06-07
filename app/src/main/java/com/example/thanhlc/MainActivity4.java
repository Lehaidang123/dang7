package com.example.thanhlc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
                        if(pass.getText().toString().length()>=8) {
                            ten = snapshot.child(tai).child("ten").getValue(String.class);
                            Mdata = FirebaseDatabase.getInstance().getReference().child("account");
                            Helperclass helperclass = null;
                            try {
                                helperclass = new Helperclass(tai, md5(password), khau, ten);
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            }


                            //Toast.makeText(MainActivitycapnhat.this,"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                            Mdata.child(tai).setValue(helperclass);

                            Toast.makeText(MainActivity4.this, "Lấy Mật Khẩu Thành Công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity4.this, "PassWord phải lớn hơn 8 kí tự", Toast.LENGTH_SHORT).show();
                        }

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