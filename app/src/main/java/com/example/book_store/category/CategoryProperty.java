package com.example.book_store.category;

public class CategoryProperty {
    private String tenTheloai;
    private String maTheloai;

    public String getTenTheloai() {
        return tenTheloai;
    }

    public void setTenTheloai(String tenTheloai) {
        this.tenTheloai = tenTheloai;
    }

    public String getMaTheloai() {
        return maTheloai;
    }

    public void setMaTheloai(String maTheloai) {
        this.maTheloai = maTheloai;
    }

    public CategoryProperty( String maTheloai,String tenTheloai) {
        this.tenTheloai = tenTheloai;
        this.maTheloai = maTheloai;
    }
}
