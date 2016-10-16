package com.example.user.student_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.user.student_management.other.StudentAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentsListActivity extends AppCompatActivity {
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    private List<Student> studentList;
    private StudentAdapter adapter;

    private static final int REQUEST_CODE_ADD_STUDENT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.mnInsert){
            Intent i = new Intent(this,AddStudentActivity.class);
            startActivityForResult(i,REQUEST_CODE_ADD_STUDENT);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ADD_STUDENT){
            if(resultCode == RESULT_OK && data != null){

                //get fullName Extra from AddStudentActivity
                String fullName = data.getStringExtra("fullName");

                //get dateOfBirth Extra from AddStudentActivity
                Date dOB = new Date(data.getLongExtra("year",0));
                SimpleDateFormat df = new SimpleDateFormat("yyyy");
                int yearOfBirth = Integer.parseInt(df.format(dOB));

                //generate random ID
                long id = System.currentTimeMillis();

                studentList = new ArrayList<>();

                recyclerView.setHasFixedSize(true);

                adapter = new StudentAdapter(studentList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

                insertData(fullName,id,yearOfBirth);
            }
        }
    }

    private void insertData(String fullName, long id, int yearOfBirth){
        Student _student = new Student(id, yearOfBirth, fullName);
        studentList.add(_student);
        adapter.notifyDataSetChanged();

    }


}
