package com.example.asm_android.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_android.Model.User;

import java.util.ArrayList;

public class UserDAO {
    private SQLiteDatabase db;
    MyDatabase myDatabase;
    public UserDAO(Context context){
        myDatabase=new MyDatabase(context);
    }
    public void ThemUser(String user_name, String password, int avatar){
        db=myDatabase.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("user_name",user_name);
        values.put("password",password);
        values.put("avatar",avatar);
        db.insert("User",null,values);
    }
    public void XoaUser(int id_user){
        db=myDatabase.getWritableDatabase();
        String x[]=new String[]{id_user+""};
        db.delete("User","id_user=?",x);
    }
    public void SuaUser(String password, int id_user){
        db=myDatabase.getWritableDatabase();
        ContentValues values=new ContentValues();
        String x[]=new String[]{id_user+""};
        values.put("password",password);
        db.update("User",values,"id_user=?",x);
    }

    public void SuaAvatar(int avatar,int id_user){
        db=myDatabase.getWritableDatabase();
        ContentValues values=new ContentValues();
        String x[]=new String[]{id_user+""};
        values.put("avatar",avatar);
        db.update("User",values,"id_user=?",x);
    }

    public ArrayList<User> getAllUser(){
        db=myDatabase.getReadableDatabase();
        ArrayList<User> dsuser = new ArrayList<>();
        Cursor c = db.rawQuery("select * from User",null);
        if(c.moveToFirst())
        {
            do{
                User x=new User();
                x.setId_user(c.getInt(0));
                x.setUser_name(c.getString(1));
                x.setPassword(c.getString(2));
                x.setAvatar(c.getInt(3));
                dsuser.add(x);
            }while (c.moveToNext());
        }
        return dsuser;
    }
}
