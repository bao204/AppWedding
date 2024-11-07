package com.example.weddingpackage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weddingpackage.adapter.LichSuDatTiecAdapter;
import com.example.weddingpackage.modle.User;
import com.example.weddingpackage.modle.datTiec;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LichSuDatTiecFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<datTiec> datTiecs;
    LichSuDatTiecAdapter lichSuDatTiecAdapter;
    User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lich_su_dat_tiec, container, false);
        recyclerView = view.findViewById(R.id.rcVLichSuDatTiecFragment);

        Bundle bundle = getArguments();
        if(bundle != null){
            user = (User) bundle.get("user");
        }
        else {

        }

        if(user != null){
            datTiecs = new ArrayList<>();
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

            FirebaseDatabase.getInstance().getReference("dattiec").orderByChild("sodt")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    datTiec datTiec1 = dataSnapshot.getValue(datTiec.class);

                                    if(datTiec1.getSodt().equals(user.getSodt())){
                                        datTiecs.add(datTiec1);
                                    }
                                }
                                lichSuDatTiecAdapter = new LichSuDatTiecAdapter(getActivity(), datTiecs);
                                recyclerView.setAdapter(lichSuDatTiecAdapter);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}});
        }
        else {

        }
        return view;
    }
}