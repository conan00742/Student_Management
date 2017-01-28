package com.example.user.student_management.ui.marking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.student_management.R;
import com.example.user.student_management.db.DatabaseHandler;
import com.example.user.student_management.model.Classes;
import com.example.user.student_management.model.Marking;
import com.example.user.student_management.model.Subject;
import com.example.user.student_management.ui.class_list.ClassAdapter;
import com.example.user.student_management.ui.class_list.ClassesListActivity;
import com.example.user.student_management.ui.home.LoginSuccessActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.user.student_management.ui.class_list.StudentInClassAdapter.HEADER;
import static com.example.user.student_management.ui.class_list.StudentInClassAdapter.INPUTROW;

public class ViewMarkActivity extends AppCompatActivity {
    public static final String SEMESTER_TAG = "view_mark_semester";
    public static final String SUBJECT_TAG = "view_mark_subject";
    public static final String MARK_TYPE_TAG = "view_mark_type";
    public static final String CLASS_NAME_TAG = "view_mark_class_name";
    public static final String CLASS_QUANTITY_TAG = "view_mark_class_quantity";

    @BindView(R.id.marking_recycler_view)
    RecyclerView marking_recycler_view;

    DatabaseHandler db;
    Classes _class;
    Subject _subject;
    List<Marking> markingList;
    ViewMarkAdapter viewMarkAdapter;
    int[] mDataViewType = {HEADER,INPUTROW};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mark);
        ButterKnife.bind(this);
        getIntentExtra();
        initView();
    }



    public void initView(){

        db = new DatabaseHandler(getApplicationContext());
        //chia ra 3 th điểm trung bình môn, tb học kỳ, điểm tổng
        String typeOfMark = getIntent().getStringExtra(MARK_TYPE_TAG);
        if(typeOfMark.equals("Semester Summary Mark")){
            markingList = db.getSemesterSummaryMarkList(_class,_subject);
        }else if(typeOfMark.equals("Final Mark")){
            markingList = db.getFinalMarkList(_class,_subject);
        }else{
            markingList = db.getAll(_class,_subject);
        }

        marking_recycler_view.setHasFixedSize(true);
        viewMarkAdapter = new ViewMarkAdapter(getApplicationContext(),markingList,mDataViewType,
                _class,_subject);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        marking_recycler_view.setLayoutManager(layoutManager);
        marking_recycler_view.setAdapter(viewMarkAdapter);


    }


    public void getIntentExtra(){
        Intent i = getIntent();
        _class = new Classes();
        _class.set_name(i.getStringExtra(CLASS_NAME_TAG));
        _class.set_quantity(Integer.parseInt(i.getStringExtra(CLASS_QUANTITY_TAG)));

        _subject = new Subject();
        String typeOfMark = i.getStringExtra(MARK_TYPE_TAG);
        if(typeOfMark.equals("Semester Summary Mark")){
            _subject.setSubjectSemester(Integer.parseInt(i.getStringExtra(SEMESTER_TAG)));
            _subject.setSubjectTypeOfMark(i.getStringExtra(MARK_TYPE_TAG));
        }else if(typeOfMark.equals("Final Mark")){
            _subject.setSubjectTypeOfMark(i.getStringExtra(MARK_TYPE_TAG));
        }else{
            _subject.setSubjectSemester(Integer.parseInt(i.getStringExtra(SEMESTER_TAG)));
            _subject.setSubjectName(i.getStringExtra(SUBJECT_TAG));
            _subject.setSubjectTypeOfMark(i.getStringExtra(MARK_TYPE_TAG));
        }




    }


}
