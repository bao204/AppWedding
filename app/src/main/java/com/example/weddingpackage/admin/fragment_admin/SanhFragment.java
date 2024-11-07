package com.example.weddingpackage.admin.fragment_admin;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weddingpackage.R;
import com.example.weddingpackage.adapter.LobbyAdapter;
import com.example.weddingpackage.modle.Lobby;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;

public class SanhFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Lobby> admin_lobbyList;
    DatabaseReference lobbyRef = FirebaseDatabase.getInstance().getReference().child("lobby");
    LobbyAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_sanh, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_Lobby);
        LobbyAdapter.ItemType itemType = LobbyAdapter.ItemType.TYPE_2;

        admin_lobbyList = new ArrayList<>();

        view.findViewById(R.id.imgB_adminClickAddLobby).setOnClickListener(v -> {
            addLoby();
        });

        loadAdminLobby(itemType);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        return view;
    }
    void loadAdminLobby(LobbyAdapter.ItemType i){
        lobbyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot data : snapshot.getChildren()){
                        Lobby lobby = data.getValue(Lobby.class);
                        lobby.setKey(data.getKey());

                        admin_lobbyList.add(lobby);
                    }
                }
                adapter = new LobbyAdapter(getContext(), admin_lobbyList, i);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
    void addLoby(){
        DialogPlus dialog = DialogPlus.newDialog(getContext())
                .setContentHolder(new ViewHolder(R.layout.bottom_sheet_add_lobby))
                .setExpanded(true, 800)
                .setGravity(Gravity.TOP)
                .create();
        View ve = dialog.getHolderView().getRootView();
        ve.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        View view = dialog.getHolderView();

        EditText add_ten = view.findViewById(R.id.admin_edtNameLobby);
        EditText add_quan = view.findViewById(R.id.admin_edtQuanLobby);
        EditText add_diachi = view.findViewById(R.id.admin_edtDiaChiLobby);
        EditText add_soluongban = view.findViewById(R.id.admin_edtSoLuongBanLobby);
        EditText admin_edtGiaBanLobby = view.findViewById(R.id.admin_edtGiaBanLobby);
        EditText add_kichthuoc = view.findViewById(R.id.admin_edtKichThuocLobby);
        EditText add_thongtinkhac = view.findViewById(R.id.admin_edtThongTinKhacLobby);
        EditText add_anh = view.findViewById(R.id.admin_edtAnhLobby);
        EditText add_gia = view.findViewById(R.id.admin_edtGiaLobby);

        dialog.show();

        view.findViewById(R.id.clickOk_addLobby).setOnClickListener(v -> {
            String tenss = add_ten.getText().toString();
            String quanss = add_quan.getText().toString();
            String diachiss = add_diachi.getText().toString();
            String soluongbanss = add_soluongban.getText().toString();
            String GiaBanLobbyss = admin_edtGiaBanLobby.getText().toString();
            String kichthuocss = add_kichthuoc.getText().toString();
            String thongtinkhacss = add_thongtinkhac.getText().toString();
            String anhss = add_anh.getText().toString();
            String giass = add_gia.getText().toString();

            if(tenss.isEmpty()){
                add_ten.setError("Tên sảnh đâu?");
            }
            if(quanss.isEmpty()){
                add_quan.setError("Where quận?");
            }
            if(diachiss.isEmpty()){
                add_diachi.setError("Địa chỉ đâu?");
            }
            if(soluongbanss.isEmpty()){
                add_soluongban.setError("Cho số lượng!");
            }
            if(GiaBanLobbyss.isEmpty()){
                admin_edtGiaBanLobby.setError("Cho số lượng!");
            }
            if(kichthuocss.isEmpty()){
                add_kichthuoc.setError("Kích thước?");
            }
            if(thongtinkhacss.isEmpty()){
                add_thongtinkhac.setError("Cho thông tin!!");
            }
            if(anhss.isEmpty()){
                add_anh.setError("Link ảnh đâu!!");
            }
            else {
                AlertDialog.Builder aler = new AlertDialog.Builder(getContext());
                aler.setCancelable(false);
                aler.setView(R.layout.progress_save_data);
                AlertDialog dialogClose = aler.create();
                dialogClose.show();

                Lobby lobby = new Lobby(tenss, quanss, anhss, diachiss, kichthuocss, soluongbanss, GiaBanLobbyss, thongtinkhacss, giass);
                DatabaseReference lobbyPush = FirebaseDatabase.getInstance().getReference().child("lobby").push();
                lobby.setKey(lobbyPush.getKey());
                lobbyPush.setValue(lobby)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getContext(), "Add success", Toast.LENGTH_SHORT).show();
                                //------------------------------
                                admin_lobbyList.add(lobby);
                                adapter.notifyDataSetChanged();
                                //-----------------------------thang kiet lam
                                InputMethodManager imm = (InputMethodManager)
                                        getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                                // Ẩn bàn phím
                                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                                dialog.dismiss();
                                dialogClose.dismiss();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        view.findViewById(R.id.clickLose_addLobby).setOnClickListener(v ->{
            InputMethodManager imm = (InputMethodManager)
                    getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

            // Ẩn bàn phím
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            dialog.dismiss();
        });
    }
}