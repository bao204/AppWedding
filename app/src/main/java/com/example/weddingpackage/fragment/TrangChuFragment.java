package com.example.weddingpackage.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weddingpackage.CookActivity;
import com.example.weddingpackage.GoiTiecActivity;
import com.example.weddingpackage.LobbyActivity;
import com.example.weddingpackage.R;
import com.example.weddingpackage.ServiceActivity;
import com.example.weddingpackage.adapter.CookAdapter;
import com.example.weddingpackage.adapter.DichVuAdapter;
import com.example.weddingpackage.adapter.GoiTiecAdapter;
import com.example.weddingpackage.adapter.LobbyAdapter;
import com.example.weddingpackage.modle.Cook;
import com.example.weddingpackage.modle.DichVu;
import com.example.weddingpackage.modle.GoiTiec;
import com.example.weddingpackage.modle.Lobby;

import java.util.ArrayList;
import java.util.List;

public class TrangChuFragment extends Fragment {

    RecyclerView LobbyRecycler;
    RecyclerView MonAnRecycler;
    RecyclerView DichVuRecycler;
    RecyclerView trangChu_goiTiec;
    TextView tvClick_toAllLobby, tvClick_toAllMonAn, tvClick_toAllDichVu, tvClick_toAllGoi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        views(view);

        tvClick_toAllLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LobbyActivity.class));
            }
        });
        tvClick_toAllMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CookActivity.class));
            }
        });
        tvClick_toAllDichVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ServiceActivity.class));
            }
        });


        LinearLayoutManager layoutManager_Lobby = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//Sanh------------------------------------------------------------------------------------------
        LobbyRecycler.setLayoutManager(layoutManager_Lobby);
        ServiceActivity.getServiceItems("lobby", getContext(), LobbyRecycler);

//MónĂn-------------------------------------------------------------------------------------
        LinearLayoutManager layoutManager_Lobbys = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        MonAnRecycler.setLayoutManager(layoutManager_Lobbys);
        ServiceActivity.getServiceItems("monan", getContext(), MonAnRecycler);

//Dich vu------------------------------------------------------------------------------------------
        LinearLayoutManager layoutManager_Lobbyss = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        DichVuRecycler.setLayoutManager(layoutManager_Lobbyss);
        ServiceActivity.getServiceItems("dichvu", getContext(), DichVuRecycler);

        return view;
    }
   /* public void tvClick_toAllLobby(View view) {
        startActivity(new Intent(requireContext(), LobbyActivity.class));
    }*/
    public void views(View view){
        LobbyRecycler = view.findViewById(R.id.trangChu_sanh);
        MonAnRecycler = view.findViewById(R.id.trangchu_monAn);
        DichVuRecycler = view.findViewById(R.id.trangChu_dichVu);

        tvClick_toAllLobby = view.findViewById(R.id.tvClick_toAllLobby);
        tvClick_toAllMonAn = view.findViewById(R.id.tvClick_toAllMonAn);
        tvClick_toAllDichVu = view.findViewById(R.id.tvClick_toAllDichVu);
    }
}