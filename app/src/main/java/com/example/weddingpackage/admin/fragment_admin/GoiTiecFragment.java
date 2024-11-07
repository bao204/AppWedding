package com.example.weddingpackage.admin.fragment_admin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weddingpackage.R;
import com.example.weddingpackage.adapter.CookAdapter;
import com.example.weddingpackage.adapter.DichVuAdapter;
import com.example.weddingpackage.adapter.GoiTiecAdapter;
import com.example.weddingpackage.admin.Selected_ItemsActivity;
import com.example.weddingpackage.admin.Selected_ItemsCookActivity;
import com.example.weddingpackage.admin.selectedLobby_itemActivity;
import com.example.weddingpackage.modle.Cook;
import com.example.weddingpackage.modle.DichVu;
import com.example.weddingpackage.modle.GoiTiec;
import com.example.weddingpackage.modle.Lobby;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
public class GoiTiecFragment extends Fragment {
    RecyclerView recyclerView;
    DichVuAdapter adapter;
    GoiTiecAdapter tiecAdapter;
    ArrayList<DichVu> list;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    ArrayList<DichVu> listServiceSelected;
    ArrayList<Cook> listKhaiVi;
    ArrayList<Cook> listMonChinh;
    ArrayList<Cook> listTrangMieng;
    ArrayList<Cook> listNuocUong;
    Lobby lobby;
    boolean checkLobby = false;
    boolean checkListService = false;
    boolean checkKhaiVi = false;
    boolean checkMonChinh = false;
    boolean checkTrangMieng = false;
    boolean checkNuocUong = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_goi_tiec, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_admingoiTiec);

        clickAdd(view);
        list = new ArrayList<>();

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), o -> {
            if(o.getResultCode() == Activity.RESULT_OK){
                Intent intent = o.getData();

                /*refreshData(view);*/
                
                listServiceSelected = (ArrayList<DichVu>) intent.getSerializableExtra("listSelected");
                checkListService = intent.getBooleanExtra("checkListService", false);

                lobby = (Lobby) intent.getSerializableExtra("lobbySelected");
                checkLobby = intent.getBooleanExtra("checkLobbySelected", false);

                listKhaiVi = (ArrayList<Cook>) intent.getSerializableExtra("listKhaiViChoose");
                checkKhaiVi = intent.getBooleanExtra("checkKhaiVi", false);

                checkMonChinh = intent.getBooleanExtra("checkMonChinh", false);

                checkTrangMieng = intent.getBooleanExtra("checkTrangMieng", false);

                checkNuocUong = intent.getBooleanExtra("checkNuocUong", false);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        /*loadItem();*/

        return view;
    }
    void refreshData(View view){
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                addPackage(view);
                Toast.makeText(getActivity(), "refresh data", Toast.LENGTH_SHORT).show();
                handler.postDelayed(this,100);

                /*handler.removeCallbacks(this);*/
            }
        };
        handler.postDelayed(runnable, 100);
    }
    /*void loadItem(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("dichvu");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot data : snapshot.getChildren()){
                        DichVu dichVu = data.getValue(DichVu.class);

                        list.add(dichVu);
                    }
                    DichVuAdapter.Item item = DichVuAdapter.Item.ITEM_2;
                    adapter = new DichVuAdapter(getContext(), list, item);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}});
    }*/

    @SuppressLint("ResourceAsColor")
    void addPackage(View view){

        DichVuAdapter.Item item = DichVuAdapter.Item.ITEM_1;
        CookAdapter.ItemCook itemCook = CookAdapter.ItemCook.ITEM_COOK_1;

        DialogPlus dialogPlus = DialogPlus.newDialog(getActivity())
                .setContentHolder(new ViewHolder(R.layout.bottom_sheet_add_package))
                .setExpanded(true, 1400)
                .setGravity(Gravity.TOP)
                .setCancelable(false)
                .create();

        view = dialogPlus.getHolderView();

        EditText tenPackage = view.findViewById(R.id.admin_addTenPackage);
        EditText giaPackage = view.findViewById(R.id.admin_addGiaCook);
        EditText uuDaiPackage = view.findViewById(R.id.admin_addUuDaiPackage);

        dialogPlus.show();

        ImageView showAnhLoobySelected = view.findViewById(R.id.showAnhLoobySelected);
        LinearLayout visibilityShowLobbySelected = view.findViewById(R.id.visibilityShowLobbySelected);
        TextView notiService = view.findViewById(R.id.textWarningNotChooseService);
        TextView notiLobby = view.findViewById(R.id.textWarningNotChooseLobby);
        TextView notiKhaiVi = view.findViewById(R.id.notiKhaiVi);
        TextView notiMonChinh = view.findViewById(R.id.textWarningNotChooseMonChinh);
        TextView notiTrangMieng = view.findViewById(R.id.textWarningNotChooseTrangMieng);
        TextView notiNuocUong = view.findViewById(R.id.textWarningNotChooseNuocUong);
        RecyclerView rcVService = view.findViewById(R.id.recyclerView_listItemChooseService);
        RecyclerView rcVMonKhaiVi = view.findViewById(R.id.recyclerView_listItemChooseMonAnKhaiVi);
        RecyclerView rcVMonChinh = view.findViewById(R.id.recyclerView_listItemChooseMonAnMonChinh);
        RecyclerView rcVMonTrangMieng = view.findViewById(R.id.recyclerView_listItemChooseMonAnTrangMieng);
        RecyclerView rcVMonNuocUong = view.findViewById(R.id.recyclerView_listItemChooseMonAnNuocUong);

        ImageView clickCancelCloseImageLobbyChoose = view.findViewById(R.id.clickCancelCloseImageLobbyChoose);
        ImageView clickCancelListService = view.findViewById(R.id.clickCancelListService);
        ImageView clickCancelListKhaiVi = view.findViewById(R.id.clickCancelListKhaiVi);
        ImageView clickCancelListMonChinh = view.findViewById(R.id.clickCancelListMonChinh);
        ImageView clickCancelListTrangMieng = view.findViewById(R.id.clickCancelListTrangMieng);
        ImageView clickCancelListNuocUong = view.findViewById(R.id.clickCancelListNuocUong);

        LinearLayout clickHideIconCancleService = view.findViewById(R.id.clickHideIconCancleService);
        LinearLayout clickHideIconCancleKhaiVi = view.findViewById(R.id.clickHideIconCancleKhaiVi);
        LinearLayout clickHideIconCancleMonChinh = view.findViewById(R.id.clickHideIconCancleMonChinh);
        LinearLayout clickHideIconCancleTrangMieng = view.findViewById(R.id.clickHideIconCancleTrangMieng);
        LinearLayout clickHideIconCancleNuocUong = view.findViewById(R.id.clickHideIconCancleNuocUong);

        Button btnClicktoSelectedItem = view.findViewById(R.id.btnClicktoSelectedItem);

        view.findViewById(R.id.clicktoSelectedChooseLobby).setOnClickListener(v->{
            if(!checkLobby){
                Intent intent = new Intent(getActivity(), selectedLobby_itemActivity.class);
                activityResultLauncher.launch(intent);
                notiLobby.setVisibility(View.GONE);
            }
            else {
                visibilityShowLobbySelected.setVisibility(View.VISIBLE);
                Glide.with(getActivity()).load(lobby.getAnh()).placeholder(R.drawable.ic_launcher_foreground).optionalCenterCrop().into(showAnhLoobySelected);
                clickCancelCloseImageLobbyChoose.setOnClickListener(vi->{
                    lobby = null;
                    checkLobby = false;
                    visibilityShowLobbySelected.setVisibility(View.GONE);
                });
            }
        });

        btnClicktoSelectedItem.setOnClickListener(v->{
            if(!checkListService){
                Intent intent = new Intent(new Intent(getActivity(), Selected_ItemsActivity.class));
                activityResultLauncher.launch(intent);
                notiService.setVisibility(View.GONE);
                /*notiService.setVisibility(!checkListService ? View.GONE : View.VISIBLE);*/
            }
            else {
                clickHideIconCancleService.setVisibility(View.VISIBLE);
                rcVService.setVisibility(View.VISIBLE);
                rcVService.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                DichVuAdapter aadapter = new DichVuAdapter(getActivity(), listServiceSelected, item);
                rcVService.setAdapter(aadapter);
                clickCancelListService.setOnClickListener(vi->{
                    btnClicktoSelectedItem.setBackgroundResource(R.drawable.backgroud_textview_white_radius);
                    btnClicktoSelectedItem.setTextColor(R.color.white);
                    listServiceSelected = null;
                    rcVService.setVisibility(View.GONE);
                    clickHideIconCancleService.setVisibility(View.GONE);
                    checkListService = false;
                });
            }
        });

        view.findViewById(R.id.btnClicktoSelectedItemMonAnKhaiVi).setOnClickListener(v->{
            if(!checkKhaiVi){
                Intent intent = new Intent(new Intent(getActivity(), Selected_ItemsCookActivity.class));
                intent.putExtra("idCook", "1");
                activityResultLauncher.launch(intent);
                notiKhaiVi.setVisibility(View.GONE);
            }
            else {

                rcVMonKhaiVi.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                CookAdapter aadapter = new CookAdapter(getActivity(), listKhaiVi, itemCook);
                rcVMonKhaiVi.setAdapter(aadapter);
                checkKhaiVi = false;
            }
        });

        view.findViewById(R.id.btnClicktoSelectedItemMonAnMonChinh).setOnClickListener(v->{

            if(!checkMonChinh){
                Intent intent = new Intent(new Intent(getActivity(), Selected_ItemsCookActivity.class));
                intent.putExtra("idCook", "2");
                activityResultLauncher.launch(intent);
                notiMonChinh.setVisibility(View.GONE);
            }
            else {
                listMonChinh = listKhaiVi;
                rcVMonChinh.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                CookAdapter aadapter = new CookAdapter(getActivity(), listMonChinh, itemCook);
                rcVMonChinh.setAdapter(aadapter);
                checkMonChinh = false;
            }
        });

        view.findViewById(R.id.btnClicktoSelectedItemMonAnTrangMieng).setOnClickListener(v->{
            if(!checkTrangMieng){
                Intent intent = new Intent(new Intent(getActivity(), Selected_ItemsCookActivity.class));
                intent.putExtra("idCook", "3");
                activityResultLauncher.launch(intent);
                notiTrangMieng.setVisibility(View.GONE);
            }
            else {
                listTrangMieng = listKhaiVi;
                rcVMonTrangMieng.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                CookAdapter adapter = new CookAdapter(getActivity(), listTrangMieng, itemCook);
                rcVMonTrangMieng.setAdapter(adapter);
                checkTrangMieng = false;
            }
        });

        view.findViewById(R.id.btnClicktoSelectedItemMonAnNhuocUong).setOnClickListener(v->{
            if(!checkNuocUong){
                Intent intent = new Intent(new Intent(getActivity(), Selected_ItemsCookActivity.class));
                intent.putExtra("idCook", "4");
                activityResultLauncher.launch(intent);
                notiNuocUong.setVisibility(View.GONE);
            }
            else {
                listNuocUong = listKhaiVi;
                rcVMonNuocUong.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                CookAdapter adapter = new CookAdapter(getActivity(), listNuocUong, itemCook);
                rcVMonNuocUong.setAdapter(adapter);
                checkNuocUong = false;
            }
        });

        view.findViewById(R.id.addPackage_clickCloses).setOnClickListener(v->{
            dialogPlus.dismiss();
        });

        view.findViewById(R.id.addCook_clickAdd).setOnClickListener(v->{
            String ten = tenPackage.getText().toString().trim();
            String gia = giaPackage.getText().toString().trim();
            String uudai = uuDaiPackage.getText().toString().trim();

            if(ten.isEmpty()){
                tenPackage.setError("Vui lòng nhập tên gói tiệc");
                return;
            } if (gia.isEmpty()) {
                giaPackage.setError("Nhâp giá");
                return;
            } if (uudai.isEmpty()) {
                uuDaiPackage.setError("Nhập ưu đãi");
            } /*if (lobby == null) {
                notiLobby.setText(" *Vui long chon sanh");
                notiLobby.setVisibility(View.VISIBLE);
                return;
            } if (listServiceSelected == null) {
                notiService.setText(" *Vui long chon dich vu");
                notiService.setVisibility(View.VISIBLE);
                return;
            }if (listKhaiVi == null){
                notiKhaiVi.setText(" *Vui long chon mon khai vi");
                notiKhaiVi.setVisibility(View.VISIBLE);
                return;
            }if (listMonChinh == null){
                notiMonChinh.setText(" *Vui long chon mon chinh");
                notiMonChinh.setVisibility(View.VISIBLE);
                return;
            }if (listTrangMieng == null){
                notiTrangMieng.setText(" *Vui long chon mon trang mieng");
                notiTrangMieng.setVisibility(View.VISIBLE);
                return;
            }if(listNuocUong == null){
                notiNuocUong.setText(" *Vui long chon nuoc uong");
                notiNuocUong.setVisibility(View.VISIBLE);
                return;
            }*/

            ArrayList<GoiTiec> listPackage = new ArrayList<>();
            listPackage.add(new GoiTiec(lobby, listKhaiVi, listMonChinh, listTrangMieng, listNuocUong, listServiceSelected, ten, gia, uudai));
            tiecAdapter = new GoiTiecAdapter(listPackage, getContext());
            recyclerView.setAdapter(adapter);

            dialogPlus.dismiss();
        });

    }

    /*private void getList() {
        ArrayList<GoiTiec> list = new ArrayList<>();

        ArrayList<Lobby> listLobby = new ArrayList<>();
        ArrayList<DichVu> listDichVu = new ArrayList<>();
        ArrayList<Cook> listCook = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference("lobby")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot data : snapshot.getChildren()) {
                                Lobby lobby = data.getValue(Lobby.class);
                                lobby.setKey(data.getKey());

                                listLobby.add(lobby);
                            }
                            ArrayList<Lobby> getLobby = new ArrayList<>();
                            getLobby.add(listLobby.get(1));
                            Lobby lobby = getLobby.get(getLobby.size() - 1);

                            FirebaseDatabase.getInstance().getReference("dichvu")
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if(snapshot.exists()){
                                                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                                             DichVu dichVu = dataSnapshot.getValue(DichVu.class);
                                                             dichVu.setKey(dataSnapshot.getKey());

                                                            listDichVu.add(dichVu);
                                                        }
                                                        FirebaseDatabase.getInstance().getReference("monan")
                                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                        if(snapshot.exists()){
                                                                            for (DataSnapshot data : snapshot.getChildren()){
                                                                                Cook cook = data.getValue(Cook.class);
                                                                                cook.setKey(data.getKey());

                                                                                listCook.add(cook);
                                                                            }
                                                                            list.add(new GoiTiec(lobby, listCook, listDichVu, "COMBO A", "10000000", "1111"));
                                                                            adapter = new GoiTiecAdapter(list, getContext());
                                                                            recyclerView.setAdapter(adapter);
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError error) {
                                                                    }
                                                                });
                                                    }
                                                }
                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {}});
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }*/
    void clickAdd(View view){
        view.findViewById(R.id.imgB_adminClickAddPackage).setOnClickListener(v->{
            addPackage(view);
        });
    }
}