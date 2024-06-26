package com.example.book_store.model;

public class CategoryProperty {
    private String maTheloai;
    private String tenTheloai;

    public String getMaTheloai() {
        return maTheloai;
    }

    public void setMaTheloai(String maTheloai) {
        this.maTheloai = maTheloai;
    }

    public String getTenTheloai() {
        return tenTheloai;
    }

    public void setTenTheloai(String tenTheloai) {
        this.tenTheloai = tenTheloai;
    }

    public CategoryProperty(String maTheloai, String tenTheloai) {
        this.maTheloai = maTheloai;
        this.tenTheloai = tenTheloai;
    }

}
