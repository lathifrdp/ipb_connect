package com.example.lathifrdp.demoapp.model;

public class Item {
    private String _id;
    private String nim;

    public Item(String _id, String nim){
        this._id = _id;
        this.nim = nim;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }
}
