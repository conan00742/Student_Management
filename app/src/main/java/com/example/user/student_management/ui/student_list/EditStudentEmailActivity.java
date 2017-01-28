package com.example.user.student_management.ui.student_list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
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

public class EditStudentEmailActivity extends AppCompatActivity {
    @BindView(R.id.tvOldEmail)
    TextView tvOldEmail;

    @BindView(R.id.edtNewEmail)
    EditText edtNewEmail;

    @BindView(R.id.btnSaveNewEmail)
    Button btnSaveNewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_email);
        ButterKnife.bind(this);

        tvOldEmail.setText(getIntent().getStringExtra("editedEmail"));

    }

    @OnClick(R.id.btnSaveNewEmail)
    public void editNewEmail(){
        Intent i = new Intent(EditStudentEmailActivity.this, LoginSuccessActivity.class);
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        Student student = new Student();
        student.setStudentId(getIntent().getStringExtra("studentId"));
        String newEmail = edtNewEmail.getText().toString().trim();
        if(isValidEmail(newEmail)){
            student.setEmail(newEmail);
            db.editStudentEmailById(student);
            startActivity(i);
        }else{
            Toast.makeText(this, "Wrong email syntax", Toast.LENGTH_SHORT).show();
        }


    }

    public boolean isValidEmail(CharSequence target){
        return target != null && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
