package com.example.book_store.Model;

public class Employee {
    private String id;
    private String name;
    private String gender;
    private String phone;
    private String address;
    private String username;

    public Employee(String id, String name, String gender, String phone, String address, String username) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }
}
