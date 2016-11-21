package com.example.user.student_management.ui.class_list;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.student_management.RecyclerViewClickListener;
import com.example.user.student_management.db.DatabaseHandler;
import com.example.user.student_management.model.Classes;
import com.example.user.student_management.OnClassListListener;
import com.example.user.student_management.R;
import com.example.user.student_management.model.Student;
import com.example.user.student_management.ui.student_list.StudentsListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassesListActivity extends AppCompatActivity {
    @BindView(R.id.class_recycler_view)
    RecyclerView classRecyclerView;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    private ClassAdapter adapter;
    private List<String> grades;
    private List<Classes> classList;

    DatabaseHandler db;
    int grade;
    private AlertDialog addClassDialog;
    int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes_list);

        ButterKnife.bind(this);


        Typeface typeFace= Typeface.createFromAsset(getAssets(),"CoolDots.ttf");
        tvTitle.setTypeface(typeFace);

        initAddClassDialog();
        spinnerClass();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.class_list_option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.mnInsertClass){
            if(addClassDialog != null && !addClassDialog.isShowing()){
                addClassDialog.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *
     *This dialog is used for adding new class
     *
     * **/
    private void initAddClassDialog(){


        /**Init Layout inside Dialog**/
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ClassesListActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_add_class, null);
        dialogBuilder.setView(dialogView);

        /**find view by ID**/
        final EditText edtClassName = (EditText) dialogView.findViewById(R.id.edtClassName);
        final EditText edtClassQuantity = (EditText) dialogView.findViewById(R.id.edtClassQuantity);
        final Spinner addClassSpinner = (Spinner) dialogView.findViewById(R.id.addClassSpinner);
        final Button btnAddClass = (Button) dialogView.findViewById(R.id.btnAddClass);
        final Button btnAddCancelFromAdding = (Button) dialogView.findViewById(R.id.btnCancelFromAdding);


        /**
         *
         *
         * GRADES SPINNER INSIDE DIALOG
         *
         *
         * **/
        addClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //If Grade 10 is selected
                if(position == 1){
                    grade = Integer.parseInt(addClassSpinner.getSelectedItem().toString().trim());
                }
                //If Grade 11 is selected
                else if(position == 2){
                    grade = Integer.parseInt(addClassSpinner.getSelectedItem().toString().trim());
                }
                //If Grade 12 is selected
                else if(position == 3){
                    grade = Integer.parseInt(addClassSpinner.getSelectedItem().toString().trim());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Spinner Drop down elements
        initGrades();

        // Creating adapter for spinner
        ArrayAdapter<String> gradesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, grades);

        // Drop down layout style - list view with radio button
        gradesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        addClassSpinner.setAdapter(gradesAdapter);



        /**
         *
         * btnAddClass
         *
         **/
        btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**get className**/
                String className = edtClassName.getText().toString().trim();
                /**get classQuantity**/
                int classQuantity = Integer.parseInt(edtClassQuantity.getText().toString().trim());

                Classes _class = new Classes(className, classQuantity, grade);
                if(db != null){
                    db.generateClasses(_class);
                    Toast.makeText(ClassesListActivity.this, "Add successfully", Toast.LENGTH_SHORT).show();

                    /**set dialog edit text to null**/
                    edtClassName.setText(null);
                    edtClassName.requestFocus();
                    edtClassQuantity.setText(null);
                }else{
                    Toast.makeText(ClassesListActivity.this, "Can not add new class", Toast.LENGTH_SHORT).show();
                }
                addClassDialog.dismiss();

            }
        });

        /**
         *
         * btnCancelFromAdding
         *
         **/
        btnAddCancelFromAdding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClassDialog.dismiss();
            }
        });


        /**
         *
         * create and show dialog
         *
         * **/
        addClassDialog = dialogBuilder.create();
    }

    /**
     *
     *
     * Init Recyclerview
     *
     * **/
    private void initView() {
        //recyclerView
        classRecyclerView.setHasFixedSize(true);
        adapter = new ClassAdapter(classList);
        adapter.setClassListListener(new OnClassListListener() {
            @Override
            public void onClassClick(int position) {
                Classes classes = classList.get(position);
                //TODO:
                if(classes != null){
                    Intent intent = new Intent(ClassesListActivity.this, ClassDetailsActivity.class);
                    intent.putExtra("className", classes.get_name());
                    intent.putExtra("classQuantity", ""+classes.get_quantity());
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
     * Spinner Activity show a list of classes by choosing grade
     *
     * **/
    private void spinnerClass(){
        db = new DatabaseHandler(getApplicationContext());
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

                // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //If Grade 10 is selected
                if(position == 0){
                    classList = db.getClassesListByGrade(10);
                    initView();
                }
                //If Grade 11 is selected
                else if(position == 1){
                    classList = db.getClassesListByGrade(11);
                    initView();
                }
                //If Grade 12 is selected
                else if(position == 2){
                    classList = db.getClassesListByGrade(12);
                    initView();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Spinner Drop down elements
        initGrades();

        // Creating adapter for spinner
        ArrayAdapter<String> gradesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, grades);

        // Drop down layout style - list view with radio button
        gradesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(gradesAdapter);
    }




    /**
     *
     * Init grades List
     *
     * **/

    public void initGrades(){
        grades = new ArrayList<String>();
        grades.add("10");
        grades.add("11");
        grades.add("12");
    }


}
