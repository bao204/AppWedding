package com.example.weddingpackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.weddingpackage.adapter.GoiTiecAdapter;
import com.example.weddingpackage.modle.GoiTiec;

import java.util.ArrayList;
import java.util.List;

public class GoiTiecActivity extends AppCompatActivity {
    private RecyclerView rcv_GoiTiec;
    private List<GoiTiec> mListgoitiec;
    private GoiTiecAdapter goitiecadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goitiec);

        rcv_GoiTiec=findViewById(R.id.rcvGoiTiec);
        mListgoitiec= new ArrayList<>();

        /*mListgoitiec.add(new GoiTiec("COMBO A",10000000,"TP.HCM","Quan 3","menu A","dich vu dac biet",R.drawable.lnom1899_1,"trang tri dac biet"));
        mListgoitiec.add(new GoiTiec("COMBO B",12000000,"TP.HCM","Quan 1","menu A","dich vu dac biet",R.drawable.lnom1598,"trang tri dac biet"));
        mListgoitiec.add(new GoiTiec("COMBO C",5500000,"TP.HCM","Quận Tân Bình","menu A","dich vu dac biet",R.drawable.lnom1807_1,"trang tri dac biet"));
        mListgoitiec.add(new GoiTiec("COMBO D",7000000,"TP.HCM","Quận 5","menu A","dich vu dac biet",R.drawable.sanhcuoi2,"trang tri dac biet"));
        mListgoitiec.add(new GoiTiec("COMBO E",5000000,"TP.HCM","Quan 10","menu A","dich vu dac biet",R.drawable.sanhcuoi3,"trang tri dac biet"));

        goitiecadapter=new GoiTiecAdapter(mListgoitiec, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcv_GoiTiec.setLayoutManager(linearLayoutManager);
        rcv_GoiTiec.addItemDecoration(decoration);
        rcv_GoiTiec.setAdapter(goitiecadapter);*/
    }
}