package com.example.asm_android.Model;

public class LoaiThu {
    private int id_loaithu;
    private int id_user;
    private String loaithu_name;
    public LoaiThu(){

    }

    public LoaiThu(int id_loaithu, int id_user, String loaithu_name) {
        this.id_loaithu = id_loaithu;
        this.id_user = id_user;
        this.loaithu_name = loaithu_name;
    }

    public int getId_loaithu() {
        return id_loaithu;
    }

    public void setId_loaithu(int id_loaithu) {
        this.id_loaithu = id_loaithu;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getLoaithu_name() {
        return loaithu_name;
    }

    public void setLoaithu_name(String loaithu_name) {
        this.loaithu_name = loaithu_name;
    }
}
