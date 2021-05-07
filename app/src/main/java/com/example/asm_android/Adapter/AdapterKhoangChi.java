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

import com.example.asm_android.MainActivity2;
import com.example.asm_android.Model.KhoangChi;
import com.example.asm_android.Model.LoaiChi;
import com.example.asm_android.Model.User;
import com.example.asm_android.Model.date;
import com.example.asm_android.QuanLyThuChi;
import com.example.asm_android.R;
import com.example.asm_android.SQLite.KhoangChiDAO;
import com.example.asm_android.SQLite.LoaiChiDAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AdapterKhoangChi extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<KhoangChi> dsKhoangChi;
    KhoangChiDAO khoangChiDAO;
    RecyclerView recyclerView;

    LoaiChiDAO loaiChiDAO;
    ArrayList<Integer> arrayList1;
    date ngaychi;

    public AdapterKhoangChi(Context context,ArrayList<KhoangChi> dsKhoangChi,RecyclerView recyclerView){
        this.context=context;
        this.dsKhoangChi=dsKhoangChi;
        this.recyclerView=recyclerView;
    }

    public class KhoangChiViewHolder extends RecyclerView.ViewHolder{
        public TextView khoangchi_ma;
        public TextView khoangchi_name;
        public TextView khoangchi_soluong;
        public TextView khoangchi_loaichi;
        public TextView ngaychi;
        public ImageView delete_khoangchi;
        public ImageView edit_khoangchi;
        public KhoangChiViewHolder(@NonNull View itemView) {
            super(itemView);
            khoangchi_ma=(TextView)itemView.findViewById(R.id.tv_layout_maKhoangChi);
            khoangchi_name=(TextView)itemView.findViewById(R.id.tv_layout_tenKhoangChi);
            khoangchi_soluong=(TextView)itemView.findViewById(R.id.tv_layout_soluongKhoangChi);
            khoangchi_loaichi=(TextView)itemView.findViewById(R.id.tv_layout_LoaiChi);
            ngaychi=(TextView)itemView.findViewById(R.id.tv_layout_NgayChi);
            delete_khoangchi=(ImageView)itemView.findViewById(R.id.btnXoaKhoangChi);
            edit_khoangchi=(ImageView)itemView.findViewById(R.id.btnSuaKhoangChi);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.khoangchi,parent,false);
        return new KhoangChiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        KhoangChi khoangChi=dsKhoangChi.get(position);
        ((KhoangChiViewHolder)holder).khoangchi_ma.setText("Mã: "+khoangChi.getId_khoangchi());
        ((KhoangChiViewHolder)holder).khoangchi_name.setText("Tên: "+khoangChi.getKhoangchi_name());
        ((KhoangChiViewHolder)holder).khoangchi_soluong.setText("Số lượng: "+khoangChi.getKhoangchi_soluong());
        ((KhoangChiViewHolder)holder).khoangchi_loaichi.setText("Mã loại chi: "+khoangChi.getId_loaichi());
        ((KhoangChiViewHolder)holder).ngaychi.setText("Ngày chi: "+khoangChi.getNgay_chi());
        ((KhoangChiViewHolder)holder).delete_khoangchi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XoaKhoangChi(position);
            }
        });
        ((KhoangChiViewHolder)holder).edit_khoangchi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuaKhoangChi(position);
            }
        });
    }
    public void CapNhatKhoangChi(){
        loaiChiDAO=new LoaiChiDAO(context);
        User user;
        try{
            user=((QuanLyThuChi)context).UserActive();
        }
        catch (Exception exception){
            user=((MainActivity2)context).UserActive();
        }
        ArrayList<LoaiChi> loaiChis=loaiChiDAO.getAllLoaiChi(user.getId_user());
        arrayList1=new ArrayList<>();
        for(int i=0;i<loaiChis.size();i++){
            arrayList1.add(loaiChis.get(i).getId_loaichi());
        }
        dsKhoangChi=khoangChiDAO.getAllKhoangChi(arrayList1);
        AdapterKhoangChi adapterKhoangChi=new AdapterKhoangChi(context,dsKhoangChi,recyclerView);
        recyclerView.setAdapter(adapterKhoangChi);
    }

    public void XoaKhoangChi(int position){
        for(int i=0;i<dsKhoangChi.size();i++){
            try {
                if(position==i){
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    View view= LayoutInflater.from(context).inflate(R.layout.dialog_xac_nhan,null);
                    TextView xacnhan=view.findViewById(R.id.tvXacNhan);
                    xacnhan.setText("XÁC NHẬN XÓA KHOẢNG CHI");
                    builder.setView(view);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            khoangChiDAO=new KhoangChiDAO(context);
                            khoangChiDAO.XoaKhoangChi(dsKhoangChi.get(position).getId_khoangchi());
                            CapNhatKhoangChi();
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
    public void SuaKhoangChi(int position){
        for(int i=0;i<dsKhoangChi.size();i++){
            try {
                if(position==i){
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    View view= LayoutInflater.from(context).inflate(R.layout.dialog_sua_khoang_chi,null);
                    builder.setView(view);
                    TextView tvMaKhoangChi=view.findViewById(R.id.tvMaKhoangChi);
                    EditText etSuaTenKhoangChi=view.findViewById(R.id.etSuaTenKhoangChi);
                    EditText etSuaSoLuongKhoangChi=view.findViewById(R.id.etSuaSoLuongKhoangChi);
                    EditText SuaNgayChi=view.findViewById(R.id.etSuaNgayChi);
                    ImageView btnSuaNgayChi=view.findViewById(R.id.btnSuaNgayChi);

                    loaiChiDAO=new LoaiChiDAO(context);
                    User user;
                    try{
                        user=((QuanLyThuChi)context).UserActive();
                    }
                    catch (Exception exception){
                        user=((MainActivity2)context).UserActive();
                    }
                    ArrayList<LoaiChi> loaiChis=loaiChiDAO.getAllLoaiChi(user.getId_user());
                    ArrayList<String> arrayList=new ArrayList<String>();
                    for(int j=0;j<loaiChis.size();j++){
                        arrayList.add(loaiChis.get(j).getLoaichi_name());
                    }
                    Spinner spinnerSuaKhoangChi=view.findViewById(R.id.spinnerSuaKhoangChi);
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item,arrayList);
                    spinnerSuaKhoangChi.setAdapter(adapter);

                    btnSuaNgayChi.setOnClickListener(new View.OnClickListener() {
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
                                    SuaNgayChi.setText(simpleDateFormat.format(calendar.getTime()));
                                    ngaychi=new date(dayOfMonth,month+1,year);
                                }
                            },nam,thang,ngay);
                            datePickerDialog.show();
                        }
                    });

                    tvMaKhoangChi.setText("Mã khoảng chi:"+dsKhoangChi.get(position).getId_khoangchi());
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int vitri = 0;
                            for(int k=0;k<loaiChis.size();k++){
                                if(spinnerSuaKhoangChi.getSelectedItemPosition()==k){
                                    vitri=loaiChis.get(k).getId_loaichi();
                                    break;
                                }
                            }
                            try{
                                if (etSuaTenKhoangChi.getText().toString().equals("")||etSuaSoLuongKhoangChi.getText().toString().equals("")||SuaNgayChi.getText().toString().equals("")||vitri==0){
                                    Toast.makeText(context,"Không được để trống, sửa thất bại",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    khoangChiDAO=new KhoangChiDAO(context);
                                    khoangChiDAO.SuaKhoangChi(dsKhoangChi.get(position).getId_khoangchi(),etSuaTenKhoangChi.getText().toString(),Double.parseDouble(etSuaSoLuongKhoangChi.getText().toString()),vitri,ngaychi);
                                    CapNhatKhoangChi();
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
        return dsKhoangChi.size();
    }
}
