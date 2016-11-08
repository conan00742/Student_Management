package com.example.user.student_management.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user.student_management.model.Classes;
import com.example.user.student_management.model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khiem Ichigo on 10/30/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 28;

    // Database Name
    private static final String DATABASE_NAME = "STUDENT_MANAGEMENT";




    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Student.getInitSql());
        db.execSQL(Classes.getInitClassesSql());
        db.execSQL(Classes.getInitStudentsInClassSql());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Student.TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + Classes.TABLE_CLASSES);
        db.execSQL("DROP TABLE IF EXISTS " + Classes.TABLE_STUDENTS_IN_CLASS);
        // Create tables again
        onCreate(db);
    }


    /**
     *
     *
     * STUDENT TABLE METHODS
     *
     *
     * **/


    /**Adding new student**/
    public void addNewStudent(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        /**Inserting Row**/
        db.insert(Student.TABLE_STUDENTS,null,student.getContentValues());
        db.close();
    }

    /**get Student List**/
    public List<Student> getStudentList(){
        List<Student> studentList = new ArrayList<>();

        /**Select all query**/
        String selectQuery = "SELECT * FROM " + Student.TABLE_STUDENTS;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);

        /**looping through all rows and adding to list**/
        if(cursor.moveToFirst()){
            do{
                Student student = new Student();
                student.setStudentId(Long.parseLong(cursor.getString(0)));
                student.setStudentName(cursor.getString(1));
                student.setYearOfBirth(Integer.parseInt(cursor.getString(2)));
                student.setMale(cursor.getInt(5) == 1);
                studentList.add(student);
            }while(cursor.moveToNext());
        }

        return studentList;
    }


    /**get StudentList by ID**/
    public List<Student> getStudentListById(String className){
        List<Student> studentList = new ArrayList<>();

        /**Select all query**/
        String selectQuery = "SELECT  * FROM " + Student.TABLE_STUDENTS + " st, "
                + Classes.TABLE_CLASSES + " cl, " + Classes.TABLE_STUDENTS_IN_CLASS + " sic WHERE cl."
                + Classes.KEY_CLASS_NAME + " = '" + className + "'" + " AND cl." + Classes.KEY_CLASS_NAME
                + " = " + "sic." + Classes.KEY_CLASS_NAME + " AND st." + Student.KEY_ID + " = "
                + "sic." + Classes.KEY_STUDENT_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        /**looping through all rows and adding to list**/
        if(cursor.moveToFirst()){
            do{
                Student student = new Student();
                student.setStudentId(Long.parseLong(cursor.getString(0)));
                student.setStudentName(cursor.getString(1));
                student.setYearOfBirth(Integer.parseInt(cursor.getString(2)));
                student.setMale(cursor.getInt(5) == 1);
                studentList.add(student);
            }while(cursor.moveToNext());
        }

        return studentList;
    }


    /**
     *
     *
     * CLASSES TABLE METHODS
     *
     *
     * **/

    /**generate class**/
    public void generateClasses(Classes _class){
        SQLiteDatabase db = this.getWritableDatabase();
        /**Inserting Row**/
        db.insert(Classes.TABLE_CLASSES,null,_class.getContentValues());
        db.close();
    }


    /**
     *
     * get Classes list
     *
     * **/
    public List<Classes> getClassesListByGrade(int grade){
        List<Classes> classesList = new ArrayList<>();


        /**Select all query**/
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String queryString = "SELECT * FROM "
                + Classes.TABLE_CLASSES + " WHERE " + Classes.KEY_GRADE_ID + " = " + grade + " ORDER BY "
                + Classes.KEY_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(queryString,null);

        /**looping through all rows and adding to list**/
        if(cursor.moveToFirst()){
            do{
                Classes _class = new Classes();
                _class.set_name(cursor.getString(cursor.getColumnIndex(Classes.KEY_NAME)));
                _class.set_quantity(cursor.getInt(cursor.getColumnIndex(Classes.KEY_QUANTITY)));
                _class.set_gradeID(cursor.getInt(cursor.getColumnIndex(Classes.KEY_GRADE_ID)));
                classesList.add(_class);
            }while(cursor.moveToNext());
        }

        return classesList;
    }


    /**
     *
     * add Student to class
     *
     * **/
    public void addStudentToClass(Classes classes, List<Student> list, int position){
        SQLiteDatabase db = this.getWritableDatabase();
        /**inserting row**/
        db.insert(Classes.TABLE_STUDENTS_IN_CLASS,null,classes.getStudentsInClassContentValues(list,position));
        db.close();
    }


    /**
     *
     * get studentList from table STUDENTS_IN_CLASS
     *
     * **/
    /*public List<Student> getStudentListFromClass(String className){
        List<Student> studentList = new ArrayList<>();

        *//**Select all query**//*
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String queryString = "SELECT * FROM "
                + Classes.TABLE_STUDENTS_IN_CLASS
                + " WHERE " + Classes.KEY_CLASS_NAME + " = '" + className + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(queryString,null);

        *//**looping through all rows and adding to list**//*
        if(cursor.moveToFirst()){
            do{
                Student student = new Student();
                student.setStudentId(Long.parseLong(cursor.getString(0)));
                student.setStudentName(cursor.getString(1));
                student.setYearOfBirth(Integer.parseInt(cursor.getString(2)));
                student.setMale(cursor.getInt(5) == 1);
                studentList.add(student);
            }while(cursor.moveToNext());
        }

        return studentList;
    }*/

}
