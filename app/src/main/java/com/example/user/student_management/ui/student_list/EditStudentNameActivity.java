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
import com.example.user.student_management.ui.class_list.ClassDetailsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditStudentNameActivity extends AppCompatActivity {
    @BindView(R.id.tvOldName)
    TextView tvOldName;

    @BindView(R.id.edtNewName)
    EditText edtNewName;

    @BindView(R.id.btnSaveNewName)
    Button btnSaveNewName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_name);
        ButterKnife.bind(this);

        tvOldName.setText(getIntent().getStringExtra("editedName"));


    }

    @OnClick(R.id.btnSaveNewName)
    void saveNewName(){
        Intent i = new Intent(EditStudentNameActivity.this, StudentsListActivity.class);
        String newName = edtNewName.getText().toString().trim();
        String studentId = getIntent().getStringExtra("studentId");
        DatabaseHandler db = new DatabaseHandler(EditStudentNameActivity.this);
        db.editStudentNameById(studentId, newName);
        startActivity(i);
    }
}
