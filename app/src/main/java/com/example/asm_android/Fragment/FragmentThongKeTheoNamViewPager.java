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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FragmentThongKeTheoNamViewPager extends Fragment {
    ViewPager viewPager;
    ArrayList<KhoangChi> khoangChis=new ArrayList<>();
    ArrayList<KhoangThu> khoangThus=new ArrayList<>();

    KhoangChiDAO khoangChiDAO;
    KhoangThuDAO khoangThuDAO;

    User user;

    ArrayList<Integer> Nams;

    LoaiChiDAO loaiChiDAO;
    ArrayList<Integer> arrayList1;
    LoaiThuDAO loaiThuDAO;
    ArrayList<Integer> arrayList2;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_thong_ke_theo_nam_viewpager,null);
        user=((QuanLyThuChi)getContext()).UserActive();

        loaiChiDAO=new LoaiChiDAO(getContext());
        ArrayList<LoaiChi> loaiChis=loaiChiDAO.getAllLoaiChi(user.getId_user());
        arrayList1=new ArrayList<>();
        for(int i=0;i<loaiChis.size();i++){
            arrayList1.add(loaiChis.get(i).getId_loaichi());
        }

        khoangChiDAO=new KhoangChiDAO(getContext());
        khoangChis=khoangChiDAO.getAllKhoangChi(arrayList1);


        loaiThuDAO=new LoaiThuDAO(getContext());
        ArrayList<LoaiThu> loaiThus=loaiThuDAO.getAllLoaiThu(user.getId_user());
        arrayList2=new ArrayList<>();
        for(int i=0;i<loaiThus.size();i++){
            arrayList2.add(loaiThus.get(i).getId_loaithu());
        }

        khoangThuDAO=new KhoangThuDAO(getContext());
        khoangThus=khoangThuDAO.getAllKhoangThu(arrayList2);

        Nams=new ArrayList<>();
        for(int i=0;i<khoangChis.size();i++){
            if(Nams.contains(khoangChis.get(i)._getNgay_chi().getNam())==false){
                Nams.add(khoangChis.get(i)._getNgay_chi().getNam());
            }
        }
        for(int i=0;i<khoangThus.size();i++){
            if(Nams.contains(khoangThus.get(i)._getNgay_thu().getNam())==false){
                if(Nams.contains(khoangThus.get(i)._getNgay_thu().getNam())==false){
                    Nams.add(khoangThus.get(i)._getNgay_thu().getNam());
                }
            }
        }
        SapXepNam();

        viewPager = (ViewPager) view.findViewById(R.id.viewpagerthongketheonam);
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));


        return view;

    }

    public void SapXepNam() {
        Collections.sort(Nams,new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });
    };

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position)
        {
            for(int i=0;i<Nams.size();i++){
                return new FragmentThongKeTheoNam(Nams.get(position));
            }
            return null;
        }
        @Override
        public int getCount() {
            return Nams.size();
        }
    }
}
