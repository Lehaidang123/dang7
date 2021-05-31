package com.example.thanhlc.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Spannable;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.thanhlc.Hinhanh;
import com.example.thanhlc.MainActivitychitiet;
import com.example.thanhlc.MainActivitysapxep;
import com.example.thanhlc.MainActivitysapxepgiamtheogia;
import com.example.thanhlc.MainActivitysapxeptheochu;
import com.example.thanhlc.MainActivitytimkiem;
import com.example.thanhlc.PhotoAdapter;
import com.example.thanhlc.R;
import com.example.thanhlc.photo;
import com.example.thanhlc.sanphamAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    FirebaseStorage storage = FirebaseStorage.getInstance();
    DatabaseReference Mdata;
    ArrayList<Hinhanh>manghinh= new ArrayList<>();
    sanphamAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    GridView lvhinhanh;
    ImageView te;
    Spinner spinner;
    ArrayList<Hinhanh> listHinhAnh = new ArrayList<>();
    private ViewPager viewPager;
   private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;

    private List<photo> mListPhoto;
    private Timer mTimer;
 //   com.example.thanhlc.hinhanhadapter adapter=null;
    ArrayList<Hinhanh>mang;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


                View view = inflater.inflate(R.layout.fragment_home,container,false);
                te=(ImageView) view.findViewById(R.id.txtcangi);
        viewPager = (ViewPager)view. findViewById(R.id.viewpaper);
        circleIndicator = (CircleIndicator)view. findViewById(R.id.circle_indicator);

     //   viewPager.setTag("https://www.facebook.com/All-RacingShop-106377424321048");
      //  Linkify.addLinks((Spannable) viewPager, Linkify.WEB_URLS);
        mListPhoto = getListPhoto();

        photoAdapter = new PhotoAdapter(this, getListPhoto());
        viewPager.setAdapter(photoAdapter);

        circleIndicator.setViewPager(viewPager);
      circleIndicator.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              viewPager.setTag("https://www.facebook.com/All-RacingShop-106377424321048");
                Linkify.addLinks((Spannable) viewPager, Linkify.WEB_URLS);
          }
      });
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
                spinner =(Spinner)view.findViewById(R.id.spinhome);
        String m[] = {"Sắp Xếp Theo Giá","Sắp Xếp Tăng Theo Giá","Sắp Xếp Giảm Theo Giá","Sắp xép theo chữ cái"};
        ArrayAdapter adapte=new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,m);
        adapte.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
        spinner.setAdapter(adapte);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1)
                {
                    Intent intent=new Intent(getContext().getApplicationContext(), MainActivitysapxep.class);

                    startActivity(intent);


                }

                if(position==2)
                {
                    Intent intent=new Intent(getContext().getApplicationContext(), MainActivitysapxepgiamtheogia.class);

                    startActivity(intent);
                }
                if(position == 3)
                {
                    Intent intent=new Intent(getContext().getApplicationContext(), MainActivitysapxeptheochu.class);

                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
            lvhinhanh= view.findViewById(R.id.grid_view);
                te.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext().getApplicationContext(), MainActivitytimkiem.class);
                        startActivity(intent);
                    }
                });


                DatafromFirebase();
                adapter = new sanphamAdapter(this.getContext(),listHinhAnh);
                lvhinhanh.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                lvhinhanh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), MainActivitychitiet.class);
                        intent.putExtra("ten",listHinhAnh.get(position).getTenhinh());
                        intent.putExtra("gia",listHinhAnh.get(position).getGia());
                        intent.putExtra("noidung",listHinhAnh.get(position).getNoidung());
                        intent.putExtra("hinh",listHinhAnh.get(position).getLink());
                        intent.putExtra("sdt",listHinhAnh.get(position).getSdt());
                        intent.putExtra("tinhtrang",listHinhAnh.get(position).getTinhtrang());


                        startActivity(intent);
                    }
                });


        //ArrayAdapter<String> list= new ArrayAdapter<String>(getActivity(), );
  //   lvhinhanh.setAdapter(list);
       // getAdapter = new Hinhanhadapter(getActivity(),R.layout.donghinhanh,manghinh);
    //    adapter=new Hinhanhadapter(this,R.layout.donghinhanh,mang);
      //  lvhinhanh.setAdapter(getAdapter);
       // lad();

autoSlideImages();
     //   View view= inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }
    private void DatafromFirebase(){
        listHinhAnh = new ArrayList<>();
        Mdata= FirebaseDatabase.getInstance().getReference().child("sanpham");
        Mdata.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                  //  Log.d("abc", "onDataChange: vao day");
                    String key = ds.getKey();
                    String ten = ds.child("tenhinh").getValue(String.class);
                    String gia = ds.child("gia").getValue(String.class);
                    String hinh = ds.child("link").getValue(String.class);
                    String noidung = ds.child("noidung").getValue(String.class);
                    String tinh=ds.child("tinhtrang").getValue(String.class);
                    String sdt= ds.child("sdt").getValue(String.class);
                    AtomicBoolean isSP = new AtomicBoolean();
                    listHinhAnh.forEach(sanpham -> {
                        if (sanpham.getId() == key) {
                            isSP.set(true);
                        }
                    });


                        Hinhanh ha = new Hinhanh(tinh,"",key,ten,gia,noidung,hinh, sdt);
                        listHinhAnh.add(ha);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private List<photo> getListPhoto(){
        List<photo> list = new ArrayList<>();
        list.add(new photo(R.drawable.img1));
        list.add(new photo(R.drawable.img2));
        list.add(new photo(R.drawable.allracing));
        list.add(new photo(R.drawable.huoc));

        return list;

    }
    private  void autoSlideImages(){
        if(mListPhoto == null || mListPhoto.isEmpty() || viewPager == null){
            return;
        }
        //Init timer
        if(mTimer == null){
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currenItem = viewPager.getCurrentItem();
                        int totalItem = mListPhoto.size() - 1; //tổng số ảnh
                        if(currenItem < totalItem){
                            currenItem ++;
                            viewPager.setCurrentItem(currenItem);
                        }else{
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 1000, 3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }
    }

}