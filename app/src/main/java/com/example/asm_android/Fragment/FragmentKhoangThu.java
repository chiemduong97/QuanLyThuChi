package com.example.asm_android.Fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_android.Adapter.AdapterKhoangThu;
import com.example.asm_android.MainActivity2;
import com.example.asm_android.Model.KhoangThu;
import com.example.asm_android.Model.LoaiThu;
import com.example.asm_android.Model.User;
import com.example.asm_android.Model.date;
import com.example.asm_android.QuanLyThuChi;
import com.example.asm_android.R;
import com.example.asm_android.SQLite.KhoangThuDAO;
import com.example.asm_android.SQLite.LoaiThuDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FragmentKhoangThu extends Fragment {
    RecyclerView recyclerView;
    ArrayList<KhoangThu> khoangThus=new ArrayList<>();
    KhoangThuDAO khoangThuDAO;
    User user;
    date ngaythu;

    LoaiThuDAO loaiThuDAO;
    ArrayList<Integer> arrayList1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public FragmentKhoangThu() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_khoang_thu, container, false);
        recyclerView=view.findViewById(R.id.recyclerKhoangThu);
        try{
            user=((QuanLyThuChi)getContext()).UserActive();
        }
        catch (Exception exception){
            user=((MainActivity2)getContext()).UserActive();
        }
        FloatingActionButton floatingActionButton=view.findViewById(R.id.ThemKhoangThu);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());

        loaiThuDAO=new LoaiThuDAO(getContext());
        ArrayList<LoaiThu> loaiThus=loaiThuDAO.getAllLoaiThu(user.getId_user());
        arrayList1=new ArrayList<>();
        for(int i=0;i<loaiThus.size();i++){
            arrayList1.add(loaiThus.get(i).getId_loaithu());
        }

        khoangThuDAO=new KhoangThuDAO(getContext());
        khoangThus=khoangThuDAO.getAllKhoangThu(arrayList1);
        AdapterKhoangThu adapterKhoangThu=new AdapterKhoangThu(getContext(),khoangThus,recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterKhoangThu);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemKhoangThu();
            }
        });
        return view;
    }
    public void CapNhatKhoangThu(){
        ArrayList<LoaiThu> loaiThus=loaiThuDAO.getAllLoaiThu(user.getId_user());
        arrayList1=new ArrayList<>();
        for(int i=0;i<loaiThus.size();i++){
            arrayList1.add(loaiThus.get(i).getId_loaithu());
        }
        khoangThuDAO=new KhoangThuDAO(getContext());
        khoangThus=khoangThuDAO.getAllKhoangThu(arrayList1);
        AdapterKhoangThu adapterKhoangThu=new AdapterKhoangThu(getContext(),khoangThus,recyclerView);
        recyclerView.setAdapter(adapterKhoangThu);
    }
    public boolean CheckTenKhoangThu(String tenkhoangthu){
        boolean check=false;
        for(int i=0;i<khoangThus.size();i++){
            if(tenkhoangthu.equalsIgnoreCase(khoangThus.get(i).getKhoangthu_name())){
                check=true;
                break;
            }
        }
        return  check;
    }
    public void ThemKhoangThu(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        View view1= LayoutInflater.from(getContext()).inflate(R.layout.dialog_them_khoang_thu,null);
        builder.setView(view1);
        EditText ThemTenKhoangThu=view1.findViewById(R.id.etThemTenKhoangThu);
        EditText ThemSoLuongKhoangThu=view1.findViewById(R.id.etThemSoLuongKhoangThu);
        EditText ThemNgayThu=view1.findViewById(R.id.etThemNgayThu);
        ImageView btnThemNgayThu=view1.findViewById(R.id.btnThemNgayThu);
        loaiThuDAO=new LoaiThuDAO(getContext());
        ArrayList<LoaiThu> loaiThus=loaiThuDAO.getAllLoaiThu(user.getId_user());
        ArrayList<String> arrayList=new ArrayList<String>();
        for(int i=0;i<loaiThus.size();i++){
            arrayList.add(loaiThus.get(i).getLoaithu_name());
        }
        Spinner spinnerThemKhoangThu=view1.findViewById(R.id.spinnerThemKhoangThu);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,arrayList);
        spinnerThemKhoangThu.setAdapter(adapter);

        btnThemNgayThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int ngay=calendar.get(Calendar.DATE);
                int thang=calendar.get(Calendar.MONTH);
                int nam=calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(),new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
                        ThemNgayThu.setText(simpleDateFormat.format(calendar.getTime()));
                        ngaythu=new date(dayOfMonth,month+1,year);
                    }
                },nam,thang,ngay);
                datePickerDialog.show();
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int vitri = 0;
                for(int i=0;i<loaiThus.size();i++){
                    if(spinnerThemKhoangThu.getSelectedItemPosition()==i){
                        vitri=loaiThus.get(i).getId_loaithu();
                        break;
                    }
                }

                try{
                    if (ThemTenKhoangThu.getText().toString().equals("")||ThemSoLuongKhoangThu.getText().toString().equals("")||ThemNgayThu.getText().toString().equals("")||vitri==0){
                        Toast.makeText(getContext(),"Không được để trống, thêm thất bại",Toast.LENGTH_SHORT).show();
                    }
                    else if(CheckTenKhoangThu(ThemTenKhoangThu.getText().toString())){
                        Toast.makeText(getContext(),"Trùng tên khoảng thu, thêm thất bại",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        khoangThuDAO=new KhoangThuDAO(getContext());
                        khoangThuDAO.ThemKhoangThu(vitri,ThemTenKhoangThu.getText().toString(),Double.parseDouble(ThemSoLuongKhoangThu.getText().toString()),ngaythu);
                        CapNhatKhoangThu();
                        Toast.makeText(getContext(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception ex){
                    Toast.makeText(getContext(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
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
}
