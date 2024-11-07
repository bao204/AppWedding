package com.example.weddingpackage.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.weddingpackage.ChangePassActivity;
import com.example.weddingpackage.DetailAccount;
import com.example.weddingpackage.LoginActivity;
import com.example.weddingpackage.R;
import com.example.weddingpackage.modle.User;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class CaiDatFragment extends Fragment {
    TextView textView_logOut, tvInformation, tvContact, tvChangePass;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        TextView settingAccount_ten = view.findViewById(R.id.settingAccount_ten);
        Bundle bundle = getArguments();
        if(bundle != null){
            User user = (User) bundle.get("user");
            settingAccount_ten.setText(user.getTen());
        }
        else {
            settingAccount_ten.setText("USER NULL");
        }
        textView_logOut = view.findViewById(R.id.textView_logOut);

        textView_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                Toast.makeText(getActivity(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            }
        });

        tvInformation = view.findViewById(R.id.textView_information);
        tvInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getArguments();
                startActivity(new Intent(getActivity(), DetailAccount.class).putExtras(bundle));
            }
        });

        tvContact = view.findViewById(R.id.textView_contact);
        tvContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPlus dialogPlus = DialogPlus.newDialog(getContext()).setContentHolder(new ViewHolder(R.layout.dialogplus_contact)).setExpanded(true, 900).create();
                View view = dialogPlus.getHolderView();
                ImageView img = view.findViewById(R.id.imageContact);

                Glide.with(getContext()).load(img).placeholder(R.drawable.contact).optionalCenterCrop().into(img);
                dialogPlus.show();
            }
        });

        tvChangePass = view.findViewById(R.id.textView_changePassword);
        tvChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getArguments();
                startActivity(new Intent(getActivity(), ChangePassActivity.class).putExtras(bundle));
            }
        });
        return view;
    }
}
