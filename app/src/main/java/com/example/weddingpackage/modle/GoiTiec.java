package com.example.weddingpackage.modle;

import java.io.Serializable;
import java.util.ArrayList;

public class GoiTiec implements Serializable {

    Lobby lobby;
    ArrayList<Cook> listKhaiVi;
    ArrayList<Cook> listMonChinh;
    ArrayList<Cook> listTangMieng;
    ArrayList<Cook> listNuocUong;
    ArrayList<DichVu> listService;

    ArrayList<Cook> Menusos;
    String ten;
    String gia;
    String uuDai;
    String key;

    GoiTiec() {
    }

    public GoiTiec(Lobby lobby, ArrayList<Cook> listKhaiVi, ArrayList<Cook> listMonChinh, ArrayList<Cook> listTangMieng, ArrayList<Cook> listNuocUong, ArrayList<DichVu> listService, String ten, String gia, String uuDai) {
        this.lobby = lobby;
        this.listKhaiVi = listKhaiVi;
        this.listMonChinh = listMonChinh;
        this.listTangMieng = listTangMieng;
        this.listNuocUong = listNuocUong;
        this.listService = listService;
        this.ten = ten;
        this.gia = gia;
        this.uuDai = uuDai;
    }

    public ArrayList<Cook> getMenusos(){
        return Menusos;
    }

    public void setMenusos(ArrayList<Cook> listKhaiVi, ArrayList<Cook> listMonChinh, ArrayList<Cook> listTangMieng, ArrayList<Cook> listNuocUong){
        this.Menusos.addAll(listKhaiVi);
        this.Menusos.addAll(listMonChinh);
        this.Menusos.addAll(listTangMieng);
        this.Menusos.addAll(listNuocUong);
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public ArrayList<Cook> getListKhaiVi() {
        return listKhaiVi;
    }

    public void setListKhaiVi(ArrayList<Cook> listKhaiVi) {
        this.listKhaiVi = listKhaiVi;
    }

    public ArrayList<Cook> getListMonChinh() {
        return listMonChinh;
    }

    public void setListMonChinh(ArrayList<Cook> listMonChinh) {
        this.listMonChinh = listMonChinh;
    }

    public ArrayList<Cook> getListTangMieng() {
        return listTangMieng;
    }

    public void setListTangMieng(ArrayList<Cook> listTangMieng) {
        this.listTangMieng = listTangMieng;
    }

    public ArrayList<Cook> getListNuocUong() {
        return listNuocUong;
    }

    public void setListNuocUong(ArrayList<Cook> listNuocUong) {
        this.listNuocUong = listNuocUong;
    }

    public ArrayList<DichVu> getListService() {
        return listService;
    }

    public void setListService(ArrayList<DichVu> listService) {
        this.listService = listService;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getUuDai() {
        return uuDai;
    }

    public void setUuDai(String uuDai) {
        this.uuDai = uuDai;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}