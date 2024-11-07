package com.example.weddingpackage.admin.fragment_admin;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weddingpackage.R;
import com.example.weddingpackage.adapter.CookAdapter;
import com.example.weddingpackage.admin.AdminActivity;
import com.example.weddingpackage.modle.Cook;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class MonAnFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Cook> list;
    CookAdapter adapter;
    ImageButton imgB_adminClickAddCook;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_mon_an, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_Cook);
        imgB_adminClickAddCook = view.findViewById(R.id.imgB_adminClickAddCook);
        imgB_adminClickAddCook.setOnClickListener(v -> {
            addCook(v);
        });

        list = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);

        loadItem();

        return view;
    }
    void loadItem(){
        DatabaseReference a = FirebaseDatabase.getInstance().getReference("monan");
                a.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot data : snapshot.getChildren()){
                                Cook cook = data.getValue(Cook.class);
                                cook.setKey(data.getKey());

                                list.add(cook);
                                Collections.sort(list);
                            }
                            CookAdapter.ItemCook item = CookAdapter.ItemCook.ITEM_COOK_2;
                            adapter = new CookAdapter(getContext(), list, item);

                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
    }
    void tooRuomRa(String anhs, String gias, String loais, String motas, String tens, String ids, DialogPlus dialogPlus, View view){
        AlertDialog.Builder aler = new AlertDialog.Builder(getContext());
        aler.setCancelable(false);
        aler.setView(R.layout.progress_save_data);
        AlertDialog dialog = aler.create();
        dialog.show();
        Cook cook = new Cook(anhs, gias, loais, motas, tens, ids);
        FirebaseDatabase.getInstance().getReference("monan")
                .push().setValue(cook)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Add success", Toast.LENGTH_SHORT).show();
                        refresh();
                        dialogPlus.dismiss();
                        dialog.dismiss();
                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Add fail", Toast.LENGTH_SHORT).show();
                        dialogPlus.dismiss();
                    }
                });
    }
    void addCook(View view){
        DialogPlus dialogPlus = DialogPlus.newDialog(getContext())
                .setContentHolder(new ViewHolder(R.layout.bottom_sheet_add_cook))
                .setExpanded(true, 1000)
                .setGravity(Gravity.TOP)
                .create();

        view = dialogPlus.getHolderView().getRootView();
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {return true;}});

        view = dialogPlus.getHolderView();
        EditText ten = view.findViewById(R.id.admin_addNameCook);
        EditText anh = view.findViewById(R.id.admin_addAnhCook);
        EditText loai = view.findViewById(R.id.admin_addLoaiCook);
        EditText mota = view.findViewById(R.id.admin_addMotaCook);
        EditText gia = view.findViewById(R.id.admin_addGiaCook);

        dialogPlus.show();

        view.findViewById(R.id.addCook_clickAdd).setOnClickListener(v->{
            String tens = ten.getText().toString().trim();
            String anhs = anh.getText().toString().trim();
            String loais = loai.getText().toString().trim();
            String motas = mota.getText().toString().trim();
            String gias = gia.getText().toString().trim();

            if(loais.trim().toLowerCase().contains("khai vi")){
                tooRuomRa(anhs, gias, "Khai vị", motas, tens, "1", dialogPlus, v);
                return;
            }if (loais.trim().toLowerCase().contains("mon chinh")) {
                tooRuomRa(anhs, gias, "Món chính", motas, tens, "2", dialogPlus, v);
                return;
            } if (loais.trim().toLowerCase().contains("trang mieng")) {
                tooRuomRa(anhs, gias, "Tráng miệng", motas, tens, "3", dialogPlus, v);
                return;
            } if (loais.trim().toLowerCase().contains("do uong")) {
                tooRuomRa(anhs, gias, "Đồ uống", motas, tens, "4", dialogPlus, v);
            }
            else {
                loai.setError("Không đọc lưu ý à?");
            }
        });
        view.findViewById(R.id.addCook_clickCloses).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPlus.dismiss();
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
    }
    void refresh(){
        MonAnFragment monAnFragment = (MonAnFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if(monAnFragment.isVisible()){
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new MonAnFragment()).commit();
        }
        else {
            getParentFragmentManager().beginTransaction().detach(this).attach(this).commit();

        }
    }
}