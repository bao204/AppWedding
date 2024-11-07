package com.example.weddingpackage.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weddingpackage.R;
import com.example.weddingpackage.adapter.DichVuAdapter;
import com.example.weddingpackage.admin.fragment_admin.GoiTiecFragment;
import com.example.weddingpackage.modle.Cook;
import com.example.weddingpackage.modle.DichVu;
import com.example.weddingpackage.modle.Lobby;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;

public class Selected_ItemsActivity extends AppCompatActivity{

    RecyclerView rcV;
    DichVuAdapter adapter;
    ArrayList<DichVu> list;
    Toolbar toolbar;
    InterfaceSelectedService interfaceSelectedService;
    ArrayList<DichVu> listServiceSelected = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_items);

        toolbar = findViewById(R.id.toolbar_selectedActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Danh sách dịch vụ");

        findViewById(R.id.clickback_selectedActivity).setOnClickListener(v->{finish();});

        interfaceSelectedService = new InterfaceSelectedService() {
            @Override
            public void ArrayListServiceSelected(ArrayList<DichVu> listSelectedService) {
                listServiceSelected = listSelectedService;
            }
            @Override
            public void LobbySelected(Lobby lobby) {}
            @Override
            public void ArrayListCookSelected(ArrayList<Cook> SelectedCook) {}
        };

        rcV = findViewById(R.id.recyclerSelectedActivity);
        list = new ArrayList<>();
        rcV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        loadItem();
    }
    void loadItem(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("dichvu");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot data : snapshot.getChildren()){
                        DichVu dichVu = data.getValue(DichVu.class);
                        dichVu.setKey(data.getKey());
                        list.add(dichVu);
                    }
                    DichVuAdapter.Item item = DichVuAdapter.Item.ITEM_3;
                    adapter = new DichVuAdapter(Selected_ItemsActivity.this, list, item);
                    adapter.setInterfaceSelectedService(interfaceSelectedService);
                    rcV.setAdapter(adapter);
                }
            }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.selected, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Save:
                DialogPlus dialogPlus = DialogPlus.newDialog(this)
                        .setContentHolder(new ViewHolder(R.layout.notiquantilyselectedservice))
                        .setExpanded(true, 390)
                        .setGravity(Gravity.CENTER)
                        .setCancelable(false)
                        .create();

                View view = dialogPlus.getHolderView();
                TextView tv = view.findViewById(R.id.notiQuantilySelectedService);
                tv.setText("Số dịch vụ bạn đã chọn là : " + listServiceSelected.size());

                View layoutA = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_add_package, null, false);
                Button btnClicktoSelectedItem = layoutA.findViewById(R.id.btnClicktoSelectedItem);

                dialogPlus.show();

                view.findViewById(R.id.clickOkQuantilyServiceSelected).setOnClickListener(v->{
                    Intent intent = new Intent();
                    if (!listServiceSelected.isEmpty()) {

                        btnClicktoSelectedItem.setBackgroundResource(R.drawable.backgroud_radius_yellow);
                        intent.putExtra("listSelected", new ArrayList<>(listServiceSelected));
                        intent.putExtra("checkListService", true);
                        setResult(Activity.RESULT_OK, intent);
                        dialogPlus.dismiss();
                        finish();
                    } else {
                        intent.putExtra("checkListService", false);
                        setResult(Activity.RESULT_OK, intent);
                        dialogPlus.dismiss();
                        finish();
                    }
                });

                view.findViewById(R.id.clickCloseQuantilyServiceSelected).setOnClickListener(v->{
                    dialogPlus.dismiss();
                });
            }
        return super.onOptionsItemSelected(item);
    }
}