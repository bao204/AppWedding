package com.example.weddingpackage.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weddingpackage.R;
import com.example.weddingpackage.ThanhToanActivity;
import com.example.weddingpackage.modle.Cook;
import com.example.weddingpackage.modle.DichVu;
import com.example.weddingpackage.modle.Lobby;
import com.example.weddingpackage.modle.User;
import com.example.weddingpackage.modle.datTiec;
import com.example.weddingpackage.modle.datTiec_Cook;
import com.example.weddingpackage.modle.datTiec_Lobby;
import com.example.weddingpackage.modle.datTiec_dichVu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LichSuDatTiecAdapter extends RecyclerView.Adapter<LichSuDatTiecAdapter.LichSuViewHolder> {
    ArrayList<datTiec> listDatTiec;
    ArrayList<datTiec_Cook> datTiec_CookList = new ArrayList<>();
    ArrayList<Cook> cookArrayList = new ArrayList<>();
    ArrayList<datTiec_dichVu> datTiec_dichVuList = new ArrayList<>();
    ArrayList<DichVu> dichVuArrayList = new ArrayList<>();
    datTiec_Lobby datTiec_Lobby1;
    User user1;
    Context context;

    public LichSuDatTiecAdapter(Context context, ArrayList<datTiec> listDatTiec) {
        this.context = context;
        this.listDatTiec = listDatTiec;
    }

    @NonNull
    @Override
    public LichSuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lichsu_dattiec, parent, false);
        return new LichSuDatTiecAdapter.LichSuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LichSuViewHolder holder, @SuppressLint("RecyclerView") int position) {
        datTiec datTiec1 = listDatTiec.get(position);

        holder.trangThai.setText(datTiec1.getTrangthai());
        holder.tinhTrangThanhToan.setText(datTiec1.getTinhTrangThanhToan());
        holder.ngayToChuc.setText(datTiec1.getNgay());
        holder.gioToChuc.setText(datTiec1.getGio());
        holder.soDienThoai.setText(datTiec1.getSodt());

        FirebaseDatabase.getInstance().getReference("dattiec_cook")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            datTiec_CookList.clear();
                            for (DataSnapshot snapshot1 : snapshot.getChildren()){
                                datTiec_Cook datTiecCook1 = snapshot1.getValue(datTiec_Cook.class);

                                if(datTiecCook1.getMaDatTiec().equals(datTiec1.getMa())){

                                    datTiec_CookList.add(datTiecCook1);
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}});

        FirebaseDatabase.getInstance().getReference("monan")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            cookArrayList.clear();
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                Cook cook = snapshot.getValue(Cook.class);
                                cook.setKey(snapshot.getKey());
                                for(int i = 0; i < datTiec_CookList.size(); i++){
                                    if(datTiec_CookList.get(i).getMaCook().equals(cook.getKey()) && datTiec_CookList.get(i).getMaDatTiec().equals(datTiec1.getMa())){
                                        cookArrayList.add(cook);
                                    }
                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}});

        FirebaseDatabase.getInstance().getReference("dattiec_dichvu").orderByChild("maDatTiec")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            datTiec_dichVuList.clear();
                            for(DataSnapshot snapshot2 : snapshot.getChildren()){
                                datTiec_dichVu datTiec_dichVu1 = snapshot2.getValue(datTiec_dichVu.class);

                                if (datTiec_dichVu1.getMaDatTiec().equals(datTiec1.getMa())){

                                    datTiec_dichVuList.add(datTiec_dichVu1);

                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}});

        FirebaseDatabase.getInstance().getReference("dichvu")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    dichVuArrayList.clear();
                                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                        DichVu dichVu = snapshot.getValue(DichVu.class);
                                        dichVu.setKey(snapshot.getKey());
                                        for(int i = 0; i < datTiec_dichVuList.size(); i++){
                                            if(datTiec_dichVuList.get(i).getMaDichVu().equals(dichVu.getKey()) && datTiec_dichVuList.get(i).getMaDatTiec().equals(datTiec1.getMa())){
                                                dichVuArrayList.add(dichVu);
                                            }
                                        }

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {}});

        FirebaseDatabase.getInstance().getReference("dattiec_lobby").orderByChild("maDatTiec")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                datTiec_Lobby datTiecLobby1 = dataSnapshot.getValue(datTiec_Lobby.class);

                                if(datTiecLobby1.getMaDatTiec().equals(datTiec1.getMa())){

                                    datTiec_Lobby1 = datTiecLobby1;

                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}});

        FirebaseDatabase.getInstance().getReference("user").orderByChild("sodt")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                User user = dataSnapshot1.getValue(User.class);

                                if(user.getSodt().equals(datTiec1.getSodt())){
                                    user1 = user;

                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}});

        holder.viewGroupItemLichSuDatTiec.setOnClickListener(v->{

            Intent intent = new Intent(context, ThanhToanActivity.class);
            intent.putExtra("datTiec_CookList", datTiec_CookList);
            intent.putExtra("datTiec_dichVuList", datTiec_dichVuList);
            intent.putExtra("datTiec_Lobby1", datTiec_Lobby1);
            intent.putExtra("user", user1);
            intent.putExtra("dattiec", datTiec1);
            intent.putExtra("dichVuArrayList", dichVuArrayList);
            intent.putExtra("cookArrayList", cookArrayList);

            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return listDatTiec.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class LichSuViewHolder extends RecyclerView.ViewHolder {

        TextView trangThai, tinhTrangThanhToan, ngayToChuc, gioToChuc, soDienThoai;
        LinearLayout viewGroupItemLichSuDatTiec;
        public LichSuViewHolder(@NonNull View itemView) {
            super(itemView);

            trangThai = itemView.findViewById(R.id.trangThaiLichSuDatTiec);
            tinhTrangThanhToan = itemView.findViewById(R.id.tinhTrangThanhToanLichSuDatTiec);
            ngayToChuc = itemView.findViewById(R.id.ngayToChucLichSuDatTiec);
            gioToChuc = itemView.findViewById(R.id.gioToChucLichSuDatTiec);
            soDienThoai = itemView.findViewById(R.id.soDienThoaiLichSuDatTiec);

            viewGroupItemLichSuDatTiec = itemView.findViewById(R.id.viewGroupItemLichSuDatTiec);
        }
    }
}
