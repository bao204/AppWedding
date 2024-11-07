package com.example.weddingpackage.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weddingpackage.DetailLobby;
import com.example.weddingpackage.R;

import com.example.weddingpackage.admin.InterfaceSelectedService;
import com.example.weddingpackage.modle.Lobby;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
public class LobbyAdapter extends RecyclerView.Adapter<LobbyAdapter.LobbyViewHolder> {

    Context context;
    ArrayList<Lobby> lobbyList;
    Lobby lobbySelected;
    ItemType itemType;
    InterfaceSelectedService interfaceSelectedService;
    int selectedPosition = RecyclerView.NO_POSITION;
    public void setInterfaceSelectedService(InterfaceSelectedService selectedService){
        this.interfaceSelectedService = selectedService;
    }
    public LobbyAdapter(Context context, ArrayList<Lobby> lobbyList, ItemType itemType) {
        this.context = context;
        this.lobbyList = lobbyList;
        this.itemType = itemType;
    }
    public enum ItemType{
        TYPE_1,
        TYPE_2,
        TYPE_3
    }

    @NonNull
    @Override
    public LobbyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(itemType == ItemType.TYPE_1){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lobby, parent, false);
            return new LobbyViewHolder(view);
        } else if (itemType == ItemType.TYPE_2) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_lobby, parent, false);
            return new LobbyViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_choose_lobby, parent, false);
            return new LobbyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull LobbyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Lobby lobby = lobbyList.get(position);

        if(itemType == ItemType.TYPE_1){
            holder.tvNameLobby.setText(lobby.getTen());
            holder.tvGia.setText(lobby.getGia());
            holder.tvQuan.setText(lobby.getQuan());
            Glide.with(holder.ImgLobby.getContext()).load(lobby.getAnh()).placeholder(R.drawable.ic_launcher_foreground).optionalCenterCrop().into(holder.ImgLobby);

            holder.tvClick_toDetailLobby.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(context, DetailLobby.class);
                    i.putExtra("ten", lobbyList.get(position).getTen());
                    i.putExtra("quan", lobbyList.get(position).getQuan());
                    i.putExtra("diachi", lobbyList.get(position).getDiachi());
                    i.putExtra("soluongban", lobbyList.get(position).getSoluongban());
                    i.putExtra("giaban", lobbyList.get(position).getGiaban());
                    i.putExtra("kichthuoc", lobbyList.get(position).getKichthuoc());
                    i.putExtra("thongtinkhac", lobbyList.get(position).getThongtinkhac());
                    i.putExtra("anh", lobbyList.get(position).getAnh());
                    i.putExtra("gia", lobbyList.get(position).getGia());
                    i.putExtra("key", lobbyList.get(position).getKey());
                    context.startActivity(i);
                }
            });
        }

        else if (itemType == ItemType.TYPE_2) {
            holder.ten.setText(lobby.getTen());
            holder.quan.setText(lobby.getQuan());

            Glide.with(holder.anh.getContext()).load(lobby.getAnh()).placeholder(R.drawable.ic_launcher_foreground).optionalCenterCrop().into(holder.anh);

            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogPlus dialogPlus = DialogPlus.newDialog(context)
                            .setContentHolder(new ViewHolder(R.layout.bottom_sheet_edit_lobby))
                            .setGravity(Gravity.TOP)
                            .setExpanded(true,920)
                            .create();

                    View ve = dialogPlus.getHolderView().getRootView();
                    ve.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            return true;
                        }
                    });

                    View view = dialogPlus.getHolderView();
                    EditText edit_ten = view.findViewById(R.id.admin_editTenLobby);
                    EditText edit_quan = view.findViewById(R.id.admin_editQuanLobby);
                    EditText edit_diachi = view.findViewById(R.id.admin_editDiaChiLobby);
                    EditText edit_soluongban = view.findViewById(R.id.admin_editSoLuongBanLobby);
                    EditText admin_editGiaBanLobby = view.findViewById(R.id.admin_editGiaBanLobby);
                    EditText edit_kichthuoc = view.findViewById(R.id.admin_editKichThuocLobby);
                    EditText edit_thongtinkhac = view.findViewById(R.id.admin_editThongTinKhacLobby);
                    EditText edit_anh = view.findViewById(R.id.admin_editAnhLobby);
                    EditText edit_gia = view.findViewById(R.id.admin_editGiaLobby);

                    edit_ten.setText(lobby.getTen());
                    edit_quan.setText(lobby.getQuan());
                    edit_diachi.setText(lobby.getDiachi());
                    edit_soluongban.setText(lobby.getSoluongban());
                    admin_editGiaBanLobby.setText(lobby.getGiaban());
                    edit_kichthuoc.setText(lobby.getKichthuoc());
                    edit_thongtinkhac.setText(lobby.getThongtinkhac());
                    edit_anh.setText(lobby.getAnh());
                    edit_gia.setText(lobby.getGia());

                    dialogPlus.show();

                    view.findViewById(R.id.clickOk_updateLobby).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String ten =  edit_ten.getText().toString().trim();
                            String quan = edit_quan.getText().toString().trim();
                            String diachi = edit_diachi.getText().toString().trim();
                            String soluongban = edit_soluongban.getText().toString().trim();
                            String giaban = admin_editGiaBanLobby.getText().toString().trim();
                            String kichthuoc = edit_kichthuoc.getText().toString().trim();
                            String thongtinkhac = edit_thongtinkhac.getText().toString().trim();
                            String anh = edit_anh.getText().toString().trim();
                            String gia = edit_gia.getText().toString().trim();

                            Lobby lobby1 = new Lobby(ten, quan, anh, diachi, kichthuoc, soluongban, giaban, thongtinkhac, gia);

                            Toast.makeText(context, lobby.getKey(), Toast.LENGTH_SHORT).show();

                            FirebaseDatabase.getInstance().getReference().child("lobby")
                                    .child(lobby.getKey()).setValue(lobby1)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show();

                                            lobby1.setKey(lobby.getKey());
                                            lobbyList.set(position, lobby1);
                                            notifyItemChanged(position);

                                            InputMethodManager input = (InputMethodManager)
                                            context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                            input.hideSoftInputFromWindow(v.getWindowToken(), 0);

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
                    view.findViewById(R.id.imgB_clickClose).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            InputMethodManager imm = (InputMethodManager)
                                    context.getSystemService(Context.INPUT_METHOD_SERVICE);

                            // Ẩn bàn phím
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                            dialogPlus.dismiss();
                        }
                    });
                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Chắc chưa");
                    builder.setPositiveButton("Rồi", (dialog, which) -> {
                        FirebaseDatabase.getInstance().getReference().child("lobby")
                                .child(lobby.getKey()).removeValue();
                        //-------------------------------------------
                        lobbyList.remove(lobby);
                        notifyItemRemoved(position);
                        notifyDataSetChanged();
                        //------------------------------
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
            Glide.with(context).load(lobby.getAnh()).placeholder(R.drawable.ic_launcher_foreground).optionalCenterCrop().into(holder.anhLobby_selected);
            holder.selected_tenLobby.setText(lobby.getTen());
            holder.quanLobby_selected.setText(lobby.getQuan());
            holder.quantilyPersonLobby_selected.setText(lobby.getSoluongban());

            boolean isSelected = position == selectedPosition; //Kiểm tra vị trí position có bằng vị trí được chọn hay không?
            if(isSelected){
                holder.clickChooseLobby_selected.setBackgroundResource(R.drawable.background_radius_green);
                lobbySelected = lobby;
                interfaceSelectedService.LobbySelected(lobbySelected);
                Toast.makeText(context, "Đã chọn sảnh", Toast.LENGTH_SHORT).show();
            }
            else {
                holder.clickChooseLobby_selected.setBackgroundResource(R.drawable.background_radius_grey);
                lobbySelected = null;
            }
            holder.itemSelected_clickToDetailLobby.setOnClickListener(v->{
                Intent i = new Intent(context, DetailLobby.class);
                i.putExtra("ten", lobbyList.get(position).getTen());
                i.putExtra("quan", lobbyList.get(position).getQuan());
                i.putExtra("diachi", lobbyList.get(position).getDiachi());
                i.putExtra("soluongban", lobbyList.get(position).getSoluongban());
                i.putExtra("giaban", lobbyList.get(position).getGiaban());
                i.putExtra("kichthuoc", lobbyList.get(position).getKichthuoc());
                i.putExtra("thongtinkhac", lobbyList.get(position).getThongtinkhac());
                i.putExtra("anh", lobbyList.get(position).getAnh());
                i.putExtra("gia", lobbyList.get(position).getGia());
                i.putExtra("key", lobbyList.get(position).getKey());
                context.startActivity(i);
            });
            
            holder.clickChooseLobby_selected.setOnClickListener(v->{
                int previous = selectedPosition;
                selectedPosition = position;
                notifyItemChanged(previous);
                notifyItemChanged(selectedPosition);
            });
        }
    }
    public void search(ArrayList<Lobby> searchL){
        lobbyList = searchL;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return lobbyList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class LobbyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameLobby, tvGia, tvQuan, tvClick_toDetailLobby;
        ImageView ImgLobby;
        TextView ten, quan, edit, delete;
        ImageView  anh;

        ImageView anhLobby_selected;
        TextView quanLobby_selected, quantilyPersonLobby_selected, selected_tenLobby, clickChooseLobby_selected, itemSelected_clickToDetailLobby;
        public LobbyViewHolder(@NonNull View itemView) {
            super(itemView);

                tvNameLobby = itemView.findViewById(R.id.tvNameLobby);
                ImgLobby = itemView.findViewById(R.id.ImgLobby);
                tvGia = itemView.findViewById(R.id.tvGia);
                tvQuan = itemView.findViewById(R.id.tvQuan);
                tvClick_toDetailLobby = itemView.findViewById(R.id.tvClick_toDetailLobby);

                ten = itemView.findViewById(R.id.admin_tvNameLooby);
                edit = itemView.findViewById(R.id.click_editLobby);
                delete = itemView.findViewById(R.id.click_deleteLobby);
                anh = itemView.findViewById(R.id.Admin_imageLobby);
                quan = itemView.findViewById(R.id.Admin_quanLobby);

                anhLobby_selected = itemView.findViewById(R.id.anhLobby_selected);
                quanLobby_selected = itemView.findViewById(R.id.quanLobby_selected);
                quantilyPersonLobby_selected = itemView.findViewById(R.id.quantilyPersonLobby_selected);
                selected_tenLobby = itemView.findViewById(R.id.selected_tenLobby);
                clickChooseLobby_selected = itemView.findViewById(R.id.clickChooseLobby_selected);
                itemSelected_clickToDetailLobby = itemView.findViewById(R.id.itemSelected_clickToDetailLobby);
        }
    }
}
