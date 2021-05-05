package com.example.asm_android.Adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_android.Model.KhoangThu;
import com.example.asm_android.Model.LoaiThu;
import com.example.asm_android.Model.date;
import com.example.asm_android.QuanLyThuChi;
import com.example.asm_android.R;
import com.example.asm_android.SQLite.KhoangThuDAO;
import com.example.asm_android.SQLite.LoaiThuDAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AdapterKhoangThu extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<KhoangThu> dsKhoangThu;
    KhoangThuDAO khoangThuDAO;
    RecyclerView recyclerView;

    LoaiThuDAO loaiThuDAO;
    ArrayList<Integer> arrayList1;
    date ngaythu;
    public AdapterKhoangThu(Context context, ArrayList<KhoangThu> dsKhoangThu, RecyclerView recyclerView){
        this.context=context;
        this.dsKhoangThu=dsKhoangThu;
        this.recyclerView=recyclerView;
    }

    public class KhoangThuViewHolder extends RecyclerView.ViewHolder{
        public TextView khoangthu_ma;
        public TextView khoangthu_name;
        public TextView khoangthu_soluong;
        public TextView khoangthu_loaithu;
        public TextView ngaythu;
        public ImageView delete_khoangthu;
        public ImageView edit_khoangthu;
        public KhoangThuViewHolder(@NonNull View itemView) {
            super(itemView);
            khoangthu_ma=(TextView)itemView.findViewById(R.id.tv_layout_maKhoangThu);
            khoangthu_name=(TextView)itemView.findViewById(R.id.tv_layout_tenKhoangThu);
            khoangthu_soluong=(TextView)itemView.findViewById(R.id.tv_layout_soluongKhoangThu);
            khoangthu_loaithu=(TextView)itemView.findViewById(R.id.tv_layout_LoaiThu);
            ngaythu=(TextView)itemView.findViewById(R.id.tv_layout_NgayThu);
            delete_khoangthu=(ImageView)itemView.findViewById(R.id.btnXoaKhoangThu);
            edit_khoangthu=(ImageView)itemView.findViewById(R.id.btnSuaKhoangThu);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.khoangthu,parent,false);
        return new KhoangThuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        KhoangThu khoangThu=dsKhoangThu.get(position);
        ((KhoangThuViewHolder)holder).khoangthu_ma.setText("Mã: "+khoangThu.getId_khoangthu());
        ((KhoangThuViewHolder)holder).khoangthu_name.setText("Tên: "+khoangThu.getKhoangthu_name());
        ((KhoangThuViewHolder)holder).khoangthu_soluong.setText("Số lượng: "+khoangThu.getKhoangthu_soluong());
        ((KhoangThuViewHolder)holder).khoangthu_loaithu.setText("Mã loại thu: "+khoangThu.getId_loaithu());
        ((KhoangThuViewHolder)holder).ngaythu.setText("Ngày thu: "+khoangThu.getNgay_thu());
        ((KhoangThuViewHolder)holder).delete_khoangthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XoaKhoangThu(position);
            }
        });
        ((KhoangThuViewHolder)holder).edit_khoangthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuaKhoangThu(position);
            }
        });
    }
    public void CapNhatKhoangThu(){
        loaiThuDAO=new LoaiThuDAO(context);
        ArrayList<LoaiThu> loaiThus=loaiThuDAO.getAllLoaiThu(((QuanLyThuChi)context).UserActive().getId_user());
        arrayList1=new ArrayList<>();
        for(int i=0;i<loaiThus.size();i++){
            arrayList1.add(loaiThus.get(i).getId_loaithu());
        }
        dsKhoangThu=khoangThuDAO.getAllKhoangThu(arrayList1);
        AdapterKhoangThu adapterKhoangThu=new AdapterKhoangThu(context,dsKhoangThu,recyclerView);
        recyclerView.setAdapter(adapterKhoangThu);
    }

    public void XoaKhoangThu(int position){
        for(int i=0;i<dsKhoangThu.size();i++){
            try {
                if(position==i){
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    View view= LayoutInflater.from(context).inflate(R.layout.dialog_xac_nhan,null);
                    TextView xacnhan=view.findViewById(R.id.tvXacNhan);
                    xacnhan.setText("XÁC NHẬN XÓA KHOẢNG THU");
                    builder.setView(view);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            khoangThuDAO=new KhoangThuDAO(context);
                            khoangThuDAO.XoaKhoangThu(dsKhoangThu.get(position).getId_khoangthu());
                            CapNhatKhoangThu();
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
    public void SuaKhoangThu(int position){
        for(int i=0;i<dsKhoangThu.size();i++){
            try {
                if(position==i){
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    View view= LayoutInflater.from(context).inflate(R.layout.dialog_sua_khoang_thu,null);
                    builder.setView(view);
                    TextView tvMaKhoangThu=view.findViewById(R.id.tvMaKhoangThu);
                    EditText etSuaTenKhoangThu=view.findViewById(R.id.etSuaTenKhoangThu);
                    EditText etSuaSoLuongKhoangThu=view.findViewById(R.id.etSuaSoLuongKhoangThu);
                    EditText SuaNgayThu=view.findViewById(R.id.etSuaNgayThu);
                    ImageView btnSuaNgayThu=view.findViewById(R.id.btnSuaNgayThu);

                    loaiThuDAO=new LoaiThuDAO(context);
                    ArrayList<LoaiThu> loaiThus=loaiThuDAO.getAllLoaiThu(((QuanLyThuChi)context).UserActive().getId_user());
                    ArrayList<String> arrayList=new ArrayList<String>();
                    for(int j=0;j<loaiThus.size();j++){
                        arrayList.add(loaiThus.get(j).getLoaithu_name());
                    }
                    Spinner spinnerSuaKhoangThu=view.findViewById(R.id.spinnerSuaKhoangThu);
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item,arrayList);
                    spinnerSuaKhoangThu.setAdapter(adapter);

                    btnSuaNgayThu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar calendar=Calendar.getInstance();
                            int ngay=calendar.get(Calendar.DATE);
                            int thang=calendar.get(Calendar.MONTH);
                            int nam=calendar.get(Calendar.YEAR);
                            DatePickerDialog datePickerDialog=new DatePickerDialog(context,new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    calendar.set(year,month,dayOfMonth);
                                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
                                    SuaNgayThu.setText(simpleDateFormat.format(calendar.getTime()));
                                    ngaythu=new date(dayOfMonth,month+1,year);
                                }
                            },nam,thang,ngay);
                            datePickerDialog.show();
                        }
                    });

                    tvMaKhoangThu.setText("Mã khoảng thu:"+dsKhoangThu.get(position).getId_khoangthu());
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int vitri = 0;
                            for(int k=0;k<loaiThus.size();k++){
                                if(spinnerSuaKhoangThu.getSelectedItemPosition()==k){
                                    vitri=loaiThus.get(k).getId_loaithu();
                                    break;
                                }
                            }
                            try{
                                if (etSuaTenKhoangThu.getText().toString().equals("")||etSuaSoLuongKhoangThu.getText().toString().equals("")||SuaNgayThu.getText().toString().equals("")||vitri==0){
                                    Toast.makeText(context,"Không được để trống, sửa thất bại",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    khoangThuDAO=new KhoangThuDAO(context);
                                    khoangThuDAO.SuaKhoangThu(dsKhoangThu.get(position).getId_khoangthu(),etSuaTenKhoangThu.getText().toString(),Double.parseDouble(etSuaSoLuongKhoangThu.getText().toString()),vitri,ngaythu);
                                    CapNhatKhoangThu();
                                    Toast.makeText(context,"Sửa thành công",Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception ex){
                                Toast.makeText(context,"Sửa thất bại",Toast.LENGTH_SHORT).show();
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
                break;
            }

        }
    }
    @Override
    public int getItemCount() {
        return dsKhoangThu.size();
    }
}
