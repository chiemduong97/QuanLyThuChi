package com.example.asm_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.asm_android.Fragment.FragmentKhoangChi;
import com.example.asm_android.Fragment.FragmentKhoangThu;
import com.example.asm_android.Fragment.FragmentLoaiChi;
import com.example.asm_android.Fragment.FragmentLoaiThu;
import com.example.asm_android.Fragment.FragmentTaiKhoan;
import com.example.asm_android.Fragment.FragmentThongKeChi;
import com.example.asm_android.Fragment.FragmentThongKeTheoNamViewPager;
import com.example.asm_android.Fragment.FragmentThongKeThu;
import com.example.asm_android.Model.User;
import com.example.asm_android.SQLite.UserDAO;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar=findViewById(R.id.toolbar2);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent=getIntent();
        int position=intent.getIntExtra("danhmuc",-1);
        if(position==0){
            if((intent.getStringExtra("user").equals("chiemduong97"))){
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout2,new FragmentTaiKhoan()).commit();
                toolbar.setTitle("QUẢN LÝ TÀI KHOẢN");

            }
            else
                Toast.makeText(MainActivity2.this,"Bạn không có quyền truy cập!",Toast.LENGTH_SHORT).show();
        }
        if(position==1){
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout2,new FragmentThongKeTheoNamViewPager()).commit();
            toolbar.setTitle("THỐNG KÊ THEO NĂM");

        }
        if(position==2){
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout2,new FragmentLoaiThu()).commit();
            toolbar.setTitle("LOẠI THU");
        }
        if(position==3){
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout2,new FragmentLoaiChi()).commit();
            toolbar.setTitle("LOẠI CHI");
        }
        if(position==4){
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout2,new FragmentKhoangThu()).commit();
            toolbar.setTitle("KHOẢNG THU");
        }
        if(position==5){
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout2,new FragmentKhoangChi()).commit();
            toolbar.setTitle("KHOẢNG CHI");
        }
        if(position==6){
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout2,new FragmentThongKeThu()).commit();
            toolbar.setTitle("BIỂU ĐỒ THU");
        }
        if(position==7){
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout2,new FragmentThongKeChi()).commit();
            toolbar.setTitle("BIỂU ĐỒ CHI");
        }
    }
    public User UserActive() {
        User user = new User();
        UserDAO userDAO = new UserDAO(MainActivity2.this);
        ArrayList<User> dsUser = userDAO.getAllUser();
        for (int i = 0; i < dsUser.size(); i++) {
            if (dsUser.get(i).getUser_name().equals(getIntent().getStringExtra("user"))) {
                user = dsUser.get(i);
                break;
            }
        }
        return user;
    }
}