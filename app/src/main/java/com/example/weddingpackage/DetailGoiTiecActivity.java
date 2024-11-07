package com.example.weddingpackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailGoiTiecActivity extends AppCompatActivity {

    Toolbar toolbar_detail_goiTiec;
    ImageView imageViewDetail;
    TextView tvInformation_detailGoiTiec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_goi_tiec);

        toolbar_detail_goiTiec = findViewById(R.id.toolbar_detail_goiTiec);
        setSupportActionBar(toolbar_detail_goiTiec);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageViewDetail = findViewById(R.id.imageViewDetail);
        tvInformation_detailGoiTiec = findViewById(R.id.tvInformation_detailGoiTiec);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}