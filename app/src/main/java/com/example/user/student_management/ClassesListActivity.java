package com.example.user.student_management;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.user.student_management.other.ClassAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassesListActivity extends AppCompatActivity {
    @BindView(R.id.class_recycler_view)
    RecyclerView classRecyclerView;

    private ClassAdapter adapter;

    private List<Classes> gradeTenList;
    private List<Classes> gradeElevenList;
    private List<Classes> gradeTwelveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes_list);

        ButterKnife.bind(this);


        spinnerClass();

    }

    private void spinnerClass(){
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //init 3 grades ArrayList
                gradeTenList = new ArrayList<Classes>();
                gradeElevenList = new ArrayList<Classes>();
                gradeTwelveList = new ArrayList<Classes>();

                //recyclerView
                classRecyclerView.setHasFixedSize(true);
                //If Grade 10 is selected
                if(position == 1){
                    adapter = new ClassAdapter(gradeTenList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    classRecyclerView.setLayoutManager(layoutManager);
                    classRecyclerView.setAdapter(adapter);
                    initGradeTen();
                }
                //If Grade 11 is selected
                else if(position == 2){
                    adapter = new ClassAdapter(gradeElevenList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    classRecyclerView.setLayoutManager(layoutManager);
                    classRecyclerView.setAdapter(adapter);
                    initGradeEleven();
                }
                //If Grade 12 is selected
                else if(position == 3){
                    adapter = new ClassAdapter(gradeTwelveList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    classRecyclerView.setLayoutManager(layoutManager);
                    classRecyclerView.setAdapter(adapter);
                    initGradeTwelve();
                }

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

    //fake data for Grade 10
    private void initGradeTen(){
        Classes classTen = new Classes("10A1",39);
        gradeTenList.add(classTen);

        classTen = new Classes("10A2",37);
        gradeTenList.add(classTen);

        classTen = new Classes("10A3",40);
        gradeTenList.add(classTen);

        classTen = new Classes("10A4", 36);
        gradeTenList.add(classTen);
    }

    //fake data for Grade 11
    private void initGradeEleven(){
        Classes classEleven = new Classes("11A1",40);
        gradeElevenList.add(classEleven);

        classEleven = new Classes("11A2",37);
        gradeElevenList.add(classEleven);

        classEleven = new Classes("11A3",39);
        gradeElevenList.add(classEleven);


    }

    //fake data for Grade 12
    private void initGradeTwelve(){
        Classes classTwelve = new Classes("12A1",35);
        gradeTwelveList.add(classTwelve);

        classTwelve = new Classes("12A2", 34);
        gradeTwelveList.add(classTwelve);
    }


}
