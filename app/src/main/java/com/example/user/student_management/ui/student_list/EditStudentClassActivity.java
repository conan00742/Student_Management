package com.example.user.student_management.ui.student_list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.user.student_management.R;
import com.example.user.student_management.db.DatabaseHandler;
import com.example.user.student_management.model.Classes;
import com.example.user.student_management.model.Student;
import com.example.user.student_management.ui.home.LoginSuccessActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditStudentClassActivity extends AppCompatActivity {
    @BindView(R.id.tvOldClass)
    TextView tvOldClass;

    @BindView(R.id.spnClassList)
    Spinner spnClassList;

    @BindView(R.id.btnSaveNewClass)
    Button btnSaveNewClass;

    String newClass;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_class);
        ButterKnife.bind(this);

        tvOldClass.setText(getIntent().getStringExtra("editedClass"));

        loadSpinnerData();
    }

    public void loadSpinnerData(){
        db = new DatabaseHandler(getApplicationContext());
        List<String> classList = db.getAllClasses();

        spnClassList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newClass = spnClassList.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> classAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,classList);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnClassList.setAdapter(classAdapter);
    }

    @OnClick(R.id.btnSaveNewClass)
    public void editNewClass(){
        Intent i = new Intent(EditStudentClassActivity.this, LoginSuccessActivity.class);
        String studentID = getIntent().getStringExtra("studentId");
        Student student = new Student();
        student.setStudentId(studentID);
        db.editStudentClassById(studentID,newClass);
        db.deleteStudentFromScoreRecord(student);
        startActivity(i);
    }
}
