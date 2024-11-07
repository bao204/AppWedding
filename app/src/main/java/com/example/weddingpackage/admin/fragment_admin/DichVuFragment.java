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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weddingpackage.R;
import com.example.weddingpackage.adapter.DichVuAdapter;
import com.example.weddingpackage.modle.DichVu;
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

public class DichVuFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<DichVu> list;
    DichVuAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_dich_vu, container, false);
        Toast.makeText(requireContext() , "Bạn đang đến danh sách dịch vụ", Toast.LENGTH_SHORT).show();
        recyclerView = view.findViewById(R.id.recyclerView_Service);

        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        loadService();

        view.findViewById(R.id.imgB_adminClickAddService).setOnClickListener(v -> {
            addService();
        });
        return view;
    }

    void loadService(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("dichvu");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot data : snapshot.getChildren()){
                        DichVu dichVu = data.getValue(DichVu.class);
                        dichVu.setKey(data.getKey());

                        list.add(dichVu);
                    }

                    DichVuAdapter.Item item = DichVuAdapter.Item.ITEM_2;
                    adapter = new DichVuAdapter(getContext(), list, item);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}});
    }
    void addService(){
        DialogPlus dialog = DialogPlus.newDialog(getContext())
                .setContentHolder(new ViewHolder(R.layout.bottom_sheet_add_service))
                .setExpanded(true, 1000)
                .setGravity(Gravity.TOP)
                .create();
        View ve = dialog.getHolderView().getRootView();
        ve.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View view, MotionEvent motionEvent) {return true;}});

        View view = dialog.getHolderView();
        EditText ten = view.findViewById(R.id.admin_addNameService);
        EditText mota = view.findViewById(R.id.admin_addMotaService);
        EditText anh = view.findViewById(R.id.admin_addAnhService);
        EditText gia = view.findViewById(R.id.admin_addGiaService);

        dialog.show();

        ve.findViewById(R.id.clickOk_addService).setOnClickListener(v->{
            String tens = ten.getText().toString().trim();
            String motas = ten.getText().toString().trim();
            String anhs = ten.getText().toString().trim();
            String gias = ten.getText().toString().trim();
            if(tens.isEmpty()){
                ten.setError("Tên service đâu?");
            }
            if(motas.isEmpty()){
                mota.setError("Mô tả?");
            }
            if(anhs.isEmpty()){
                anh.setError("Link ảnh??");
            }
            if(gias.isEmpty()){
                gia.setError("Cho cái giá!");
            }
            else{
                AlertDialog.Builder aler = new AlertDialog.Builder(getContext());
                aler.setCancelable(false);
                aler.setView(R.layout.progress_save_data);
                AlertDialog dialogClose = aler.create();
                dialogClose.show();

                DichVu dichVu = new DichVu(tens, motas, anhs, gias);
                FirebaseDatabase.getInstance().getReference("dichvu").push().setValue(dichVu)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getContext(), "Add success", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                refresh();
                                dialogClose.dismiss();
                                InputMethodManager imm = (InputMethodManager)
                                        getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                                // Ẩn bàn phím
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Add fail", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        ve.findViewById(R.id.clickLose_addService).setOnClickListener(v->{
            InputMethodManager imm = (InputMethodManager)
                    getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

            // Ẩn bàn phím
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            dialog.dismiss();
        });

    }
    void refresh(){
        DichVuFragment dichVuFragment = (DichVuFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if(dichVuFragment.isVisible()){
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new DichVuFragment()).commit();
        }
        else {
            getParentFragmentManager().beginTransaction().detach(this).attach(this).commit();

        }
    }
}