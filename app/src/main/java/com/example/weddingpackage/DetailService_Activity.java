package com.example.weddingpackage;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.weddingpackage.modle.DichVu;
import com.example.weddingpackage.modle.Lobby;

public class DetailService_Activity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView anh;
    TextView ten;
    TextView mota;
    TextView gia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_service);
        toolbar = (Toolbar) findViewById(R.id.toolbar_service_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chi tiết dịch vụ");

        ten = findViewById(R.id.tvNameService);
        mota = findViewById(R.id.tv_valueThongtin);
        gia = findViewById(R.id.tvGiaservice);
        anh = findViewById(R.id.ImgService);

        Bundle bundle = getIntent().getExtras();

        DichVu dichVu = (DichVu) bundle.get("model");

        mota.setText(dichVu.getMota());
        ten.setText(dichVu.getTen());
        gia.setText(dichVu.getGia());
        Glide.with(this).load(dichVu.getAnh()).into(anh);

        findViewById(R.id.iconBackClick_toolbar).setOnClickListener(v->{
            finish();
        });
    }
}