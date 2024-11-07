package com.example.weddingpackage.modle;

import java.io.Serializable;

public class Lobby implements Serializable {
    String ten, quan, anh, diachi, kichthuoc, soluongban, giaban, thongtinkhac, gia;
    String key;
    public Lobby(){}
    public Lobby(String ten, String quan, String anh, String diachi, String kichthuoc, String soluongban, String giaban, String thongtinkhac, String gia) {
        this.ten = ten;
        this.quan = quan;
        this.anh = anh;
        this.diachi = diachi;
        this.kichthuoc = kichthuoc;
        this.soluongban = soluongban;
        this.giaban = giaban;
        this.thongtinkhac = thongtinkhac;
        this.gia = gia;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getGiaban() {
        return giaban;
    }

    public void setGiaban(String giaban) {
        this.giaban = giaban;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getKichthuoc() {
        return kichthuoc;
    }

    public void setKichthuoc(String kichthuoc) {
        this.kichthuoc = kichthuoc;
    }

    public String getSoluongban() {
        return soluongban;
    }

    public void setSoluongban(String soluongban) {
        this.soluongban = soluongban;
    }

    public String getThongtinkhac() {
        return thongtinkhac;
    }

    public void setThongtinkhac(String thongtinkhac) {
        this.thongtinkhac = thongtinkhac;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }
}
