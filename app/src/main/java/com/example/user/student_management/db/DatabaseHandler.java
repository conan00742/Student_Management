package com.example.user.student_management.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user.student_management.model.Classes;
import com.example.user.student_management.model.Marking;
import com.example.user.student_management.model.Student;
import com.example.user.student_management.model.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khiem Ichigo on 10/30/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 36;

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
        db.execSQL(Marking.getInitSql());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Student.TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + Classes.TABLE_CLASSES);
        db.execSQL("DROP TABLE IF EXISTS " + Classes.TABLE_STUDENTS_IN_CLASS);
        db.execSQL("DROP TABLE IF EXISTS " + Marking.TABLE_SCORE_RECORD);
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
                student.setStudentId(cursor.getString(cursor.getColumnIndex(Student.KEY_ID)));
                student.setStudentName(cursor.getString(cursor.getColumnIndex(Student.KEY_NAME)));
                student.setDateOfBirth(cursor.getString(cursor.getColumnIndex(Student.KEY_DATE_OF_BIRTH)));
                student.setMale(cursor.getInt(cursor.getColumnIndex(Student.KEY_GENDER)) == 1);
                student.setChecked(false);
                student.setEmail(cursor.getString(cursor.getColumnIndex(Student.KEY_EMAIL)));
                student.setStudentAddress(cursor.getString(cursor.getColumnIndex(Student.KEY_ADDRESS)));
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
                student.setStudentId(cursor.getString(cursor.getColumnIndex(Student.KEY_ID)));
                student.setStudentName(cursor.getString(cursor.getColumnIndex(Student.KEY_NAME)));
                student.setDateOfBirth(cursor.getString(cursor.getColumnIndex(Student.KEY_DATE_OF_BIRTH)));
                student.setMale(cursor.getInt(cursor.getColumnIndex(Student.KEY_GENDER)) == 1);
                student.setEmail(cursor.getString(cursor.getColumnIndex(Student.KEY_EMAIL)));
                student.setStudentAddress(cursor.getString(cursor.getColumnIndex(Student.KEY_ADDRESS)));
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
     * getAllClasses
     *
     * **/
    public List<String> getAllClasses(){
        List<String> classList = new ArrayList<>();

        /**Select all query**/
        String selectQuery = "SELECT * FROM " + Classes.TABLE_CLASSES;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                Classes _class = new Classes();
                _class.set_name(cursor.getString(cursor.getColumnIndex(Classes.KEY_NAME)));
                _class.set_quantity(cursor.getInt(cursor.getColumnIndex(Classes.KEY_QUANTITY)));
                _class.set_gradeID(cursor.getInt(cursor.getColumnIndex(Classes.KEY_GRADE_ID)));
                classList.add(_class.get_name());
            }while(cursor.moveToNext());
        }
        cursor.close();
        return classList;
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
                + Classes.KEY_NAME + " DESC";
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
     * get className by StudentID
     *
     * **/

    public String getClassNameByStudentId(String studentID){
        Classes _class = new Classes();
        /**Select all query**/
        String selectQuery = "SELECT * FROM " + Classes.TABLE_STUDENTS_IN_CLASS + " WHERE "
                + Classes.KEY_STUDENT_ID + " = '" + studentID + "'";

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
                _class.set_name(cursor.getString(cursor.getColumnIndex(Classes.KEY_CLASS_NAME)));
        }
        cursor.close();
        return (_class.get_name());

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
     * marking student
     *
     * **/
    public void markingStudent(Marking marking){
        SQLiteDatabase db = this.getWritableDatabase();
        /**inserting row**/
        db.insert(Marking.TABLE_SCORE_RECORD, null, marking.getMarkingContentValues());
        db.close();
    }


    /**
     *
     * get List of Student by className, Semester, Subject and typeOfMark
     *
     * **/
    public List<Marking> getAll(Classes _class, Subject subject){
        List<Marking> markingList = new ArrayList<>();

        /**Select all query**/
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String queryString = "SELECT * FROM "
                + Marking.TABLE_SCORE_RECORD + " WHERE " + Marking.KEY_CLASS_NAME + " = '" + _class.get_name() + "' AND "
                + Marking.KEY_SEMESTER + " = " + subject.getSubjectSemester() + " AND "
                + Marking.KEY_SUBJECT_NAME + " = '" + subject.getSubjectName() + "' AND "
                + Marking.KEY_TYPE_OF_MARK + " = '" + subject.getSubjectTypeOfMark()+"'";
        Cursor cursor = sqLiteDatabase.rawQuery(queryString,null);

        /**looping through all rows and adding to list**/
        if(cursor.moveToFirst()){
            do{
                Marking marking = new Marking();
                Student student = new Student();

                /**set Student Name and ID**/
                student.setStudentName(cursor.getString(cursor.getColumnIndex(Marking.KEY_STUDENT_NAME)));
                student.setStudentId(cursor.getString(cursor.getColumnIndex(Marking.KEY_STUDENT_ID)));
                marking.setStudent(student);


                /**set Mark Value**/
                marking.setMarkValue(cursor.getInt(cursor.getColumnIndex(Marking.KEY_MARK_VALUE)));

                markingList.add(marking);

            }while(cursor.moveToNext());
        }

        return markingList;
    }



    /****************EDIT STUDENT****************/

    /**
     *
     * editStudentName
     *
     * **/
    public void editStudentNameById(String studentID, String newName){
        /**Select all query**/
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String queryString = "UPDATE "
                + Student.TABLE_STUDENTS + " SET " + Student.KEY_NAME + " = '" + newName + "' WHERE "
                + Student.KEY_ID + " = '" + studentID +"'";
        Cursor cursor = sqLiteDatabase.rawQuery(queryString,null);

        cursor.moveToFirst();
        cursor.close();
    }

    /**
     *
     * editStudentClass
     *
     * **/
    public void editStudentClassById(String studentID, String newClass){
        /**Select all query**/
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String queryString = "UPDATE "
                + Classes.TABLE_STUDENTS_IN_CLASS + " SET " + Classes.KEY_CLASS_NAME + " = '" + newClass + "' WHERE "
                + Classes.KEY_STUDENT_ID + " = '" + studentID +"'";
        Cursor cursor = sqLiteDatabase.rawQuery(queryString,null);

        cursor.moveToFirst();
        cursor.close();
    }


    /**
     *
     * editStudentGender
     *
     * **/
    public void editStudentGenderById(Student student){
        /**Select all query**/
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();


        ContentValues cv = new ContentValues();
        cv.put(Student.KEY_GENDER, student.isMale());

        String where = Student.KEY_ID+ " = '"+student.getStudentId()+"'";

        sqLiteDatabase.update(Student.TABLE_STUDENTS, cv, where , null);
        sqLiteDatabase.close();
    }


    /**
     *
     * editStudentDateOfBirth
     *
     * **/
    public void editStudentDoBById(Student student){
        /**Select all query**/
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();


        ContentValues cv = new ContentValues();
        cv.put(Student.KEY_DATE_OF_BIRTH, student.getDateOfBirth());

        String where = Student.KEY_ID+ " = '"+student.getStudentId()+"'";

        sqLiteDatabase.update(Student.TABLE_STUDENTS, cv, where , null);
        sqLiteDatabase.close();
    }


    /**
     *
     * editStudentEmail
     *
     * **/
    public void editStudentEmailById(Student student){
        /**Select all query**/
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();


        ContentValues cv = new ContentValues();
        cv.put(Student.KEY_EMAIL, student.getEmail());

        String where = Student.KEY_ID+ " = '"+student.getStudentId()+"'";

        sqLiteDatabase.update(Student.TABLE_STUDENTS, cv, where , null);
        sqLiteDatabase.close();
    }


    /**
     *
     * editStudentAddress
     *
     * **/
    public void editStudentAddressById(Student student){
        /**Select all query**/
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();


        ContentValues cv = new ContentValues();
        cv.put(Student.KEY_ADDRESS, student.getStudentAddress());

        String where = Student.KEY_ID+ " = '"+student.getStudentId()+"'";

        sqLiteDatabase.update(Student.TABLE_STUDENTS, cv, where , null);
        sqLiteDatabase.close();
    }


    /***************DELETE STUDENT****************/

    /**
     *
     * DELETE STUDENT FROM CLASS
     *
     * **/
    public void deleteStudentFromClass(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Classes.TABLE_STUDENTS_IN_CLASS, Classes.KEY_STUDENT_ID + " = ?",
                new String[] {student.getStudentId()});
        db.close();
    }


    /**
     *
     * DELETE STUDENT
     *
     * **/
    public int deleteStudent(Student student){
        int result = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        result = db.delete(Student.TABLE_STUDENTS, Classes.KEY_STUDENT_ID + " = ?",
                new String[] {student.getStudentId()});
        db.close();
        return result;
    }



    /**
     *
     *
     * SEARCH STUDENT
     *
     * **/
    public Cursor searchStudentByName(String studentName){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+Student.TABLE_STUDENTS+" WHERE "+Student.KEY_NAME+" LIKE '%"+studentName+"%'";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;

    }

}
