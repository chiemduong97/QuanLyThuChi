package com.example.asm_android.Model;

public class KhoangChi {
    private int id_khoangchi;
    private int id_loaichi;
    private String khoangchi_name;
    private Double khoangchi_soluong;
    private date ngay_chi;
    public KhoangChi(){

    }

    public KhoangChi(int id_khoangchi, int id_loaichi, String khoangchi_name, Double khoangchi_soluong,date ngay_chi) {
        this.id_khoangchi = id_khoangchi;
        this.id_loaichi = id_loaichi;
        this.khoangchi_name = khoangchi_name;
        this.khoangchi_soluong = khoangchi_soluong;
        this.ngay_chi=ngay_chi;
    }

    public int getId_khoangchi() {
        return id_khoangchi;
    }

    public void setId_khoangchi(int id_khoangchi) {
        this.id_khoangchi = id_khoangchi;
    }

    public int getId_loaichi() {
        return id_loaichi;
    }

    public void setId_loaichi(int id_loaichi) {
        this.id_loaichi = id_loaichi;
    }

    public String getKhoangchi_name() {
        return khoangchi_name;
    }

    public void setKhoangchi_name(String khoangchi_name) {
        this.khoangchi_name = khoangchi_name;
    }

    public Double getKhoangchi_soluong() {
        return khoangchi_soluong;
    }

    public void setKhoangchi_soluong(Double khoangchi_soluong) {
        this.khoangchi_soluong = khoangchi_soluong;
    }

    public String getNgay_chi() {
        return ngay_chi.getNgay()+"/"+ngay_chi.getThang()+"/"+ngay_chi.getNam();
    }
    public date _getNgay_chi(){
        return ngay_chi;
    }

    public void setNgay_chi(date ngay_chi) {
        this.ngay_chi = ngay_chi;
    }


}
