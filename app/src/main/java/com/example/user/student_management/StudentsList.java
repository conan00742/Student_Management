package com.example.user.student_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.user.student_management.Model.Student;
import com.example.user.student_management.other.StudentAdapter;

import java.util.ArrayList;
import java.util.List;

public class StudentsList extends AppCompatActivity {
    private List<Student> studentList = new ArrayList<>();
    private RecyclerView recyclerView;
    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        adapter = new StudentAdapter(studentList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        initData();
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
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initData(){
        Student student = new Student(31245,1995,"Jon Snow");
        studentList.add(student);

        student = new Student(37445,1995,"Slark");
        studentList.add(student);

        student = new Student(61478,1996,"Justin Timberlake");
        studentList.add(student);

        student = new Student(15648,1998,"Robin van Persie");
        studentList.add(student);

        student = new Student(64951,1994,"Storm Spirit");
        studentList.add(student);

        student = new Student(74152,1993,"Treant Protector");
        studentList.add(student);

        student = new Student(64315,1991,"Axe");
        studentList.add(student);

        student = new Student(65432,1997,"Sniper");
        studentList.add(student);

        student = new Student(31048,1999,"Troll Warlord");
        studentList.add(student);

        student = new Student(54785,1995,"Timbersaw");
        studentList.add(student);

        student = new Student(13595,1996,"Anti-Mage");
        studentList.add(student);

        student = new Student(78156,1994,"Drow Ranger");
        studentList.add(student);

        student = new Student(53347,1992,"Flappy Bird");
        studentList.add(student);

        student = new Student(64631,1993,"Winter Wyvern");
        studentList.add(student);

        student = new Student(87145,1998,"Wraith King");
        studentList.add(student);

        adapter.notifyDataSetChanged();

    }
}
