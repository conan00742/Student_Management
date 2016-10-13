package com.example.user.student_management.model;

/**
 * Created by USER on 10/13/2016.
 */
public class Classes {
    private String _name;
    private int _quantity;

    public Classes() {
    }

    public Classes(String _name, int _quantity) {
        this._name = _name;
        this._quantity = _quantity;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_quantity() {
        return _quantity;
    }

    public void set_quantity(int _quantity) {
        this._quantity = _quantity;
    }
}
