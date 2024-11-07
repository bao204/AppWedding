package com.example.weddingpackage.adapter;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weddingpackage.DetailLobby;
import com.example.weddingpackage.DetailService_Activity;
import com.example.weddingpackage.R;
import com.example.weddingpackage.admin.AdminActivity;
import com.example.weddingpackage.admin.InterfaceSelectedService;
import com.example.weddingpackage.admin.fragment_admin.DichVuFragment;
import com.example.weddingpackage.modle.DichVu;
import com.example.weddingpackage.modle.Lobby;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DichVuAdapter extends RecyclerView.Adapter<DichVuAdapter.ServiceViewHolder>{
    ArrayList<DichVu> list;
    ArrayList<DichVu> listSelected = new ArrayList<>();
    Context context;
    Item item;

    InterfaceSelectedService interfaceSelectedService;
    public DichVuAdapter(Context context, ArrayList<DichVu> list, Item item){
        this.context=context;
        this.list = list;
        this.item = item;
    }

    public void setInterfaceSelectedService(InterfaceSelectedService selectedService) {
        this.interfaceSelectedService = selectedService;
    }

    public enum Item{
        ITEM_1,
        ITEM_2,
        ITEM_3
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(item == Item.ITEM_1){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service,parent,false);
            return new ServiceViewHolder(view);
        } else if (item == Item.ITEM_2) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_service, parent,false);
            return new ServiceViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected, parent,false);
            return new ServiceViewHolder(view);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DichVu model = list.get(position);

        if(item == Item.ITEM_1){
            holder.ten.setText(model.getTen());
            holder.gia.setText(model.getGia());
            Glide.with(context).load(model.getAnh()).placeholder(R.drawable.ic_launcher_foreground).optionalCenterCrop().into(holder.anh);

            holder.click_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailService_Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("model", model);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
        else if(item == Item.ITEM_2){
            holder.admin_ten.setText(model.getTen());
            Glide.with(context).load(model.getAnh()).placeholder(R.drawable.ic_launcher_foreground).optionalCenterCrop().into(holder.admin_anh);

            holder.edit.setOnClickListener(v ->{
                DialogPlus dialogPlus = DialogPlus.newDialog(context)
                        .setContentHolder(new ViewHolder(R.layout.bottom_sheet_edit_service))
                        .setExpanded(true, 950)
                        .setGravity(Gravity.TOP)
                        .create();

                View ve = dialogPlus.getHolderView().getRootView();
                ve.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });

                View view = dialogPlus.getHolderView();
                EditText name = view.findViewById(R.id.admin_editNameService);
                EditText mota = view.findViewById(R.id.admin_editMotaService);
                EditText anh = view.findViewById(R.id.admin_editAnhService);
                EditText gia = view.findViewById(R.id.admin_editGiaService);

                name.setText(model.getTen());
                mota.setText(model.getMota());
                anh.setText(model.getAnh());
                gia.setText(model.getGia());

                dialogPlus.show();

                view.findViewById(R.id.admin_UpdateService).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tens = name.getText().toString().trim();
                        String motas = mota.getText().toString().trim();
                        String anhs = anh.getText().toString().trim();
                        String gias = gia.getText().toString().trim();

                        DichVu dichVu = new DichVu(tens, motas, anhs, gias);

                        FirebaseDatabase.getInstance().getReference().child("dichvu")
                                .child(model.getKey()).setValue(dichVu)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show();
                                        dichVu.setKey(model.getKey());
                                        list.set(position, dichVu);
                                        notifyItemChanged(position);

                                        InputMethodManager imm = (InputMethodManager)
                                                context.getSystemService(Context.INPUT_METHOD_SERVICE);

                                        // Ẩn bàn phím
                                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Update fail", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
                view.findViewById(R.id.edit_clickClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InputMethodManager imm = (InputMethodManager)
                                context.getSystemService(Context.INPUT_METHOD_SERVICE);

                        // Ẩn bàn phím
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        dialogPlus.dismiss();
                    }
                });
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Chắc chưa");
                    builder.setPositiveButton("Rồi", (dialog, which) -> {
                        FirebaseDatabase .getInstance().getReference().child("dichvu")
                                .child(model.getKey()).removeValue();

                        list.remove(model);
                        notifyItemRemoved(position);
                        notifyDataSetChanged();

                        Toast.makeText(context, "Delete Success", Toast.LENGTH_SHORT).show();
                    });
                    builder.setNegativeButton("Chưa", (dialog, which) -> {
                        Toast.makeText(context, "Delete Fail", Toast.LENGTH_SHORT).show();
                    });
                    builder.show();
                }
            });
        }
        else {
            DichVu dichVu = list.get(position);
            Glide.with(context).load(dichVu.getAnh()).placeholder(R.drawable.ic_launcher_foreground).optionalCenterCrop().into(holder.anhService_selected);
            holder.tenService_selected.setText(dichVu.getTen());

            holder.clicktoDetail_selected.setOnClickListener(v->{
                Intent intent = new Intent(context, DetailService_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("model", dichVu);
                intent.putExtras(bundle);
                context.startActivity(intent);
            });

            holder.clickChoose.setOnClickListener(v->{
                if(!listSelected.contains(list.get(position))){
                    listSelected.add(list.get(position));
                    holder.clickChoose.setBackgroundResource(R.drawable.background_radius_green);
                    Toast.makeText(context, "Đã chọn dịch vụ " + dichVu.getTen(), Toast.LENGTH_SHORT).show();
                    interfaceSelectedService.ArrayListServiceSelected(listSelected);
                }
                else{
                    listSelected.remove(list.get(position));
                    Toast.makeText(context, "Đã bỏ chọn dịch vụ " + dichVu.getTen(), Toast.LENGTH_SHORT).show();
                    holder.clickChoose.setBackgroundResource(R.drawable.background_radius_grey);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void search(ArrayList<DichVu> searchL){
        list = searchL;
        notifyDataSetChanged();
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder {
        TextView ten, gia, click_detail;
        ImageView anh;

        TextView admin_ten, edit, delete;
        ImageView admin_anh;

        TextView clicktoDetail_selected, tenService_selected, clickChoose;
        ImageView anhService_selected;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            ten = itemView.findViewById(R.id.tvTenService);
            anh = itemView.findViewById(R.id.imgMoTaService);
            gia = itemView.findViewById(R.id.tvGiaDichVu);
            click_detail = itemView.findViewById(R.id.tvClick_toServicelLobby);

            clickChoose = itemView.findViewById(R.id.clickChoose_selected);

            admin_anh = itemView.findViewById(R.id.Admin_imageService);
            admin_ten = itemView.findViewById(R.id.admin_tvNameService);

            edit = itemView.findViewById(R.id.click_editService);
            delete = itemView.findViewById(R.id.click_deleteService);

            clicktoDetail_selected = itemView.findViewById(R.id.itemSelected_clickToDetailService);
            tenService_selected = itemView.findViewById(R.id.selected_tenService);
            anhService_selected = itemView.findViewById(R.id.anhService_selected);

        }
    }
}
