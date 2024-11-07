package com.example.weddingpackage;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weddingpackage.adapter.hoadontiecdadatAdapter;
import com.example.weddingpackage.modle.ChiTietDonDatTiec;
import com.example.weddingpackage.modle.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailAccount extends AppCompatActivity {

    TextView ten, sodt, payInfo;
    RecyclerView recyclerlichsudadattiec;
    ArrayList<ChiTietDonDatTiec> list;
    hoadontiecdadatAdapter adapter;
    ImageView aaaa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_account);

        list = new ArrayList<>();
        ten= findViewById(R.id.tenInf);
        sodt = findViewById(R.id.sodtInfo);
        payInfo = findViewById(R.id.payInfo);

        Bundle i = getIntent().getExtras();
        User user = (User) i.get("user");

        ten.setText(user.getTen());
        sodt.setText(user.getSodt());
        payInfo.setText(user.getPay());

        aaaa = findViewById(R.id.aaaa);
        recyclerlichsudadattiec = findViewById(R.id.recyclerlichsudadattiec);
        recyclerlichsudadattiec.setLayoutManager(new LinearLayoutManager(DetailAccount.this, LinearLayoutManager.VERTICAL, false));


        FirebaseDatabase.getInstance().getReference("hoadon")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                ChiTietDonDatTiec chiTietDonDatTiec = dataSnapshot1.getValue(ChiTietDonDatTiec.class);

                                if(chiTietDonDatTiec.getTrangthai().equals("da thanh toan (PAY)") && user.getSodt().equals(chiTietDonDatTiec.getSodt())){
                                    list.add(chiTietDonDatTiec);
                                }
                                else {
                                    recyclerlichsudadattiec.setVisibility(View.GONE);
                                    aaaa.setVisibility(View.VISIBLE);
                                }
                            }
                            adapter = new hoadontiecdadatAdapter(DetailAccount.this, list);
                            recyclerlichsudadattiec.setAdapter(adapter);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}});


    }
    public void backInfo(View view) {
        finish();
    }
}