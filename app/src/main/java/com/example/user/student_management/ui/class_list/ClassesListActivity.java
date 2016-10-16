package com.example.user.student_management.ui.class_list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.user.student_management.ClassDetailsActivity;
import com.example.user.student_management.Classes;
import com.example.user.student_management.OnClassListListener;
import com.example.user.student_management.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassesListActivity extends AppCompatActivity {
    @BindView(R.id.class_recycler_view)
    RecyclerView classRecyclerView;

    private ClassAdapter adapter;

    private List<Classes> classesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes_list);

        ButterKnife.bind(this);
        initView();
        spinnerClass();

    }

    private void initView() {
        //init class list
        classesList = new ArrayList<>();
        //recyclerView
        classRecyclerView.setHasFixedSize(true);
        adapter = new ClassAdapter(classesList);
        adapter.setClassListListener(new OnClassListListener() {
            @Override
            public void onClassClick(int position) {
                Classes classes = classesList.get(position);
                if(classes != null){
                    Intent intent = new Intent(ClassesListActivity.this, ClassDetailsActivity.class);
                    intent.putExtra("className", classes.get_name());
                    intent.putExtra("classQuantity", String.valueOf(classes.get_quantity()));
                    startActivity(intent);
                }
            }

        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        classRecyclerView.setLayoutManager(layoutManager);
        classRecyclerView.setAdapter(adapter);
    }

    /**
     *
     * Spinner Activity
     *
     * **/
    private void spinnerClass(){
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //If Grade 10 is selected
                if(position == 1){

                    initGradeTen();
                }
                //If Grade 11 is selected
                else if(position == 2){
                    initGradeEleven();
                }
                //If Grade 12 is selected
                else if(position == 3){
                    initGradeTwelve();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Spinner Drop down elements
        List<String> grades = new ArrayList<String>();
        grades.add("---Grade---");
        grades.add("Grade 10");
        grades.add("Grade 11");
        grades.add("Grade 12");

        // Creating adapter for spinner
        ArrayAdapter<String> gradesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, grades);

        // Drop down layout style - list view with radio button
        gradesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(gradesAdapter);
    }

    /**
     *
     * Fake Data
     *
     * **/

    //fake data for Grade 10
    private void initGradeTen(){
        classesList.clear();
        Classes classTen = new Classes("10A1",39);
        classesList.add(classTen);

        classTen = new Classes("10A2",37);
        classesList.add(classTen);

        classTen = new Classes("10A3",40);
        classesList.add(classTen);

        classTen = new Classes("10A4", 36);
        classesList.add(classTen);
    }

    //fake data for Grade 11
    private void initGradeEleven(){
        classesList.clear();
        Classes classEleven = new Classes("11A1",40);
        classesList.add(classEleven);

        classEleven = new Classes("11A2",37);
        classesList.add(classEleven);

        classEleven = new Classes("11A3",39);
        classesList.add(classEleven);


    }

    //fake data for Grade 12
    private void initGradeTwelve(){
        classesList.clear();
        Classes classTwelve = new Classes("12A1",35);
        classesList.add(classTwelve);

        classTwelve = new Classes("12A2", 34);
        classesList.add(classTwelve);
    }


}
