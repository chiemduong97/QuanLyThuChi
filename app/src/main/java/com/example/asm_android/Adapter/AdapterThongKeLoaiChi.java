package com.example.asm_android.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_android.Model.KhoangChi;
import com.example.asm_android.Model.LoaiChi;
import com.example.asm_android.Model.LoaiThu;
import com.example.asm_android.QuanLyThuChi;
import com.example.asm_android.R;
import com.example.asm_android.SQLite.KhoangChiDAO;
import com.example.asm_android.SQLite.LoaiChiDAO;

import java.util.ArrayList;

public class AdapterThongKeLoaiChi extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<LoaiChi> dsLoaiChi;
    public AdapterThongKeLoaiChi(Context context, ArrayList<LoaiChi> dsLoaiChi) {
        this.context = context;
        this.dsLoaiChi = dsLoaiChi;
    }
    public class ThongKeLoaiChiViewHolder extends RecyclerView.ViewHolder{
        public TextView name_loaichi;
        public TextView tongtien_loaichi;
        public ThongKeLoaiChiViewHolder(@NonNull View itemView) {
            super(itemView);
            name_loaichi=(TextView)itemView.findViewById(R.id.tv_ThongKeTenLoaiChi);
            tongtien_loaichi=(TextView)itemView.findViewById(R.id.tv_ThongKeLoaiTongChi);

        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.thong_ke_loai_chi,parent,false);
        return new ThongKeLoaiChiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LoaiChi loaiChi=dsLoaiChi.get(position);
        ((ThongKeLoaiChiViewHolder)holder).name_loaichi.setText("Tên loại chi: "+loaiChi.getLoaichi_name());
        ((ThongKeLoaiChiViewHolder)holder).tongtien_loaichi.setText("Tổng chi: "+TongChi(position));
        ((ThongKeLoaiChiViewHolder)holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ThongKeKhoangChi(position);
                return false;
            }
        });
    }

    public void ThongKeKhoangChi(int position){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View view= LayoutInflater.from(context).inflate(R.layout.dialog_thong_ke_khoang_chi,null);
        TextView ThongKeKhoangChiTenLoaiChi=view.findViewById(R.id.tv_ThongKeKhoangChiTenLoaiChi);
        ThongKeKhoangChiTenLoaiChi.setText(dsLoaiChi.get(position).getLoaichi_name());
        RecyclerView recyclerView=view.findViewById(R.id.recyclerThongKeKhoangChi);
        LoaiChiDAO loaiChiDAO=new LoaiChiDAO(context);
        ArrayList<LoaiChi> loaiChis=loaiChiDAO.getAllLoaiChi(((QuanLyThuChi)context).UserActive().getId_user());
        ArrayList<Integer> arrayList1=new ArrayList<>();
        for(int i=0;i<loaiChis.size();i++){
            if(dsLoaiChi.get(position).getId_loaichi()==loaiChis.get(i).getId_loaichi())
                arrayList1.add(loaiChis.get(i).getId_loaichi());
        }
        KhoangChiDAO khoangChiDAO=new KhoangChiDAO(context);
        ArrayList<KhoangChi> khoangChis=khoangChiDAO.getAllKhoangChi(arrayList1);
        AdapterThongKeKhoangChi adapterThongKeKhoangChi=new AdapterThongKeKhoangChi(context,khoangChis);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
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

    public Double TongChi(int position){
        Double tongchi=0.0;
        LoaiChiDAO loaiChiDAO;
        KhoangChiDAO khoangChiDAO;
        loaiChiDAO = new LoaiChiDAO(context);
        ArrayList<LoaiChi> dsLoaiChi=loaiChiDAO.getAllLoaiChi(((QuanLyThuChi)context).UserActive().getId_user());
        khoangChiDAO=new KhoangChiDAO(context);
        ArrayList<Integer> abc=new ArrayList<>();
        abc.add(dsLoaiChi.get(position).getId_loaichi());
        ArrayList<KhoangChi> dsKhoangChi=khoangChiDAO.getAllKhoangChi(abc);
        for(int k=0;k<dsKhoangChi.size();k++){
            tongchi=tongchi+dsKhoangChi.get(k).getKhoangchi_soluong();
        }
        return tongchi;
    }
    @Override
    public int getItemCount() {
        return dsLoaiChi.size();
    }
}
