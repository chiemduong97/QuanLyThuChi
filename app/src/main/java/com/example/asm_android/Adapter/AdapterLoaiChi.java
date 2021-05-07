package com.example.asm_android.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_android.MainActivity2;
import com.example.asm_android.Model.KhoangChi;
import com.example.asm_android.Model.LoaiChi;
import com.example.asm_android.Model.User;
import com.example.asm_android.QuanLyThuChi;
import com.example.asm_android.R;
import com.example.asm_android.SQLite.KhoangChiDAO;
import com.example.asm_android.SQLite.LoaiChiDAO;

import java.util.ArrayList;

public class AdapterLoaiChi extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    ArrayList<LoaiChi> dsLoaiChi;
    LoaiChiDAO loaiChiDAO;
    RecyclerView recyclerView;
    public AdapterLoaiChi(Context context,ArrayList<LoaiChi> dsLoaiChi,RecyclerView recyclerView){
        this.context=context;
        this.dsLoaiChi=dsLoaiChi;
        this.recyclerView=recyclerView;
    }

    public class LoaiChiViewHolder extends RecyclerView.ViewHolder{
        public TextView loaichi_ma;
        public TextView loaichi_name;
        public ImageView delete_loaichi;
        public ImageView edit_loaichi;
        public LoaiChiViewHolder(@NonNull View itemView) {
            super(itemView);
            loaichi_ma=(TextView)itemView.findViewById(R.id.tv_layout_maLoaiChi);
            loaichi_name=(TextView)itemView.findViewById(R.id.tv_layout_tenLoaiChi);
            delete_loaichi=(ImageView)itemView.findViewById(R.id.btnXoaLoaiChi);
            edit_loaichi=(ImageView)itemView.findViewById(R.id.btnSuaLoaiChi);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.loaichi,parent,false);
        return new LoaiChiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LoaiChi loaiChi=dsLoaiChi.get(position);
        ((LoaiChiViewHolder)holder).loaichi_ma.setText("Mã: "+loaiChi.getId_loaichi());
        ((LoaiChiViewHolder)holder).loaichi_name.setText("Tên: "+loaiChi.getLoaichi_name());
        ((LoaiChiViewHolder)holder).delete_loaichi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XoaLoaiChi(position);

            }
        });
        ((LoaiChiViewHolder)holder).edit_loaichi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuaLoaiChi(position);
            }
        });
    }
    public void XoaLoaiChi(int position){
        for(int i=0;i<dsLoaiChi.size();i++){
            try {
                if(position==i){
                    ArrayList<Integer> arrayList=new ArrayList<>();
                    for(int k=0;k<dsLoaiChi.size();k++){
                        arrayList.add(dsLoaiChi.get(k).getId_loaichi());
                    }
                    KhoangChiDAO khoangChiDAO =new KhoangChiDAO(context);
                    ArrayList<KhoangChi> khoangChis=khoangChiDAO.getAllKhoangChi(arrayList);
                    boolean tontai=false;
                    for(int j=0;j<khoangChis.size();j++){
                        if(khoangChis.get(j).getId_loaichi()==dsLoaiChi.get(position).getId_loaichi())
                            tontai=true;
                    }
                    if(tontai==false){
                        AlertDialog.Builder builder=new AlertDialog.Builder(context);
                        View view= LayoutInflater.from(context).inflate(R.layout.dialog_xac_nhan,null);
                        TextView xacnhan=view.findViewById(R.id.tvXacNhan);
                        xacnhan.setText("XÁC NHẬN XÓA LOẠI CHI");
                        builder.setView(view);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                loaiChiDAO=new LoaiChiDAO(context);
                                loaiChiDAO.XoaLoaiChi(dsLoaiChi.get(position).getId_loaichi());
                                CapNhatLoaiChi();
                                Toast.makeText(context,"Xóa thành công!",Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.create().show();
                    }
                    else
                        Toast.makeText(context,"Có khoảng chi xóa thất bại!",Toast.LENGTH_SHORT).show();

                }
            }catch (Exception ex){
                Toast.makeText(context,"Xóa thất bại",Toast.LENGTH_SHORT).show();
                break;
            }

        }
    }
    public void SuaLoaiChi(int position){
        for(int i=0;i<dsLoaiChi.size();i++){
            try {
                if(position==i){
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    View view= LayoutInflater.from(context).inflate(R.layout.dialog_sua_loai_chi,null);
                    builder.setView(view);
                    TextView tvMaLoaiChi=view.findViewById(R.id.tvMaLoaiChi);
                    EditText etSuaTenLoaiChi=view.findViewById(R.id.etSuaTenLoaiChi);
                    tvMaLoaiChi.setText("Mã loại chi:"+dsLoaiChi.get(position).getId_loaichi());
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(etSuaTenLoaiChi.getText().toString().equals(""))
                                Toast.makeText(context,"Không được để trống,sửa thất bại",Toast.LENGTH_SHORT).show();
                            else if(CheckTenLoaiChi(etSuaTenLoaiChi.getText().toString())){
                                Toast.makeText(context,"Trùng tên loại chi, sửa thất bại",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                loaiChiDAO=new LoaiChiDAO(context);
                                loaiChiDAO.SuaLoaiChi(dsLoaiChi.get(position).getId_loaichi(),etSuaTenLoaiChi.getText().toString());
                                CapNhatLoaiChi();
                                Toast.makeText(context,"Sửa thành công!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }
            }catch (Exception ex){
                Toast.makeText(context,"Sửa thất bại",Toast.LENGTH_SHORT).show();
            }

        }
    }
    public boolean CheckTenLoaiChi(String tenloaichi){
        boolean check=false;
        for(int i=0;i<dsLoaiChi.size();i++){
            if(tenloaichi.equalsIgnoreCase(dsLoaiChi.get(i).getLoaichi_name())){
                check=true;
                break;
            }
        }
        return  check;
    }
    public void CapNhatLoaiChi(){
        User user;
        try{
            user=((QuanLyThuChi)context).UserActive();
        }
        catch (Exception exception){
            user=((MainActivity2)context).UserActive();
        }
        dsLoaiChi=loaiChiDAO.getAllLoaiChi(user.getId_user());
        AdapterLoaiChi adapterLoaiChi=new AdapterLoaiChi(context,dsLoaiChi,recyclerView);
        recyclerView.setAdapter(adapterLoaiChi);
    }
    @Override
    public int getItemCount() {
        return dsLoaiChi.size();
    }
}
