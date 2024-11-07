package com.example.weddingpackage.modle;

import java.io.Serializable;

public class DichVu implements Serializable {
    private String ten,mota,anh,gia;
    String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public DichVu(String ten, String mota, String anh, String gia) {
        this.ten = ten;
        this.mota = mota;
        this.anh = anh;
        this.gia = gia;
    }
    DichVu(){
    }

        /*@Override
        public int compareTo(DichVu o) {
            return this.getTen().compareTo(o.getTen());
        }*/
}
