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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weddingpackage.R;
import com.example.weddingpackage.admin.adapter.AccountUserAdapter;
import com.example.weddingpackage.modle.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.Collections;

public class AccountUserFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<User> UserList;
    AccountUserAdapter adapter;
    ImageButton imgB_adminClickAddUser;
    ImageView img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_account_user, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_AccountUser);
        imgB_adminClickAddUser = view.findViewById(R.id.imgB_adminClickAddUser);
        imgB_adminClickAddUser.setOnClickListener(v -> {
            addAccount(view);
        });

        UserList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        loadItem();
        return view;
    }
    void loadItem(){
        FirebaseDatabase.getInstance().getReference("user")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                User accountUser = dataSnapshot.getValue(User.class);
                                accountUser.setKey(dataSnapshot.getKey());

                                UserList.add(accountUser);
                                Collections.sort(UserList);
                            }
                            adapter = new AccountUserAdapter(getContext(), UserList);
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    void addAccount(View view){
        InputMethodManager inp = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        DialogPlus dialogPlus = DialogPlus.newDialog(getContext())
                .setContentHolder(new ViewHolder(R.layout.bottom_sheet_add_user))
                .setExpanded(true, 520)
                .setGravity(Gravity.TOP)
                .create();

        view = dialogPlus.getHolderView().getRootView();
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {return true;}});

        view = dialogPlus.getHolderView();
        EditText ten = view.findViewById(R.id.admin_addNameUser);
        EditText sodt = view.findViewById(R.id.admin_addSodt);
        EditText matkhau = view.findViewById(R.id.admin_addMatKhauUser);
        EditText pay = view.findViewById(R.id.admin_addPayAccount);

        dialogPlus.show();
        view.findViewById(R.id.addUser_clickAdd).setOnClickListener(v -> {

            String tens = ten.getText().toString().trim();
            String sodts = sodt.getText().toString().trim();
            String matkhaus = matkhau.getText().toString().trim();
            String pays = pay.getText().toString().trim();

            if(tens.isEmpty()){
                ten.setError("Điền tên");
                return;
            }
            if(sodts.length()  != 10){
                sodt.setError("Số điện thoại chỉ 10 số");
                return;
            }
            if(matkhaus.isEmpty())
            {
                matkhau.setError("Nhập mật khẩu");
                return;
            }
            FirebaseDatabase.getInstance().getReference("user").orderByChild("sodt").equalTo(sodts)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                sodt.setError("Số điện thoại này đã được đăng kí");
                            }
                            else {
                                AlertDialog.Builder a = new AlertDialog.Builder(getContext());
                                a.setCancelable(false);
                                a.setView(R.layout.progress_save_data);
                                AlertDialog aa = a.create();
                                aa.show();
                                User user =  new User(tens, sodts, pays, matkhaus, "mo");
                                FirebaseDatabase.getInstance().getReference("user").push().setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(getContext(), "Add success", Toast.LENGTH_SHORT).show();
                                                dialogPlus.dismiss();
                                                refresh();
                                                inp.hideSoftInputFromWindow(v.getWindowToken(), 0);
                                                aa.dismiss();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), "Add fail", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getContext(), "Add account bug", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        view.findViewById(R.id.addAccount_clickCloses).setOnClickListener(v -> {
            dialogPlus.dismiss();
            inp.hideSoftInputFromWindow(v.getWindowToken(), 0);
        });
    }
    void refresh(){
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new AccountUserFragment()).commit();
    }
}
