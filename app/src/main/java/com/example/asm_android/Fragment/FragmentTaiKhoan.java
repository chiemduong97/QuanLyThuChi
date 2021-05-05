package com.example.asm_android.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_android.Adapter.AdapterUser;
import com.example.asm_android.Model.User;
import com.example.asm_android.R;
import com.example.asm_android.SQLite.UserDAO;

import java.util.ArrayList;

public class FragmentTaiKhoan extends Fragment {
    RecyclerView recyclerView;
    ArrayList<User> users=new ArrayList<>();
    UserDAO userDAO;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public FragmentTaiKhoan() {
        
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_tai_khoan, container, false);
        recyclerView=view.findViewById(R.id.recyclerUser);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        userDAO=new UserDAO(getContext());
        users=userDAO.getAllUser();
        AdapterUser adapterUser= new AdapterUser(getContext(),users,recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterUser);
        return view;
    }
    public void CapNhatUser(){
        users=userDAO.getAllUser();
        AdapterUser adapterUser=new AdapterUser(getContext(),users,recyclerView);
        recyclerView.setAdapter(adapterUser);
    }

}
