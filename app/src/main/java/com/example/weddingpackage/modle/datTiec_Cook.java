package com.example.weddingpackage.modle;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class datTiec_Cook implements Serializable {
    String maDatTiec;
    String maCook;
    String anh, gia, loai, mota, ten, id;

    datTiec_Cook(){}
    public datTiec_Cook(String maDatTiec, String maCook, String anh, String gia, String loai, String mota, String ten, String id) {
        this.maDatTiec = maDatTiec;
        this.maCook = maCook;
        this.anh = anh;
        this.gia = gia;
        this.loai = loai;
        this.mota = mota;
        this.ten = ten;
        this.id = id;
    }

    public String getMaDatTiec() {
        return maDatTiec;
    }

    public void setMaDatTiec(String maDatTiec_Cook) {
        this.maDatTiec = maDatTiec_Cook;
    }

    public String getMaCook() {
        return maCook;
    }

    public void setMaCook(String maCook) {
        this.maCook = maCook;
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


}
