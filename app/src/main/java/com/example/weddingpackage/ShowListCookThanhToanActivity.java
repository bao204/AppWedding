package com.example.weddingpackage;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weddingpackage.adapter.CookAdapter;
import com.example.weddingpackage.adapter.DichVuAdapter;
import com.example.weddingpackage.modle.Cook;
import com.example.weddingpackage.modle.DichVu;

import java.util.ArrayList;

public class ShowListCookThanhToanActivity extends AppCompatActivity {

    RecyclerView rcv_ShowListCookThanhToanActivity;
    ArrayList<Cook> listCook_ShowListCookThanhToanActivity = new ArrayList<>();
    CookAdapter adapter_ShowListCookThanhToanActivity;
    Toolbar toolbar_ShowListCookThanhToanActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_cook_thanh_toan);

        findViewById(R.id.iconBack_ShowListCookThanhToanActivity).setOnClickListener(v->{
            finish();
        });

        toolbar_ShowListCookThanhToanActivity = findViewById(R.id.toolbar_ShowListCookThanhToanActivity);
        setSupportActionBar(toolbar_ShowListCookThanhToanActivity);
        getSupportActionBar().setTitle("Danh sách món ăn từ đơn đặt tiệc");
        rcv_ShowListCookThanhToanActivity = findViewById(R.id.rcv_ShowListCookThanhToanActivity);

        Intent intent = getIntent();
        if(intent != null){
            listCook_ShowListCookThanhToanActivity = (ArrayList<Cook>) intent.getSerializableExtra("listCook");
        }
        CookAdapter.ItemCook item = CookAdapter.ItemCook.ITEM_COOK_1;
        rcv_ShowListCookThanhToanActivity.setLayoutManager(new LinearLayoutManager(ShowListCookThanhToanActivity.this, LinearLayoutManager.VERTICAL, false));
        adapter_ShowListCookThanhToanActivity = new CookAdapter(ShowListCookThanhToanActivity.this, listCook_ShowListCookThanhToanActivity, item);
        rcv_ShowListCookThanhToanActivity.setAdapter(adapter_ShowListCookThanhToanActivity);
    }
}