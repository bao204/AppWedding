package com.example.weddingpackage;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.weddingpackage.fragment.CaiDatFragment;
import com.example.weddingpackage.fragment.DatTiecFragment;
import com.example.weddingpackage.fragment.DeXuatFragment;
import com.example.weddingpackage.fragment.TrangChuFragment;
import com.example.weddingpackage.modle.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CaiDatFragment caiDatFragment = new CaiDatFragment();
        DatTiecFragment datTiecFragment = new DatTiecFragment();
        LichSuDatTiecFragment lichSuDatTiecFragment = new LichSuDatTiecFragment();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            datTiecFragment.setArguments(bundle);
            caiDatFragment.setArguments(bundle);
            lichSuDatTiecFragment.setArguments(bundle);
        }

        Toolbar toolbar = findViewById(R.id.toolbar_activityHome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_trangChu);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        loadFragment(new TrangChuFragment());

navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.navigation_trangChu){
            toolbar.setTitle("Shop");
            loadFragment(new TrangChuFragment());
            return true;}

        else if (id==R.id.navigation_datTiec){
            toolbar.setTitle("Đặt tiệc");
            loadFragment(datTiecFragment);
            return true;}

        else if (id==R.id.navigation_lichSuDatTiec){
            toolbar.setTitle("Lịch sử đặt tiệc");
            loadFragment(lichSuDatTiecFragment);
            return true;}

        else if (id==R.id.navigation_setting){
            toolbar.setTitle("Cài đặt");
            loadFragment(caiDatFragment);
            return true;}
        else
            return false;
        }
});
    }

    void loadFragment(Fragment Home){
        getSupportFragmentManager().beginTransaction().replace(R.id.FrameL,Home).addToBackStack(null).commit();
    }
}