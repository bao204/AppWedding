package com.example.weddingpackage.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weddingpackage.DetailGoiTiecActivity;
import com.example.weddingpackage.R;
import com.example.weddingpackage.modle.GoiTiec;

import java.util.ArrayList;
import java.util.List;

public class GoiTiecAdapter extends RecyclerView.Adapter<GoiTiecAdapter.GoiTiecViewHolder>{
    ArrayList<GoiTiec> list;
    Context context;
    public GoiTiecAdapter(ArrayList<GoiTiec> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public GoiTiecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_goitiec,parent,false);
        return new GoiTiecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoiTiecViewHolder holder, int position) {
        GoiTiec goitiec = list.get(position);

        /*String tenCook = "";
        for(int i = 0; i < goitiec.getListCookPackage().size(); i++){
            tenCook += goitiec.getListCookPackage().get(i).getLoai();
        }*/

        Glide.with(context).load(goitiec.getLobby().getAnh()).placeholder(R.drawable.ic_launcher_foreground).optionalCenterCrop().into(holder.imgPackage);
        holder.tenPackage.setText(goitiec.getTen());
        holder.tenLobbyPackage.setText(goitiec.getLobby().getTen());
        holder.soluongLobbyPackage.setText(goitiec.getLobby().getSoluongban());
        /*holder.tenMonAnPackage.setText(tenCook);*/

        goitiec.setMenusos(goitiec.getListKhaiVi(), goitiec.getListMonChinh(), goitiec.getListTangMieng(), goitiec.getListNuocUong());

        DichVuAdapter.Item itemService = DichVuAdapter.Item.ITEM_1;
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerDichvu.setLayoutManager(layoutManager);
        holder.recyclerDichvu.setFocusable(false);
        DichVuAdapter adapter = new DichVuAdapter(context, goitiec.getListService(), itemService);
        holder.recyclerDichvu.setAdapter(adapter);

        CookAdapter.ItemCook itemCook = CookAdapter.ItemCook.ITEM_COOK_1;
        LinearLayoutManager layoutManagers = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerMonan.setLayoutManager(layoutManagers);
        holder.recyclerMonan.setFocusable(false);
        CookAdapter adapters = new CookAdapter(context, goitiec.getMenusos(), itemCook);
        holder.recyclerMonan.setAdapter(adapters);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class GoiTiecViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPackage;
        TextView tenPackage, tenLobbyPackage, soluongLobbyPackage, tenMonAnPackage;

        RecyclerView recyclerDichvu;
        RecyclerView recyclerMonan;

        public GoiTiecViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPackage = itemView.findViewById(R.id.Admin_imagePackage);
            tenPackage = itemView.findViewById(R.id.admin_tenPackage);
            tenLobbyPackage = itemView.findViewById(R.id.admin_tenLobbyPackage);
            soluongLobbyPackage = itemView.findViewById(R.id.admin_soluongLobbyPackage);
            tenMonAnPackage = itemView.findViewById(R.id.admin_tenMonAnPackage);

            recyclerDichvu = itemView.findViewById(R.id.rcvservicePackage);
            recyclerMonan = itemView.findViewById(R.id.rcvmenuPackage);

        }
    }
}
