package com.example.user.student_management.ui.student_list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.student_management.R;
import com.example.user.student_management.db.DatabaseHandler;
import com.example.user.student_management.model.Student;
import com.example.user.student_management.ui.home.LoginSuccessActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditStudentAddressActivity extends AppCompatActivity {
    @BindView(R.id.tvOldAddress)
    TextView tvOldAddress;

    @BindView(R.id.edtNewAddress)
    EditText edtNewAddress;

    @BindView(R.id.btnSaveNewAddress)
    Button btnSaveNewAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_address);
        ButterKnife.bind(this);

        tvOldAddress.setText(getIntent().getStringExtra("editedAddress"));

    }


    @OnClick(R.id.btnSaveNewAddress)
    public void editNewAddress(){
        Intent i = new Intent(EditStudentAddressActivity.this, LoginSuccessActivity.class);
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        Student student = new Student();
        student.setStudentId(getIntent().getStringExtra("studentId"));
        student.setStudentAddress(edtNewAddress.getText().toString().trim());
        db.editStudentAddressById(student);
        startActivity(i);

    }


}
