package com.example.user.student_management.model;

import android.content.ContentValues;

/**
 * Created by USER on 10/11/2016.
 */
public class Student {
    // Contacts Table Columns names

    public static final String TABLE_STUDENTS = "STUDENT";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DATE_OF_BIRTH = "date_of_birth";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_GENDER = "gender";

    private long studentId;
    private int yearOfBirth;
    private String studentName;
    private String studentAddress;
    private String email;
    private boolean isMale;
    private boolean isChecked = true;

    public Student() {
    }

    public Student(long studentId, int yearOfBirth, String studentName,
                   String studentAddress, String email, boolean isMale, boolean isChecked) {
        this.studentId = studentId;
        this.yearOfBirth = yearOfBirth;
        this.studentName = studentName;
        this.studentAddress = studentAddress;
        this.email = email;
        this.isMale = isMale;
        this.isChecked = isChecked;
    }

    public static String getInitSql (){
        return  "CREATE TABLE " + TABLE_STUDENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_NAME + " TEXT, "
                + KEY_DATE_OF_BIRTH + " TEXT, "
                + KEY_ADDRESS + " TEXT, "
                + KEY_EMAIL + " TEXT, "
                + KEY_GENDER + " INTEGER" + ");";
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, this.getStudentId());
        contentValues.put(KEY_NAME, this.getStudentName());
        contentValues.put(KEY_DATE_OF_BIRTH, this.getYearOfBirth());
        contentValues.put(KEY_ADDRESS, this.getStudentAddress());
        contentValues.put(KEY_EMAIL, this.getStudentAddress());
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

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
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
