package com.example.weddingpackage.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weddingpackage.R;
import com.example.weddingpackage.adapter.CookAdapter;
import com.example.weddingpackage.fragment.KhaiViFragment;
import com.example.weddingpackage.modle.Cook;
import com.example.weddingpackage.modle.DichVu;
import com.example.weddingpackage.modle.Lobby;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.Collections;

public class Selected_ItemsCookActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<Cook> list;
    CookAdapter adapter;
    InterfaceSelectedService interfaceSelectedService;
    ArrayList<Cook> listKhaiVi = new ArrayList<>();
    ArrayList<Cook> listMonChinh = new ArrayList<>();
    ArrayList<Cook> listTrangMieng = new ArrayList<>();
    ArrayList<Cook> listNuocUong = new ArrayList<>();
    String idCook = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_items_cook);

        findViewById(R.id.clickBack_selectedCookActivity).setOnClickListener(v->{finish();});

        toolbar = findViewById(R.id.toolbar_selectedCookActivity);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            Toast.makeText(this, "idCook NULL", Toast.LENGTH_SHORT).show();
            return;
        }
        idCook = bundle.getString("idCook");

        if(idCook.equals("1")){
            getSupportActionBar().setTitle("Danh sách món khai vị");
        } else if ((idCook.equals("2"))) {
            getSupportActionBar().setTitle("Danh sách món chính");
        } else if ((idCook.equals("3"))) {
            getSupportActionBar().setTitle("Danh sách món tráng miệng̣");
        }
        else {
            getSupportActionBar().setTitle("Danh sách nước uống");
        }

        interfaceSelectedService = new InterfaceSelectedService() {
            @Override
            public void ArrayListServiceSelected(ArrayList<DichVu> SelectedService) {}
            @Override
            public void LobbySelected(Lobby lobby) {}
            @Override
            public void ArrayListCookSelected(ArrayList<Cook> SelectedCook) {
                if(idCook.equals("1")){
                    listKhaiVi = SelectedCook;
                } else if (idCook.equals("2")) {
                    listMonChinh = SelectedCook;
                } else if (idCook.equals("3")) {
                    listTrangMieng = SelectedCook;
                } else if (idCook.equals("4")) {
                    listNuocUong = SelectedCook;
                }else {
                    Toast.makeText(Selected_ItemsCookActivity.this, Selected_ItemsCookActivity.this+"", Toast.LENGTH_SHORT).show();
                }
            }
        };

        recyclerView = findViewById(R.id.recyclerSelectedCookActivity);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(Selected_ItemsCookActivity.this, LinearLayoutManager.VERTICAL, false));
        
        loadCook();
    }

    void loadCook(){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("monan");
        Query query = ref.orderByChild("id").equalTo(idCook);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Cook cook = dataSnapshot.getValue(Cook.class);
                        cook.setKey(dataSnapshot.getKey());
                        list.add(cook);
                    }
                    CookAdapter.ItemCook item = CookAdapter.ItemCook.ITEM_COOK_3;
                    adapter = new CookAdapter(Selected_ItemsCookActivity.this, list, item);
                    adapter.setInterfaceSelectedService(interfaceSelectedService);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Selected_ItemsCookActivity.this, "BUG_Selected_ItemsCookActivity", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.selected, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Save:
                DialogPlus dialogPlus = DialogPlus.newDialog(Selected_ItemsCookActivity.this)
                        .setContentHolder(new ViewHolder(R.layout.noti_show_cook_selected))
                        .setExpanded(true, 390)
                        .setGravity(Gravity.CENTER)
                        .setCancelable(false)
                        .create();

                View view = dialogPlus.getHolderView().getRootView();
                TextView notiQuantilySelectedCook = view.findViewById(R.id.notiQuantilySelectedCook);
                
                if(idCook.equals("1")){
                    notiQuantilySelectedCook.setText("Bạn đã chọn " + listKhaiVi.size() + " món khai vị");
                } else if (idCook.equals("2")) {
                    notiQuantilySelectedCook.setText("Bạn đã chọn " + listMonChinh.size() + " món chính");
                } else if (idCook.equals("3")) {
                    notiQuantilySelectedCook.setText("Bạn đã chọn " + listTrangMieng.size() + " món tráng miệng");
                } else if (idCook.equals("4")) {
                    notiQuantilySelectedCook.setText("Bạn đã chọn " + listNuocUong.size() + " nước");
                }
                else {
                    Toast.makeText(this, "BUG_Selected_ItemsCookActivity_notiQuantilyChoose", Toast.LENGTH_SHORT).show();
                }

                dialogPlus.show();

                view.findViewById(R.id.clickOKChooseCookSelected).setOnClickListener(v-> {
                    Intent intent = new Intent();
                    if(idCook.equals("1")){
                        if (!listKhaiVi.isEmpty()) {
                            intent.putExtra("listKhaiViChoose", new ArrayList<>(listKhaiVi));
                            intent.putExtra("checkKhaiVi", true);
                            setResult(Activity.RESULT_OK, intent);
                            dialogPlus.dismiss();
                            finish();
                        } else {
                            Toast.makeText(this, "BUG listKhaiVi == NULL", Toast.LENGTH_SHORT).show();
                            intent.putExtra("checkKhaiVi", false);
                            setResult(Activity.RESULT_OK, intent);
                            dialogPlus.dismiss();
                            finish();
                        }
                    } else if (idCook.equals("2")) {
                        if (!listMonChinh.isEmpty()) {
                            intent.putExtra("listMonChinhChoose", new ArrayList<>(listMonChinh));
                            intent.putExtra("checkMonChinh", true);
                            setResult(Activity.RESULT_OK, intent);
                            dialogPlus.dismiss();
                            finish();
                        } else {
                            Toast.makeText(this, "BUG listMonChinh == NULL", Toast.LENGTH_SHORT).show();
                            intent.putExtra("checkMonChinh", false);
                            setResult(Activity.RESULT_OK, intent);
                            dialogPlus.dismiss();
                            finish();
                        }
                    } else if (idCook.equals("3")) {
                        if (!listTrangMieng.isEmpty()) {
                            intent.putExtra("listTrangMiengChoose", new ArrayList<>(listTrangMieng));
                            intent.putExtra("checkTrangMieng", true);
                            setResult(Activity.RESULT_OK, intent);
                            dialogPlus.dismiss();
                            finish();
                        } else {
                            Toast.makeText(this, "BUG listTrangMieng == NULL", Toast.LENGTH_SHORT).show();
                            intent.putExtra("checkTrangMieng", false);
                            setResult(Activity.RESULT_OK, intent);
                            dialogPlus.dismiss();
                            finish();
                        }
                    } else if (idCook.equals("4")) {
                        if (!listNuocUong.isEmpty()) {
                            intent.putExtra("listNuocUongChoose", new ArrayList<>(listNuocUong));
                            intent.putExtra("checkNuocUong", true);
                            setResult(Activity.RESULT_OK, intent);
                            dialogPlus.dismiss();
                            finish();
                        } else {
                            Toast.makeText(this, "BUG listNuocUong == NULL", Toast.LENGTH_SHORT).show();
                            intent.putExtra("checkNuocUong", false);
                            setResult(Activity.RESULT_OK, intent);
                            dialogPlus.dismiss();
                            finish();
                        }
                    }
                    else {
                        Toast.makeText(this, "BUG_Selected_ItemsCookActivity_notiQuantilyChoose", Toast.LENGTH_SHORT).show();
                    }

                });

                view.findViewById(R.id.clickCloseChooseCookSelected).setOnClickListener(v->{
                    dialogPlus.dismiss();
                });
        }
        return super.onOptionsItemSelected(item);
    }
}