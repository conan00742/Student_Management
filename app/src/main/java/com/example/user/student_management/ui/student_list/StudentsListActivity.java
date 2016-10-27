package com.example.user.student_management.ui.student_list;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.user.student_management.R;
import com.example.user.student_management.model.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentsListActivity extends AppCompatActivity {
    @BindView(R.id.recycler_view) RecyclerView recyclerView;


    private List<Student> studentList;
    private StudentAdapter adapter;
    private AlertDialog addStudentDialog;
    private DatePickerDialog datePickerDialog;
    private boolean _isMale = false;
    private boolean isChecked;
    private SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);
        ButterKnife.bind(this);

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setUpRecyclerView();
        initAddStudentDialog();
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
            if(addStudentDialog != null && !addStudentDialog.isShowing()){
                addStudentDialog.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }



    /**Set up recycler view**/
    private void setUpRecyclerView(){
        /**init student list**/
        studentList = new ArrayList<>();


        /**init recycler view adapter**/
        recyclerView.setHasFixedSize(true);
        adapter = new StudentAdapter(studentList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }



    /**Open A Dialog when click + **/
    private void initAddStudentDialog(){
        /**Init Layout inside Dialog**/
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(StudentsListActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_add_student, null);
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
                try {
                    /**get Fullname**/
                    String studentName = getEdittextString(edtName);
                    /**get Address**/
                    String studentAddress = getEdittextString(edtAddress);
                    /**get Email**/
                    String studentEmail = getEdittextString(edtEmail);
                    /**get Date of Birth**/
                    String dateInString = getEdittextString(edtDoB);

                    if (!TextUtils.isEmpty(studentName) && !TextUtils.isEmpty(studentAddress)
                            && !TextUtils.isEmpty(studentEmail) && !TextUtils.isEmpty(dateInString)
                            && isValidEmail(studentEmail)) {
                        /**generate random ID**/
                        long id = System.currentTimeMillis();

                        /**get gender from radio button**/
                        int selectedId = groupGender.getCheckedRadioButtonId();
                        _isMale = selectedId == R.id.isMale;

                        Calendar selectedCalendar = Calendar.getInstance();

                        selectedCalendar.setTime(simpleDateFormat.parse(dateInString));
                        Student student = new Student(id, selectedCalendar.get(Calendar.YEAR), studentName,
                                studentAddress, studentEmail, _isMale, true);

                        // Add to list and refresh adapter
                        studentList.add(student);
                        adapter.notifyDataSetChanged();
                        edtName.setText(null);
                        edtName.requestFocus();
                        edtAddress.setText(null);
                        edtEmail.setText(null);
                        edtDoB.setText(null);
                        addStudentDialog.dismiss();
                    }else{
                        Toast.makeText(StudentsListActivity.this, "Something went wrong. Please correct!", Toast.LENGTH_SHORT).show();
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

    private String getEdittextString(EditText editText) {
        return editText.getText().toString().trim();
    }

    public boolean isValidEmail(CharSequence target){
        return target != null && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


}
