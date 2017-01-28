package com.example.user.student_management.ui.student_list;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.student_management.R;
import com.example.user.student_management.RecyclerViewClickListener;
import com.example.user.student_management.db.DatabaseHandler;
import com.example.user.student_management.model.Student;
import com.example.user.student_management.ui.class_list.ClassDetailsActivity;
import com.example.user.student_management.ui.class_list.ClassesListActivity;
import com.example.user.student_management.ui.home.LoginSuccessActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentsListActivity extends AppCompatActivity implements RecyclerViewClickListener{
    private static final String TAG = StudentsListActivity.class.getSimpleName();
    public static final String EXTRA_IS_ADD_MODE = "EXTRA_IS_ADD_MODE";

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    DatabaseHandler db;
    List<Student> studentList;
    private StudentAdapter adapter;
    private AlertDialog addStudentDialog;
    private DatePickerDialog datePickerDialog;
    private boolean _isMale = false;
    private boolean isAddMode;
    private SimpleDateFormat simpleDateFormat;
    private String grade;
    boolean status;
    Student student;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);
        ButterKnife.bind(this);



        /**getIntent**/
        isAddMode = getIntent().getBooleanExtra(EXTRA_IS_ADD_MODE, false);

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setUpRecyclerView();
        initAddStudentDialog();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_option_menu,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.mnSearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                StudentsListActivity.this.adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                StudentsListActivity.this.adapter.getFilter().filter(newText);
                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        db = new DatabaseHandler(getApplicationContext());
        int id = item.getItemId();
        String sentEmail = getIntent().getStringExtra("email");
        String role = db.getRoleByEmail(sentEmail);
        if(id == R.id.mnInsert){
            if(role.equals("Manager") || role.equals("Administrator")){
                if(addStudentDialog != null && !addStudentDialog.isShowing()){
                    addStudentDialog.show();
                }
            }else{
                warningDialog("You are not allowed to add new student");
            }
        }
        return super.onOptionsItemSelected(item);
    }



    /**Set up recycler view**/
    private void setUpRecyclerView(){
        db = new DatabaseHandler(StudentsListActivity.this);
        if (isAddMode) {
            studentList = db.getStudentListNotInClass();
        }else {
            studentList = db.getStudentList();
        }
        /**init recycler view adapter**/
        recyclerView.setHasFixedSize(true);

        //TODO: problem: need to parse student object into adapter
        adapter = new StudentAdapter(isAddMode);


        adapter.setViewClickListener(this);
        adapter.refreshData(studentList == null ? new ArrayList<Student>() : studentList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }



    /**Open A Dialog when click + **/
    private void initAddStudentDialog(){
        /**Init Layout inside Dialog**/
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(StudentsListActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_add_student, null);
        dialogBuilder.setView(dialogView);

        /**find view by ID**/
        final EditText edtName = (EditText) dialogView.findViewById(R.id.edtName);
        final EditText edtDoB = (EditText) dialogView.findViewById(R.id.edtDoB);
        final EditText edtAddress = (EditText) dialogView.findViewById(R.id.edtAddress);
        final EditText edtEmail = (EditText) dialogView.findViewById(R.id.edtEmail);
        final RadioGroup groupGender = (RadioGroup) dialogView.findViewById(R.id.groupGender);
        groupGender.check(R.id.isMale);



        Button btnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
        Button btnAdd = (Button) dialogView.findViewById(R.id.btnAdd);


        /**set time field**/
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(StudentsListActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year,month,dayOfMonth);
                    edtDoB.setText(simpleDateFormat.format(newDate.getTime()));
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        /**Show datepicker dialog**/
        edtDoB.setFocusable(false);
        edtDoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(datePickerDialog != null && !datePickerDialog.isShowing()){
                    datePickerDialog.show();
                }
            }
        });



        /**Add button**/
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**get Fullname**/
                String studentName = getEdittextString(edtName);
                /**get Address**/
                String studentAddress = getEdittextString(edtAddress);
                /**get Email**/
                String studentEmail = getEdittextString(edtEmail);
                /**get Date of Birth**/
                String dateInString = getEdittextString(edtDoB);


                try {
                    if (!TextUtils.isEmpty(studentName)  && !TextUtils.isEmpty(studentAddress)
                            && !TextUtils.isEmpty(studentEmail) && !TextUtils.isEmpty(dateInString)) {
                        if(isValidEmail(studentEmail) && !containsDigit(studentName)){
                            /**generate random ID**/
                            long id = System.currentTimeMillis();

                            /**get gender from radio button**/
                            int selectedId = groupGender.getCheckedRadioButtonId();
                            _isMale = selectedId == R.id.isMale;

                            Calendar selectedCalendar = Calendar.getInstance();

                            selectedCalendar.setTime(simpleDateFormat.parse(dateInString));

                            if (2016 - selectedCalendar.get(Calendar.YEAR) == 16) {
                                grade = "10";
                            } else if (2016 - selectedCalendar.get(Calendar.YEAR) == 17) {
                                grade = "11";
                            } else if (2016 - selectedCalendar.get(Calendar.YEAR) == 18) {
                                grade = "12";
                            }

                            String studentId = "16" + grade + String.valueOf(id).substring(9);

                            student = new Student(studentId, dateInString, studentName,
                                    studentAddress, studentEmail, _isMale, status);

                            /**Add to database**/
                            if (db != null) {
                                db.addNewStudent(student);

                                /**get studentList from db and show in recyclerview**/
                                List<Student> studentList = db.getStudentList();
                                adapter.refreshData(studentList);

                                /**set dialog edit text to null**/
                                edtName.setText(null);
                                edtName.requestFocus();
                                edtAddress.setText(null);
                                edtEmail.setText(null);
                                edtDoB.setText(null);
                                addStudentDialog.dismiss();
                            }
                            else{
                                warningDialog("Something went wrong. Please correct!");
                            }
                        }else{
                            warningDialog("Check your student name/email address");
                        }
                    }else{
                        warningDialog("Can not leave blank");
                    }
                }catch (ParseException e) {
                    e.printStackTrace();
                    addStudentDialog.dismiss();
                }


                    }

            });

        /**Cancel button**/
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudentDialog.dismiss();
            }
        });



        /**create and show dialog**/
        addStudentDialog = dialogBuilder.create();

    }

    public void warningDialog(String message){
        final AlertDialog.Builder builder = new AlertDialog.Builder(StudentsListActivity.this);

        //set Title
        builder.setTitle("Warning");

        //set Message
        builder.setMessage(message);

        //set Icon
        builder.setIcon(R.drawable.warning);



        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private String getEdittextString(EditText editText) {
        return editText.getText().toString().trim();
    }

    public boolean isValidEmail(CharSequence target){
        return target != null && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean containsDigit(String s) {
        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) {
                    return true;
                }
            }
        }
        return false;
    }



    @Override
    public void recyclerViewListLongClick(final int position) {
        //TODO: implement alert dialog to have an option DELETE
        final AlertDialog.Builder builder = new AlertDialog.Builder(StudentsListActivity.this);
        final Student _student = adapter.getItemAtPosition(position);
        //set Title
        builder.setTitle("Delete");

        //set Message
        builder.setMessage("Are you sure you want to delete this student?");

        //set Icon
        builder.setIcon(R.drawable.trash_bin);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String role = db.getRoleByEmail(getIntent().getStringExtra("email"));
                if(role.equals("Manager") || role.equals("Administrator")){
                    if(_student != null){
                        if(db.deleteStudent(_student) != -1){
                            db.deleteStudentFromScoreRecord(_student);
                            studentList.remove(_student);
                            adapter.refreshData(studentList);
                        }
                    }else {
                        Toast.makeText(StudentsListActivity.this, "Can not delete", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(StudentsListActivity.this, "You are not allowed to do this", Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        builder.show();

    }

    @Override
    public void recyclerViewListClick(int position) {
        DatabaseHandler db = new DatabaseHandler(this);
        Intent intent = new Intent(this, StudentDetailsActivity.class);

        Student student = adapter.getItemAtPosition(position);

        if(student != null){
            intent.putExtra("_studentID",student.getStudentId());
            intent.putExtra("_studentName", student.getStudentName());
            intent.putExtra("_studentYearOfBirth", student.getDateOfBirth());
            intent.putExtra("_studentAddress",student.getStudentAddress());
            intent.putExtra("_studentEmail", student.getEmail());
            intent.putExtra("_studentGender" , String.valueOf(student.isMale()));
            intent.putExtra("_studentClass", db.getClassNameByStudentId(student.getStudentId()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
