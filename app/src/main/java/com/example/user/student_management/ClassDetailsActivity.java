package com.example.user.student_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.TextView;

import com.example.user.student_management.ui.student_list.StudentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassDetailsActivity extends AppCompatActivity {
    @BindView(R.id.tvClassName)
    TextView tvClassName;

    @BindView(R.id.tvClassQuantity)
    TextView getTvClassQuantity;

    @BindView(R.id.class_details_recycler_view)
    RecyclerView class_details_recycler_view;

    private StudentAdapter studentAdapter;
    List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);

        ButterKnife.bind(this);

        updateHeaderContent();

        initView();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void updateHeaderContent(){
        Intent i = getIntent();
        String className = i.getStringExtra("className");
        String classQuantity = i.getStringExtra("classQuantity");
        tvClassName.setText(String.format(getString(R.string.class_details_name), className));
        getTvClassQuantity.setText(String.format(getString(R.string.class_details_quantity), classQuantity));
    }

    private void initData(){
        Student student = new Student(System.currentTimeMillis(),1994,"Khiêm Ichigo");
        student.setMale(true);
        studentList.add(student);

        student = new Student(System.currentTimeMillis(),1996,"Slark");
        student.setMale(true);
        studentList.add(student);

        student = new Student(System.currentTimeMillis(),1993,"Storm Spirit");
        studentList.add(student);
    }

    private void initView(){
        studentList = new ArrayList<>();

        class_details_recycler_view.setHasFixedSize(true);

        studentAdapter = new StudentAdapter(studentList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        class_details_recycler_view.setLayoutManager(layoutManager);
        class_details_recycler_view.setAdapter(studentAdapter);
    }


}
