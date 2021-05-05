package com.example.asm_android.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_android.Model.KhoangChi;
import com.example.asm_android.Model.KhoangThu;
import com.example.asm_android.Model.LoaiChi;
import com.example.asm_android.Model.LoaiThu;
import com.example.asm_android.QuanLyThuChi;
import com.example.asm_android.R;
import com.example.asm_android.SQLite.KhoangChiDAO;
import com.example.asm_android.SQLite.KhoangThuDAO;
import com.example.asm_android.SQLite.LoaiChiDAO;
import com.example.asm_android.SQLite.LoaiThuDAO;

import java.util.ArrayList;

public class AdapterThongKeLoaiThu extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<LoaiThu> dsLoaiThu;
    public AdapterThongKeLoaiThu(Context context, ArrayList<LoaiThu> dsLoaiThu) {
        this.context = context;
        this.dsLoaiThu = dsLoaiThu;
    }
    public class ThongKeLoaiThuViewHolder extends RecyclerView.ViewHolder{
        public TextView name_loaithu;
        public TextView tongtien_loaithu;
        public ThongKeLoaiThuViewHolder(@NonNull View itemView) {
            super(itemView);
            name_loaithu=(TextView)itemView.findViewById(R.id.tv_ThongKeTenLoaiThu);
            tongtien_loaithu=(TextView)itemView.findViewById(R.id.tv_ThongKeLoaiTongThu);

        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.thong_ke_loai_thu,parent,false);
        return new ThongKeLoaiThuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LoaiThu loaiThu=dsLoaiThu.get(position);
        ((ThongKeLoaiThuViewHolder)holder).name_loaithu.setText("Tên loại thu: "+loaiThu.getLoaithu_name());
        ((ThongKeLoaiThuViewHolder)holder).tongtien_loaithu.setText("Tổng thu: "+TongThu(position));
        ((ThongKeLoaiThuViewHolder)holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ThongKeKhoangThu(position);
                return false;
            }
        });
    }
    public void ThongKeKhoangThu(int position){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View view= LayoutInflater.from(context).inflate(R.layout.dialog_thong_ke_khoang_thu,null);
        TextView ThongKeKhoangThuTenLoaiThu=view.findViewById(R.id.tv_ThongKeKhoangThuTenLoaiThu);
        ThongKeKhoangThuTenLoaiThu.setText(dsLoaiThu.get(position).getLoaithu_name());
        RecyclerView recyclerView=view.findViewById(R.id.recyclerThongKeKhoangThu);
        LoaiThuDAO loaiThuDAO=new LoaiThuDAO(context);
        ArrayList<LoaiThu> loaiThus=loaiThuDAO.getAllLoaiThu(((QuanLyThuChi)context).UserActive().getId_user());
        ArrayList<Integer> arrayList1=new ArrayList<>();
        for(int i=0;i<loaiThus.size();i++){
            if(dsLoaiThu.get(position).getId_loaithu()==loaiThus.get(i).getId_loaithu())
                arrayList1.add(loaiThus.get(i).getId_loaithu());
        }
        KhoangThuDAO khoangThuDAO=new KhoangThuDAO(context);
        ArrayList<KhoangThu> khoangThus=khoangThuDAO.getAllKhoangThu(arrayList1);
        AdapterThongKeKhoangThu adapterThongKeKhoangThu=new AdapterThongKeKhoangThu(context,khoangThus);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
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

    public Double TongThu(int position){
        Double tongthu=0.0;
        LoaiThuDAO loaiThuDAO;
        KhoangThuDAO khoangThuDAO;
        loaiThuDAO = new LoaiThuDAO(context);
        ArrayList<LoaiThu> dsLoaiThu=loaiThuDAO.getAllLoaiThu(((QuanLyThuChi)context).UserActive().getId_user());
        khoangThuDAO=new KhoangThuDAO(context);
        ArrayList<Integer> abc=new ArrayList<>();
        abc.add(dsLoaiThu.get(position).getId_loaithu());
        ArrayList<KhoangThu> dsKhoangThu=khoangThuDAO.getAllKhoangThu(abc);
        for(int k=0;k<dsKhoangThu.size();k++){
            tongthu=tongthu+dsKhoangThu.get(k).getKhoangthu_soluong();
        }
        return tongthu;
    }
    @Override
    public int getItemCount() {
        return dsLoaiThu.size();
    }
}
