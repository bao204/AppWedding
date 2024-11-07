package com.example.weddingpackage.modle;

import java.io.Serializable;

public class ChiTietDonDatTiec implements Serializable {
    String ten, sodt, soluongbandat, ngaytochuc, giotochuc, giothue, ngaydat, giasanh, giamonan,
            giadichvu, tonggia, trangthai, xacthuc, madattiec, mahoadon,
    madattiec_lobby;

    public ChiTietDonDatTiec() {
    }

    public ChiTietDonDatTiec(String ten, String sodt, String soluongbandat, String ngaytochuc, String giotochuc, String giothue, String ngaydat, String giasanh, String giamonan, String giadichvu, String tonggia, String trangthai, String xacthuc, String madattiec, String mahoadon, String madattiec_lobby) {
        this.ten = ten;
        this.sodt = sodt;
        this.soluongbandat = soluongbandat;
        this.ngaytochuc = ngaytochuc;
        this.giotochuc = giotochuc;
        this.giothue = giothue;
        this.ngaydat = ngaydat;
        this.giasanh = giasanh;
        this.giamonan = giamonan;
        this.giadichvu = giadichvu;
        this.tonggia = tonggia;
        this.trangthai = trangthai;
        this.xacthuc = xacthuc;
        this.madattiec = madattiec;
        this.mahoadon = mahoadon;
        this.madattiec_lobby = madattiec_lobby;
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

    public String getSoluongbandat() {
        return soluongbandat;
    }

    public void setSoluongbandat(String soluongbandat) {
        this.soluongbandat = soluongbandat;
    }

    public String getNgaytochuc() {
        return ngaytochuc;
    }

    public void setNgaytochuc(String ngaytochuc) {
        this.ngaytochuc = ngaytochuc;
    }

    public String getGiotochuc() {
        return giotochuc;
    }

    public void setGiotochuc(String giotochuc) {
        this.giotochuc = giotochuc;
    }

    public String getGiothue() {
        return giothue;
    }

    public void setGiothue(String giothue) {
        this.giothue = giothue;
    }

    public String getNgaydat() {
        return ngaydat;
    }

    public void setNgaydat(String ngaydat) {
        this.ngaydat = ngaydat;
    }

    public String getGiasanh() {
        return giasanh;
    }

    public void setGiasanh(String giasanh) {
        this.giasanh = giasanh;
    }

    public String getGiamonan() {
        return giamonan;
    }

    public void setGiamonan(String giamonan) {
        this.giamonan = giamonan;
    }

    public String getGiadichvu() {
        return giadichvu;
    }

    public void setGiadichvu(String giadichvu) {
        this.giadichvu = giadichvu;
    }

    public String getTonggia() {
        return tonggia;
    }

    public void setTonggia(String tonggia) {
        this.tonggia = tonggia;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getXacthuc() {
        return xacthuc;
    }

    public void setXacthuc(String xacthuc) {
        this.xacthuc = xacthuc;
    }

    public String getMadattiec() {
        return madattiec;
    }

    public void setMadattiec(String madattiec) {
        this.madattiec = madattiec;
    }

    public String getMahoadon() {
        return mahoadon;
    }

    public void setMahoadon(String mahoadon) {
        this.mahoadon = mahoadon;
    }
    public String getMadattiec_lobby() {
        return madattiec_lobby;
    }

    public void setMadattiec_lobby(String madattiec_lobby) {
        this.madattiec_lobby = madattiec_lobby;
    }
}

