package com.example.user.student_management.model;

/**
 * Created by USER on 10/11/2016.
 */
public class Student {
    private long studentId;
    private int yearOfBirth;
    private String studentName;

    public Student() {
    }

    public Student(long studentId, int yearOfBirth, String studentName) {
        this.studentId = studentId;
        this.yearOfBirth = yearOfBirth;
        this.studentName = studentName;
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
}
