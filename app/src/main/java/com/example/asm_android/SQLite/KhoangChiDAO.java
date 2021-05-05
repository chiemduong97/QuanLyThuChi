package com.example.asm_android.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.asm_android.Model.KhoangChi;
import com.example.asm_android.Model.date;
import com.example.asm_android.QuanLyThuChi;

import java.util.ArrayList;

public class KhoangChiDAO {
    private SQLiteDatabase db;
    MyDatabase myDatabase;
    public KhoangChiDAO(Context context){myDatabase=new MyDatabase(context);}

    public void ThemKhoangChi(int id_loaichi, String khoangchi_name, Double khoangchi_soluong,date ngay_chi){
        db=myDatabase.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("id_loaichi",id_loaichi);
        values.put("khoangchi_name",khoangchi_name);
        values.put("khoangchi_soluong",khoangchi_soluong);
        values.put("ngay_chi",ngay_chi.getNgay());
        values.put("thang_chi",ngay_chi.getThang());
        values.put("nam_chi",ngay_chi.getNam());
        db.insert("KhoangChi",null,values);
    }
    public void XoaKhoangChi(int id_khoangchi){
        db=myDatabase.getWritableDatabase();
        String x[]=new String[]{id_khoangchi+""};
        db.delete("KhoangChi","id_khoangchi=?",x);
    }

    public void SuaKhoangChi(int id_khoangchi, String khoangchi_name, Double khoangchi_soluong,int id_loaichi,date ngay_chi){
        db=myDatabase.getWritableDatabase();
        ContentValues values=new ContentValues();
        String x[]=new String[]{id_khoangchi+""};
        values.put("khoangchi_name",khoangchi_name);
        values.put("khoangchi_soluong",khoangchi_soluong);
        values.put("id_loaichi",id_loaichi);
        values.put("ngay_chi",ngay_chi.getNgay());
        values.put("thang_chi",ngay_chi.getThang());
        values.put("nam_chi",ngay_chi.getNam());
        db.update("KhoangChi",values,"id_khoangchi=?",x);
    }
    public ArrayList<KhoangChi> getAllKhoangChi(ArrayList<Integer> loaichis){
        db=myDatabase.getReadableDatabase();
        ArrayList<KhoangChi> dsKhoangChi = new ArrayList<>();
        Cursor c = db.rawQuery("select * from KhoangChi",null);
        if(c.moveToFirst())
        {
            do{
                KhoangChi x=new KhoangChi();
                x.setId_khoangchi(c.getInt(0));
                x.setId_loaichi(c.getInt(1));
                x.setKhoangchi_name(c.getString(2));
                x.setKhoangchi_soluong(c.getDouble(3));
                x.setNgay_chi(new date(c.getInt(4),c.getInt(5),c.getInt(6)));
                for(int i=0;i<loaichis.size();i++){
                    if(x.getId_loaichi()==loaichis.get(i))
                        dsKhoangChi.add(x);
                }
            }while (c.moveToNext());
        }
        return dsKhoangChi;
    }
}
