package com.example.asm_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_android.Model.KhoangChi;
import com.example.asm_android.R;

import java.util.ArrayList;

public class AdapterThongKeKhoangChi extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<KhoangChi> dsKhoangChi;
    public AdapterThongKeKhoangChi(Context context,ArrayList<KhoangChi> dsKhoangChi){
        this.context=context;
        this.dsKhoangChi=dsKhoangChi;
    }
    public class ThongKeKhoangChiViewHolder extends RecyclerView.ViewHolder{
        public TextView name_khoangchi;
        public TextView soluong_khoangchi;
        public ThongKeKhoangChiViewHolder(@NonNull View itemView) {
            super(itemView);
            name_khoangchi=(TextView)itemView.findViewById(R.id.tv_ThongKeTenKhoangChi);
            soluong_khoangchi=(TextView)itemView.findViewById(R.id.tv_ThongKeSoLuongKhoangChi);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.thong_ke_khoang_chi,parent,false);
        return new ThongKeKhoangChiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        KhoangChi khoangChi=dsKhoangChi.get(position);
        ((ThongKeKhoangChiViewHolder)holder).name_khoangchi.setText("Tên khoảng chi: "+khoangChi.getKhoangchi_name());
        ((ThongKeKhoangChiViewHolder)holder).soluong_khoangchi.setText("Số lượng: "+khoangChi.getKhoangchi_soluong());

    }

    @Override
    public int getItemCount() {
        return dsKhoangChi.size();
    }
}
