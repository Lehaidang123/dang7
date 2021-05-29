package com.example.thanhlc;

import java.util.ArrayList;

public class Helperclass {
    String usernamae, pass , sdt ,ten;

    public  Helperclass(MainActivity4 mainActivity4, int simple_list_item_1, ArrayList<String> mang){}

    public Helperclass(String usernamae, String pass, String sdt, String ten) {
        this.usernamae = usernamae;
        this.pass = pass;
        this.sdt = sdt;
        this.ten = ten;
    }


    public String getUsernamae() {
        return usernamae;
    }

    public void setUsernamae(String usernamae) {
        this.usernamae = usernamae;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
