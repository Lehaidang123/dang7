package com.example.thanhlc.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.thanhlc.Danhmuc;
import com.example.thanhlc.Hinhanh;
import com.example.thanhlc.MainActivity;
import com.example.thanhlc.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link dangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dangFragment extends Fragment {
    Button dang, select;
    ListView listView;
    ArrayList<Hinhanh> mangchinh;
    // hinhanhadapter adapter=null;
    ImageView imageView;
    EditText ten;
    EditText gia;
    EditText noidung;
    EditText danhmuc;
    TextView testsipn;
    com.example.thanhlc.adapterdanhmuc adapterdanhmuc;
    ArrayList<Danhmuc> danhmucs;
    Spinner spinner,spinnertinhtrang,khuvuc;
    private static final int SELECT_PICTURE = 1;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    DatabaseReference Mdata;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public dangFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment dangFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static dangFragment newInstance(String param1, String param2) {
        dangFragment fragment = new dangFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }


    }
    //  anhxa();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dang, container, false);
        Mdata = FirebaseDatabase.getInstance().getReference().child("sanpham");
        imageView = (ImageView) view.findViewById(R.id.foodImage);
        dang = (Button) view.findViewById(R.id.dangtin);
        ten = (EditText) view.findViewById(R.id.tensp);
        gia = (EditText) view.findViewById(R.id.gia);
        noidung = (EditText) view.findViewById(R.id.mota);
        khuvuc = (Spinner)view.findViewById(R.id.khhuvuc);
        spinnertinhtrang=(Spinner)view.findViewById(R.id.spinnertinhtrang);
        String y[] ={"TP,HCM","Vĩnh Long","CÀ Mau","Bến tre","Tiền Giang","" +
                "sóc Trăng","Bạc Liêu","An Giang","Kiên Giang","Cần Thơ"};
        ArrayAdapter adapt=new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,y);
        adapt.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
        khuvuc.setAdapter(adapt);

        String t[] ={"Mới 100%","Đã khui","Đã qua sử dụng","Đã tân Trang"};
        ArrayAdapter adapte=new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,t);
        adapte.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
        spinnertinhtrang.setAdapter(adapte);

        //select =(Button) view.findViewById(R.id.selectig);
        spinner =(Spinner)view.findViewById(R.id.spinner);
        String m[] = {"Nội -Ngoại Thất","Đồ Điện Tử","Xe Cộ","Thời Trang","Mẹ và bé","Giải Trí-Thể Thao","Đồ Văn Phòng","Dịch Vụ"};
       ArrayAdapter adapter=new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,m);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);

       spinner.setAdapter(adapter);



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, SELECT_PICTURE);*/
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , SELECT_PICTURE);
            }
        });
        StorageReference storageRef = storage.getReferenceFromUrl("gs://thanh-l-c.appspot.com");


        dang.setOnClickListener(new View.OnClickListener() {


            @Override

            public void onClick(View v) {
                if (ten.getText().toString().length()!=0 && gia.getText().toString().length()!=0 && noidung.getText().toString().length()!=0 ) {
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
                                Toast.makeText(getContext().getApplicationContext(), "Thành Công", Toast.LENGTH_SHORT).show();
                                //Log.d("AAAA",down+"");

                                String key = Mdata.push().getKey();
                                Hinhanh hinhanh = new Hinhanh(khuvuc.getSelectedItem().toString(),spinnertinhtrang.getSelectedItem().toString(),spinner.getSelectedItem().toString(),key,ten.getText().toString(), gia.getText().toString(), noidung.getText().toString(), String.valueOf(downloadUri), MainActivity.sdt);

                                //tạo node trên database



                                Mdata.child(key).setValue(hinhanh, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        if (error != null) {
                                            Toast.makeText(getContext().getApplicationContext(), "k Thành Công", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext().getApplicationContext(), "Lưu Thành Công", Toast.LENGTH_SHORT).show();
                                            //getActivity().finish();
                                        }
                                    }

                                });


                            } else {
                                // Handle failures
                                // ...
                            }

                        }


                    });
                }else {
                    Toast.makeText(getContext().getApplicationContext(), "Vui lòng điền đầy đủ!!!", Toast.LENGTH_SHORT).show();
                }
            }

        });


        return view;
        // Inflate the layout for this fragment


    }
    private void DatafromFireba(){
        danhmucs = new ArrayList<>();
        Mdata= FirebaseDatabase.getInstance().getReference().child("danhmuc");
        Mdata.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    Log.d("abc", "onDataChange: vao day");
                    String ten = ds.child("tendm").getValue(String.class);
                  //  String hinh = ds.child("hinh").getValue(String.class);
                    Danhmuc ha = new Danhmuc(ten,"");
                    danhmucs.add(ha);
                }
                adapterdanhmuc.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
  /* public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }*/
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);


      if(requestCode == SELECT_PICTURE && resultCode == RESULT_OK){
          try {
              final Uri imageUri = data.getData();
              final InputStream imageStream =getActivity(). getContentResolver().openInputStream(imageUri);
              final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
              imageView.setImageBitmap(selectedImage);
          } catch (FileNotFoundException e) {
              e.printStackTrace();

          }
      }
  }

}
