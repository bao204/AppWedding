package com.example.weddingpackage;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity implements  InternetNotify.InternetNotifyListener {
    Button btnClickLogin_startLayout, btnClickRegis_startLayout;

    AlertDialog dialog;
    InternetNotify internetNotify = new InternetNotify(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        btnClickLogin_startLayout = findViewById(R.id.btnClickLogin_startLayout);
        btnClickRegis_startLayout = findViewById(R.id.btnClickResgis_startLayout);
        btnClickLogin_startLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        btnClickRegis_startLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, RegisActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(internetNotify, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(internetNotify);
    }
    @Override
    public void onNetworkChanged(boolean isConnected) {
        if (isConnected) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
        } else {
            if (dialog == null || !dialog.isShowing()) {
                ShowDialog();
            }
        }
    }
    private void ShowDialog() {

        dialog = new AlertDialog.Builder(StartActivity.this)
                .setView(R.layout.internet_dialog_layout)
                .setCancelable(false)
                .create();
        dialog.show();

        TextView playButton = dialog.findViewById(R.id.tvOK);

        playButton.setOnClickListener(view->{

            Toast.makeText(this, "Vui Lòng Kết Nối Wifi", Toast.LENGTH_SHORT).show();
        });
    }
}
