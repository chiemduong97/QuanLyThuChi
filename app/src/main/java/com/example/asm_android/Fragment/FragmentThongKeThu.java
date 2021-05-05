package com.example.asm_android.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_android.Adapter.AdapterThongKeKhoangThu;
import com.example.asm_android.Adapter.AdapterThongKeLoaiThu;
import com.example.asm_android.MainActivity2;
import com.example.asm_android.Model.KhoangThu;
import com.example.asm_android.Model.LoaiThu;
import com.example.asm_android.Model.User;
import com.example.asm_android.QuanLyThuChi;
import com.example.asm_android.R;
import com.example.asm_android.SQLite.KhoangThuDAO;
import com.example.asm_android.SQLite.LoaiThuDAO;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class FragmentThongKeThu extends Fragment {
    BottomNavigationView bottomNavigationView;

    LoaiThuDAO loaiThuDAO;
    ArrayList<LoaiThu> loaiThus;
    KhoangThuDAO khoangThuDAO;
    ArrayList<KhoangThu> khoangThus;
    User user;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public FragmentThongKeThu() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try{
            user=((QuanLyThuChi)getContext()).UserActive();
        }
        catch (Exception exception){
            user=((MainActivity2)getContext()).UserActive();
        }
        View view=inflater.inflate(R.layout.fragment_thong_ke_thu, container, false);
        TextView textView=view.findViewById(R.id.tv_ThongKeTongThu);
        textView.setText("Tổng thu tất cả: "+TongThu());
        RecyclerView recyclerView=view.findViewById(R.id.recyclerThongKeThu);
        loaiThuDAO= new LoaiThuDAO(getContext());
        loaiThus=loaiThuDAO.getAllLoaiThu(user.getId_user());
        AdapterThongKeLoaiThu adapterThongKeLoaiThu=new AdapterThongKeLoaiThu(getContext(),loaiThus);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterThongKeLoaiThu);

        PieChart pieChart=view.findViewById(R.id.BieuDoThu);
        ArrayList bieudo=new ArrayList();
        for(int i=0;i<loaiThus.size();i++){
            int t=(int)Math.round(TongThu(i));
            bieudo.add(new PieEntry(t, loaiThus.get(i).getLoaithu_name()));
        }
        PieDataSet pieDataSet = new PieDataSet(bieudo, "");
        PieData data = new PieData(pieDataSet);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.setData(data);
        pieChart.getDescription().setText("THỐNG KÊ TỔNG THU");
        pieChart.setDrawSliceText(false);
        pieChart.animateX(1000);
        pieChart.setCenterText("TỔNG THU: "+TongThu());
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                for(int i=0;i<bieudo.size();i++){
                    if(bieudo.get(i)==e){
                        ThongKeKhoangThu(i);
                    }
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
        return view;
    }
    public Double TongThu(){
        Double tongthu=0.0;
        LoaiThuDAO loaiThuDAO;
        KhoangThuDAO khoangThuDAO;
        ArrayList<LoaiThu> dsLoaiThu;
        ArrayList<KhoangThu> dsKhoangThu;
        loaiThuDAO = new LoaiThuDAO(getContext());
        dsLoaiThu =new ArrayList<>();
        dsLoaiThu=loaiThuDAO.getAllLoaiThu(user.getId_user());
        for(int j=0;j<dsLoaiThu.size();j++){
            khoangThuDAO=new KhoangThuDAO(getContext());
            dsKhoangThu=new ArrayList<>();
            ArrayList<Integer> abc=new ArrayList<>();
            abc.add(dsLoaiThu.get(j).getId_loaithu());
            dsKhoangThu=khoangThuDAO.getAllKhoangThu(abc);
            for(int k=0;k<dsKhoangThu.size();k++){
                tongthu=tongthu+dsKhoangThu.get(k).getKhoangthu_soluong();
            }
        }
        return tongthu;
    }
    public Double TongThu(int position){
        Double tongthu=0.0;
        LoaiThuDAO loaiThuDAO;
        KhoangThuDAO khoangThuDAO;
        loaiThuDAO = new LoaiThuDAO(getContext());
        User user;
        try{
            user=((QuanLyThuChi)getContext()).UserActive();
        }
        catch (Exception exception){
            user=((MainActivity2)getContext()).UserActive();
        }
        ArrayList<LoaiThu> dsLoaiThu=loaiThuDAO.getAllLoaiThu(user.getId_user());
        khoangThuDAO=new KhoangThuDAO(getContext());
        ArrayList<Integer> abc=new ArrayList<>();
        abc.add(dsLoaiThu.get(position).getId_loaithu());
        ArrayList<KhoangThu> dsKhoangThu=khoangThuDAO.getAllKhoangThu(abc);
        for(int k=0;k<dsKhoangThu.size();k++){
            tongthu=tongthu+dsKhoangThu.get(k).getKhoangthu_soluong();
        }
        return tongthu;
    }
    public void ThongKeKhoangThu(int position){
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        View view= LayoutInflater.from(getContext()).inflate(R.layout.dialog_thong_ke_khoang_thu,null);
        TextView ThongKeKhoangThuTenLoaiThu=view.findViewById(R.id.tv_ThongKeKhoangThuTenLoaiThu);
        ThongKeKhoangThuTenLoaiThu.setText(loaiThus.get(position).getLoaithu_name());
        RecyclerView recyclerView=view.findViewById(R.id.recyclerThongKeKhoangThu);
        LoaiThuDAO loaiThuDAO=new LoaiThuDAO(getContext());
        ArrayList<LoaiThu> loaiThus=loaiThuDAO.getAllLoaiThu(((QuanLyThuChi)getContext()).UserActive().getId_user());
        ArrayList<Integer> arrayList1=new ArrayList<>();
        for(int i=0;i<loaiThus.size();i++){
            if(loaiThus.get(position).getId_loaithu()==loaiThus.get(i).getId_loaithu())
                arrayList1.add(loaiThus.get(i).getId_loaithu());
        }
        KhoangThuDAO khoangThuDAO=new KhoangThuDAO(getContext());
        ArrayList<KhoangThu> khoangThus=khoangThuDAO.getAllKhoangThu(arrayList1);
        AdapterThongKeKhoangThu adapterThongKeKhoangThu=new AdapterThongKeKhoangThu(getContext(),khoangThus);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterThongKeKhoangThu);
        builder.setView(view);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

}
