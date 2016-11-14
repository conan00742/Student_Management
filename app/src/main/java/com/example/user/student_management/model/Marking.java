package com.example.user.student_management.model;

import android.content.ContentValues;

/**
 * Created by USER on 11/12/2016.
 */

public class Marking {

    public static final String TABLE_SCORE_RECORD = "SCORE_RECORD";
    public static final String KEY_SCORE_RECORD_ID = "id";
    public static final String KEY_STUDENT_ID = "studentId";
    public static final String KEY_STUDENT_NAME = "studentName";
    public static final String KEY_CLASS_NAME = "className";
    public static final String KEY_SUBJECT_NAME = "subjectName";
    public static final String KEY_SEMESTER = "semester";
    public static final String KEY_TYPE_OF_MARK = "typeOfMark";
    public static final String KEY_MARK_VALUE = "markValue";



    private Student student;
    private Classes _class;
    private Subject subject;
    private double markValue;

    public static String getInitSql(){
        return  "CREATE TABLE " + TABLE_SCORE_RECORD + "("
                + KEY_SCORE_RECORD_ID + " INTEGER PRIMARY KEY,"
                + KEY_STUDENT_ID + " TEXT,"
                + KEY_STUDENT_NAME + " TEXT,"
                + KEY_CLASS_NAME + " TEXT,"
                + KEY_SUBJECT_NAME + " TEXT,"
                + KEY_SEMESTER + " INTEGER,"
                + KEY_TYPE_OF_MARK + " TEXT,"
                + KEY_MARK_VALUE + " REAL"
                + ")";
    }

    public ContentValues getMarkingContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_STUDENT_ID, this.student.getStudentId());
        contentValues.put(KEY_STUDENT_NAME, this.student.getStudentName());
        contentValues.put(KEY_CLASS_NAME, this._class.get_name());
        contentValues.put(KEY_SUBJECT_NAME, this.subject.getSubjectName());
        contentValues.put(KEY_SEMESTER, this.subject.getSubjectSemester());
        contentValues.put(KEY_TYPE_OF_MARK, this.subject.getSubjectTypeOfMark());
        contentValues.put(KEY_MARK_VALUE, this.markValue);
        return contentValues;
    }

    public Marking() {
    }

    public Marking(Student student, Classes _class, Subject subject, double markValue) {
        this.student = student;
        this._class = _class;
        this.subject = subject;
        this.markValue = markValue;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Classes get_class() {
        return _class;
    }

    public void set_class(Classes _class) {
        this._class = _class;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public double getMarkValue() {
        return markValue;
    }

    public void setMarkValue(double markValue) {
        this.markValue = markValue;
    }
}
