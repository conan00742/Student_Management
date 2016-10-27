package com.example.user.student_management.model;

/**
 * Created by USER on 10/11/2016.
 */
public class Student {
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
