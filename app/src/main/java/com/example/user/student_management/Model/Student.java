package com.example.user.student_management.Model;

/**
 * Created by USER on 10/11/2016.
 */
public class Student {
    private int studentId, yearOfBirth;
    private String studentName;

    public Student() {
    }

    public Student(int studentId, int yearOfBirth, String studentName) {
        this.studentId = studentId;
        this.yearOfBirth = yearOfBirth;
        this.studentName = studentName;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
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
