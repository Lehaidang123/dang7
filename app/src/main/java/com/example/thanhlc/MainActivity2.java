package com.example.thanhlc;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity2 extends AppCompatActivity {
ImageView imageView;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    EditText user,pass,sdt,ten;
    int SELECT_PICTURE=1;
    Button dangky;
    FirebaseDatabase root;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        user =findViewById(R.id.tendn);
        pass = findViewById(R.id.pass);
        sdt= findViewById(R.id.sdt);
        ten=findViewById(R.id.hoten);
        dangky = findViewById(R.id.dangky);
        imageView = (ImageView)findViewById(R.id.imm);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,SELECT_PICTURE);
            }
        });
        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root = FirebaseDatabase.getInstance();
                reference = root.getReference("account");
                String name = user.getText().toString();
                String p = pass.getText().toString();
                String st = sdt.getText().toString();
                String loaitk="Kh??ch H??ng";
                String hoten = ten.getText().toString();


                Query userr = FirebaseDatabase.getInstance().getReference("account").orderByChild("sdt").equalTo(st);
                userr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {


                            String tendn = snapshot.child("usernamae").getValue(String.class);

                            if (snapshot.exists()) {

                                Toast.makeText(MainActivity2.this, "S??? ??i???n tho???i ???? ???????c s??? d???ng", Toast.LENGTH_SHORT).show();



                            }
                            else {

                                if (user.getText().toString().length() != 0  && pass.getText().toString().length() != 0 && sdt.getText().toString().length() != 0 && ten.getText().toString().length() != 0) {
                                   if(user.getText().toString().length() >= 8 && pass.getText().toString().length() >=8) {
                                       Helperclass helperclass = null;
                                       try {
                                           helperclass = new Helperclass(name,md5(p), st, hoten,loaitk);
                                       } catch (NoSuchAlgorithmException e) {
                                           e.printStackTrace();
                                       }
                                       Toast.makeText(MainActivity2.this, "????ng k?? th??nh c??ng", Toast.LENGTH_SHORT).show();
                                       reference.child(name).setValue(helperclass);
                                       Intent intent = new Intent(MainActivity2.this, MainActivity8.class);
                                       startActivity(intent);
                                   }else {
                                       Toast.makeText(MainActivity2.this, "Username v?? pass ph???i tr??n 8 k?? t???", Toast.LENGTH_SHORT).show();
                                   }
                                }else {
                                    Toast.makeText(MainActivity2.this, "Vui l??ng ??i???n ?????y ?????", Toast.LENGTH_SHORT).show();
                                }

                            }


                        }
                        //Toast.makeText(MainActivity2.this, "Vui l??ng ??i???n ?????y ?????", Toast.LENGTH_SHORT).show();

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });




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
        }catch (NoSuchAlgorithmException e)
        {
e.printStackTrace();
return "";
        }
    }
}