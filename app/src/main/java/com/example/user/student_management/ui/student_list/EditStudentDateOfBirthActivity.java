package com.example.user.student_management.ui.student_list;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.student_management.R;
import com.example.user.student_management.db.DatabaseHandler;
import com.example.user.student_management.model.Student;
import com.example.user.student_management.ui.home.LoginSuccessActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditStudentDateOfBirthActivity extends AppCompatActivity {
    @BindView(R.id.tvCurrentDoB)
    TextView tvCurrentDoB;

    @BindView(R.id.edtNewDoB)
    EditText edtNewDoB;

    @BindView(R.id.btnSaveNewDoB)
    Button btnSaveNewDoB;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat simpleDateFormat;
    String dateInString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_date_of_birth);
        ButterKnife.bind(this);

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        tvCurrentDoB.setText(getIntent().getStringExtra("editedDoB"));
        datePickerDialog();
    }

    public void datePickerDialog(){
        /**set time field**/
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(EditStudentDateOfBirthActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,month,dayOfMonth);
                edtNewDoB.setText(simpleDateFormat.format(newDate.getTime()));
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        /**Show datepicker dialog**/
        edtNewDoB.setFocusable(false);
        edtNewDoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(datePickerDialog != null && !datePickerDialog.isShowing()){
                    datePickerDialog.show();
                }
            }
        });


    }


    @OnClick(R.id.btnSaveNewDoB)
    public void saveNewDateOfBirth(){
        Intent i = new Intent(EditStudentDateOfBirthActivity.this, LoginSuccessActivity.class);
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        dateInString = edtNewDoB.getText().toString().trim();
        Student student = new Student();
        student.setStudentId(getIntent().getStringExtra("studentId"));
        student.setDateOfBirth(dateInString);
        db.editStudentDoBById(student);
        startActivity(i);
    }
}
