package com.example.user.student_management.ui.marking;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.student_management.OnClassListListener;
import com.example.user.student_management.R;
import com.example.user.student_management.db.DatabaseHandler;
import com.example.user.student_management.model.Classes;
import com.example.user.student_management.model.Student;
import com.example.user.student_management.ui.class_list.ClassAdapter;
import com.example.user.student_management.ui.class_list.ClassDetailsActivity;
import com.example.user.student_management.ui.class_list.ClassDetailsAdapter;
import com.example.user.student_management.ui.class_list.ClassesListActivity;
import com.example.user.student_management.ui.home.LoginSuccessActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewMarkActivity extends AppCompatActivity {
    @BindView(R.id.class_recycler_view)
    RecyclerView classRecyclerView;

    Spinner spnViewMarkSemester;
    Spinner spnViewMarkSubject;
    Spinner spnViewMarkTypeOfMark;

    Button btnForward;
    Button btnMainMenu;

    LinearLayout ll;

    String semester;
    String subject;
    String markType;


    private ClassAdapter adapter;
    private List<String> grades;
    private List<Classes> classList;

    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mark);
        ButterKnife.bind(this);

        initSpinner();
        onClick();
        spinnerClass();
    }


    private void initSpinner(){
        spnViewMarkSemester = (Spinner) findViewById(R.id.spnViewMarkSemester);
        spnViewMarkSubject = (Spinner) findViewById(R.id.spnViewMarkSubject);
        spnViewMarkTypeOfMark = (Spinner) findViewById(R.id.spnViewMarkTypeOfMark);


        spnViewMarkSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    Toast.makeText(ViewMarkActivity.this, "1", Toast.LENGTH_SHORT).show();
                    semester = parent.getItemAtPosition(position).toString();
                } else if (position == 2) {
                    Toast.makeText(ViewMarkActivity.this, "2", Toast.LENGTH_SHORT).show();
                    semester = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnViewMarkSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                        Toast.makeText(ViewMarkActivity.this, "Maths", Toast.LENGTH_SHORT).show();
                    case 2:
                        Toast.makeText(ViewMarkActivity.this, "Physics", Toast.LENGTH_SHORT).show();
                    case 3:
                        Toast.makeText(ViewMarkActivity.this, "Chemistry", Toast.LENGTH_SHORT).show();
                    case 4:
                        Toast.makeText(ViewMarkActivity.this, "Biology", Toast.LENGTH_SHORT).show();
                    case 5:
                        Toast.makeText(ViewMarkActivity.this, "History", Toast.LENGTH_SHORT).show();
                    case 6:
                        Toast.makeText(ViewMarkActivity.this, "Geography", Toast.LENGTH_SHORT).show();
                    case 7:
                        Toast.makeText(ViewMarkActivity.this, "Literature", Toast.LENGTH_SHORT).show();
                    case 8:
                        Toast.makeText(ViewMarkActivity.this, "English", Toast.LENGTH_SHORT).show();
                    case 9:
                        Toast.makeText(ViewMarkActivity.this, "Astronomy", Toast.LENGTH_SHORT).show();
                }
                subject = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnViewMarkTypeOfMark.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                        Toast.makeText(ViewMarkActivity.this, "15 minutes", Toast.LENGTH_SHORT).show();
                    case 2:
                        Toast.makeText(ViewMarkActivity.this, "45 minutes", Toast.LENGTH_SHORT).show();
                    case 3:
                        Toast.makeText(ViewMarkActivity.this, "Summary mark", Toast.LENGTH_SHORT).show();
                }
                markType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /** Spinner Dropdown for semester elements**/
        List<String> semesters = new ArrayList<String>();
        semesters.add("---Semester---");
        semesters.add("1");
        semesters.add("2");

        /** Spinner Dropdown for subject elements**/
        List<String> subjects = new ArrayList<>();
        subjects.add("---Subjects---");
        subjects.add("Maths"); //Toán
        subjects.add("Physics"); //Lý
        subjects.add("Chemistry"); //Hóa
        subjects.add("Biology"); //Sinh
        subjects.add("History"); //Sử
        subjects.add("Geography"); //Địa
        subjects.add("Literature"); //Văn
        subjects.add("English"); //Anh
        subjects.add("Astronomy"); //Thiên Văn Học

        /** Spinner Dropdown for type of mark elements**/
        List<String> markTypes = new ArrayList<String>();
        markTypes.add("---Type of Marks---");
        markTypes.add("15 minutes");
        markTypes.add("45 minutes");
        markTypes.add("Summary Mark");


        /** Creating adapter for SEMESTER spinner **/
        ArrayAdapter<String> semestersAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                semesters);

        /** Creating adapter for SUBJECT spinner **/
        ArrayAdapter<String> subjectsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                subjects);

        /** Creating adapter for TYPE OF MARK spinner **/
        ArrayAdapter<String> markTypesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                markTypes);





        /**Drop down layout style - list view with radio button**/
        semestersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        markTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);




        /**attaching data adapter to spinner**/
        spnViewMarkSemester.setAdapter(semestersAdapter);
        spnViewMarkSubject.setAdapter(subjectsAdapter);
        spnViewMarkTypeOfMark.setAdapter(markTypesAdapter);
    }

    public void onClick(){
        ll = (LinearLayout) findViewById(R.id.classList);
        btnForward = (Button) findViewById(R.id.btnForward);
        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll.setVisibility(View.VISIBLE);
            }
        });


        btnMainMenu = (Button) findViewById(R.id.btnMainMenu);
        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewMarkActivity.this, LoginSuccessActivity.class);
                startActivity(intent);
            }
        });
    }


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
                    Intent intent = new Intent(ViewMarkActivity.this, ClassDetailsActivity.class);
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
                if(position == 1){
                    classList = db.getClassesListByGrade(10);
                    initView();
                }
                //If Grade 11 is selected
                else if(position == 2){
                    classList = db.getClassesListByGrade(11);
                    initView();
                }
                //If Grade 12 is selected
                else if(position == 3){
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
        grades.add("---Grade---");
        grades.add("10");
        grades.add("11");
        grades.add("12");
    }


}
