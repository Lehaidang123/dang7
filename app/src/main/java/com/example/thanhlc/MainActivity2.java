package com.example.thanhlc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.jetbrains.annotations.NotNull;

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
                String hoten = ten.getText().toString();
                Query userr = FirebaseDatabase.getInstance().getReference("account").orderByChild("sdt").equalTo(st);
                userr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot.exists() )
                        {

                                Toast.makeText(MainActivity2.this, "Số điện thoại đã được sử dụng", Toast.LENGTH_SHORT).show();


                        }
                        else {
                            if (user.getText().toString().length() != 0 && pass.getText().toString().length() != 0 && sdt.getText().toString().length() != 0 && ten.getText().toString().length() != 0) {
                                Helperclass helperclass = new Helperclass(name, p, st, hoten);
                                Toast.makeText(MainActivity2.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                reference.child(name).setValue(helperclass);
                                Intent intent = new Intent(MainActivity2.this, MainActivity8.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity2.this, "Vui lòng điền đầy đủ", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });




            }
        });
    }
}