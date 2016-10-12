package com.example.user.student_management;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddStudentActivity extends AppCompatActivity {

    @BindView(R.id.edtName)
    EditText edtName;

    @BindView(R.id.edtDoB)
    EditText edtDoB;

    @BindView(R.id.btnAdd)
    Button btnAdd;

    private DatePickerDialog datePickerDialog;

    private SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        ButterKnife.bind(this);

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        edtDoB.requestFocus();

        setDateTimeField();
    }


    private void setDateTimeField(){
        edtDoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,month,dayOfMonth);
                edtDoB.setText(simpleDateFormat.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

    }

    @OnClick(R.id.btnAdd)
    public void addStudent(){
        String fullName = edtName.getText().toString();
        //TODO: Nhap xong an ADD quay lai StudentList show ra hoc sinh vua moi add nhung chi hien NAM SINH
    }

}
