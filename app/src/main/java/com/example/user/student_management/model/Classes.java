package com.example.user.student_management.model;

import java.util.List;

/**
 * Created by USER on 10/13/2016.
 */
public class Classes {
    private String _name;
    private int _quantity;
    private List<Student> studentList;

    public Classes() {
    }

    public Classes(String _name, int _quantity) {
        this._name = _name;
        this._quantity = _quantity;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
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
