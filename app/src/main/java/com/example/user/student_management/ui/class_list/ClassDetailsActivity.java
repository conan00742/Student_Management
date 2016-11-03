package com.example.user.student_management.ui.class_list;

import android.app.AlertDialog;
import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.student_management.R;
import com.example.user.student_management.model.Classes;
import com.example.user.student_management.model.Student;
import com.example.user.student_management.ui.marking.MarkingActivity;
import com.example.user.student_management.ui.student_list.StudentsListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.user.student_management.ui.class_list.ClassDetailsAdapter.HEADER;
import static com.example.user.student_management.ui.class_list.ClassDetailsAdapter.INPUTROW;

public class ClassDetailsActivity extends AppCompatActivity {
    @BindView(R.id.class_details_recycler_view)
    RecyclerView class_details_recycler_view;


    Button btnOk;
    Button btnMarkingCancel;

    String semester;
    String subject;
    String markType;

    Spinner spinnerSemester;
    Spinner spinnerSubject;
    Spinner spinnerTypeOfMark;

    private AlertDialog markingDialog;
    ClassDetailsAdapter classDetailsAdapter;
    List<Student> studentList = new ArrayList<>();
    int[] mDataViewType = {HEADER,INPUTROW};
    Classes currentClass = new Classes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);
        ButterKnife.bind(this);


        if(getIntent().getExtras() != null){
            currentClass.set_name(getIntent().getStringExtra("className"));
            currentClass.set_quantity(Integer.parseInt(getIntent().getStringExtra("classQuantity")));
        }
        initData();
        initView();
        initMarkingDialog();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.class_details_option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.mnAdd:
                Intent intent = new Intent(ClassDetailsActivity.this, StudentsListActivity.class);
                startActivity(intent);
                break;
            case R.id.mnMarking:
                if(markingDialog != null && !markingDialog.isShowing()){
                    markingDialog.show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initData(){
        Student student = new Student(System.currentTimeMillis(),1994,"Khiêm Ichigo", "Radiant",
                "khiemichigo@gmail.com",true,false);
        studentList.add(student);

        student = new Student(System.currentTimeMillis(),1996,"Ember Spirit","Dire",
                "emberspirit@gmail.com", true,false);
        studentList.add(student);

        student = new Student(System.currentTimeMillis(),1993,"Death Prophet","Radiant",
                "deathprophet@gmail.com",false,false);
        studentList.add(student);
    }

    private void initView(){
        class_details_recycler_view.setHasFixedSize(true);
        classDetailsAdapter = new ClassDetailsAdapter(getApplicationContext(),studentList,mDataViewType,currentClass.get_name(),
                currentClass.get_quantity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        class_details_recycler_view.setLayoutManager(layoutManager);
        class_details_recycler_view.setAdapter(classDetailsAdapter);
    }

    private void initMarkingDialog(){
        /**Init Layout inside Dialog**/
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ClassDetailsActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_marking, null);
        dialogBuilder.setView(dialogView);

        initSpinner(dialogView);

        btnOk = (Button) dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClassDetailsActivity.this, MarkingActivity.class);
                i.putExtra("semester",semester);
                i.putExtra("subject",subject);
                i.putExtra("markType",markType);
                i.putExtra("classNameForMarking",getIntent().getStringExtra("className"));
                i.putExtra("classQuantityForMarking",getIntent().getStringExtra("classQuantity"));
                startActivity(i);
            }
        });

        btnMarkingCancel = (Button) dialogView.findViewById(R.id.btnMarkingCancel);
        btnMarkingCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markingDialog.dismiss();
            }
        });

        markingDialog = dialogBuilder.create();


    }


    private void initSpinner(View dialogView){
        spinnerSemester = (Spinner) dialogView.findViewById(R.id.spinnerSemester);
        spinnerSubject = (Spinner) dialogView.findViewById(R.id.spinnerSubject);
        spinnerTypeOfMark = (Spinner) dialogView.findViewById(R.id.spinnerTypeOfMark);


        spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    Toast.makeText(ClassDetailsActivity.this, "1", Toast.LENGTH_SHORT).show();
                    semester = parent.getItemAtPosition(position).toString();
                } else if (position == 2) {
                    Toast.makeText(ClassDetailsActivity.this, "2", Toast.LENGTH_SHORT).show();
                    semester = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                        Toast.makeText(ClassDetailsActivity.this, "Maths", Toast.LENGTH_SHORT).show();
                    case 2:
                        Toast.makeText(ClassDetailsActivity.this, "Physics", Toast.LENGTH_SHORT).show();
                    case 3:
                        Toast.makeText(ClassDetailsActivity.this, "Chemistry", Toast.LENGTH_SHORT).show();
                    case 4:
                        Toast.makeText(ClassDetailsActivity.this, "Biology", Toast.LENGTH_SHORT).show();
                    case 5:
                        Toast.makeText(ClassDetailsActivity.this, "History", Toast.LENGTH_SHORT).show();
                    case 6:
                        Toast.makeText(ClassDetailsActivity.this, "Geography", Toast.LENGTH_SHORT).show();
                    case 7:
                        Toast.makeText(ClassDetailsActivity.this, "Literature", Toast.LENGTH_SHORT).show();
                    case 8:
                        Toast.makeText(ClassDetailsActivity.this, "English", Toast.LENGTH_SHORT).show();
                    case 9:
                        Toast.makeText(ClassDetailsActivity.this, "Astronomy", Toast.LENGTH_SHORT).show();
                }
                subject = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTypeOfMark.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                        Toast.makeText(ClassDetailsActivity.this, "15 minutes", Toast.LENGTH_SHORT).show();
                    case 2:
                        Toast.makeText(ClassDetailsActivity.this, "45 minutes", Toast.LENGTH_SHORT).show();
                    case 3:
                        Toast.makeText(ClassDetailsActivity.this, "Summary mark", Toast.LENGTH_SHORT).show();
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
        spinnerSemester.setAdapter(semestersAdapter);
        spinnerSubject.setAdapter(subjectsAdapter);
        spinnerTypeOfMark.setAdapter(markTypesAdapter);
    }


    //TODO: MarkingActivity


}
