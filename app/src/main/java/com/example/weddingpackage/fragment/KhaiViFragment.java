package com.example.weddingpackage.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weddingpackage.LobbyActivity;
import com.example.weddingpackage.R;
import com.example.weddingpackage.adapter.CookAdapter;
import com.example.weddingpackage.adapter.LobbyAdapter;
import com.example.weddingpackage.modle.Cook;
import com.example.weddingpackage.modle.Lobby;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class KhaiViFragment extends Fragment {
    CookAdapter cookAdapter;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<Cook> list;
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_khai_vi, container, false);

        /*searchView = (SearchView) view.findViewById(R.id.searchCook);*/

        list = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("monan");

        recyclerView = view.findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        CookAdapter.ItemCook item = CookAdapter.ItemCook.ITEM_COOK_1;
        getLobbyItems("1", getContext(), recyclerView, item);

        /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Cook> searchCook = new ArrayList<>();
                for(Cook cook : list){
                    if(cook.getTen().toLowerCase().contains(newText.toLowerCase())){
                        searchCook.add(cook);
                    }
                }
                cookAdapter.search(searchCook);
                return true;
            }
        });*/

        return view;
    }

    public static void getLobbyItems(String s, Context context, RecyclerView recyclerView, CookAdapter.ItemCook itemCook) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("monan");
        Query query = databaseReference.orderByChild("id").equalTo(s);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Cook> list = new ArrayList<>();
                if (snapshot.exists() && snapshot.hasChildren()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Cook cook = dataSnapshot.getValue(Cook.class);
                        list.add(cook);
                    }
                    CookAdapter adapter = new CookAdapter(context, list, itemCook);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}