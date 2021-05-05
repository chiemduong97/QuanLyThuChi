package com.example.asm_android.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.asm_android.SQLite.UserDAO;

import java.util.ArrayList;

public class AdapterUser extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    Context context;
    private ArrayList<User> dsUser;
    UserDAO userDAO;
    RecyclerView recyclerView;
    public AdapterUser(Context context,ArrayList<User> dsUser,RecyclerView recyclerView){
        this.context=context;
        this.dsUser=dsUser;
        this.recyclerView=recyclerView;
    }
    public class UserViewHolder extends RecyclerView.ViewHolder{
        public TextView user_name;
        public TextView user_tong_thu;
        public TextView user_tong_chi;
        public ImageView delete_user;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name=(TextView)itemView.findViewById(R.id.tv_layout_tenUser);
            user_tong_thu=(TextView)itemView.findViewById(R.id.tv_user_tong_thu);
            user_tong_chi=(TextView)itemView.findViewById(R.id.tv_user_tong_chi);
            delete_user=(ImageView)itemView.findViewById(R.id.btnXoaUser);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user,parent,false);
        return new UserViewHolder(view);
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        User user=dsUser.get(position);
        ((UserViewHolder)holder).user_name.setText("Tên user: "+user.getUser_name());
        ((UserViewHolder)holder).user_tong_thu.setText("Tổng thu: "+TongThu(position));
        ((UserViewHolder)holder).user_tong_chi.setText("Tổng chi: "+TongChi(position));
        ((UserViewHolder)holder).delete_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XoaUser(position);
            }
        });
    }
    public void XoaUser(int position){
        for(int i=0;i<dsUser.size();i++){
            try {
                if(((QuanLyThuChi)context).UserActive().getUser_name().equals(dsUser.get(position).getUser_name())){
                    Toast.makeText(context,"Đang đăng nhập tài khoản này!",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(position==i){
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    View view= LayoutInflater.from(context).inflate(R.layout.dialog_xac_nhan,null);
                    TextView xacnhan=view.findViewById(R.id.tvXacNhan);
                    xacnhan.setText("XÁC NHẬN XÓA USER");
                    builder.setView(view);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            userDAO=new UserDAO(context);
                            userDAO.XoaUser(dsUser.get(position).getId_user());
                            CapNhatUser();
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
            }catch (Exception ex){
                Toast.makeText(context,"Xóa thất bại",Toast.LENGTH_SHORT).show();
                break;
            }

        }
    }
    public void CapNhatUser(){
        dsUser=userDAO.getAllUser();
        AdapterUser adapterUser=new AdapterUser(context,dsUser,recyclerView);
        recyclerView.setAdapter(adapterUser);
    }
    public Double TongThu(int position){
        Double tongthu=0.0;
        LoaiThuDAO loaiThuDAO;
        KhoangThuDAO khoangThuDAO;
        ArrayList<LoaiThu> dsLoaiThu;
        ArrayList<KhoangThu> dsKhoangThu;
        loaiThuDAO = new LoaiThuDAO(context);
        dsLoaiThu =new ArrayList<>();
        dsLoaiThu=loaiThuDAO.getAllLoaiThu(dsUser.get(position).getId_user());
        for(int j=0;j<dsLoaiThu.size();j++){
            khoangThuDAO=new KhoangThuDAO(context);
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
    public Double TongChi(int position){
        Double tongchi=0.0;
        LoaiChiDAO loaiChiDAO;
        KhoangChiDAO khoangChiDAO;
        ArrayList<LoaiChi> dsLoaiChi;
        ArrayList<KhoangChi> dsKhoangChi;
        loaiChiDAO = new LoaiChiDAO(context);
        dsLoaiChi =new ArrayList<>();
        dsLoaiChi=loaiChiDAO.getAllLoaiChi(dsUser.get(position).getId_user());
        for(int j=0;j<dsLoaiChi.size();j++){
            khoangChiDAO=new KhoangChiDAO(context);
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
    @Override
    public int getItemCount() {
        return dsUser.size();
    }
}
