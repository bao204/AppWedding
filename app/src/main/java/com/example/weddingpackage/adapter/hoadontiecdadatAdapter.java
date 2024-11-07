package com.example.weddingpackage.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weddingpackage.R;
import com.example.weddingpackage.ThanhToanActivity;
import com.example.weddingpackage.modle.ChiTietDonDatTiec;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;

public class hoadontiecdadatAdapter extends RecyclerView.Adapter<hoadontiecdadatAdapter.hoadontiecdadatViewHolder> {
    ArrayList<ChiTietDonDatTiec> list;
    Context context;
    Lobby lobbys;

    ArrayList<datTiec_Cook> datTiec_CookList = new ArrayList<>();
    ArrayList<Cook> cookArrayList = new ArrayList<>();
    ArrayList<datTiec_dichVu> datTiec_dichVuList = new ArrayList<>();
    ArrayList<DichVu> dichVuArrayList = new ArrayList<>();
    datTiec_Lobby datTiec_Lobby1;
    User user1;
    datTiec datTiec1;
    public hoadontiecdadatAdapter(Context context, ArrayList<ChiTietDonDatTiec> list){
        this.context=context;
        this.list = list;
    }
    @NonNull
    @Override
    public hoadontiecdadatAdapter.hoadontiecdadatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemhoadon,viewGroup,false);
        return new hoadontiecdadatAdapter.hoadontiecdadatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull hoadontiecdadatAdapter.hoadontiecdadatViewHolder holder, int i) {
        ChiTietDonDatTiec chiTietDonDatTiec = list.get(i);

        FirebaseDatabase.getInstance().getReference("lobby")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                        Lobby lobby = dataSnapshot1.getValue(Lobby.class);
                                        lobby.setKey(dataSnapshot1.getKey());
                                        if(chiTietDonDatTiec.getMadattiec_lobby().equals(lobby.getKey())){
                                            holder.daichitochuctiecthongtindondattiec_dadat.setText(lobby.getDiachi());

                                        }

                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {}});

        FirebaseDatabase.getInstance().getReference("dattiec_cook")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            datTiec_CookList.clear();
                            for (DataSnapshot snapshot1 : snapshot.getChildren()){
                                datTiec_Cook datTiecCook1 = snapshot1.getValue(datTiec_Cook.class);

                                if(datTiecCook1.getMaDatTiec().equals(chiTietDonDatTiec.getMadattiec())){

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
                                    if(datTiec_CookList.get(i).getMaCook().equals(cook.getKey()) && datTiec_CookList.get(i).getMaDatTiec().equals(chiTietDonDatTiec.getMadattiec())){
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

                                if (datTiec_dichVu1.getMaDatTiec().equals(chiTietDonDatTiec.getMadattiec())){

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
                                    if(datTiec_dichVuList.get(i).getMaDichVu().equals(dichVu.getKey()) && datTiec_dichVuList.get(i).getMaDatTiec().equals(chiTietDonDatTiec.getMadattiec())){
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

                                if(datTiecLobby1.getMaDatTiec().equals(chiTietDonDatTiec.getMadattiec())){

                                    datTiec_Lobby1 = datTiecLobby1;

                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}});

        FirebaseDatabase.getInstance().getReference("user").orderByChild("sodt").equalTo(chiTietDonDatTiec.getSodt())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                User user = dataSnapshot1.getValue(User.class);


                                    user1 = user;


                                break;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}});

        FirebaseDatabase.getInstance().getReference("dattiec")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                datTiec user = dataSnapshot1.getValue(datTiec.class);

                                if(user.getMa().equals(chiTietDonDatTiec.getMadattiec())){
                                    datTiec1 = user;

                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}});

        holder.soluongdondattiecthanhcong.setText("1");
        holder.ngaythongtindondattiec_dadat.setText(chiTietDonDatTiec.getNgaytochuc());
        holder.thoigianthongtindondattiec_dadat.setText(chiTietDonDatTiec.getGiotochuc());
        holder.ngaydattiecthongtindondattiec_dadat.setText(chiTietDonDatTiec.getNgaydat());
        holder.giatiendathanhtoanthongtindondattiec_dadat.setText(chiTietDonDatTiec.getTonggia());



        holder.clicktodetailthongtindondattiec_dadat.setOnClickListener(v->{

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
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class hoadontiecdadatViewHolder extends RecyclerView.ViewHolder {
        TextView soluongdondattiecthanhcong, ngaythongtindondattiec_dadat, clicktodetailthongtindondattiec_dadat,
                thoigianthongtindondattiec_dadat, ngaydattiecthongtindondattiec_dadat, giatiendathanhtoanthongtindondattiec_dadat,
                daichitochuctiecthongtindondattiec_dadat;
        public hoadontiecdadatViewHolder(@NonNull View itemView) {
            super(itemView);

            soluongdondattiecthanhcong = itemView.findViewById(R.id.soluongdondattiecthanhcong);
            ngaythongtindondattiec_dadat = itemView.findViewById(R.id.ngaythongtindondattiec_dadat);
            thoigianthongtindondattiec_dadat = itemView.findViewById(R.id.thoigianthongtindondattiec_dadat);
            ngaydattiecthongtindondattiec_dadat = itemView.findViewById(R.id.ngaydattiecthongtindondattiec_dadat);
            giatiendathanhtoanthongtindondattiec_dadat = itemView.findViewById(R.id.giatiendathanhtoanthongtindondattiec_dadat);
            daichitochuctiecthongtindondattiec_dadat = itemView.findViewById(R.id.daichitochuctiecthongtindondattiec_dadat);
            clicktodetailthongtindondattiec_dadat = itemView.findViewById(R.id.clicktodetailthongtindondattiec_dadat);
        }
    }
}
