package com.example.weddingpackage.adapter;

import static androidx.core.content.ContentProviderCompat.requireContext;
import static androidx.core.content.ContextCompat.getSystemService;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weddingpackage.DetailCook;
import com.example.weddingpackage.admin.InterfaceSelectedService;
import com.example.weddingpackage.modle.Cook;
import com.example.weddingpackage.R;
import com.example.weddingpackage.modle.DichVu;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CookAdapter extends RecyclerView.Adapter<CookAdapter.CookViewHolder>{
    ArrayList<Cook> cookList;
    ArrayList<Cook> listCookSelected = new ArrayList<>();
    Context context;
    ItemCook itemCook;
    InterfaceSelectedService interfaceSelectedService;
    public void setData(ArrayList<Cook> cookList){
        this.cookList = cookList;
        notifyDataSetChanged();
    }

    public void setInterfaceSelectedService(InterfaceSelectedService interfaceSelectedService){
        this.interfaceSelectedService = interfaceSelectedService;
    }

    public CookAdapter(Context context, ArrayList<Cook> cookList, ItemCook itemCook)
    {
        this.context = context;
        this.cookList = cookList;
        this.itemCook = itemCook;
    }
    public enum ItemCook{
        ITEM_COOK_1,
        ITEM_COOK_2,
        ITEM_COOK_3
    }
    @Override
    public CookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(itemCook == ItemCook.ITEM_COOK_1){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_chinh, parent, false);
            return new CookViewHolder(view);
        } else if (itemCook == ItemCook.ITEM_COOK_2) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_cook, parent, false);
            return new CookViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_choose_cook, parent, false);
            return new CookViewHolder(view);
        }

    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull CookViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Cook cook = cookList.get(position);

        if(itemCook == ItemCook.ITEM_COOK_1){
            holder.tvNameCook.setText(cook.getTen());
            holder.tvGia_foodChinh.setText(cook.getGia());
            Glide.with(holder.ImgCook.getContext()).load(cook.getAnh()).placeholder(R.drawable.ic_launcher_foreground).optionalCenterCrop().into(holder.ImgCook);


            holder.tvClick_toFoodLobby.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailCook.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("objCook", cook);
                    intent.putExtras(bundle);
                    context.startActivity(intent);

                }
            });
        }
        else if (itemCook == ItemCook.ITEM_COOK_2) {
            holder.ten.setText(cook.getTen());
            holder.loai.setText(cook.getLoai());
            Glide.with(context).load(cook.getAnh()).placeholder(R.drawable.ic_launcher_foreground).optionalCenterCrop().into(holder.anh);

            holder.edit.setOnClickListener(v->{
                DialogPlus dialogPlus = DialogPlus.newDialog(context)
                        .setContentHolder(new ViewHolder(R.layout.bottom_sheet_edit_cook))
                        .setGravity(Gravity.TOP)
                        .setExpanded(true,1400)
                        .create();

                View ve = dialogPlus.getHolderView().getRootView();
                ve.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });

                View view = dialogPlus.getHolderView();
                EditText name = view.findViewById(R.id.admin_editNameCook);
                EditText mota = view.findViewById(R.id.admin_editMotaCook);
                EditText loai = view.findViewById(R.id.admin_editLoaiCook);
                EditText anh = view.findViewById(R.id.admin_editAnhCook);
                EditText gia = view.findViewById(R.id.admin_editGiaCook);
                EditText id = view.findViewById(R.id.admin_editIdCook);
                id.isEnabled();

                name.setText(cook.getTen());
                loai.setText(cook.getLoai());
                mota.setText(cook.getMota());
                anh.setText(cook.getAnh());
                gia.setText(cook.getGia());
                id.setText(cook.getId());

                dialogPlus.show();

                view.findViewById(R.id.clickOk_updateCook).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String tens = name.getText().toString().trim();
                        String loais = loai.getText().toString().trim();
                        String motas = mota.getText().toString().trim();
                        String anhs = anh.getText().toString().trim();
                        String gias = gia.getText().toString().trim();

                        if(loais.trim().toLowerCase().contains("khai vi")){
                            ruomra(cook, anhs, gias, "Khai vị", motas, tens, "1", position, dialogPlus, v);
                            return;
                        }if (loais.trim().toLowerCase().contains("mon chinh")) {
                            ruomra(cook, anhs, gias, "Món chính", motas, tens, "2", position, dialogPlus, v);
                            return;
                        } if (loais.trim().toLowerCase().contains("trang mieng")) {
                            ruomra(cook, anhs, gias, "Tráng miệng", motas, tens, "3", position, dialogPlus, v);
                            return;
                        } if (loais.trim().toLowerCase().contains("do uong")) {
                            ruomra(cook, anhs, gias, "Đồ uống", motas, tens, "4", position, dialogPlus, v);
                        }
                        else {
                            loai.setError("Không đọc lưu ý à?");
                        }
                    }
                });

                view.findViewById(R.id.editCook_clickClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
                        FirebaseDatabase .getInstance().getReference().child("monan")
                                .child(cook.getKey()).removeValue();

                        cookList.remove(cook);
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
            Cook cook1 = cookList.get(position);
            Glide.with(context).load(cook1.getAnh()).placeholder(R.drawable.ic_launcher_foreground).optionalCenterCrop().into(holder.anhCook_selected);
            holder.selected_tenCook.setText(cook1.getTen());
            holder.loaiCook_selected.setText(cook1.getLoai());

            holder.itemSelected_clickToDetailCook.setOnClickListener(v->{
                Intent intent = new Intent(context, DetailCook.class);
                intent.putExtra("objCook", cook);
                context.startActivity(intent);
            });

            holder.clickChooseCook_selected.setOnClickListener(v->{
                if(!listCookSelected.contains(cookList.get(position))){
                    listCookSelected.add(cookList.get(position));
                    Toast.makeText(context, "Bạn đã chọn món " + cook1.getTen(), Toast.LENGTH_SHORT).show();
                    holder.clickChooseCook_selected.setBackgroundResource(R.drawable.background_radius_green);
                    if(interfaceSelectedService == null){
                        Toast.makeText(context, "BUG interfaceSelectedService CookAdapter.java", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    interfaceSelectedService.ArrayListCookSelected(listCookSelected);
                }
                else {
                    holder.clickChooseCook_selected.setBackgroundResource(R.drawable.background_radius_grey);
                    listCookSelected.remove(cookList.get(position));
                    Toast.makeText(context, "Bạn đã bỏ chọn món " + cook1.getTen(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @Override
    public int getItemCount() {// trả về số item trong danh sách(RecyclerView)
        if(cookList == null){
            Toast.makeText(context, "BUG CookAdapter.size", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return cookList.size();
    }
    @Override
    public int getItemViewType(int position) {return position;}

    public void search(ArrayList<Cook> searchL){
        cookList = searchL;
        notifyDataSetChanged();
    }

    void ruomra(Cook cook, String anhs, String gias, String loais, String motas, String tens, String ids, int position, DialogPlus dialogPlus, View view){
        Cook cook1 = new Cook(anhs, gias, loais, motas, tens, ids);
        FirebaseDatabase.getInstance().getReference().child("monan")
                .child(cook.getKey()).setValue(cook1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        cook1.setKey(cook.getKey());
                        cookList.set(position, cook1);
                        notifyItemChanged(position);
                        Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show();
                        dialogPlus.dismiss();

                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Update fail", Toast.LENGTH_SHORT).show();
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        dialogPlus.dismiss();
                    }
                });
    }

    class CookViewHolder extends RecyclerView.ViewHolder{
        TextView tvNameCook, tvGia_foodChinh, tvClick_toFoodLobby;
        ImageView ImgCook;

        TextView ten, loai, edit, delete;
        ImageView anh;

        ImageView anhCook_selected;
        TextView selected_tenCook, loaiCook_selected, itemSelected_clickToDetailCook, clickChooseCook_selected;
        public CookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameCook = itemView.findViewById(R.id.tvNameCook);
            tvGia_foodChinh = itemView.findViewById(R.id.tvGia_foodChinh);
            ImgCook = itemView.findViewById(R.id.ImgCook);
            tvClick_toFoodLobby = itemView.findViewById(R.id.tvClick_toFoodLobby);

            anh = itemView.findViewById(R.id.Admin_imageCook);
            ten = itemView.findViewById(R.id.admin_tvNameCook);
            loai = itemView.findViewById(R.id.Admin_loaiCook);
            edit = itemView.findViewById(R.id.click_editCook);
            delete = itemView.findViewById(R.id.click_deleteCook);

            anhCook_selected = itemView.findViewById(R.id.anhCook_selected);
            selected_tenCook = itemView.findViewById(R.id.selected_tenCook);
            loaiCook_selected = itemView.findViewById(R.id.loaiCook_selected);
            itemSelected_clickToDetailCook = itemView.findViewById(R.id.itemSelected_clickToDetailCook);
            clickChooseCook_selected = itemView.findViewById(R.id.clickChooseCook_selected);

        }
    }
}
