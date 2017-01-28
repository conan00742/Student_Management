package com.example.user.student_management.ui.student_list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.user.student_management.R;
import com.example.user.student_management.db.DatabaseHandler;
import com.example.user.student_management.model.Student;
import com.example.user.student_management.ui.home.LoginSuccessActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditStudentGenderActivity extends AppCompatActivity {
    @BindView(R.id.tvCurrentGender)
    TextView tvCurrentGender;

    @BindView(R.id.radioGroupGender)
    RadioGroup radioGroupGender;

    @BindView(R.id.btnSaveNewGender)
    Button btnSaveNewGender;

    boolean newGender = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_gender);
        ButterKnife.bind(this);

        tvCurrentGender.setText( (getIntent().getStringExtra("editedGender").equals("true") ? "Male" : "Female") );
        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.editedMale){
                    newGender = true;
                }else if(checkedId == R.id.editedFemale){
                    newGender = false;
                }
            }
        });

    }



    @OnClick(R.id.btnSaveNewGender)
    public void saveNewGender(){
        Intent i = new Intent(EditStudentGenderActivity.this, LoginSuccessActivity.class);
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        Student student = new Student();
        student.setStudentId(getIntent().getStringExtra("studentId"));
        student.setMale(newGender);
        db.editStudentGenderById(student);
        startActivity(i);
    }
}
