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

import com.example.asm_android.Adapter.AdapterKhoangChi;
import com.example.asm_android.Adapter.AdapterLoaiChi;
import com.example.asm_android.Model.KhoangChi;
import com.example.asm_android.Model.LoaiChi;
import com.example.asm_android.Model.User;
import com.example.asm_android.Model.date;
import com.example.asm_android.QuanLyThuChi;
import com.example.asm_android.R;
import com.example.asm_android.SQLite.KhoangChiDAO;
import com.example.asm_android.SQLite.LoaiChiDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FragmentKhoangChi extends Fragment {
    RecyclerView recyclerView;
    ArrayList<KhoangChi> khoangChis=new ArrayList<>();
    KhoangChiDAO khoangChiDAO;
    User user;
    date ngaychi;

    LoaiChiDAO loaiChiDAO;
    ArrayList<Integer> arrayList1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public FragmentKhoangChi() {
            
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_khoang_chi, container, false);
        recyclerView=view.findViewById(R.id.recyclerKhoangChi);
        user=((QuanLyThuChi)getContext()).UserActive();
        FloatingActionButton floatingActionButton=view.findViewById(R.id.ThemKhoangChi);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());

        loaiChiDAO=new LoaiChiDAO(getContext());
        ArrayList<LoaiChi> loaiChis=loaiChiDAO.getAllLoaiChi(user.getId_user());
        arrayList1=new ArrayList<>();
        for(int i=0;i<loaiChis.size();i++){
            arrayList1.add(loaiChis.get(i).getId_loaichi());
        }

        khoangChiDAO=new KhoangChiDAO(getContext());
        khoangChis=khoangChiDAO.getAllKhoangChi(arrayList1);
        AdapterKhoangChi adapterKhoangChi=new AdapterKhoangChi(getContext(),khoangChis,recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterKhoangChi);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemKhoangChi();
            }
        });
        return view;
    }
    public void CapNhatKhoangChi(){
        ArrayList<LoaiChi> loaiChis=loaiChiDAO.getAllLoaiChi(user.getId_user());
        arrayList1=new ArrayList<>();
        for(int i=0;i<loaiChis.size();i++){
            arrayList1.add(loaiChis.get(i).getId_loaichi());
        }
        khoangChiDAO=new KhoangChiDAO(getContext());
        khoangChis=khoangChiDAO.getAllKhoangChi(arrayList1);
        AdapterKhoangChi adapterKhoangChi=new AdapterKhoangChi(getContext(),khoangChis,recyclerView);
        recyclerView.setAdapter(adapterKhoangChi);
    }
    public boolean CheckTenKhoangChi(String tenkhoangchi){
        boolean check=false;
        for(int i=0;i<khoangChis.size();i++){
            if(tenkhoangchi.equalsIgnoreCase(khoangChis.get(i).getKhoangchi_name())){
                check=true;
                break;
            }
        }
        return  check;
    }
    public void ThemKhoangChi(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        View view1= LayoutInflater.from(getContext()).inflate(R.layout.dialog_them_khoang_chi,null);
        builder.setView(view1);
        EditText ThemTenKhoangChi=view1.findViewById(R.id.etThemTenKhoangChi);
        EditText ThemSoLuongKhoangChi=view1.findViewById(R.id.etThemSoLuongKhoangChi);
        EditText ThemNgayChi=view1.findViewById(R.id.etThemNgayChi);
        ImageView btnThemNgayChi=view1.findViewById(R.id.btnThemNgayChi);
        loaiChiDAO=new LoaiChiDAO(getContext());
        ArrayList<LoaiChi> loaiChis=loaiChiDAO.getAllLoaiChi(user.getId_user());
        ArrayList<String> arrayList=new ArrayList<String>();
        for(int i=0;i<loaiChis.size();i++){
            arrayList.add(loaiChis.get(i).getLoaichi_name());
        }
        Spinner spinnerThemKhoangChi=view1.findViewById(R.id.spinnerThemKhoangChi);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,arrayList);
        spinnerThemKhoangChi.setAdapter(adapter);

        btnThemNgayChi.setOnClickListener(new View.OnClickListener() {
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
                        ThemNgayChi.setText(simpleDateFormat.format(calendar.getTime()));
                        ngaychi=new date(dayOfMonth,month+1,year);
                    }
                },nam,thang,ngay);
                datePickerDialog.show();
            }
        });


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int vitri = 0;
                for(int i=0;i<loaiChis.size();i++){
                    if(spinnerThemKhoangChi.getSelectedItemPosition()==i){
                        vitri=loaiChis.get(i).getId_loaichi();
                        break;
                    }
                }
                try{
                    if (ThemTenKhoangChi.getText().toString().equals("")||ThemSoLuongKhoangChi.getText().toString().equals("")||ThemNgayChi.getText().toString().equals("")||vitri==0){
                        Toast.makeText(getContext(),"Không được để trống, thêm thất bại",Toast.LENGTH_SHORT).show();
                    }
                    else if(CheckTenKhoangChi(ThemTenKhoangChi.getText().toString())){
                        Toast.makeText(getContext(),"Trùng tên khoảng chi, thêm thất bại",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        khoangChiDAO=new KhoangChiDAO(getContext());
                        khoangChiDAO.ThemKhoangChi(vitri,ThemTenKhoangChi.getText().toString(),Double.parseDouble(ThemSoLuongKhoangChi.getText().toString()),ngaychi);
                        CapNhatKhoangChi();
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
