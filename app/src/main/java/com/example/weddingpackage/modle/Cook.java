package com.example.weddingpackage.modle;

import java.io.Serializable;

public class Cook implements Serializable, Comparable<Cook> {
     String anh, gia, loai, mota, ten, id;

     String key;
    Cook() {

    }
    public Cook(String anh, String gia, String loai, String mota, String ten, String id) {
        this.anh = anh;
        this.gia = gia;
        this.loai = loai;
        this.mota = mota;
        this.ten = ten;
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int compareTo(Cook o) {
        return this.getId().compareTo(o.getId());
    }
}