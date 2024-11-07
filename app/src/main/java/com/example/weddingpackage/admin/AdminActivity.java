package com.example.weddingpackage.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.weddingpackage.LobbyActivity;
import com.example.weddingpackage.LoginActivity;
import com.example.weddingpackage.R;
import com.example.weddingpackage.admin.fragment_admin.AccountUserFragment;
import com.example.weddingpackage.admin.fragment_admin.DatTiecFragment;
import com.example.weddingpackage.admin.fragment_admin.DichVuFragment;
import com.example.weddingpackage.admin.fragment_admin.GoiTiecFragment;
import com.example.weddingpackage.admin.fragment_admin.HoaDonFragment;
import com.example.weddingpackage.admin.fragment_admin.MonAnFragment;
import com.example.weddingpackage.admin.fragment_admin.SanhFragment;

import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity);

//-------------------------------------------------------------------------------
        toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nvgationDrawer);
        drawerLayout = findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
//-------------------------------------------------------------------------------
        LoadFragment(new SanhFragment());
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.datTiec:
                        LoadFragment(new DatTiecFragment());
                        drawerLayout.close();
                        toolbar.setTitle("List manager đặt tiệc");
                        break;
                    case R.id.dichVu:
                        LoadFragment(new DichVuFragment());
                        toolbar.setTitle("List manager dịch vụ");
                        drawerLayout.close();
                        break;
                    case R.id.goiTiecCuoi:
                        LoadFragment(new GoiTiecFragment());
                        toolbar.setTitle("List manager gói tiệc cưới");
                        drawerLayout.close();
                        break;
                    case R.id.monAn:
                        LoadFragment(new MonAnFragment());
                        toolbar.setTitle("List manager foods");
                        drawerLayout.close();
                        break;
                    case R.id.sanh:
                        LoadFragment(new SanhFragment());
                        toolbar.setTitle("List manager lobbys");
                        drawerLayout.close();
                        break;
                    case R.id.hoaDon:
                        LoadFragment(new HoaDonFragment());
                        toolbar.setTitle("List manager hóa đơn");
                        drawerLayout.close();
                        break;
                    case R.id.acountUser:
                        LoadFragment(new AccountUserFragment());
                        toolbar.setTitle("List manager user accounts");
                        drawerLayout.close();
                        break;
                    case R.id.logOut:
                        startActivity(new Intent(AdminActivity.this, LoginActivity.class));
                        break;
                }
                return true;
            }
        });
    }
    public void LoadFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

}