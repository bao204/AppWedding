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
import com.example.weddingpackage.modle.DichVu;
import com.example.weddingpackage.modle.datTiec_dichVu;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class ShowListDichVuThanhToanActivity extends AppCompatActivity {

    RecyclerView rcv_ShowListDichVuThanhToanActivity;
    ArrayList<DichVu> listDichVu_ShowListDichVuThanhToanActivity = new ArrayList<>();
    DichVuAdapter adapter_ShowListDichVuThanhToanActivity;
    Toolbar toolbar_ShowListDichVuThanhToanActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_dich_vu_thanh_toan);

        findViewById(R.id.iconBack_ShowListDichVuThanhToanActivity).setOnClickListener(v->{
            finish();
        });

        toolbar_ShowListDichVuThanhToanActivity = findViewById(R.id.toolbar_ShowListDichVuThanhToanActivity);
        setSupportActionBar(toolbar_ShowListDichVuThanhToanActivity);
        getSupportActionBar().setTitle("Danh sách dịch vụ trong đơn đặt tiệc");
        rcv_ShowListDichVuThanhToanActivity = findViewById(R.id.rcv_ShowListDichVuThanhToanActivity);

        Intent intent = getIntent();
        if(intent != null){
            listDichVu_ShowListDichVuThanhToanActivity = (ArrayList<DichVu>) intent.getSerializableExtra("listServiceSelected");
        }
        DichVuAdapter.Item item = DichVuAdapter.Item.ITEM_1;
        rcv_ShowListDichVuThanhToanActivity.setLayoutManager(new LinearLayoutManager(ShowListDichVuThanhToanActivity.this, LinearLayoutManager.VERTICAL, false));
        adapter_ShowListDichVuThanhToanActivity = new DichVuAdapter(ShowListDichVuThanhToanActivity.this, listDichVu_ShowListDichVuThanhToanActivity, item);
        rcv_ShowListDichVuThanhToanActivity.setAdapter(adapter_ShowListDichVuThanhToanActivity);

    }
}