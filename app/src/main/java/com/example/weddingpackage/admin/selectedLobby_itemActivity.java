package com.example.weddingpackage.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weddingpackage.R;
import com.example.weddingpackage.adapter.CookAdapter;
import com.example.weddingpackage.adapter.LobbyAdapter;
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

public class selectedLobby_itemActivity extends AppCompatActivity implements InterfaceSelectedService{

    Toolbar toolbar;
    RecyclerView recyclerView;
    LobbyAdapter adapter;
    ArrayList<Lobby> list;
    Lobby lobbySelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_lobby_item);

        toolbar = findViewById(R.id.toolbar_selectedLobbyActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Danh sách sảnh");

        findViewById(R.id.clickBack_selectedLobbyActivity).setOnClickListener(v->{finish();});

        recyclerView = findViewById(R.id.recyclerSelectedLobbyActivity);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        loadItems();
    }
    void loadItems(){
        FirebaseDatabase.getInstance().getReference("lobby")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                Lobby lobby = dataSnapshot.getValue(Lobby.class);
                                lobby.setKey(dataSnapshot.getKey());

                                list.add(lobby);
                            }
                            LobbyAdapter.ItemType item = LobbyAdapter.ItemType.TYPE_3;
                            adapter = new LobbyAdapter(selectedLobby_itemActivity.this, list, item);
                            adapter.setInterfaceSelectedService(selectedLobby_itemActivity.this);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}});
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

                DialogPlus dialogPlus = DialogPlus.newDialog(selectedLobby_itemActivity.this)
                        .setContentHolder(new ViewHolder(R.layout.noti_show_lobby_selected))
                        .setExpanded(true, 800)
                        .setGravity(Gravity.CENTER)
                        .setCancelable(false)
                        .create();

                View view = dialogPlus.getHolderView();
                ImageView anh = view.findViewById(R.id.imgLobbySelected);
                TextView info = view.findViewById(R.id.notiQuantilySelectedLobby);

                if(lobbySelected == null){
                    anh.setImageResource(R.drawable.ic_launcher_foreground);
                    info.setText("Bạ̣n chưa chọn sảnh nào cả!!!");
                }
                else {
                    Glide.with(selectedLobby_itemActivity.this).load(lobbySelected.getAnh()).placeholder(R.drawable.ic_launcher_foreground).optionalCenterCrop().into(anh);
                    info.setText("Bạn đã chọn sảnh: " + lobbySelected.getTen() + "\n" + " với giá: " + lobbySelected.getGia() + " VND");
                }

                dialogPlus.show();

                findViewById(R.id.clickOKChooseLobbySelected).setOnClickListener(v->{
                    Intent intent = new Intent();
                    if(lobbySelected == null){
                        setResult(Activity.RESULT_CANCELED, intent);
                        dialogPlus.dismiss();
                        finish();
                        return;
                    }
                    intent.putExtra("lobbySelected", lobbySelected);
                    intent.putExtra("checkLobbySelected", true);
                    setResult(Activity.RESULT_OK, intent);
                    dialogPlus.dismiss();
                    finish();
                });
                findViewById(R.id.clickCloseChooseLobbySelected).setOnClickListener(v->{
                    /*dialog.dismiss();*/
                    dialogPlus.dismiss();
                });

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void ArrayListServiceSelected(ArrayList<DichVu> SelectedService) {}

    @Override
    public void LobbySelected(Lobby lobby) {
        lobbySelected = lobby;
    }

    @Override
    public void ArrayListCookSelected(ArrayList<Cook> SelectedCook) {}
}