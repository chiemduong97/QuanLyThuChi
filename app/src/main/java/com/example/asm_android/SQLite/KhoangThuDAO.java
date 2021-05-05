package com.example.asm_android.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_android.Model.KhoangThu;
import com.example.asm_android.Model.date;

import java.util.ArrayList;

public class KhoangThuDAO {
    private SQLiteDatabase db;
    MyDatabase myDatabase;
    public KhoangThuDAO(Context context){myDatabase=new MyDatabase(context);}

    public void ThemKhoangThu(int id_loaithu, String khoangthu_name, Double khoangthu_soluong,date ngay_thu){
        db=myDatabase.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("id_loaithu",id_loaithu);
        values.put("khoangthu_name",khoangthu_name);
        values.put("khoangthu_soluong",khoangthu_soluong);
        values.put("ngay_thu",ngay_thu.getNgay());
        values.put("thang_thu",ngay_thu.getThang());
        values.put("nam_thu",ngay_thu.getNam());
        db.insert("KhoangThu",null,values);
    }
    public void XoaKhoangThu(int id_khoangthu){
        db=myDatabase.getWritableDatabase();
        String x[]=new String[]{id_khoangthu+""};
        db.delete("KhoangThu","id_khoangthu=?",x);
    }

    public void SuaKhoangThu(int id_khoangthu, String khoangthu_name, Double khoangthu_soluong,int id_loaithu,date ngay_thu){
        db=myDatabase.getWritableDatabase();
        ContentValues values=new ContentValues();
        String x[]=new String[]{id_khoangthu+""};
        values.put("khoangthu_name",khoangthu_name);
        values.put("khoangthu_soluong",khoangthu_soluong);
        values.put("id_loaithu",id_loaithu);
        values.put("ngay_thu",ngay_thu.getNgay());
        values.put("thang_thu",ngay_thu.getThang());
        values.put("nam_thu",ngay_thu.getNam());
        db.update("KhoangThu",values,"id_khoangthu=?",x);
    }
    public ArrayList<KhoangThu> getAllKhoangThu(ArrayList<Integer> loaithus){
        db=myDatabase.getReadableDatabase();
        ArrayList<KhoangThu> dsKhoangThu = new ArrayList<>();
        Cursor c = db.rawQuery("select * from KhoangThu",null);
        if(c.moveToFirst())
        {
            do{
                KhoangThu x=new KhoangThu();
                x.setId_khoangthu(c.getInt(0));
                x.setId_loaithu(c.getInt(1));
                x.setKhoangthu_name(c.getString(2));
                x.setKhoangthu_soluong(c.getDouble(3));
                x.setNgay_thu(new date(c.getInt(4),c.getInt(5),c.getInt(6)));
                for(int i=0;i<loaithus.size();i++){
                    if(x.getId_loaithu()==loaithus.get(i))
                        dsKhoangThu.add(x);
                }
            }while (c.moveToNext());
        }
        return dsKhoangThu;
    }
}
