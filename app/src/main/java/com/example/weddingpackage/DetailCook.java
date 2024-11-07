package com.example.weddingpackage;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.weddingpackage.modle.Cook;

public class DetailCook extends AppCompatActivity {
    TextView ten, gia, mota,loai;
    ImageView anh;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cook);

        findViewById(R.id.iconBackDetailCook).setOnClickListener(v->{
            finish();
        });

        toolbar = findViewById(R.id.toolbar_food_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chi tiết món ăn");

        ten = findViewById(R.id.tvNameKhaiVi);
        mota = findViewById(R.id.tv_valueThongtinKhaiVi);
        gia = findViewById(R.id.tvGiafoodKhaiVi);
        anh = findViewById(R.id.ImgFoodKhaiVi);
        loai = findViewById(R.id.tvTypeFoodKhaiVi);


        Bundle bundle = getIntent().getExtras();

        Cook cook = (Cook) bundle.get("objCook");
        ten.setText(cook.getTen());
        gia.setText(cook.getGia());
        mota.setText(cook.getMota());
        loai.setText(cook.getLoai());
        Glide.with(this).load(cook.getAnh()).into(anh);
    }
}
