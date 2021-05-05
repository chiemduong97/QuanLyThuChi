package com.example.asm_android.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.asm_android.R;
import com.google.android.material.tabs.TabLayout;

public class FragmentThu extends Fragment {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2 ;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* gan layout va anh xa */
        View x =  inflater.inflate(R.layout.fragment_thu,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabsthu);
        viewPager = (ViewPager) x.findViewById(R.id.viewpagerthu);

        /* Set adapter cho viewpager  */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        /* can cai nay chu khong se bi loi */
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.getTabAt(0).setIcon(R.drawable.ic_loai);
                tabLayout.getTabAt(1).setIcon(R.drawable.ic_khoang);
            }
        });
        return x;
    }

    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        LayoutInflater inflate;
        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new FragmentLoaiThu();
                case 1 : return new FragmentKhoangThu();
            }
            return null;
        }
        @Override
        public int getCount() {
            return int_items;
        }
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Loại thu";
                case 1 :
                    return "Khoảng thu";
            }
            return null;
        }

    }
}
