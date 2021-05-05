package com.example.asm_android.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_android.Model.LoaiThu;

import java.util.ArrayList;

public class LoaiThuDAO {
    private SQLiteDatabase db;
    MyDatabase myDatabase;
    public LoaiThuDAO(Context context){myDatabase=new MyDatabase(context);}
    public void ThemLoaiThu(int id_user, String loaithu_name){
        db=myDatabase.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("id_user",id_user);
        values.put("loaithu_name",loaithu_name);
        db.insert("LoaiThu",null,values);
    }
    public void XoaLoaiThu(int id_loaithu){
        db=myDatabase.getWritableDatabase();
        String x[]=new String[]{id_loaithu+""};
        db.delete("LoaiThu","id_loaithu=?",x);
    }
    public void SuaLoaiThu(int id_loaithu, String loaithu_name){
        db=myDatabase.getWritableDatabase();
        ContentValues values=new ContentValues();
        String x[]=new String[]{id_loaithu+""};
        values.put("loaithu_name",loaithu_name);
        db.update("LoaiThu",values,"id_loaithu=?",x);
    }
    public ArrayList<LoaiThu> getAllLoaiThu(int id_user){
        db=myDatabase.getReadableDatabase();
        ArrayList<LoaiThu> dsLoaiThu = new ArrayList<>();
        Cursor c = db.rawQuery("select * from LoaiThu",null);
        if(c.moveToFirst())
        {
            do{
                LoaiThu x=new LoaiThu();
                x.setId_loaithu(c.getInt(0));
                x.setId_user(c.getInt(1));
                x.setLoaithu_name(c.getString(2));
                if(x.getId_user()==id_user)
                    dsLoaiThu.add(x);
            }while (c.moveToNext());
        }
        return dsLoaiThu;
    }
}
