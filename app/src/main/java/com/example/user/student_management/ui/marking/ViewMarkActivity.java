package com.example.user.student_management.ui.marking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.user.student_management.ui.class_list.ClassAdapter;
import com.example.user.student_management.ui.class_list.ClassesListActivity;
import com.example.user.student_management.ui.home.LoginSuccessActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class ViewMarkActivity extends AppCompatActivity {

    Spinner spnViewMarkSemester;
    Spinner spnViewMarkSubject;
    Spinner spnViewMarkTypeOfMark;

    Button btnForward;
    Button btnMainMenu;


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
        setContentView(R.layout.activity_mark_selection);
        ButterKnife.bind(this);

        initSpinner();
        onClick();
    }


    private void initSpinner(){
        spnViewMarkSemester = (Spinner) findViewById(R.id.spnViewMarkSemester);
        spnViewMarkSubject = (Spinner) findViewById(R.id.spnViewMarkSubject);
        spnViewMarkTypeOfMark = (Spinner) findViewById(R.id.spnViewMarkTypeOfMark);


        spnViewMarkSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    semester = parent.getItemAtPosition(position).toString();
                } else if (position == 1) {
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
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
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
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
                markType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /** Spinner Dropdown for semester elements**/
        List<String> semesters = new ArrayList<String>();
        semesters.add("1");
        semesters.add("2");

        /** Spinner Dropdown for subject elements**/
        List<String> subjects = new ArrayList<>();
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
        btnForward = (Button) findViewById(R.id.btnForward);
        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: ViewMark
                Intent i = new Intent(ViewMarkActivity.this, ViewMarkWindow.class);
                String className = getIntent().getStringExtra("classNameForViewMark");
                String classQuantity = getIntent().getStringExtra("classQuantityForViewMark");
                i.putExtra("classNameForViewMark1", className);
                i.putExtra("classQuantityForViewMark1", classQuantity);
                i.putExtra("semester",semester);
                i.putExtra("subject",subject);
                i.putExtra("typeOfMark",markType);
                startActivity(i);
            }
        });


        btnMainMenu = (Button) findViewById(R.id.btnMainMenu);
        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewMarkActivity.this, ClassesListActivity.class);
                startActivity(intent);
            }
        });
    }





}
