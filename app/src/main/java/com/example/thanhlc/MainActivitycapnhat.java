package com.example.thanhlc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MainActivitycapnhat extends AppCompatActivity {
TextView ten,sdt;
Button capnhat;
    ImageView profileImageView;
    FirebaseAuth fAuth;
    FirebaseUser user;
    StorageReference storageReference;
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

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        profileImageView = findViewById(R.id.profileImageView);
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Truy cập thư viện người dùng---------------------------------------
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1);
            }
        });

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
        String loaitk="Khách Hàng";
        Mdata= FirebaseDatabase.getInstance().getReference().child("account");
        Helperclass helperclass = new Helperclass(MainActivity.username, MainActivity.passs, khau, tai,loaitk);
        Toast.makeText(MainActivitycapnhat.this, "Cập nhật thành công Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();




                    //Toast.makeText(MainActivitycapnhat.this,"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                    Mdata.child(MainActivity.username).setValue(helperclass);



                  //  String passcheck = snapshot.child(tai).child("pass").getValue(String.class);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                uploadImageToFirebase(imageUri);//cập nhật lên firebase

            }
        }

    }
    //cập nhật lên firebase
    private void uploadImageToFirebase(Uri imageUri) {
        // tải hình ảnh lên firebase storage
        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://thanh-l-c.appspot.com");
        StorageReference fileRef = storageReference.child("users/"+MainActivity.sdt);
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(getApplicationContext()).load(uri).into(profileImageView);//hiển thị hình ảnh từ một nguồn bên ngoài
                    }
                });
                Toast.makeText(getApplicationContext(), "Uploaded.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}