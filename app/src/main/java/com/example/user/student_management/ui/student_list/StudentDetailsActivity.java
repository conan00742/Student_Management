package com.example.user.student_management.ui.student_list;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.student_management.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @BindView(R.id.btnEditGender)
    Button btnEditGender;

    @BindView(R.id.btnEditName)
    Button btnEditName;

    @BindView(R.id.btnEditClass)
    Button btnEditClass;

    @BindView(R.id.btnEditDateOfBirth)
    Button btnEditDateOfBirth;

    @BindView(R.id.btnEditEmail)
    Button btnEditEmail;

    @BindView(R.id.btnEditAddress)
    Button btnEditAddress;

    String studentID, studentName, dateOfBirth, studentAddress, studentEmail
            ,studentGender, studentClass;


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
        studentID = intent.getStringExtra("_studentID");
        studentName = intent.getStringExtra("_studentName");
        dateOfBirth = intent.getStringExtra("_studentYearOfBirth");
        studentAddress = intent.getStringExtra("_studentAddress");
        studentEmail = intent.getStringExtra("_studentEmail");
        studentGender = intent.getStringExtra("_studentGender");
        studentClass = intent.getStringExtra("_studentClass");

        tvStudentDetailsName.invalidate();
        tvStudentDetailsID.setText(studentID);
        tvStudentDetailsName.setText(studentName);
        if(studentClass != null){
            tvStudentDetailsClass.setText(studentClass);
        }else{
            tvStudentDetailsClass.setText("Waiting...");
        }
        tvStudentDetailsYear.setText(dateOfBirth);
        tvStudentDetailsEmail.setText(studentEmail);
        tvStudentDetailsAddress.setText(studentAddress);
        if(studentGender.equals("true")){
            imgViewStudentDetails.setImageResource(R.drawable.male_student);
        }else{
            imgViewStudentDetails.setImageResource(R.drawable.female_student);
        }
    }

    @OnClick({R.id.btnEditGender, R.id.btnEditName, R.id.btnEditClass, R.id.btnEditDateOfBirth, R.id.btnEditEmail, R.id.btnEditAddress})
    public void editStudent(View v){
        Intent editIntent;
        if(v.getId() == R.id.btnEditName){
            editIntent = new Intent(StudentDetailsActivity.this, EditStudentNameActivity.class);
            editIntent.putExtra("studentId", studentID);
            editIntent.putExtra("editedName", studentName);
            startActivity(editIntent);
        }else if(v.getId() == R.id.btnEditClass){
            if(studentClass == null){
                Snackbar.make(v, "This student still hasn't had class yet!!!", Snackbar.LENGTH_LONG).show();
            }else{
                editIntent = new Intent(StudentDetailsActivity.this, EditStudentClassActivity.class);
                editIntent.putExtra("studentId", studentID);
                editIntent.putExtra("editedClass", studentClass);
                startActivity(editIntent);
            }
        }else if(v.getId() == R.id.btnEditDateOfBirth){
            editIntent = new Intent(StudentDetailsActivity.this, EditStudentDateOfBirthActivity.class);
            editIntent.putExtra("studentId", studentID);
            editIntent.putExtra("editedDoB", dateOfBirth);
            startActivity(editIntent);
        }else if(v.getId() == R.id.btnEditEmail){
            editIntent = new Intent(StudentDetailsActivity.this, EditStudentEmailActivity.class);
            editIntent.putExtra("studentId", studentID);
            editIntent.putExtra("editedEmail", studentEmail);
            startActivity(editIntent);
        }else if(v.getId() == R.id.btnEditAddress){
            editIntent = new Intent(StudentDetailsActivity.this, EditStudentAddressActivity.class);
            editIntent.putExtra("studentId", studentID);
            editIntent.putExtra("editedAddress", studentAddress);
            startActivity(editIntent);
        }else if(v.getId() == R.id.btnEditGender){
            editIntent = new Intent(StudentDetailsActivity.this, EditStudentGenderActivity.class);
            editIntent.putExtra("studentId", studentID);
            editIntent.putExtra("editedGender", studentGender);
            startActivity(editIntent);
        }
    }

}
