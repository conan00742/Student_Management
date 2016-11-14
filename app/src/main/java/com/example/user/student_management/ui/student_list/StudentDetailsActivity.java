package com.example.user.student_management.ui.student_list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.student_management.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentDetailsActivity extends AppCompatActivity {
    @BindView(R.id.tvStudentDetailsID)
    TextView tvStudentDetailsID;

    @BindView(R.id.tvStudentDetailsName)
    TextView tvStudentDetailsName;

    @BindView(R.id.tvStudentDetailsClass)
    TextView tvStudentDetailsClass;

    @BindView(R.id.tvStudentDetailsYear)
    TextView tvStudentDetailsYear;

    @BindView(R.id.tvStudentDetailsEmail)
    TextView tvStudentDetailsEmail;

    @BindView(R.id.tvStudentDetailsAddress)
    TextView tvStudentDetailsAddress;

    @BindView(R.id.imgViewStudentDetails)
    ImageView imgViewStudentDetails;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        ButterKnife.bind(this);
        //TODO: getIntent from ClassDetailsActivity ---> complete StudentDetailsActivity
        getStudentDetails();
    }


    public void getStudentDetails(){
        Intent intent = getIntent();
        String studentID = intent.getStringExtra("_studentID");
        String studentName = intent.getStringExtra("_studentName");
        String dateOfBirth = intent.getStringExtra("_studentYearOfBirth");
        String studentAddress = intent.getStringExtra("_studentAddress");
        String studentEmail = intent.getStringExtra("_studentEmail");
        String studentGender = intent.getStringExtra("_studentGender");
        String studentClass = intent.getStringExtra("_studentClass");

        tvStudentDetailsID.setText(studentID);
        tvStudentDetailsName.setText(studentName);
        tvStudentDetailsClass.setText(studentClass);
        tvStudentDetailsYear.setText(dateOfBirth);
        tvStudentDetailsEmail.setText(studentEmail);
        tvStudentDetailsAddress.setText(studentAddress);
        if(studentGender.equals("true")){
            imgViewStudentDetails.setImageResource(R.drawable.male_student);
        }else{
            imgViewStudentDetails.setImageResource(R.drawable.female_student);
        }
    }

}
