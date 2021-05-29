package com.example.thanhlc;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class userdata {
    SharedPreferences u;
    SharedPreferences.Editor editor;
    Context context;

    public static  final String SEson= "userLoginSession";
    public static  final String Key= "rememberMe";
    public static  final String islogin= "IsLoginUser";
    public static  final String KEY_ten= "ten";
    public static  final String KEY_sdt= "sdt";

    public userdata(Context applicationContext, String sEson) {
    }

    public void Sessionmanager(Context context1, String sesionname)
    {
        context= context1;
        u=context1.getSharedPreferences("sesionname",context.MODE_PRIVATE);
        editor = u.edit();
    }
    public void createLogin(String ten,String sdt)
    {
        editor.putBoolean(islogin,true);
        editor.putString(ten,ten);
        editor.putString(sdt,sdt);
        editor.commit();
    }
    public HashMap<String , String> getInformationuser(){
        HashMap<String,String> userdata = new HashMap<>();
        userdata.put(KEY_ten,u.getString(KEY_ten,null));
        userdata.put(KEY_sdt,u.getString(KEY_sdt,null));


        return userdata;
    }
    public boolean checklogin()
    {
        if(u.getBoolean(islogin,false))
        {
            return true;
        }
        else
            return false;
    }
    public  void louser()
    {
        editor.clear();
        editor.commit();
    }

    public void createLoginSession(String ten, String sdt) {
    }
}
