package com.example.weddingpackage.modle;

import java.io.Serializable;

public class User implements Serializable, Comparable<User> {
    String ten, sodt, pay, matkhau, trangthai;
    String key;
    User(){

    }

    public User(String ten, String sodt, String pay, String matkhau, String trangthai) {
        this.ten = ten;
        this.sodt = sodt;
        this.pay = pay;
        this.matkhau = matkhau;
        this.trangthai = trangthai;
    }

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

    public String getSodt() {
        return sodt;
    }

    public void setSodt(String sodt) {
        this.sodt = sodt;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    @Override
    public int compareTo(User o) {
        return this.sodt.compareTo(o.getSodt());
    }
}
