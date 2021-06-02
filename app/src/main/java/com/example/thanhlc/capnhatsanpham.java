package com.example.thanhlc;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class capnhatsanpham extends AppCompatActivity {
    EditText ten, gia, noidung;
    ImageView imageView;
    TextView idd;
    Spinner danhmuc, tinhtrang,khuvuc;
    Button capnhat, xoa;
    DatabaseReference Mdata;
    public static String tenhinh;
    public static String id;


    private static final int SELECT_PICTURE = 1;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capnhatsanpham);
        anhxa();
        load();

    }

    public void anhxa() {
        ten = findViewById(R.id.captensp);
        gia = findViewById(R.id.capnhatgia);
        noidung = findViewById(R.id.capnahtmota);
        imageView = findViewById(R.id.capnhathinh);
        danhmuc = findViewById(R.id.spinnercapnhatdanhmuc);
        tinhtrang = findViewById(R.id.capnhatspinnertinhtrang);
        capnhat = findViewById(R.id.btncapnhatsp);
        idd = findViewById(R.id.id);
        khuvuc=findViewById(R.id.cnkhhuvuc);
        xoa = findViewById(R.id.btnxoa);
        StorageReference storageRef = storage.getReferenceFromUrl("gs://thanh-l-c.appspot.com");

        capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ten.getText().toString().length() != 0 && gia.getText().toString().length() != 0 && noidung.getText().toString().length() != 0) {
                    Calendar calendar = Calendar.getInstance();
                    StorageReference mountainsRef = storageRef.child("imgae" + calendar.getTimeInMillis() + ".png");

                    imageView.setDrawingCacheEnabled(true);
                    imageView.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask = mountainsRef.putBytes(data);
                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return mountainsRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                Toast.makeText(capnhatsanpham.this, "Thành Công", Toast.LENGTH_SHORT).show();
                                //Log.d("AAAA",down+"");
                                String tai = idd.getText().toString().trim();
                                Query user = FirebaseDatabase.getInstance().getReference("sanpham").orderByChild("id").equalTo(tai);
                                user.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {


                                            tenhinh = snapshot.child(tai).child("id").getValue(String.class);
                                            Mdata = FirebaseDatabase.getInstance().getReference().child("sanpham");
                                            Hinhanh hinhanh = new Hinhanh(khuvuc.getSelectedItem().toString(),tinhtrang.getSelectedItem().toString(), danhmuc.getSelectedItem().toString(), tenhinh, ten.getText().toString(), gia.getText().toString(), noidung.getText().toString(), String.valueOf(downloadUri), MainActivity.sdt);
                                            Mdata.child(tai).setValue(hinhanh, new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                    if (error != null) {
                                                        Toast.makeText(capnhatsanpham.this, "k Thành Công", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(capnhatsanpham.this, "Lưu Thành Công", Toast.LENGTH_SHORT).show();

                                                    }
                                                }

                                            });


                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                    }
                                });
                                // String test = spinner.getSelectedItem().toString().trim();


                                //tạo node trên database


                            } else {
                                // Handle failures
                                // ...
                            }

                        }


                    });
                } else {
                    Toast.makeText(capnhatsanpham.this, "Vui lòng điền đầy đủ!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        String y[] ={"TP,HCM","Vĩnh Long","CÀ Mau","Bến tre","Tiền Giang","" +
                "sóc Trăng","Bác Liêu","An Giang","Kiên Giang","Cần Thơ"};
        ArrayAdapter adapt=new ArrayAdapter(capnhatsanpham.this, android.R.layout.simple_spinner_item,y);
        adapt.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
        khuvuc.setAdapter(adapt);

        String t[] = {"Mới 100%", "Đã khui", "Đã qua sử dụng", "Đã tân Trang"};
        ArrayAdapter adapte = new ArrayAdapter(capnhatsanpham.this, android.R.layout.simple_spinner_item, t);
        adapte.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
        tinhtrang.setAdapter(adapte);

        //select =(Button) view.findViewById(R.id.selectig);

        String m[] = {"Nội -Ngoại Thất", "Đồ Điện Tử", "Xe Cộ", "Thời Trang", "Mẹ và bé", "Giải Trí-Thể Thao", "Đồ Văn Phòng", "Dịch Vụ"};
        ArrayAdapter adapter = new ArrayAdapter(capnhatsanpham.this, android.R.layout.simple_spinner_item, m);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
        danhmuc.setAdapter(adapter);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, SELECT_PICTURE);*/
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, SELECT_PICTURE);
            }
        });

    }

    public void load() {
        Intent intent = getIntent();
        ten.setText(intent.getStringExtra("ten"));
        gia.setText(intent.getStringExtra("gia"));
        noidung.setText(intent.getStringExtra("noidung"));
        idd.setText(intent.getStringExtra("id"));
        //tinhtrang.setText(intent.getStringExtra("tinhtrang"));
        //  sdt.setText(intent.getStringExtra("sdt"));

        String url = intent.getStringExtra("hinh");
        Picasso.with(this).load(url).into(imageView);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            }
        }

    }

    public void xoasanpham(View view) {
        String t = idd.getText().toString().trim();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(capnhatsanpham.this);
        alertDialogBuilder.setMessage("Bán có muốn xóa sản phẩm này!");
        alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // xóa sp đang nhấn giữ
                Mdata = FirebaseDatabase.getInstance().getReference().child("sanpham").child(t);
                Mdata.removeValue();
                Toast.makeText(capnhatsanpham.this, "Xóa Thành Công", Toast.LENGTH_SHORT).show();

            }
        });
        alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //không làm gì
            }
        });
        alertDialogBuilder.show();

    }
}

