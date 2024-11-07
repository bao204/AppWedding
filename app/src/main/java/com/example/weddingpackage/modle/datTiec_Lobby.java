package com.example.weddingpackage.modle;

import java.io.Serializable;

public class datTiec_Lobby implements Serializable {
    String maLobby;
    String maDatTiec;
    String ten, quan, anh, diachi, kichthuoc, soluongban, giaban, thongtinkhac, gia;

    datTiec_Lobby(){}
    public datTiec_Lobby(String maLobby, String maDatTiec, String ten, String quan, String anh, String diachi, String kichthuoc, String soluongban, String giaban, String thongtinkhac, String gia) {
        this.maLobby = maLobby;
        this.maDatTiec = maDatTiec;
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

    public String getMaLobby() {
        return maLobby;
    }

    public void setMaLobby(String maLobby) {
        this.maLobby = maLobby;
    }

    public String getGiaban() {
        return giaban;
    }

    public void setGiaban(String giaban) {
        this.giaban = giaban;
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
