package com.example.asm_android.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {
    public MyDatabase(@Nullable Context context) {
        super(context, "quanlythuchi", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table_loaithu="create table LoaiThu" +
                "(" +
                "id_loaithu INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_user INTEGER,"+
                "loaithu_name TEXT" +
                " )";
        String table_khoangthu="create table KhoangThu" +
                "(" +
                "id_khoangthu INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_loaithu INTEGER," +
                "khoangthu_name TEXT,"+
                "khoangthu_soluong REAL," +
                "ngay_thu INTEGER," +
                "thang_thu INTEGER," +
                "nam_thu INTEGER" +
                " )";
        String table_loaichi="create table LoaiChi" +
                "(" +
                "id_loaichi INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_user INTEGER,"+
                "loaichi_name TEXT" +
                " )";
        String table_khoangchi="create table KhoangChi" +
                "(" +
                "id_khoangchi INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_loaichi INTEGER," +
                "khoangchi_name TEXT,"+
                "khoangchi_soluong REAL," +
                "ngay_chi INTEGER," +
                "thang_chi INTEGER," +
                "nam_chi INTEGER" +
                " )";
        String table_user="create table User" +
                "(" +
                "id_user INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_name TEXT," +
                "password TEXT," +
                "avatar INTEGER" +
                " )";

        db.execSQL(table_loaithu);
        db.execSQL(table_khoangthu);
        db.execSQL(table_loaichi);
        db.execSQL(table_khoangchi);
        db.execSQL(table_user);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable1="DROP TABLE IF EXISTS LoaiThu";
        db.execSQL(dropTable1);
        String dropTable2="DROP TABLE IF EXISTS KhoangThu";
        db.execSQL(dropTable2);
        String dropTable3="DROP TABLE IF EXISTS LoaiChi";
        db.execSQL(dropTable3);
        String dropTable4="DROP TABLE IF EXISTS KhoangChi";
        db.execSQL(dropTable4);
        String dropTable5="DROP TABLE IF EXISTS User";
        db.execSQL(dropTable5);
        onCreate(db);
    }
}
