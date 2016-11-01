package com.example.user.student_management.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user.student_management.model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khiem Ichigo on 10/30/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 6;

    // Database Name
    private static final String DATABASE_NAME = "STUDENT_MANAGEMENT";




    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Student.getInitSql());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Student.TABLE_STUDENTS);

        // Create tables again
        onCreate(db);
    }


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
                student.setMale(Boolean.parseBoolean(cursor.getString(5)));
                studentList.add(student);
            }while(cursor.moveToNext());
        }

        return studentList;
    }
}
