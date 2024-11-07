package com.example.weddingpackage;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weddingpackage.adapter.LobbyAdapter;
import com.example.weddingpackage.modle.DichVu;
import com.example.weddingpackage.modle.Lobby;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LobbyActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    Toolbar toolbar;
    LobbyAdapter lobbyAdapter;
    ArrayList<Lobby> lobbyList;
    DatabaseReference lobbyDatabaseRef;
    SearchView searchLobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        searchLobby = findViewById(R.id.searchLobby);
        recyclerView = findViewById(R.id.recyclerViewLobby);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        lobbyList = new ArrayList<>();

        lobbyDatabaseRef = FirebaseDatabase.getInstance().getReference().child("lobby");

        getLobbyItems();

        searchLobby.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Lobby> searchDV = new ArrayList<>();
                for(Lobby lobby : lobbyList){
                    if(lobby.getQuan().toLowerCase().contains(newText.toLowerCase())){
                        searchDV.add(lobby);
                    }
                }
                lobbyAdapter.search(searchDV);
                return true;
            }
        });
        findViewById(R.id.iconBackClick_inLobbyAcitivity).setOnClickListener(v->{
            finish();
        });
    }

    private void getLobbyItems() {
        LobbyAdapter.ItemType itemType = LobbyAdapter.ItemType.TYPE_1;
        lobbyDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists() && snapshot.hasChildren()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Lobby lobbyItem = dataSnapshot.getValue(Lobby.class);
                        lobbyItem.setKey(dataSnapshot.getKey());

                        lobbyList.add(lobbyItem);
                    }
                    if(!lobbyList.isEmpty())
                    {lobbyAdapter = new LobbyAdapter(LobbyActivity.this, lobbyList, itemType);
                    recyclerView.setAdapter(lobbyAdapter);}
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}