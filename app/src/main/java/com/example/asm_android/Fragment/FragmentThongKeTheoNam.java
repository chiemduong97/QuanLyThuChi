package com.example.asm_android.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asm_android.Model.KhoangChi;
import com.example.asm_android.Model.KhoangThu;
import com.example.asm_android.Model.LoaiChi;
import com.example.asm_android.Model.LoaiThu;
import com.example.asm_android.Model.User;
import com.example.asm_android.Model.date;
import com.example.asm_android.QuanLyThuChi;
import com.example.asm_android.R;
import com.example.asm_android.SQLite.KhoangChiDAO;
import com.example.asm_android.SQLite.KhoangThuDAO;
import com.example.asm_android.SQLite.LoaiChiDAO;
import com.example.asm_android.SQLite.LoaiThuDAO;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FragmentThongKeTheoNam extends Fragment {
    ArrayList<KhoangChi> khoangChis=new ArrayList<>();
    ArrayList<KhoangThu> khoangThus=new ArrayList<>();

    KhoangChiDAO khoangChiDAO;
    KhoangThuDAO khoangThuDAO;

    User user;
    int nam;

    ArrayList<Integer> Thangs;

    LoaiChiDAO loaiChiDAO;
    ArrayList<Integer> arrayList1;
    LoaiThuDAO loaiThuDAO;
    ArrayList<Integer> arrayList2;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public FragmentThongKeTheoNam() {

    }

    public FragmentThongKeTheoNam(int nam){
        this.nam=nam;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke_theo_nam, null);
        user=((QuanLyThuChi)getContext()).UserActive();
        BarChart barChart=view.findViewById(R.id.BieuDoTheoNam);
        TextView NamThongKe=view.findViewById(R.id.ThongKeNam);

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

        Thangs=new ArrayList<>();

        for(int i=0;i<khoangChis.size();i++){
            if(khoangChis.get(i)._getNgay_chi().getNam()==nam){
                if(Thangs.contains(khoangChis.get(i)._getNgay_chi().getThang())==false){
                    Thangs.add(khoangChis.get(i)._getNgay_chi().getThang());
                }
            }
        }
        for(int i=0;i<khoangThus.size();i++){
            if(khoangThus.get(i)._getNgay_thu().getNam()==nam){
                if(Thangs.contains(khoangThus.get(i)._getNgay_thu().getThang())==false){
                    Thangs.add(khoangThus.get(i)._getNgay_thu().getThang());
                }
            }
        }
        SapXepThang();


        NamThongKe.setText(nam+"");
        for(int j=0;j<khoangChis.size();j++){
            if(nam==khoangChis.get(j)._getNgay_chi().getNam()){
                if(Thangs.contains(khoangChis.get(j)._getNgay_chi().getNam())){
                    Thangs.add(khoangChis.get(j)._getNgay_chi().getNam());
                }
            }
        }
        for(int j=0;j<khoangThus.size();j++){
            if(nam==khoangThus.get(j)._getNgay_thu().getNam()){
                if(Thangs.contains(khoangThus.get(j)._getNgay_thu().getNam())){
                    Thangs.add(khoangThus.get(j)._getNgay_thu().getNam());
                }
            }
        }
        SapXepThang();
        ArrayList bieudochi = new ArrayList<>();
        ArrayList bieudothu = new ArrayList<>();
        for(int j=0;j<Thangs.size();j++){
            int tong1=(int)Math.round(TongChiTheoThang(Thangs.get(j),nam));
            int tong2=(int)Math.round(TongThuTheoThang(Thangs.get(j),nam));
            bieudochi.add(new BarEntry(Thangs.get(j),tong1));
            bieudothu.add(new BarEntry(Thangs.get(j),tong2));
        }
        BarDataSet bardataset1 = new BarDataSet(bieudochi, "Tổng chi");
        bardataset1.setColors(Color.RED);
        BarDataSet bardataset2 = new BarDataSet(bieudothu, "Tổng thu");
        bardataset2.setColors(Color.YELLOW);

        BarData data = new BarData(bardataset1,bardataset2);
        barChart.setData(data);
        XAxis xAxis = barChart.getXAxis();
        ArrayList<String> month=new ArrayList<>();
        for(int j=0;j<Thangs.size();j++){
            month.add("Th"+Thangs.get(j));
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(month));
        barChart.getAxisLeft().setAxisMinimum(0);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);

        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularityEnabled(true);

        barChart.getXAxis().setAxisMinimum(0);
        data.setBarWidth(0.3f);
        barChart.groupBars(0,0.4f,0f);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(1000);

        return view;
    }
    public void SapXepThang() {
        Collections.sort(Thangs,new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });
    };

    public Double TongChiTheoThang(int thang,int nam){
        Double tong=0.0;
        loaiChiDAO=new LoaiChiDAO(getContext());
        ArrayList<LoaiChi> loaiChis=loaiChiDAO.getAllLoaiChi(user.getId_user());
        arrayList1=new ArrayList<>();
        for(int i=0;i<loaiChis.size();i++){
            arrayList1.add(loaiChis.get(i).getId_loaichi());
        }

        khoangChiDAO=new KhoangChiDAO(getContext());
        khoangChis=khoangChiDAO.getAllKhoangChi(arrayList1);

        for(int i=0;i<khoangChis.size();i++){
            if(khoangChis.get(i)._getNgay_chi().getNam()==nam&&khoangChis.get(i)._getNgay_chi().getThang()==thang){
                tong=tong+khoangChis.get(i).getKhoangchi_soluong();
            }
        }

        return tong;
    }
    public Double TongThuTheoThang(int thang,int nam){
        Double tong=0.0;
        loaiThuDAO=new LoaiThuDAO(getContext());
        ArrayList<LoaiThu> loaiThus=loaiThuDAO.getAllLoaiThu(user.getId_user());
        arrayList2=new ArrayList<>();
        for(int i=0;i<loaiThus.size();i++){
            arrayList2.add(loaiThus.get(i).getId_loaithu());
        }

        khoangThuDAO=new KhoangThuDAO(getContext());
        khoangThus=khoangThuDAO.getAllKhoangThu(arrayList2);

        for(int i=0;i<khoangThus.size();i++){
            if(khoangThus.get(i)._getNgay_thu().getNam()==nam&&khoangThus.get(i)._getNgay_thu().getThang()==thang){
                tong=tong+khoangThus.get(i).getKhoangthu_soluong();
            }
        }

        return tong;
    }
}
