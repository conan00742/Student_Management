package com.example.user.student_management.model;

import android.content.ContentValues;

/**
 * Created by Khiem Ichigo on 12/3/2016.
 */

public class Account {
    public static final String TABLE_ACCOUNT = "Account";
    public static final String KEY_ID = "id";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_RESPONSIBLE_SUBJECT = "responsibleSubject";
    public static final String KEY_ROLE = "role";

    private String email;
    private String password;
    private String responsibleSubject;
    private String role;

    public static String getInitAccountSql(){
        return  "CREATE TABLE " + TABLE_ACCOUNT + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_RESPONSIBLE_SUBJECT + " TEXT,"
                + KEY_ROLE + " TEXT"
                + ")";
    }

    public ContentValues getAccountContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_EMAIL, this.getEmail());
        contentValues.put(KEY_PASSWORD, this.getPassword());
        contentValues.put(KEY_RESPONSIBLE_SUBJECT, this.getResponsibleSubject());
        contentValues.put(KEY_ROLE, this.getRole());
        return contentValues;
    }

    public Account() {
    }

    public Account(String email, String password, String responsibleSubject, String role) {
        this.email = email;
        this.password = password;
        this.responsibleSubject = responsibleSubject;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResponsibleSubject() {
        return responsibleSubject;
    }

    public void setResponsibleSubject(String responsibleSubject) {
        this.responsibleSubject = responsibleSubject;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
