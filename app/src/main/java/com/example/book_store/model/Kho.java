package com.example.book_store.model;

public class Kho {
    private String maKho;
    private String maSach;
    private String ngayNhap;
    private int giaNhap;
    private int soLuongNhap;

    public Kho(String maKho, String maSach, String ngayNhap, int giaNhap, int soLuongNhap) {
        this.maKho = maKho;
        this.maSach = maSach;
        this.ngayNhap = ngayNhap;
        this.giaNhap = giaNhap;
        this.soLuongNhap = soLuongNhap;
    }

    public String getMaKho() {
        return maKho;
    }

    public void setMaKho(String maKho) {
        this.maKho = maKho;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public int getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(int giaNhap) {
        this.giaNhap = giaNhap;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }
}
