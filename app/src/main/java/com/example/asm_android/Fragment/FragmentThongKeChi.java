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

import com.example.asm_android.Adapter.AdapterThongKeKhoangChi;
import com.example.asm_android.Adapter.AdapterThongKeLoaiChi;
import com.example.asm_android.Model.KhoangChi;
import com.example.asm_android.Model.LoaiChi;
import com.example.asm_android.Model.User;
import com.example.asm_android.QuanLyThuChi;
import com.example.asm_android.R;
import com.example.asm_android.SQLite.KhoangChiDAO;
import com.example.asm_android.SQLite.LoaiChiDAO;
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

public class FragmentThongKeChi extends Fragment {
    BottomNavigationView bottomNavigationView;
    LoaiChiDAO loaiChiDAO;
    ArrayList<LoaiChi> loaiChis;
    KhoangChiDAO khoangChiDAO;
    ArrayList<KhoangChi> khoangChis;
    User user;
    View view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public FragmentThongKeChi() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        user=((QuanLyThuChi)getContext()).UserActive();
        View view=inflater.inflate(R.layout.fragment_thong_ke_chi, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.recyclerThongKeChi);
        TextView textView=view.findViewById(R.id.tv_ThongKeTongChi);
        textView.setText("Tổng chi tất cả: "+TongChi());
        loaiChiDAO=new LoaiChiDAO(getContext());
        loaiChis=loaiChiDAO.getAllLoaiChi(user.getId_user());
        AdapterThongKeLoaiChi adapterThongKeLoaiChi=new AdapterThongKeLoaiChi(getContext(),loaiChis);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterThongKeLoaiChi);
        PieChart pieChart=view.findViewById(R.id.BieuDoChi);
        ArrayList bieudo=new ArrayList();
        for(int i=0;i<loaiChis.size();i++){
            int t=(int)Math.round(TongChi(i));
            bieudo.add(new PieEntry(t, loaiChis.get(i).getLoaichi_name()));
        }
        PieDataSet pieDataSet = new PieDataSet(bieudo, "");
        PieData data = new PieData(pieDataSet);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.setData(data);
        pieChart.getDescription().setText("THỐNG KÊ TỔNG CHI");
        pieChart.setDrawSliceText(false);
        pieChart.setCenterText("TỔNG CHI: "+TongChi());
        pieChart.animateX(1000);
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                for(int i=0;i<bieudo.size();i++){
                    if(bieudo.get(i)==e){
                        ThongKeKhoangChi(i);
                    }
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
        return view;
    }
    public Double TongChi(){
        Double tongchi=0.0;
        LoaiChiDAO loaiChiDAO;
        KhoangChiDAO khoangChiDAO;
        ArrayList<LoaiChi> dsLoaiChi;
        ArrayList<KhoangChi> dsKhoangChi;
        loaiChiDAO = new LoaiChiDAO(getContext());
        dsLoaiChi =new ArrayList<>();
        dsLoaiChi=loaiChiDAO.getAllLoaiChi(user.getId_user());
        for(int j=0;j<dsLoaiChi.size();j++){
            khoangChiDAO=new KhoangChiDAO(getContext());
            dsKhoangChi=new ArrayList<>();
            ArrayList<Integer> abc=new ArrayList<>();
            abc.add(dsLoaiChi.get(j).getId_loaichi());
            dsKhoangChi=khoangChiDAO.getAllKhoangChi(abc);
            for(int k=0;k<dsKhoangChi.size();k++){
                tongchi=tongchi+dsKhoangChi.get(k).getKhoangchi_soluong();
            }
        }
        return tongchi;
    }
    public Double TongChi(int position){
        Double tongchi=0.0;
        LoaiChiDAO loaiChiDAO;
        KhoangChiDAO khoangChiDAO;
        loaiChiDAO = new LoaiChiDAO(getContext());
        ArrayList<LoaiChi> dsLoaiChi=loaiChiDAO.getAllLoaiChi(((QuanLyThuChi)getActivity()).UserActive().getId_user());
        khoangChiDAO=new KhoangChiDAO(getContext());
        ArrayList<Integer> abc=new ArrayList<>();
        abc.add(dsLoaiChi.get(position).getId_loaichi());
        ArrayList<KhoangChi> dsKhoangChi=khoangChiDAO.getAllKhoangChi(abc);
        for(int k=0;k<dsKhoangChi.size();k++){
            tongchi=tongchi+dsKhoangChi.get(k).getKhoangchi_soluong();
        }
        return tongchi;
    }

    public void ThongKeKhoangChi(int position){
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        View view= LayoutInflater.from(getContext()).inflate(R.layout.dialog_thong_ke_khoang_chi,null);
        TextView ThongKeKhoangChiTenLoaiChi=view.findViewById(R.id.tv_ThongKeKhoangChiTenLoaiChi);
        ThongKeKhoangChiTenLoaiChi.setText(loaiChis.get(position).getLoaichi_name());
        RecyclerView recyclerView=view.findViewById(R.id.recyclerThongKeKhoangChi);
        LoaiChiDAO loaiChiDAO=new LoaiChiDAO(getContext());
        ArrayList<LoaiChi> loaiChis=loaiChiDAO.getAllLoaiChi(((QuanLyThuChi)getContext()).UserActive().getId_user());
        ArrayList<Integer> arrayList1=new ArrayList<>();
        for(int i=0;i<loaiChis.size();i++){
            if(loaiChis.get(position).getId_loaichi()==loaiChis.get(i).getId_loaichi())
                arrayList1.add(loaiChis.get(i).getId_loaichi());
        }
        KhoangChiDAO khoangChiDAO=new KhoangChiDAO(getContext());
        ArrayList<KhoangChi> khoangChis=khoangChiDAO.getAllKhoangChi(arrayList1);
        AdapterThongKeKhoangChi adapterThongKeKhoangChi=new AdapterThongKeKhoangChi(getContext(),khoangChis);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterThongKeKhoangChi);
        builder.setView(view);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}
