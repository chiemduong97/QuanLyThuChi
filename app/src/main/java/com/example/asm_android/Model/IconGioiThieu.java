package com.example.asm_android.Model;

public class IconGioiThieu {
    private int img;
    private String ten;
    public  IconGioiThieu(){

    }
    public IconGioiThieu(int img,String ten){
        this.img=img;
        this.ten=ten;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
