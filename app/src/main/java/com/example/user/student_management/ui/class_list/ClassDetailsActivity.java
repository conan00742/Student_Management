package com.example.user.student_management.ui.class_list;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.student_management.R;
import com.example.user.student_management.RecyclerViewClickListener;
import com.example.user.student_management.db.DatabaseHandler;
import com.example.user.student_management.model.Classes;
import com.example.user.student_management.model.Student;
import com.example.user.student_management.ui.marking.MarkingActivity;
import com.example.user.student_management.ui.marking.ViewMarkActivity;
import com.example.user.student_management.ui.student_list.StudentsListActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.user.student_management.ui.class_list.ClassDetailsAdapter.HEADER;
import static com.example.user.student_management.ui.class_list.ClassDetailsAdapter.INPUTROW;

public class ClassDetailsActivity extends AppCompatActivity {

    public final static String CLASS_NAME_TAG = "className";
    public final static String CLASS_QUANTITY_TAG = "classQuantity";
    public final static int RREQUEST_CODE_ADD_STUDENT = 1;
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
    DatabaseHandler db;
    int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);
        ButterKnife.bind(this);


        if(getIntent().getExtras() != null){
            currentClass.set_name(getIntent().getStringExtra("className"));
            currentClass.set_quantity(Integer.parseInt(getIntent().getStringExtra("classQuantity")));
        }
        initView();


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
                intent.putExtra(CLASS_NAME_TAG,getIntent().getStringExtra("className"));
                intent.putExtra(CLASS_QUANTITY_TAG, getIntent().getStringExtra("classQuantity"));
                startActivityForResult(intent, RREQUEST_CODE_ADD_STUDENT);
                break;
            case R.id.mnViewMark:
                Intent intent2 = new Intent(ClassDetailsActivity.this, ViewMarkActivity.class);
                intent2.putExtra("classNameForViewMark", getIntent().getStringExtra("className"));
                intent2.putExtra("classQuantityForViewMark", getIntent().getStringExtra("classQuantity"));
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RREQUEST_CODE_ADD_STUDENT){
            if(resultCode == RESULT_OK && data != null){
                currentClass.set_name(data.getStringExtra(CLASS_NAME_TAG));
                currentClass.set_quantity(Integer.parseInt(data.getStringExtra(CLASS_QUANTITY_TAG)));
                initView();
            }
        }
    }


    private void initView(){
        db = new DatabaseHandler(getApplicationContext());
        studentList = db.getStudentListById(currentClass.get_name());

        class_details_recycler_view.setHasFixedSize(true);
        classDetailsAdapter = new ClassDetailsAdapter(getApplicationContext(),studentList,mDataViewType,currentClass.get_name(),
                currentClass.get_quantity());
        classDetailsAdapter.refreshData(studentList == null ? new ArrayList<Student>() : studentList);
        classDetailsAdapter.notifyDataSetChanged();
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
                i.putExtra("studentIDForMarking",studentList.get(pos).getStudentId());
                i.putExtra("studentNameForMarking",studentList.get(pos).getStudentName());
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
                    case 9:
                        break;
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
                        break;
                    case 2:
                        break;
                    case 3:
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
    public boolean onContextItemSelected(MenuItem item) {

        classDetailsAdapter.setViewClickListener(new RecyclerViewClickListener() {
            @Override
            public void recyclerViewListClicked(int position) {
                pos = position-1;
            }
        });
        switch (item.getItemId()) {
            case R.id.mnMarking:
                initMarkingDialog();
                markingDialog.show();
                break;
            case R.id.mnEdit:
                break;
            case R.id.mnDelete:
                Toast.makeText(getApplicationContext(),"You have clicked Delete + pos = " + pos,Toast.LENGTH_LONG).show();
                break;

        }

        return super.onContextItemSelected(item);
    }




}
