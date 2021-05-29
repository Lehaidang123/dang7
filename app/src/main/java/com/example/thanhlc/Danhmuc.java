package com.example.thanhlc;

public class Danhmuc {

    String Ten;
    String hinh;

    public Danhmuc(String ten, String hinh) {
        Ten = ten;
        this.hinh = hinh;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }
}
