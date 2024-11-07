package com.example.weddingpackage.admin.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weddingpackage.R;
import com.example.weddingpackage.R.drawable;
import com.example.weddingpackage.modle.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;

public class AccountUserAdapter extends RecyclerView.Adapter<AccountUserAdapter.AccountUserViewHolder>{
    ArrayList<User> list;
    Context context;
    public AccountUserAdapter(Context contexts, ArrayList<User> list){
        this.context = contexts;
        this.list = list;
    }
    @NonNull
    @Override
    public AccountUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_account_user, parent, false);
        return new AccountUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountUserViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User account = list.get(position);
        holder.sodt.setText(account.getSodt());
        holder.ten.setText(account.getTen());

        if(account.getTrangthai().equals("khoa")){
            holder.img.setImageResource(drawable.icon_circle_red);
            holder.trangthai.setText(account.getTrangthai());
        }
        else {
            holder.img.setImageResource(drawable.icon_circle_green);
            holder.trangthai.setText(account.getTrangthai());
        }

        holder.edit.setOnClickListener(v ->{
            DialogPlus dialogPlus = DialogPlus.newDialog(context)
                    .setContentHolder(new ViewHolder(R.layout.bottom_sheet_edit_account))
                    .setExpanded(true, 800)
                    .setGravity(Gravity.TOP)
                    .create();

            v = dialogPlus.getHolderView().getRootView();
            v.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {return true;}});
            v = dialogPlus.getHolderView();

            EditText ten = v.findViewById(R.id.admin_editNameAccount);
            EditText sodt = v.findViewById(R.id.admin_editSoDT);
            EditText matkhau = v.findViewById(R.id.admin_editPassAccount);
            EditText pay = v.findViewById(R.id.admin_editPayAccount);
            EditText trangthai = v.findViewById(R.id.admin_editTrangThaiAccount);

            ten.setText(account.getTen());
            sodt.setText(account.getSodt());
            matkhau.setText(account.getMatkhau());
            pay.setText(account.getPay());
            trangthai.setText(account.getTrangthai());

            dialogPlus.show();

            v.findViewById(R.id.clickOk_updateAccountUser).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tens = ten.getText().toString().trim();
                    String sodts = sodt.getText().toString().trim();
                    String matkhaus = matkhau.getText().toString().trim();
                    String pays = pay.getText().toString().trim();
                    String trangthais = trangthai.getText().toString().trim();
                    User accountUser = new User(tens,sodts, pays, matkhaus, trangthais);
                    FirebaseDatabase.getInstance().getReference("user").child(account.getKey()).setValue(accountUser)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    accountUser.setKey(account.getKey());
                                    list.set(position, accountUser);
                                    notifyDataSetChanged();

                                    InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                    in.hideSoftInputFromWindow(v.getWindowToken(), 0);
                                    dialogPlus.dismiss();
                                    Toast.makeText(context, "Edit success", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Edit fail", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });
            v.findViewById(R.id.editAccountUser_clickClose).setOnClickListener(vi->{
                InputMethodManager inp = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inp.hideSoftInputFromWindow(vi.getWindowToken(), 0);
                dialogPlus.dismiss();
            });
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Chắc chưa");
                builder.setPositiveButton("Rồi", (dialog, which) -> {
                    FirebaseDatabase .getInstance().getReference().child("user")
                            .child(account.getKey()).removeValue();

                    list.remove(account);
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

        holder.block.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            if(account.getTrangthai().equals("khoa")){
                builder.setTitle("Bạn muốn mở khóa? ");
                builder.setPositiveButton("Đúng vậy", (dialog, which) -> {

                    holder.img.setImageResource(drawable.icon_circle_green);
                    account.setTrangthai("mo");
                    FirebaseDatabase.getInstance().getReference("user").child(account.getKey()).setValue(account)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    account.getKey();
                                    list.set(position, account);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Đã mở khóa account "+ account.getSodt() + " success", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Account " + account.getSodt() + " mở khóa thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });

                });
                builder.setNegativeButton("Không", (dialog, which) -> {
                    Toast.makeText(context, "Đã hủy", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                });
                AlertDialog aler = builder.create();
                aler.setCancelable(false);
                aler.show();
            }
            else {
                builder.setTitle("Bạn muốn khóa account " + account.getSodt() + " à?");
                builder.setPositiveButton("Khóa", (dialog, which) -> {

                    holder.img.setImageResource(drawable.icon_circle_red);
                    account.setTrangthai("khoa");
                    FirebaseDatabase.getInstance().getReference("user").child(account.getKey()).setValue(account)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    account.getKey();
                                    list.set(position, account);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Account "+ account.getSodt() + " đã bị khóa" , Toast.LENGTH_SHORT).show();

                                    dialog.dismiss();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Khóa account " + account.getSodt() + " fail", Toast.LENGTH_SHORT).show();
                                }
                            });

                });
                builder.setNegativeButton("Hủy", (dialog, which) -> {
                    Toast.makeText(context, "Đã hủy", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                });
                AlertDialog aler = builder.create();
                aler.setCancelable(false);
                aler.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AccountUserViewHolder extends RecyclerView.ViewHolder{

        TextView sodt, ten, trangthai;
        ImageButton edit, delete, block;

        ImageView img;
        public AccountUserViewHolder(@NonNull View itemView) {
            super(itemView);

            sodt = itemView.findViewById(R.id.admin_sdtAccountUser);
            ten = itemView.findViewById(R.id.admin_nameAccountUser);
            trangthai = itemView.findViewById(R.id.admin_trangthaiAccountUser);
            edit = itemView.findViewById(R.id.edit_clickAccountUser);
            delete = itemView.findViewById(R.id.delete_clickAccountUser);
            block = itemView.findViewById(R.id.block_clickAccountUser);

            img =itemView.findViewById(R.id.statusAccount);
        }
    }
}
