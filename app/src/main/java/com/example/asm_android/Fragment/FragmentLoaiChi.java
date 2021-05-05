package com.example.asm_android.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_android.Adapter.AdapterLoaiChi;
import com.example.asm_android.Adapter.AdapterUser;
import com.example.asm_android.MainActivity2;
import com.example.asm_android.Model.LoaiChi;
import com.example.asm_android.Model.User;
import com.example.asm_android.QuanLyThuChi;
import com.example.asm_android.R;
import com.example.asm_android.SQLite.LoaiChiDAO;
import com.example.asm_android.SQLite.UserDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragmentLoaiChi extends Fragment {
    RecyclerView recyclerView;
    ArrayList<LoaiChi> loaiChis=new ArrayList<>();
    LoaiChiDAO loaiChiDAO;
    User user;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public FragmentLoaiChi() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_loai_chi, container, false);
        recyclerView=view.findViewById(R.id.recyclerLoaiChi);
        try{
            user=((QuanLyThuChi)getContext()).UserActive();
        }
        catch (Exception exception){
            user=((MainActivity2)getContext()).UserActive();
        }
        FloatingActionButton floatingActionButton=view.findViewById(R.id.ThemLoaiChi);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        loaiChiDAO=new LoaiChiDAO(getContext());
        loaiChis=loaiChiDAO.getAllLoaiChi(user.getId_user());
        AdapterLoaiChi adapterLoaiChi=new AdapterLoaiChi(getContext(),loaiChis,recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterLoaiChi);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemLoaiChi();
            }
        });
        return view;
    }
    public void CapNhatLoaiChi(){
        loaiChiDAO=new LoaiChiDAO(getContext());
        loaiChis=loaiChiDAO.getAllLoaiChi(user.getId_user());
        AdapterLoaiChi adapterLoaiChi=new AdapterLoaiChi(getContext(),loaiChis,recyclerView);
        recyclerView.setAdapter(adapterLoaiChi);
    }
    public boolean CheckTenLoaiChi(String tenloaichi){
        boolean check=false;
        for(int i=0;i<loaiChis.size();i++){
            if(tenloaichi.equalsIgnoreCase(loaiChis.get(i).getLoaichi_name())){
                check=true;
                break;
            }
        }
        return  check;
    }
    public void ThemLoaiChi(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        View view1= LayoutInflater.from(getContext()).inflate(R.layout.dialog_them_loai_chi,null);
        builder.setView(view1);
        EditText ThemTenLoaiChi=view1.findViewById(R.id.etThemTenLoaiChi);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    if (ThemTenLoaiChi.getText().toString().equals("")){
                        Toast.makeText(getActivity(),"Không được để trống, thêm thất bại",Toast.LENGTH_SHORT).show();
                    }
                    else if(CheckTenLoaiChi(ThemTenLoaiChi.getText().toString())){
                        Toast.makeText(getContext(),"Trùng tên loại chi, thêm thất bại",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        loaiChiDAO=new LoaiChiDAO(getContext());
                        loaiChiDAO.ThemLoaiChi(user.getId_user(),ThemTenLoaiChi.getText().toString());
                        CapNhatLoaiChi();
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
