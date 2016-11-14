package com.example.user.student_management.ui.marking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;

import com.example.user.student_management.R;
import com.example.user.student_management.db.DatabaseHandler;
import com.example.user.student_management.model.Classes;
import com.example.user.student_management.model.Marking;
import com.example.user.student_management.model.Student;
import com.example.user.student_management.model.Subject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.user.student_management.ui.marking.ViewMarkAdapter.HEADER;
import static com.example.user.student_management.ui.marking.ViewMarkAdapter.INPUTROW;

public class ViewMarkWindow extends AppCompatActivity {
    @BindView(R.id.view_mark_recycler_view)
    RecyclerView view_mark_recycler_view;


    List<Marking> markingList;
    int[] mDataViewType = {HEADER,INPUTROW};
    Classes currentClass = new Classes();
    Subject currentSubject = new Subject();
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mark_window);
        ButterKnife.bind(this);



        initView();
    }
    //TODO: Add mark to database

    private void initView(){
        Intent intent = getIntent();

        String className = intent.getStringExtra("classNameForViewMark1");
        /*int classQuantity = Integer.parseInt(intent.getStringExtra("classQuantityForViewMark"));*/

        int semester = Integer.parseInt(intent.getStringExtra("semester"));
        String subjectName = intent.getStringExtra("subject");
        String typeOfMark = intent.getStringExtra("typeOfMark");

        currentClass.set_name(className);
        /*currentClass.set_quantity(classQuantity);*/

        currentSubject.setSubjectSemester(semester);
        currentSubject.setSubjectName(subjectName);
        currentSubject.setSubjectTypeOfMark(typeOfMark);

        db = new DatabaseHandler(getApplicationContext());

        markingList = db.getAll(currentClass,currentSubject);



        view_mark_recycler_view.setHasFixedSize(true);
        ViewMarkAdapter adapter = new ViewMarkAdapter(getApplicationContext(),markingList,mDataViewType,currentClass,
                currentSubject);
        adapter.refreshData(markingList == null ? new ArrayList<Marking>() : markingList);
        adapter.notifyDataSetChanged();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        view_mark_recycler_view.setLayoutManager(layoutManager);
        view_mark_recycler_view.setAdapter(adapter);

    }


}
