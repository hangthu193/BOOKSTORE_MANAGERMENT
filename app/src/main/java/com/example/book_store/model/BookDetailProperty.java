package com.example.book_store.model;

public class BookDetailProperty {
    private String maSach;
    private String tenSach;
    private Integer giaBan;
    private String theLoai;
    private String nxb;
    private String tacGia;
    private String ngayXB;
    private Integer soLuong;

    private byte[] anh;

    public BookDetailProperty(String maSach, String tenSach, Integer giaBan, String theLoai, String nxb, String tacGia, String ngayXB, Integer soLuong, byte[] anh) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.giaBan = giaBan;
        this.theLoai = theLoai;
        this.nxb = nxb;
        this.tacGia = tacGia;
        this.ngayXB = ngayXB;
        this.soLuong = soLuong;
        this.anh = anh;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public Integer getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(Integer giaBan) {
        this.giaBan = giaBan;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public String getNxb() {
        return nxb;
    }

    public void setNxb(String nxb) {
        this.nxb = nxb;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getNgayXB() {
        return ngayXB;
    }

    public void setNgayXB(String ngayXB) {
        this.ngayXB = ngayXB;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }
}
