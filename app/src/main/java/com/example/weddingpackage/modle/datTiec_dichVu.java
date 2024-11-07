package com.example.weddingpackage.modle;

import java.io.Serializable;

public class datTiec_dichVu implements Serializable {
    String maDatTiec;
    String maDichVu;
    String ten,mota,anh,gia;
    String key;

    datTiec_dichVu() {}
    public datTiec_dichVu(String maDichVu, String maDatTiec, String ten, String mota, String anh, String gia) {
        this.maDichVu = maDichVu;
        this.maDatTiec = maDatTiec;
        this.ten = ten;
        this.mota = mota;
        this.anh = anh;
        this.gia = gia;
    }

    public String getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(String maDichVu) {
        this.maDichVu = maDichVu;
    }

    public String getMaDatTiec() {
        return maDatTiec;
    }

    public void setMaDatTiec(String maDatTiec) {
        this.maDatTiec = maDatTiec;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
