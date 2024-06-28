package com.example.book_store.model;

public class TacGia {
    public String MaTG;
    public String TenTG;
    public String GioiTinh;

    public TacGia(String maTG, String tenTG, String gioiTinh) {
        MaTG = maTG;
        TenTG = tenTG;
        GioiTinh = gioiTinh;
    }

    public String getMaTG() {
        return MaTG;
    }

    public String getTenTG() {
        return TenTG;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setMaTG(String maTG) {
        MaTG = maTG;
    }

    public void setTenTG(String tenTG) {
        TenTG = tenTG;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }
}
