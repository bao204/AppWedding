package com.example.weddingpackage.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.weddingpackage.fragment.DoUongFragment;
import com.example.weddingpackage.fragment.KhaiViFragment;
import com.example.weddingpackage.fragment.MonChinhFragment;
import com.example.weddingpackage.fragment.TrangMiengFragment;

public class ViewPageAdapter extends FragmentStatePagerAdapter {
    public ViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new KhaiViFragment();
            case 1:
                return new MonChinhFragment();
            case 2:
                return new TrangMiengFragment();
            case 3:
                return new DoUongFragment();
            default:
                return new KhaiViFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        switch (position){
            case 0:
                title = "Khai vị";
                break;
            case 1:
                title = "Món chính";
                break;
            case 2:
                title = "Tráng miệng";
                break;
            case 3:
                title = "Đồ uống";
                break;
        }
        return title;
    }
}
