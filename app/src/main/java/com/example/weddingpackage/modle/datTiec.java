package com.example.weddingpackage.modle;

import java.io.Serializable;
import java.util.ArrayList;

public class datTiec implements Serializable {
    String sodt, ten, ngay, gio, thoigianthue, soluongbandat, gia, ma, trangthai, tinhTrangThanhToan;

    datTiec(){}
    public datTiec(String sodt, String ten, String ngay, String gio, String thoigianthue, String soluongbandat, String gia, String ma, String trangthai, String tinhTrangThanhToan) {
        this.sodt = sodt;
        this.ten = ten;
        this.ngay = ngay;
        this.gio = gio;
        this.thoigianthue = thoigianthue;
        this.soluongbandat = soluongbandat;
        this.gia = gia;
        this.ma = ma;
        this.trangthai = trangthai;
        this.tinhTrangThanhToan = tinhTrangThanhToan;
    }

    public String getSoluongbandat() {
        return soluongbandat;
    }

    public void setSoluongbandat(String soluongbandat) {
        this.soluongbandat = soluongbandat;
    }

    public String getTinhTrangThanhToan() {
        return tinhTrangThanhToan;
    }

    public void setTinhTrangThanhToan(String tinhTrangThanhToan) {
        this.tinhTrangThanhToan = tinhTrangThanhToan;
    }

    public String getThoigianthue() {
        return thoigianthue;
    }

    public void setThoigianthue(String thoigianthue) {
        this.thoigianthue = thoigianthue;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String thoigianthue() {
        return thoigianthue;
    }

    public void setThoigiantochucbuatiec(String thoigianthue) {
        this.thoigianthue = thoigianthue;
    }

    public String getSodt() {
        return sodt;
    }

    public void setSodt(String sodt) {
        this.sodt = sodt;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }
}
