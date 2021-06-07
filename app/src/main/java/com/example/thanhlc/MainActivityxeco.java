package com.example.thanhlc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class    MainActivityxeco extends AppCompatActivity {
GridView gridView;
    DatabaseReference Mdata;
    Button sapxesp;
    EditText text;
    TextView testdanhmuc;
    ArrayList<Hinhanh> listHinhAnh = new ArrayList<>();
    sanphamAdapter adapter;
    String tendm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activityxeco);
        gridView = findViewById(R.id.grxeco);
        testdanhmuc = findViewById(R.id.txtdanhmuc);

        loadData();
        DatafromFirebase();
        adapter = new sanphamAdapter(this,listHinhAnh);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivityxeco.this, MainActivitychitiet.class);
                intent.putExtra("ten",listHinhAnh.get(position).getTenhinh());
                intent.putExtra("gia",listHinhAnh.get(position).getGia());
                intent.putExtra("noidung",listHinhAnh.get(position).getNoidung());
                intent.putExtra("hinh",listHinhAnh.get(position).getLink());
                intent.putExtra("sdt",listHinhAnh.get(position).getSdt());
                intent.putExtra("tinhtrang",listHinhAnh.get(position).getTinhtrang());
                intent.putExtra("khuvuc",listHinhAnh.get(position).getKhuvuc());
                startActivity(intent);
            }
        });
        adapter.notifyDataSetChanged();

    }
    private void DatafromFirebase(){
        listHinhAnh = new ArrayList<>();

        Mdata= FirebaseDatabase.getInstance().getReference("sanpham");
        Mdata.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    //  Log.d("abc", "onDataChange: vao day");
                        String key = ds.getKey();
                        String d=ds.child("tendm").getValue(String.class);
                        String ten = ds.child("tenhinh").getValue(String.class);
                        String gia = ds.child("gia").getValue(String.class);
                        String hinh = ds.child("link").getValue(String.class);
                        String noidung = ds.child("noidung").getValue(String.class);
                        String sdt = ds.child("sdt").getValue(String.class);
                        String tinhtrang = ds.child("tinhtrang").getValue(String.class);
                        String khu = ds.child("khuvuc").getValue(String.class);
                        if(d.equals(tendm)) {
                            Hinhanh ha = new Hinhanh(khu,tinhtrang,d, "", ten, gia, noidung, hinh,sdt);
                            listHinhAnh.add(ha);
                            Collections.sort(listHinhAnh, new Comparator<Hinhanh>() {
                                @Override
                                public int compare(Hinhanh o1, Hinhanh o2) {
                                    if (Integer.parseInt(o1.getGia()) > Integer.parseInt(o2.getGia()))
                                        return 1;
                                    else {
                                        if (o1.getGia() == o2.getGia()) {
                                            return 0;
                                        } else {
                                            return -1;
                                        }
                                    }
                                }
                                //   return (o1.getTenhinh().compareTo(o2.getTenhinh()));


                            });
                        }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void loadData(){
        Intent intent = getIntent();
        tendm = intent.getStringExtra("tendm");
        testdanhmuc.setText("Danh mục"+" "+tendm);
    }
    private void admin()
    {

        if(MainActivity.username.equals("admin123"))
        {
            gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivityxeco.this);
                    alertDialogBuilder.setMessage("Bán có muốn xóa sản phẩm này!");
                    alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // xóa sp đang nhấn giữ
                            Mdata = FirebaseDatabase.getInstance().getReference().child("sanpham").child(listHinhAnh.get(position).getId());
                            Mdata.removeValue();
                            Toast.makeText(MainActivityxeco.this, "Xóa Thành Công", Toast.LENGTH_SHORT).show();

                        }
                    });
                    alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //không làm gì
                        }
                    });
                    alertDialogBuilder.show();

                    return false;


                }
            });
        }
    }

}