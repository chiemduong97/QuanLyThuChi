package com.example.asm_android.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_android.Adapter.AdapterKhoangChi;
import com.example.asm_android.Adapter.AdapterLoaiChi;
import com.example.asm_android.Adapter.AdapterThongKeKhoangChi;
import com.example.asm_android.Adapter.AdapterThongKeLoaiChi;
import com.example.asm_android.Adapter.AdapterThongKeLoaiThu;
import com.example.asm_android.BottomNavigationBehavior;
import com.example.asm_android.Model.KhoangChi;
import com.example.asm_android.Model.KhoangThu;
import com.example.asm_android.Model.LoaiChi;
import com.example.asm_android.Model.LoaiThu;
import com.example.asm_android.Model.User;
import com.example.asm_android.QuanLyThuChi;
import com.example.asm_android.R;
import com.example.asm_android.SQLite.KhoangChiDAO;
import com.example.asm_android.SQLite.KhoangThuDAO;
import com.example.asm_android.SQLite.LoaiChiDAO;
import com.example.asm_android.SQLite.LoaiThuDAO;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class FragmentThongKe extends Fragment {
    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    LoaiChiDAO loaiChiDAO;
    ArrayList<LoaiChi> loaiChis;
    KhoangChiDAO khoangChiDAO;
    ArrayList<KhoangChi> khoangChis;

    LoaiThuDAO loaiThuDAO;
    ArrayList<LoaiThu> loaiThus;
    KhoangThuDAO khoangThuDAO;
    ArrayList<KhoangThu> khoangThus;
    User user;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public FragmentThongKe() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        user=((QuanLyThuChi)getContext()).UserActive();
        View view=inflater.inflate(R.layout.fragment_thong_ke, container, false);
        fragmentManager=getFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutThongKe,new FragmentThongKeChi()).commit();
        bottomNavigationView=view.findViewById(R.id.naviga);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.menu_chi){
                    fragmentManager=getFragmentManager();
                    fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoutThongKe,new FragmentThongKeChi()).commit();
                }
                else if(item.getItemId()==R.id.menu_thu){
                    fragmentManager=getFragmentManager();
                    fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoutThongKe,new FragmentThongKeThu()).commit();
                }
                else if(item.getItemId()==R.id.menu_thong_ke_nam){
                    fragmentManager=getFragmentManager();
                    fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayoutThongKe,new FragmentThongKeTheoNamViewPager()).commit();
                }
                return true;
            }
        });
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        return view;
    }
}
