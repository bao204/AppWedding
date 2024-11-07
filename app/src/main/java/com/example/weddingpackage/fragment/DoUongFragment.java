package com.example.weddingpackage.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weddingpackage.R;
import com.example.weddingpackage.adapter.CookAdapter;
import com.example.weddingpackage.modle.Cook;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DoUongFragment extends Fragment {
    CookAdapter cookAdapter;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<Cook> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_do_uong, container, false);

        list = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("monan");

        recyclerView = view.findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        CookAdapter.ItemCook item = CookAdapter.ItemCook.ITEM_COOK_1;
        KhaiViFragment.getLobbyItems("4", getContext(), recyclerView, item);
        return view;
    }
}