package com.example.asm_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_android.Model.KhoangThu;
import com.example.asm_android.R;

import java.util.ArrayList;

public class AdapterThongKeKhoangThu extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<KhoangThu> dsKhoangThu;
    public AdapterThongKeKhoangThu(Context context, ArrayList<KhoangThu> dsKhoangThu){
        this.context=context;
        this.dsKhoangThu=dsKhoangThu;
    }
    public class ThongKeKhoangThuViewHolder extends RecyclerView.ViewHolder{
        public TextView name_khoangthu;
        public TextView soluong_khoangthu;
        public ThongKeKhoangThuViewHolder(@NonNull View itemView) {
            super(itemView);
            name_khoangthu=(TextView)itemView.findViewById(R.id.tv_ThongKeTenKhoangThu);
            soluong_khoangthu=(TextView)itemView.findViewById(R.id.tv_ThongKeSoLuongKhoangThu);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.thong_ke_khoang_thu,parent,false);
        return new ThongKeKhoangThuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        KhoangThu khoangThu=dsKhoangThu.get(position);
        ((ThongKeKhoangThuViewHolder)holder).name_khoangthu.setText("Tên khoảng thu: "+khoangThu.getKhoangthu_name());
        ((ThongKeKhoangThuViewHolder)holder).soluong_khoangthu.setText("Số lượng: "+khoangThu.getKhoangthu_soluong());

    }

    @Override
    public int getItemCount() {
        return dsKhoangThu.size();
    }
}
