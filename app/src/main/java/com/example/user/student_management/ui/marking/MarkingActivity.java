package com.example.user.student_management.ui.marking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.student_management.R;
import com.example.user.student_management.db.DatabaseHandler;
import com.example.user.student_management.model.Classes;
import com.example.user.student_management.model.Marking;
import com.example.user.student_management.model.Student;
import com.example.user.student_management.model.Subject;
import com.example.user.student_management.ui.home.LoginSuccessActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.user.student_management.ui.class_list.ClassDetailsAdapter.HEADER;
import static com.example.user.student_management.ui.class_list.ClassDetailsAdapter.INPUTROW;

public class MarkingActivity extends AppCompatActivity {
    @BindView(R.id.marking_recycler_view)
    RecyclerView marking_recycler_view;


    List<Student> studentList = new ArrayList<>();
    int[] mDataViewType = {HEADER,INPUTROW};
    Classes currentClass = new Classes();
    Student currentStudent = new Student();
    Subject currentSubject = new Subject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marking);
        ButterKnife.bind(this);
        if(getIntent().getExtras() != null){
            /**set class name + quantity**/
            currentClass.set_name(getIntent().getStringExtra("classNameForMarking"));
            currentClass.set_quantity(Integer.parseInt(getIntent().getStringExtra("classQuantityForMarking")));

            /**set subject semester + name + type of mark**/
            currentSubject.setSubjectSemester(Integer.parseInt(getIntent().getStringExtra("semester")));
            currentSubject.setSubjectName(getIntent().getStringExtra("subject"));
            currentSubject.setSubjectTypeOfMark(getIntent().getStringExtra("markType"));

            /**set student name + ID**/
            currentStudent.setStudentId(getIntent().getStringExtra("studentIDForMarking"));
            currentStudent.setStudentName(getIntent().getStringExtra("studentNameForMarking"));
        }

        initView();
    }
    //TODO: Add mark to database

    private void initView(){
        marking_recycler_view.setHasFixedSize(true);
        MarkingAdapter adapter = new MarkingAdapter(getApplicationContext(),currentClass,currentSubject
                ,currentStudent,mDataViewType);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        marking_recycler_view.setLayoutManager(layoutManager);
        marking_recycler_view.setAdapter(adapter);

    }


}
