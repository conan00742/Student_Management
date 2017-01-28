package com.example.user.student_management.model;

import android.content.ContentValues;

/**
 * Created by Khiem Ichigo on 10/26/2016.
 */

public class Subject {

    private int subjectID;
    private String subjectName;
    private int subjectSemester;
    private String subjectTypeOfMark;


    public Subject() {
    }

    public Subject(int subjectID, String subjectName) {
        this.subjectID = subjectID;
        this.subjectName = subjectName;
    }

    public int getSubjectSemester() {
        return subjectSemester;
    }

    public void setSubjectSemester(int subjectSemester) {
        this.subjectSemester = subjectSemester;
    }

    public String getSubjectTypeOfMark() {
        return subjectTypeOfMark;
    }

    public void setSubjectTypeOfMark(String subjectTypeOfMark) {
        this.subjectTypeOfMark = subjectTypeOfMark;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }


}
