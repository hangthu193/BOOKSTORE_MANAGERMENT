package com.example.book_store.model;

public class Selected {
    private String id;
    private String name;


    public Selected(String name,String id) {
        this.name = name;
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
