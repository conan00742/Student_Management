package com.example.user.student_management.model;

import android.content.ContentValues;

import java.util.List;

/**
 * Created by USER on 10/13/2016.
 */
public class Classes {

    /**Table CLASSES**/
    public static final String TABLE_CLASSES = "CLASSES";
    public static final String KEY_ID = "classID";
    public static final String KEY_NAME = "className";
    public static final String KEY_QUANTITY = "classQuantity";
    public static final String KEY_GRADE_ID = "gradeID";


    /**Table STUDENTS_IN_CLASS**/
    public static final String TABLE_STUDENTS_IN_CLASS = "STUDENTS_IN_CLASS";
    public static final String KEY_SUB_ID = "subID";
    public static final String KEY_CLASS_NAME = "className";
    public static final String KEY_STUDENT_ID = "id";


    /**Object Classes attributes**/
    private String _name;
    private int _quantity;
    private int _gradeID;
    private List<Student> studentList;
    private Student student;

    public Classes() {
    }

    public Classes(String _name, int _quantity){
        this._name = _name;
        this._quantity = _quantity;
    }

    public Classes(String _name, int _quantity, int _gradeID) {
        this._name = _name;
        this._quantity = _quantity;
        this._gradeID = _gradeID;
    }

    public Classes(List<Student> studentList) {
        this.studentList = studentList;
    }

    /**init table CLASSES**/
    public static String getInitClassesSql (){
        return  "CREATE TABLE " + TABLE_CLASSES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_QUANTITY + " INTEGER,"
                + KEY_GRADE_ID + " INTEGER"
                + ")";
    }


    /**init table STUDENTS_IN_CLASS**/
    public static String getInitStudentsInClassSql(){
        return  "CREATE TABLE " + TABLE_STUDENTS_IN_CLASS + "("
                + KEY_SUB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CLASS_NAME + " TEXT,"
                + KEY_STUDENT_ID + " TEXT"
                + ")";
    }


    /**get CLASSES contentValues**/
    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, this.get_name());
        contentValues.put(KEY_QUANTITY, this.get_quantity());
        contentValues.put(KEY_GRADE_ID, this.get_gradeID());
        return  contentValues;
    }

    /**get STUDENT_IN_CLASS contentValues**/
    public ContentValues getStudentsInClassContentValues(List<Student> studentList, int position){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_CLASS_NAME, this.get_name());
        contentValues.put(KEY_STUDENT_ID, studentList.get(position).getStudentId());
        return contentValues;
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

    public int get_gradeID() {
        return _gradeID;
    }

    public void set_gradeID(int _gradeID) {
        this._gradeID = _gradeID;
    }
}
