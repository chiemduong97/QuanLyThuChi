package com.example.asm_android.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_android.Model.LoaiChi;

import java.util.ArrayList;

public class LoaiChiDAO {
    private SQLiteDatabase db;
    MyDatabase myDatabase;
    public LoaiChiDAO(Context context){myDatabase=new MyDatabase(context);}
    public void ThemLoaiChi(int id_user, String loaichi_name){
        db=myDatabase.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("id_user",id_user);
        values.put("loaichi_name",loaichi_name);
        db.insert("LoaiChi",null,values);
    }
    public void XoaLoaiChi(int id_loaichi){
        db=myDatabase.getWritableDatabase();
        String x[]=new String[]{id_loaichi+""};
        db.delete("LoaiChi","id_loaichi=?",x);
    }
    public void SuaLoaiChi(int id_loaichi, String loaichi_name){
        db=myDatabase.getWritableDatabase();
        ContentValues values=new ContentValues();
        String x[]=new String[]{id_loaichi+""};
        values.put("loaichi_name",loaichi_name);
        db.update("LoaiChi",values,"id_loaichi=?",x);
    }
    public ArrayList<LoaiChi> getAllLoaiChi(int id_user){
        db=myDatabase.getReadableDatabase();
        ArrayList<LoaiChi> dsLoaiChi = new ArrayList<>();
        Cursor c = db.rawQuery("select * from LoaiChi",null);
        if(c.moveToFirst())
        {
            do{
                LoaiChi x=new LoaiChi();
                x.setId_loaichi(c.getInt(0));
                x.setId_user(c.getInt(1));
                x.setLoaichi_name(c.getString(2));
                if(x.getId_user()==id_user)
                    dsLoaiChi.add(x);
            }while (c.moveToNext());
        }
        return dsLoaiChi;
    }
}
