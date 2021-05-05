package com.example.asm_android.Model;

public class LoaiChi {
    private int id_loaichi;
    private int id_user;
    private String loaichi_name;
    public LoaiChi(){

    }

    public LoaiChi(int id_loaichi, int id_user, String loaichi_name) {
        this.id_loaichi = id_loaichi;
        this.id_user = id_user;
        this.loaichi_name = loaichi_name;
    }

    public int getId_loaichi() {
        return id_loaichi;
    }

    public void setId_loaichi(int id_loaichi) {
        this.id_loaichi = id_loaichi;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getLoaichi_name() {
        return loaichi_name;
    }

    public void setLoaichi_name(String loaichi_name) {
        this.loaichi_name = loaichi_name;
    }
}
