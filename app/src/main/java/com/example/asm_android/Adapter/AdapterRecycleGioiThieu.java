package com.example.asm_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_android.Model.IconGioiThieu;
import com.example.asm_android.QuanLyThuChi;
import com.example.asm_android.R;

import java.util.ArrayList;

public class AdapterRecycleGioiThieu extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<IconGioiThieu> iconGioiThieus=new ArrayList<>();
    public AdapterRecycleGioiThieu(Context context,ArrayList<IconGioiThieu> iconGioiThieus){
        this.context=context;
        this.iconGioiThieus=iconGioiThieus;
    }
    public class IconGioiThieuViewHolder extends RecyclerView.ViewHolder{
        public ImageView img_icon;
        public TextView ten_icon;
        public IconGioiThieuViewHolder(@NonNull View itemView) {
            super(itemView);
            img_icon=(ImageView)itemView.findViewById(R.id.iconGioiThieu);
            ten_icon=(TextView)itemView.findViewById(R.id.TenIconGioiThieu);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.icon_gioi_thieu,parent,false);
        return new IconGioiThieuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        IconGioiThieu iconGioiThieu=iconGioiThieus.get(position);
        ((IconGioiThieuViewHolder)holder).img_icon.setImageResource(iconGioiThieu.getImg());
        ((IconGioiThieuViewHolder)holder).ten_icon.setText(iconGioiThieu.getTen());
    }

    @Override
    public int getItemCount() {
        return iconGioiThieus.size();
    }
}
