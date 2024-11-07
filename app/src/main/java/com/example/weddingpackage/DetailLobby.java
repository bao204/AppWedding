package com.example.weddingpackage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.weddingpackage.modle.Lobby;

public class DetailLobby extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvNameLobby;
    TextView tvDiaChiLobby;
    TextView tv_valueKichThuocSanh;
    TextView tv_valueSoLuongBan;
    TextView tv_valueGiaBan;
    TextView tv_valueThongtinKhac;
    TextView tvGia;
    ImageView ImgLobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_detail);

        findViewById(R.id.iconBackClick_inDetailLobbyAcitivity).setOnClickListener(v->{
            finish();
        });

        toolbar = findViewById(R.id.toolbar_lobby_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chi tiết sảnh");

        tvNameLobby = findViewById(R.id.tvNameLobby);
        tvDiaChiLobby = findViewById(R.id.tvDiaChiLobby);
        tv_valueKichThuocSanh = findViewById(R.id.tv_valueKichThuocSanh);
        tv_valueSoLuongBan = findViewById(R.id.tv_valueSoLuongBan);
        tv_valueGiaBan = findViewById(R.id.tv_valueGiaBan);
        tv_valueThongtinKhac = findViewById(R.id.tv_valueThongtinKhac);
        tvGia = findViewById(R.id.tvGia);
        ImgLobby = findViewById(R.id.ImgLobby);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            tvNameLobby.setText(bundle.getString("ten"));
            tvDiaChiLobby.setText(bundle.getString("diachi"));
            tv_valueKichThuocSanh.setText(bundle.getString("kichthuoc"));
            tv_valueSoLuongBan.setText(bundle.getString("soluongban"));
            tv_valueGiaBan.setText(bundle.getString("giaban"));
            tv_valueThongtinKhac.setText(bundle.getString("thongtinkhac"));
            tvGia.setText(bundle.getString("gia"));
            Glide.with(this).load(bundle.getString("anh")).into(ImgLobby);
        }
    }
}