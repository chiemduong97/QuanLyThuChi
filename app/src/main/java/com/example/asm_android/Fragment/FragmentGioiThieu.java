package com.example.asm_android.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.asm_android.Adapter.AdapterRecycleGioiThieu;
import com.example.asm_android.Adapter.RecycleAvatarAdapter;
import com.example.asm_android.MainActivity2;
import com.example.asm_android.Model.IconGioiThieu;
import com.example.asm_android.Model.KhoangChi;
import com.example.asm_android.Model.KhoangThu;
import com.example.asm_android.Model.LoaiChi;
import com.example.asm_android.Model.LoaiThu;
import com.example.asm_android.Model.User;
import com.example.asm_android.QuanLyThuChi;
import com.example.asm_android.R;
import com.example.asm_android.RecyclerTouchListener;
import com.example.asm_android.SQLite.KhoangChiDAO;
import com.example.asm_android.SQLite.KhoangThuDAO;
import com.example.asm_android.SQLite.LoaiChiDAO;
import com.example.asm_android.SQLite.LoaiThuDAO;
import com.example.asm_android.SQLite.UserDAO;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class FragmentGioiThieu extends Fragment {
    RecyclerView recyclerView;
    String s;
    ImageView select_avatar;

    UserDAO userDAO;
    ArrayList<User> dsUser=new ArrayList<>();
    User user_active = new User();
    int avar_select;
    ArrayList<IconGioiThieu> iconGioiThieus=new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public FragmentGioiThieu() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_gioi_thieu,null);
        user_active=((QuanLyThuChi)getContext()).UserActive();
        TextView user_name=(TextView)view.findViewById(R.id.user_name);
        ImageView user_avatar=(ImageView)view.findViewById(R.id.user_avatar);
        LinearLayout change_avatar=(LinearLayout) view.findViewById(R.id.change_avatar) ;
        LinearLayout change_password=(LinearLayout) view.findViewById(R.id.change_password);
        LinearLayout logout=(LinearLayout) view.findViewById(R.id.logout);

        userDAO=new UserDAO(getContext());
        dsUser=userDAO.getAllUser();
        
        user_name.setText(user_active.getUser_name());
        user_avatar.setImageResource(user_active.getAvatar());

        change_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                View view= LayoutInflater.from(getContext()).inflate(R.layout.list_avatar,null);
                RecyclerView list_avatar=(RecyclerView)view.findViewById(R.id.list_avatar);
                select_avatar=(ImageView)view.findViewById(R.id.avatar_select);
                ArrayList<Integer> integers=new ArrayList<>();
                integers.add(R.drawable.avt1);
                integers.add(R.drawable.avt2);
                integers.add(R.drawable.avt3);
                integers.add(R.drawable.avt4);
                integers.add(R.drawable.avt5);
                integers.add(R.drawable.avt6);
                integers.add(R.drawable.avt7);
                integers.add(R.drawable.avt8);
                integers.add(R.drawable.avt9);
                integers.add(R.drawable.avt10);
                integers.add(R.drawable.avt11);
                integers.add(R.drawable.avt12);
                integers.add(R.drawable.avt13);
                integers.add(R.drawable.avt14);
                integers.add(R.drawable.avt15);
                GridLayoutManager gridLayoutManager=new GridLayoutManager(view.getContext(),3);
                RecycleAvatarAdapter recycleAvatarAdapter=new RecycleAvatarAdapter(view.getContext(),integers);
                list_avatar.setLayoutManager(gridLayoutManager);
                list_avatar.setAdapter(recycleAvatarAdapter);
                list_avatar.addOnItemTouchListener(new RecyclerTouchListener(getContext(), list_avatar, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        select_avatar.setImageResource(integers.get(position));
                        avar_select=integers.get(position);
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));
                builder.setView(view);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        user_avatar.setImageResource(avar_select);
                        userDAO.SuaAvatar(avar_select,user_active.getId_user());
                        Toast.makeText(getContext(), "?????i avatar th??nh c??ng!", Toast.LENGTH_SHORT).show();
                        userDAO=new UserDAO(getContext());
                        dsUser=userDAO.getAllUser();
                        user_active=((QuanLyThuChi)getContext()).UserActive();
                        ((QuanLyThuChi)getContext()).CapNhatUser(user_active);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
        });
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                View view= LayoutInflater.from(getContext()).inflate(R.layout.dialog_doi_mat_khau,null);
                builder.setView(view);
                EditText matkhaucu=view.findViewById(R.id.etMatKhauCu);
                EditText matkhaumoi=view.findViewById(R.id.etMatKhauMoi);
                EditText xacnhanmatkhaumoi=view.findViewById(R.id.etXacNhanMatKhauMoi);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            if (matkhaucu.getText().toString().equals("") || matkhaumoi.getText().toString().equals("")) {
                                Toast.makeText(getContext(), "Kh??ng ???????c ????? tr???ng", Toast.LENGTH_SHORT).show();
                            } else if (xacnhanmatkhaumoi.getText().toString().equalsIgnoreCase(matkhaumoi.getText().toString()) == false) {
                                Toast.makeText(getContext(), "X??c nh???n m???t kh???u ch??a ch??nh x??c", Toast.LENGTH_SHORT).show();
                            } else {
                                if (user_active.getPassword().equals(matkhaucu.getText().toString())==false) {
                                    Toast.makeText(getContext(), "M???t kh???u c?? ch??a ch??nh x??c", Toast.LENGTH_SHORT).show();
                                } else {
                                    userDAO.SuaUser(matkhaumoi.getText().toString(), user_active.getId_user());
                                    Toast.makeText(getContext(), "Thay ?????i m???t kh???u th??nh c??ng", Toast.LENGTH_SHORT).show();
                                    userDAO=new UserDAO(getContext());
                                    dsUser=userDAO.getAllUser();
                                }
                            }
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), "?????i m???t kh???u th???t b???i", Toast.LENGTH_SHORT).show();
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
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(view.findViewById(R.id.root),"X??c nh???n ????ng xu???t",5000)
                        .setActionTextColor(Color.RED)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                System.exit(0);
                            }
                        })
                        .show();
            }
        });

        PieChart pieChart=view.findViewById(R.id.BieuDoUser);

        ArrayList bieudo=new ArrayList();
        int tongchi=(int)Math.round(TongChi());
        bieudo.add(new PieEntry(tongchi,"T???ng chi"));
        int tongthu=(int)Math.round(TongThu());
        bieudo.add(new PieEntry(tongthu,"T???ng thu"));
        PieDataSet pieDataSet = new PieDataSet(bieudo, "");
        PieData data = new PieData(pieDataSet);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.setData(data);
        pieChart.getDescription().setText("");
        pieChart.setDrawSliceText(false);
        pieChart.animateX(1000);
        pieChart.setCenterText("TI???N: "+ (TongThu()-TongChi()));


        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerIconGioiThieu);
        iconGioiThieus.add(new IconGioiThieu(R.drawable.quanlyuser,"Qu???n l?? User"));
        iconGioiThieus.add(new IconGioiThieu(R.drawable.thongkenam,"Th???ng k??"));
        iconGioiThieus.add(new IconGioiThieu(R.drawable.loaithu,"Lo???i thu"));
        iconGioiThieus.add(new IconGioiThieu(R.drawable.loaichi,"Lo???i chi"));
        iconGioiThieus.add(new IconGioiThieu(R.drawable.khoangthu,"Kho???ng thu"));
        iconGioiThieus.add(new IconGioiThieu(R.drawable.khoangchi,"Kho???ng chi"));
        iconGioiThieus.add(new IconGioiThieu(R.drawable.bieudothu,"T???ng thu"));
        iconGioiThieus.add(new IconGioiThieu(R.drawable.bieudochi,"T???ng chi"));

        AdapterRecycleGioiThieu adapterRecycleGioiThieu=new AdapterRecycleGioiThieu(getContext(),iconGioiThieus);
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),
                2,
                GridLayoutManager.HORIZONTAL,
                false
        );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterRecycleGioiThieu);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(getActivity(), MainActivity2.class);
                intent.putExtra("danhmuc",position);
                intent.putExtra("user",((QuanLyThuChi)getContext()).UserActive().getUser_name());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return view;
    }


    public Double TongThu(){
        Double tongthu=0.0;
        LoaiThuDAO loaiThuDAO;
        KhoangThuDAO khoangThuDAO;
        ArrayList<LoaiThu> dsLoaiThu;
        ArrayList<KhoangThu> dsKhoangThu;
        loaiThuDAO = new LoaiThuDAO(getContext());
        dsLoaiThu =new ArrayList<>();
        dsLoaiThu=loaiThuDAO.getAllLoaiThu(user_active.getId_user());
        for(int j=0;j<dsLoaiThu.size();j++){
            khoangThuDAO=new KhoangThuDAO(getContext());
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
    public Double TongChi(){
        Double tongchi=0.0;
        LoaiChiDAO loaiChiDAO;
        KhoangChiDAO khoangChiDAO;
        ArrayList<LoaiChi> dsLoaiChi;
        ArrayList<KhoangChi> dsKhoangChi;
        loaiChiDAO = new LoaiChiDAO(getContext());
        dsLoaiChi =new ArrayList<>();
        dsLoaiChi=loaiChiDAO.getAllLoaiChi(user_active.getId_user());
        for(int j=0;j<dsLoaiChi.size();j++){
            khoangChiDAO=new KhoangChiDAO(getContext());
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

}
