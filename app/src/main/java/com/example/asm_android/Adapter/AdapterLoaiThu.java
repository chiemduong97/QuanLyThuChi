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

public class AdapterLoaiThu extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    ArrayList<LoaiThu> dsLoaiThu;
    LoaiThuDAO loaiThuDAO;
    RecyclerView recyclerView;
    public AdapterLoaiThu(Context context, ArrayList<LoaiThu> dsLoaiThu, RecyclerView recyclerView){
        this.context=context;
        this.dsLoaiThu=dsLoaiThu;
        this.recyclerView=recyclerView;
    }

    public class LoaiThuViewHolder extends RecyclerView.ViewHolder{
        public TextView loaithu_ma;
        public TextView loaithu_name;
        public ImageView delete_loaithu;
        public ImageView edit_loaithu;
        public LoaiThuViewHolder(@NonNull View itemView) {
            super(itemView);
            loaithu_ma=(TextView)itemView.findViewById(R.id.tv_layout_maLoaiThu);
            loaithu_name=(TextView)itemView.findViewById(R.id.tv_layout_tenLoaiThu);
            delete_loaithu=(ImageView)itemView.findViewById(R.id.btnXoaLoaiThu);
            edit_loaithu=(ImageView)itemView.findViewById(R.id.btnSuaLoaiThu);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.loaithu,parent,false);
        return new LoaiThuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LoaiThu loaiThu=dsLoaiThu.get(position);
        ((LoaiThuViewHolder)holder).loaithu_ma.setText("Mã: "+loaiThu.getId_loaithu());
        ((LoaiThuViewHolder)holder).loaithu_name.setText("Tên: "+loaiThu.getLoaithu_name());
        ((LoaiThuViewHolder)holder).delete_loaithu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XoaLoaiThu(position);

            }
        });
        ((LoaiThuViewHolder)holder).edit_loaithu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuaLoaiThu(position);
            }
        });
    }
    public void XoaLoaiThu(int position){
        for(int i=0;i<dsLoaiThu.size();i++){
            try {
                if(position==i){
                    ArrayList<Integer> arrayList=new ArrayList<>();
                    for(int k=0;k<dsLoaiThu.size();k++){
                        arrayList.add(dsLoaiThu.get(k).getId_loaithu());
                    }
                    KhoangThuDAO khoangThuDAO =new KhoangThuDAO(context);
                    ArrayList<KhoangThu> khoangThus=khoangThuDAO.getAllKhoangThu(arrayList);
                    boolean tontai=false;
                    for(int j=0;j<khoangThus.size();j++){
                        if(khoangThus.get(j).getId_loaithu()==dsLoaiThu.get(position).getId_loaithu())
                            tontai=true;
                    }
                    if(tontai==false){
                        AlertDialog.Builder builder=new AlertDialog.Builder(context);
                        View view= LayoutInflater.from(context).inflate(R.layout.dialog_xac_nhan,null);
                        TextView xacnhan=view.findViewById(R.id.tvXacNhan);
                        xacnhan.setText("XÁC NHẬN XÓA LOẠI THU");
                        builder.setView(view);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                loaiThuDAO=new LoaiThuDAO(context);
                                loaiThuDAO.XoaLoaiThu(dsLoaiThu.get(position).getId_loaithu());
                                CapNhatLoaiThu();
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
                        Toast.makeText(context,"Có khoảng thu xóa thất bại!",Toast.LENGTH_SHORT).show();

                }
            }catch (Exception ex){
                Toast.makeText(context,"Xóa thất bại",Toast.LENGTH_SHORT).show();
                break;
            }

        }
    }
    public void SuaLoaiThu(int position){
        for(int i=0;i<dsLoaiThu.size();i++){
            try {
                if(position==i){
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    View view= LayoutInflater.from(context).inflate(R.layout.dialog_sua_loai_thu,null);
                    builder.setView(view);
                    TextView tvMaLoaiThu=view.findViewById(R.id.tvMaLoaiThu);
                    EditText etSuaTenLoaiThu=view.findViewById(R.id.etSuaTenLoaiThu);
                    tvMaLoaiThu.setText("Mã loại thu:"+dsLoaiThu.get(position).getId_loaithu());
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(etSuaTenLoaiThu.getText().toString().equals(""))
                                Toast.makeText(context,"Không được để trống,sửa thất bại",Toast.LENGTH_SHORT).show();
                            else if(CheckTenLoaiThu(etSuaTenLoaiThu.getText().toString())){
                                Toast.makeText(context,"Trùng tên loại thu, sửa thất bại",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                loaiThuDAO=new LoaiThuDAO(context);
                                loaiThuDAO.SuaLoaiThu(dsLoaiThu.get(position).getId_loaithu(),etSuaTenLoaiThu.getText().toString());
                                CapNhatLoaiThu();
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
    public boolean CheckTenLoaiThu(String tenloaithu){
        boolean check=false;
        for(int i=0;i<dsLoaiThu.size();i++){
            if(tenloaithu.equalsIgnoreCase(dsLoaiThu.get(i).getLoaithu_name())){
                check=true;
                break;
            }
        }
        return  check;
    }
    public void CapNhatLoaiThu(){
        User user;
        try{
            user=((QuanLyThuChi)context).UserActive();
        }
        catch (Exception exception){
            user=((MainActivity2)context).UserActive();
        }
        dsLoaiThu=loaiThuDAO.getAllLoaiThu(user.getId_user());
        AdapterLoaiThu adapterLoaiThu=new AdapterLoaiThu(context,dsLoaiThu,recyclerView);
        recyclerView.setAdapter(adapterLoaiThu);
    }
    @Override
    public int getItemCount() {
        return dsLoaiThu.size();
    }
}
