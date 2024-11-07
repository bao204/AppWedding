package com.example.weddingpackage;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weddingpackage.adapter.CookAdapter;
import com.example.weddingpackage.adapter.DichVuAdapter;
import com.example.weddingpackage.adapter.LobbyAdapter;
import com.example.weddingpackage.modle.Cook;
import com.example.weddingpackage.modle.DichVu;
import com.example.weddingpackage.modle.Lobby;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ServiceActivity extends AppCompatActivity {
    RecyclerView rcv_Service;
    ArrayList<DichVu> mListservice;
    DichVuAdapter adapter;
    DatabaseReference serviceDatabaseRef;
    Toolbar toolbar;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        findViewById(R.id.iconBackClick_toolbar).setOnClickListener(v->{
            finish();
        });

        DichVuAdapter.Item item = DichVuAdapter.Item.ITEM_1;

        searchView = findViewById(R.id.searchDichvu);

        mListservice= new ArrayList<>();
        serviceDatabaseRef = FirebaseDatabase.getInstance().getReference("dichvu");

        rcv_Service= findViewById(R.id.rcvService);
        rcv_Service.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        getService(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<DichVu> searchDV = new ArrayList<>();
                for(DichVu lobby : mListservice){
                    if(lobby.getTen().toLowerCase().contains(newText.toLowerCase())){
                        searchDV.add(lobby);
                    }
                }
                adapter.search(searchDV);
                return true;
            }
        });
    }

    public void getService(DichVuAdapter.Item item){
        DatabaseReference refDichvu = FirebaseDatabase.getInstance().getReference("dichvu");
        refDichvu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.hasChildren()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        DichVu dichVus = dataSnapshot.getValue(DichVu.class);
                        mListservice.add(dichVus);
                    }
                    adapter = new DichVuAdapter(ServiceActivity.this, mListservice, item);
                    rcv_Service.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
    public static void  getServiceItems(String node, Context context, RecyclerView recyclerView) {
        switch (node){
            case "dichvu":
                DatabaseReference refDichvu = FirebaseDatabase.getInstance().getReference(node);
                Query queryDichvu = refDichvu.limitToFirst(3);
                queryDichvu.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<DichVu> list_dichvu = new ArrayList<>();
                        if (snapshot.exists() && snapshot.hasChildren()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                DichVu dichVus = dataSnapshot.getValue(DichVu.class);
                                list_dichvu.add(dichVus);
                            }
                            DichVuAdapter.Item item = DichVuAdapter.Item.ITEM_1;
                            DichVuAdapter adapter = new DichVuAdapter(context, list_dichvu, item);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });break;
            case "monan":
                DatabaseReference refMonan = FirebaseDatabase.getInstance().getReference(node);
                Query queryMonan = refMonan.limitToFirst(3);
                queryMonan.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<Cook> list_cook = new ArrayList<>();
                        if (snapshot.exists() && snapshot.hasChildren()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Cook cook = dataSnapshot.getValue(Cook.class);
                                list_cook.add(cook);
                            }
                            CookAdapter.ItemCook item = CookAdapter.ItemCook.ITEM_COOK_1;
                            CookAdapter adapter = new CookAdapter(context, list_cook, item);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });break;
            case "lobby":
                DatabaseReference refLobby = FirebaseDatabase.getInstance().getReference(node);
                Query queryLobby = refLobby.limitToFirst(3);
                queryLobby.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<Lobby> list_lobby = new ArrayList<>();
                        if (snapshot.exists() && snapshot.hasChildren()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Lobby lobby = dataSnapshot.getValue(Lobby.class);
                                list_lobby.add(lobby);
                            }
                            LobbyAdapter.ItemType itemType = LobbyAdapter.ItemType.TYPE_1;
                            LobbyAdapter adapter = new LobbyAdapter(context, list_lobby, itemType);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });break;
        }
    }
}