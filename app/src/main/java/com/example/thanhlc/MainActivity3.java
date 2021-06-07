package com.example.thanhlc;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.thanhlc.fragment.HomeFragment;
import com.example.thanhlc.fragment.dangFragment;
import com.example.thanhlc.fragment.danhmucFragment;
import com.example.thanhlc.fragment.thontinFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

          BottomNavigationView bottomNavigationView;
            private  ActionBar actionBar;
            ArrayAdapter <String> arrayAdapter;
    Button dangky;
    ImageView imageView;
    EditText ten;
    EditText gia;
    EditText noidung;
    SessionManager sessionManager;
    public static String y=MainActivity.username;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    int requet= 1;
    TextView user;
    TextView txtsdt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        TextView textView = findViewById(R.id.txtSearch);
//sessionManager = new SessionManager(getApplication());
        user =(TextView)findViewById(R.id.txtuser);
        txtsdt=(TextView)findViewById(R.id.txtsodienthoai);


        List<String> mylist = new ArrayList<>();
        mylist.add("Eraser");
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1,mylist);

        HomeFragment homeFragment = new HomeFragment();
        danhmucFragment danhmucFragment = new danhmucFragment();
        dangFragment dangFragment = new dangFragment();
        thontinFragment thontinFragment = new thontinFragment();




    BottomNavigationView bottomNavigationView = findViewById(R.id.botttt_t);
        loadFragment(homeFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {




                    Fragment fragment;


                    switch (item.getItemId()) {
                        case R.id.hoe:
                            loadFragment(homeFragment);
                            return true;
                        case R.id.danhmuc:
                            fragment = new danhmucFragment();
                            loadFragment(fragment);
                            return true;
                        case R.id.dangtin:
                            fragment = new dangFragment();
                            loadFragment(fragment);
                            return true;

                        case R.id.Thongtin:

                                fragment = new thontinFragment();
                                loadFragment(fragment);

                            return true;
                    }




                return false;

                }


        });



    }

  private void loadFragment(Fragment fragment) {
        FragmentTransaction activity= getSupportFragmentManager().beginTransaction();
        activity.replace(R.id.conterner,fragment);
        activity.commit();

    }


}