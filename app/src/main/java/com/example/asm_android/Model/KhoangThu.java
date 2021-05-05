package com.example.asm_android.Model;

public class KhoangThu {
    private int id_khoangthu;
    private int id_loaithu;
    private String khoangthu_name;
    private Double khoangthu_soluong;
    private date ngay_thu;
    public KhoangThu(){

    }

    public KhoangThu(int id_khoangthu, int id_loaithu, String khoangthu_name, Double khoangthu_soluong,date ngay_thu) {
        this.id_khoangthu = id_khoangthu;
        this.id_loaithu = id_loaithu;
        this.khoangthu_name = khoangthu_name;
        this.khoangthu_soluong = khoangthu_soluong;
        this.ngay_thu=ngay_thu;
    }

    public int getId_khoangthu() {
        return id_khoangthu;
    }

    public void setId_khoangthu(int id_khoangthu) {
        this.id_khoangthu = id_khoangthu;
    }

    public int getId_loaithu() {
        return id_loaithu;
    }

    public void setId_loaithu(int id_loaithu) {
        this.id_loaithu = id_loaithu;
    }

    public String getKhoangthu_name() {
        return khoangthu_name;
    }

    public void setKhoangthu_name(String khoangthu_name) {
        this.khoangthu_name = khoangthu_name;
    }

    public Double getKhoangthu_soluong() {
        return khoangthu_soluong;
    }

    public void setKhoangthu_soluong(Double khoangthu_soluong) {
        this.khoangthu_soluong = khoangthu_soluong;
    }

    public String getNgay_thu() {
        return ngay_thu.getNgay()+"/"+ngay_thu.getThang()+"/"+ngay_thu.getNam();
    }
    public date _getNgay_thu(){
        return ngay_thu;
    }

    public void setNgay_thu(date ngay_thu) {
        this.ngay_thu = ngay_thu;
    }
}
