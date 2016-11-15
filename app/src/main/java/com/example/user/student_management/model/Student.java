package com.example.user.student_management.model;

import android.content.ContentValues;

import java.io.Serializable;

/**
 * Created by USER on 10/11/2016.
 */
public class Student implements Serializable {
    // Contacts Table Columns names

    public static final String TABLE_STUDENTS = "STUDENT";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DATE_OF_BIRTH = "date_of_birth";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_GENDER = "gender";

    private String studentId;
    private String dateOfBirth;
    private String studentName;
    private String studentAddress;
    private String email;
    private boolean isMale;
    private boolean isChecked;

    public Student() {
    }

    public Student(String studentId, String dateOfBirth, String studentName,
                   String studentAddress, String email, boolean isMale, boolean isChecked) {
        this.studentId = studentId;
        this.dateOfBirth = dateOfBirth;
        this.studentName = studentName;
        this.studentAddress = studentAddress;
        this.email = email;
        this.isMale = isMale;
        this.isChecked = isChecked;
    }

    public static String getInitSql (){
        return  "CREATE TABLE " + TABLE_STUDENTS + "("
                + KEY_ID + " TEXT PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_DATE_OF_BIRTH + " TEXT,"
                + KEY_ADDRESS + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_GENDER + " INTEGER"
                + ")";
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, this.getStudentId());
        contentValues.put(KEY_NAME, this.getStudentName());
        contentValues.put(KEY_DATE_OF_BIRTH, this.getDateOfBirth());
        contentValues.put(KEY_ADDRESS, this.getStudentAddress());
        contentValues.put(KEY_EMAIL, this.getEmail());
        contentValues.put(KEY_GENDER, this.isMale());
        return  contentValues;
    }

    public String getStudentAddress() {
        return studentAddress;
    }

    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
