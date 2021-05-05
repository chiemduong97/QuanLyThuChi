package com.example.asm_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_android.Adapter.RecycleAvatarAdapter;
import com.example.asm_android.Fragment.FragmentChi;
import com.example.asm_android.Fragment.FragmentGioiThieu;
import com.example.asm_android.Fragment.FragmentTaiKhoan;
import com.example.asm_android.Fragment.FragmentThongKe;
import com.example.asm_android.Fragment.FragmentThu;
import com.example.asm_android.Model.User;
import com.example.asm_android.SQLite.UserDAO;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class QuanLyThuChi extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
    String s;

    UserDAO userDAO;
    ArrayList<User> dsUser=new ArrayList<>();
    User user_active;

    ImageView user_avatar;
    TextView user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_thu_chi);
        drawerLayout=findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.navigationView);
        toolbar =  findViewById(R.id.toolbar);
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,new FragmentGioiThieu()).commit();
        toolbar.setTitle("GIỚI THIỆU");

        userDAO=new UserDAO(QuanLyThuChi.this);
        dsUser=userDAO.getAllUser();

        s=getIntent().getStringExtra("user_active");

        for(int i=0;i<dsUser.size();i++){
            if(dsUser.get(i).getUser_name().equals(s)){
                user_active=new User(dsUser.get(i).getId_user(),dsUser.get(i).getUser_name(), dsUser.get(i).getPassword(), dsUser.get(i).getAvatar());
                break;
            }
        }


        View view=navigationView.getHeaderView(0);
        user_avatar=view.findViewById(R.id.user_avatar_header);
        user_name=view.findViewById(R.id.user_name_header);
        user_avatar.setImageResource(user_active.getAvatar());
        user_name.setText(user_active.getUser_name());



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                drawerLayout.closeDrawers();

                if (item.getItemId() == R.id.quanlythu) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout,new FragmentThu()).commit();
                    toolbar.setTitle("QUẢN LÝ THU");
                }
                else if (item.getItemId() == R.id.quanlychi) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout,new FragmentChi()).commit();
                    toolbar.setTitle("QUẢN LÝ CHI");
                }
                else if (item.getItemId() == R.id.gioithieu) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout,new FragmentGioiThieu()).commit();
                    toolbar.setTitle("GIỚI THIỆU");
                }
                else if (item.getItemId() == R.id.thongke) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout,new FragmentThongKe()).commit();
                    toolbar.setTitle("THỐNG KÊ");
                }
                else if (item.getItemId() == R.id.quanlytaikhoan) {
                    if(s.equals("chiemduong97")){
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayout,new FragmentTaiKhoan()).commit();
                        toolbar.setTitle("QUẢN LÝ TÀI KHOẢN");
                    }
                    else
                        Toast.makeText(QuanLyThuChi.this, "Bạn không có quyền truy cập!", Toast.LENGTH_SHORT).show();
                }
                else if (item.getItemId() == R.id.thoat){
                    Snackbar.make(findViewById(R.id.root),"Xác nhận thoát",5000)
                            .setActionTextColor(Color.RED)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finishAffinity();
                                    System.exit(0);
                                }
                            })
                            .show();
                }
                return true;
            }
        });
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
    public void setTitle(String s){
        toolbar.setTitle(s);
    }



    public User UserActive(){
        UserDAO userDAO=new UserDAO(QuanLyThuChi.this);
        dsUser=userDAO.getAllUser();
        for(int i=0;i<dsUser.size();i++){
            if(dsUser.get(i).getUser_name().equals(s)){
                user_active=new User(dsUser.get(i).getId_user(),dsUser.get(i).getUser_name(), dsUser.get(i).getPassword(), dsUser.get(i).getAvatar());
                break;
            }
        }
        return user_active;
    }
    public void CapNhatUser(User user){
        user_avatar.setImageResource(user.getAvatar());
        user_name.setText(user.getUser_name());
    }
}