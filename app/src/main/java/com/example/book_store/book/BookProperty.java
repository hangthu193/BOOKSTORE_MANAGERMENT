package com.example.book_store.book;

public class BookProperty {
    private String stt;
    private String tenSach;
    private String maSach;

    public BookProperty(String stt, String tenSach, String maSach) {
        this.stt = stt;
        this.tenSach = tenSach;
        this.maSach = maSach;
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }
}
