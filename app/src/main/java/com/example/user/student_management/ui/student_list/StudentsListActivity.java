package com.example.user.student_management.ui.student_list;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.user.student_management.R;
import com.example.user.student_management.Student;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentsListActivity extends AppCompatActivity {
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    private List<Student> studentList;
    private StudentAdapter adapter;
    private DatePickerDialog datePickerDialog;
    private boolean _isMale = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.mnInsert){
            addStudentDialog();
        }
        return super.onOptionsItemSelected(item);
    }



    /**/

    private void insertData(String fullName, long id, int yearOfBirth, String address, String email, boolean isMale, boolean isChecked){
        Student _student = new Student(id, yearOfBirth, fullName, address, email, isMale, isChecked);
        studentList.add(_student);
        adapter.notifyDataSetChanged();

    }


    /**Open A Dialog when click + **/
    private void addStudentDialog(){
        /**Init Layout inside Dialog**/
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(StudentsListActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_add_student, null);
        dialogBuilder.setView(dialogView);



        /**ADD**/
        dialogBuilder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addStudent(dialogView);
            }
        });

        /**CANCEL**/
        dialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        /**create and show dialog**/
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

    }


    /**Add student**/
    private void addStudent(View dialogView){

        /**find view by ID**/
        EditText edtName = (EditText) dialogView.findViewById(R.id.edtName);
        final EditText edtDoB = (EditText) dialogView.findViewById(R.id.edtDoB);
        EditText edtAddress = (EditText) dialogView.findViewById(R.id.edtAddress);
        EditText edtEmail = (EditText) dialogView.findViewById(R.id.edtEmail);
        RadioGroup groupGender = (RadioGroup) dialogView.findViewById(R.id.groupGender);
        RadioButton radioSelectedGender = null;

        /**get Fullname**/
        String studentName = edtName.getText().toString().trim();
        /**get Address**/
        String studentAddress = edtAddress.getText().toString().trim();
        /**get Email**/
        String studentEmail = edtEmail.getText().toString().trim();




        /**set time field**/
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        edtDoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtDoB.setInputType(InputType.TYPE_NULL);
                Calendar calendar = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year,month,dayOfMonth);
                        edtDoB.setText(simpleDateFormat.format(newDate.getTime()));
                        Date date = newDate.getTime();
                        Intent intent = new Intent();
                        intent.putExtra("year",date.getTime());
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show(); /**RÕ RÀNG LÀ CÓ SHOW() Ở ĐÂY RỒI NHƯNG KHÔNG HIỆN DIALOG RA**/
            }
        });



        /**get year of birth from edittext**/
        Intent i = getIntent();
        Date dOB = new Date(i.getLongExtra("year",0));
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        int yearOfBirth = Integer.parseInt(df.format(dOB));

        /**generate random ID**/
        long id = System.currentTimeMillis();

        /**get gender from radio button**/
        int selectedId = groupGender.getCheckedRadioButtonId();
        radioSelectedGender = (RadioButton) findViewById(selectedId);
        if(radioSelectedGender.getText().equals("Male")){
            _isMale = true;
        }else{
            _isMale = false;
        }

        /**init student list**/
        studentList = new ArrayList<>();


        /**init recycler view adapter**/
        recyclerView.setHasFixedSize(true);
        adapter = new StudentAdapter(studentList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        insertData(studentName,id,yearOfBirth,studentAddress,studentEmail, _isMale, false);
        adapter.notifyDataSetChanged();


    }



}
