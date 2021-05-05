package com.example.asm_android.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_android.Adapter.AdapterLoaiChi;
import com.example.asm_android.Adapter.AdapterLoaiThu;
import com.example.asm_android.BottomNavigationBehavior;
import com.example.asm_android.Model.LoaiThu;
import com.example.asm_android.Model.User;
import com.example.asm_android.QuanLyThuChi;
import com.example.asm_android.R;
import com.example.asm_android.SQLite.LoaiChiDAO;
import com.example.asm_android.SQLite.LoaiThuDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragmentLoaiThu extends Fragment {
    RecyclerView recyclerView;
    ArrayList<LoaiThu> loaiThus=new ArrayList<>();
    LoaiThuDAO loaiThuDAO;
    User user;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public FragmentLoaiThu() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_loai_thu, container, false);
        recyclerView=view.findViewById(R.id.recyclerLoaiThu);
        user=((QuanLyThuChi)getContext()).UserActive();
        FloatingActionButton floatingActionButton=view.findViewById(R.id.ThemLoaiThu);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        loaiThuDAO=new LoaiThuDAO(getContext());
        loaiThus=loaiThuDAO.getAllLoaiThu(user.getId_user());
        AdapterLoaiThu adapterLoaiThu=new AdapterLoaiThu(getContext(),loaiThus,recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterLoaiThu);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemLoaiThu();
            }
        });
        
        return view;
    }
    public void CapNhatLoaiThu(){
        loaiThuDAO=new LoaiThuDAO(getContext());
        loaiThus=loaiThuDAO.getAllLoaiThu(user.getId_user());
        AdapterLoaiThu adapterLoaiThu=new AdapterLoaiThu(getContext(),loaiThus,recyclerView);
        recyclerView.setAdapter(adapterLoaiThu);
    }
    public boolean CheckTenLoaiThu(String tenloaichi){
        boolean check=false;
        for(int i=0;i<loaiThus.size();i++){
            if(tenloaichi.equalsIgnoreCase(loaiThus.get(i).getLoaithu_name())){
                check=true;
                break;
            }
        }
        return  check;
    }
    public void ThemLoaiThu(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        View view1= LayoutInflater.from(getContext()).inflate(R.layout.dialog_them_loai_thu,null);
        builder.setView(view1);
        EditText ThemTenLoaiThu=view1.findViewById(R.id.etThemTenLoaiThu);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    if (ThemTenLoaiThu.getText().toString().equals("")){
                        Toast.makeText(getActivity(),"Không được để trống, thêm thất bại",Toast.LENGTH_SHORT).show();
                    }
                    else if(CheckTenLoaiThu(ThemTenLoaiThu.getText().toString())){
                        Toast.makeText(getContext(),"Trùng tên loại thu, thêm thất bại",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        loaiThuDAO=new LoaiThuDAO(getContext());
                        loaiThuDAO.ThemLoaiThu(user.getId_user(),ThemTenLoaiThu.getText().toString());
                        CapNhatLoaiThu();
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
