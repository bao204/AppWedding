package com.example.weddingpackage.modle;

public class Admin {
    String matkhau, sodt;
    Admin() {

    }

    public Admin(String matkhau, String sodt) {
        this.matkhau = matkhau;
        this.sodt = sodt;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getSodt() {
        return sodt;
    }

    public void setSodt(String sodt) {
        this.sodt = sodt;
    }
}
