package com.example.user.student_management.ui.class_list;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.student_management.MarkingClickListener;
import com.example.user.student_management.R;
import com.example.user.student_management.RecyclerViewClickListener;
import com.example.user.student_management.db.DatabaseHandler;
import com.example.user.student_management.model.Classes;
import com.example.user.student_management.model.Marking;
import com.example.user.student_management.model.Student;
import com.example.user.student_management.model.Subject;
import com.example.user.student_management.ui.marking.ViewMarkActivity;
import com.example.user.student_management.ui.student_list.StudentDetailsActivity;
import com.example.user.student_management.ui.student_list.StudentsListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.user.student_management.ui.class_list.StudentInClassAdapter.HEADER;
import static com.example.user.student_management.ui.class_list.StudentInClassAdapter.INPUTROW;

public class ClassDetailsActivity extends AppCompatActivity implements RecyclerViewClickListener, MarkingClickListener{

    public final static String CLASS_NAME_TAG = "className";
    public final static String CLASS_QUANTITY_TAG = "classQuantity";
    public final static int RREQUEST_CODE_ADD_STUDENT = 1;
    @BindView(R.id.class_details_recycler_view)
    RecyclerView class_details_recycler_view;

    Button btnOk;
    Button btnMarkingCancel;

    Button btnViewMark;
    Button btnCancelViewMark;

    String semester;
    String subject;
    String markType;

    Spinner spinnerSemester,spnViewMarkSemester;
    Spinner spinnerSubject,spnViewMarkSubject;
    Spinner spinnerTypeOfMark,spnViewMarkTypeOfMark;

    private AlertDialog markingDialog;
    StudentInClassAdapter studentInClassAdapter;
    List<Student> studentList = new ArrayList<>();
    int[] mDataViewType = {HEADER,INPUTROW};
    Classes currentClass = new Classes();
    DatabaseHandler db;
    int pos;
    Student _student = new Student();
    Subject _subject = new Subject();
    Marking marking = new Marking();



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

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.mnSearchStudentInClass).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ClassDetailsActivity.this.studentInClassAdapter.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ClassDetailsActivity.this.studentInClassAdapter.getFilter().filter(newText);

                return false;
            }
        });

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.mnAdd:
                db = new DatabaseHandler(getApplicationContext());
                String sentEmail = getIntent().getStringExtra("email");
                String role = db.getRoleByEmail(sentEmail);

                if(role.equals("Manager") || role.equals("Administrator")){
                    Intent intent = new Intent(ClassDetailsActivity.this, StudentsListActivity.class);
                    intent.putExtra(CLASS_NAME_TAG,getIntent().getStringExtra("className"));
                    intent.putExtra(CLASS_QUANTITY_TAG, getIntent().getStringExtra("classQuantity"));
                    intent.putExtra(StudentsListActivity.EXTRA_IS_ADD_MODE, true);
                    startActivityForResult(intent, RREQUEST_CODE_ADD_STUDENT);
                }else{
                    warningDialog("You are not allowed to proceed this action");
                }
                break;
            case R.id.mnViewMark:
               initViewMarkDialog();
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
        studentInClassAdapter = new StudentInClassAdapter(getApplicationContext(),studentList,mDataViewType,currentClass.get_name(),
                currentClass.get_quantity());
        studentInClassAdapter.setViewClickListener(this);
        studentInClassAdapter.setMarkingClickListener(this);
        studentInClassAdapter.refreshData(studentList == null ? new ArrayList<Student>() : studentList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        class_details_recycler_view.setLayoutManager(layoutManager);
        class_details_recycler_view.setAdapter(studentInClassAdapter);
    }


    //view mark dialog
    private void initViewMarkDialog(){
        /**Init Layout inside Dialog**/
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ClassDetailsActivity.this);
        final LayoutInflater inflater = ClassDetailsActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_mark_selection, null);
        dialogBuilder.setView(dialogView);

        initViewMarkSpinner(dialogView);

        btnViewMark = (Button) dialogView.findViewById(R.id.btnViewMark);
        btnCancelViewMark = (Button) dialogView.findViewById(R.id.btnCancelViewMark);

        btnViewMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassDetailsActivity.this,ViewMarkActivity.class);
                if(markType.equals("Semester Summary Mark")){
                    intent.putExtra(ViewMarkActivity.SEMESTER_TAG, semester);
                    intent.putExtra(ViewMarkActivity.MARK_TYPE_TAG, markType);
                    intent.putExtra(ViewMarkActivity.CLASS_NAME_TAG, getIntent().getStringExtra("className"));
                    intent.putExtra(ViewMarkActivity.CLASS_QUANTITY_TAG, getIntent().getStringExtra("classQuantity"));
                }else if(markType.equals("Final Mark")){
                    intent.putExtra(ViewMarkActivity.MARK_TYPE_TAG, markType);
                    intent.putExtra(ViewMarkActivity.CLASS_NAME_TAG, getIntent().getStringExtra("className"));
                    intent.putExtra(ViewMarkActivity.CLASS_QUANTITY_TAG, getIntent().getStringExtra("classQuantity"));
                }else{
                    intent.putExtra(ViewMarkActivity.SEMESTER_TAG, semester);
                    intent.putExtra(ViewMarkActivity.SUBJECT_TAG, subject);
                    intent.putExtra(ViewMarkActivity.MARK_TYPE_TAG, markType);
                    intent.putExtra(ViewMarkActivity.CLASS_NAME_TAG, getIntent().getStringExtra("className"));
                    intent.putExtra(ViewMarkActivity.CLASS_QUANTITY_TAG, getIntent().getStringExtra("classQuantity"));
                }


                startActivity(intent);
                markingDialog.dismiss();
            }
        });


        btnCancelViewMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markingDialog.dismiss();
            }
        });


        markingDialog = dialogBuilder.create();
        markingDialog.show();
    }

    //spinner for View Mark
    private void initViewMarkSpinner(View dialogView){
        spnViewMarkSemester = (Spinner) dialogView.findViewById(R.id.spnViewMarkSemester);
        spnViewMarkSubject = (Spinner) dialogView.findViewById(R.id.spnViewMarkSubject);
        spnViewMarkTypeOfMark = (Spinner) dialogView.findViewById(R.id.spnViewMarkTypeOfMark);


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
                String selectedItem = spnViewMarkTypeOfMark.getSelectedItem().toString();
                if(selectedItem.equals("Semester Summary Mark")){
                    spnViewMarkSubject.setEnabled(false);
                    spnViewMarkSubject.setClickable(false);
                    spnViewMarkSemester.setEnabled(true);
                    spnViewMarkSemester.setClickable(true);
                }else if(selectedItem.equals("Final Mark")){
                    spnViewMarkSubject.setEnabled(false);
                    spnViewMarkSubject.setClickable(false);
                    spnViewMarkSemester.setEnabled(false);
                    spnViewMarkSemester.setClickable(false);
                }else{
                    spnViewMarkSubject.setEnabled(true);
                    spnViewMarkSemester.setEnabled(true);
                }
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
        markTypes.add("Final Exam");
        markTypes.add("Subject Summary Mark");
        markTypes.add("Semester Summary Mark");
        markTypes.add("Final Mark");


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

    //spinner for Marking
    private void initSpinner(View dialogView){
        spinnerSemester = (Spinner) dialogView.findViewById(R.id.spinnerSemester);
        spinnerSubject = (Spinner) dialogView.findViewById(R.id.spinnerSubject);
        spinnerTypeOfMark = (Spinner) dialogView.findViewById(R.id.spinnerTypeOfMark);


        spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        spinnerTypeOfMark.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        markTypes.add("Final Exam");


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

    //spinner for calculate summary mark
    private void initSubjectSummaryMarkSpinner(View dialogView){
        spnViewMarkSemester = (Spinner) dialogView.findViewById(R.id.spnViewMarkSemester);
        spnViewMarkSubject = (Spinner) dialogView.findViewById(R.id.spnViewMarkSubject);
        spnViewMarkTypeOfMark = (Spinner) dialogView.findViewById(R.id.spnViewMarkTypeOfMark);


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
                String selectedItem = spnViewMarkTypeOfMark.getSelectedItem().toString();
                if(selectedItem.equals("Semester Summary Mark")){
                    spnViewMarkSemester.setEnabled(true);
                    spnViewMarkSubject.setEnabled(false);
                }else if(selectedItem.equals("Final Mark")){
                    spnViewMarkSemester.setEnabled(false);
                    spnViewMarkSubject.setEnabled(false);
                }else{
                    spnViewMarkSemester.setEnabled(true);
                    spnViewMarkSubject.setEnabled(true);
                }
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
        markTypes.add("Subject Summary Mark");
        markTypes.add("Semester Summary Mark");
        markTypes.add("Final Mark");


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

    @Override
    public void recyclerViewListLongClick(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ClassDetailsActivity.this);
        final Student _student = studentInClassAdapter.getItemAtPosition(position - 1);
        //set Title
        builder.setTitle("Delete");

        //set Message
        builder.setMessage("Are you sure you want to delete this student?");

        //set Icon
        builder.setIcon(R.drawable.trash_bin);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String role = db.getRoleByEmail(getIntent().getStringExtra("email"));
                if(role.equals("Manager") || role.equals("Administrator") || role.equals("Head Teacher")){
                    if(_student != null){
                        if(db.deleteStudentFromClass(_student) != -1){
                            db.deleteStudentFromScoreRecord(_student);
                            studentList.remove(_student);
                            studentInClassAdapter.refreshData(studentList);
                        }
                    }else {
                        Toast.makeText(ClassDetailsActivity.this, "Can not delete", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ClassDetailsActivity.this, "You are not allowed to do this", Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();

    }

    @Override
    public void recyclerViewListClick(int position) {
        Intent intent = new Intent(ClassDetailsActivity.this, StudentDetailsActivity.class);
        Student _student = studentInClassAdapter.getItemAtPosition(position);
        intent.putExtra("_studentID",_student.getStudentId());
        intent.putExtra("_studentName", _student.getStudentName());
        intent.putExtra("_studentYearOfBirth", _student.getDateOfBirth());
        intent.putExtra("_studentAddress", _student.getStudentAddress());
        intent.putExtra("_studentEmail", _student.getEmail());
        intent.putExtra("_studentGender" , String.valueOf(_student.isMale()));
        intent.putExtra("_studentClass", currentClass.get_name());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void recyclerViewButtonClickListener(final int position) {
       /*initMarkingDialog();*/
        /*Toast.makeText(this, "studentID = "+ studentList.get(position).getStudentId(), Toast.LENGTH_SHORT).show();*/

        /**Init Layout inside Dialog**/
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ClassDetailsActivity.this);
        LayoutInflater inflater = ClassDetailsActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_marking, null);
        dialogBuilder.setView(dialogView);

        initSpinner(dialogView);

        final EditText edtMarkValue = (EditText) dialogView.findViewById(R.id.edtMarkValue);

        btnOk = (Button) dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtMarkValue.getText().toString().trim().equals("")){
                    warningDialog("Enter your mark!!!");
                }else{
                    double mark = Double.parseDouble(edtMarkValue.getText().toString().trim());

                    setObject(position);

                    if(mark > 10){
                        Toast.makeText(ClassDetailsActivity.this, "Mark is wrong!!!", Toast.LENGTH_SHORT).show();
                    }else if(mark <= 10){
                        marking.setMarkValue(mark);
                        db = new DatabaseHandler(getApplicationContext());
                        int count = db.getMarkCount(_student.getStudentId(),_subject.getSubjectSemester(), _subject.getSubjectName(),_subject.getSubjectTypeOfMark());
                        String responsibleSubject = db.getResponsibleSubjectByEmail(getIntent().getStringExtra("email"));
                        String sentEmail = getIntent().getStringExtra("email");
                        String role = db.getRoleByEmail(sentEmail);
                        if(responsibleSubject.equals(_subject.getSubjectName()) || role.equals("Administrator")){
                            if(count == 0){
                                if(db.markingStudent(marking) != 0){
                                    Toast.makeText(ClassDetailsActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                    markingDialog.dismiss();
                                }else {
                                    Toast.makeText(ClassDetailsActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                warningDialog("This student already has mark");

                            }
                        }else{
                            warningDialog("You are not allowed to mark in this subject");
                        }



                    }

                }

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
        markingDialog.show();



    }

    @Override
    public void recyclerViewCalculateButtonClickListener(final int position) {
        /**Init Layout inside Dialog**/
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ClassDetailsActivity.this);
        LayoutInflater inflater = ClassDetailsActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_mark_selection, null);
        dialogBuilder.setView(dialogView);

        initSubjectSummaryMarkSpinner(dialogView);


        btnViewMark = (Button) dialogView.findViewById(R.id.btnViewMark);
        btnViewMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setObject(position);

                db = new DatabaseHandler(getApplicationContext());

                switch(markType){
                    case "Subject Summary Mark":
                        //15 minutes mark
                        double fifteenMinutesMark = db.getFifteenMinutesMark(_student.getStudentId(),_subject.getSubjectSemester(),
                                _subject.getSubjectName());

                        //45 minutes mark
                        double fortyFiveMinutesMark = db.getFortyFiveMinutesMark(_student.getStudentId(), _subject.getSubjectSemester(),
                                _subject.getSubjectName());

                        //final exam mark
                        double finalExamMark = db.getFinalExamMark(_student.getStudentId(), _subject.getSubjectSemester(),
                                _subject.getSubjectName());

                        //subject summary mark
                        double subjectSummaryMark = (fifteenMinutesMark + (fortyFiveMinutesMark * 2) + (finalExamMark * 3))/6;

                        //count subject summary mark
                        int subjectSummaryMarkCount = db.getMarkCount(_student.getStudentId(),_subject.getSubjectSemester(),
                                _subject.getSubjectName(),_subject.getSubjectTypeOfMark());

                        //count 15 minutes mark
                        int fifteenMinutesMarkCount = db.getMarkCount(_student.getStudentId(),_subject.getSubjectSemester(),
                                _subject.getSubjectName(), "15 minutes");

                        //count 45 minutes mark
                        int fortyFiveMinutesMarkCount = db.getMarkCount(_student.getStudentId(),_subject.getSubjectSemester(),
                                _subject.getSubjectName(), "45 minutes");

                        //count final exam mark
                        int finalExamMarkCount = db.getMarkCount(_student.getStudentId(),_subject.getSubjectSemester(),
                                _subject.getSubjectName(), "Final Exam");

                        if(fifteenMinutesMarkCount == 0){
                            warningDialog("This student hasn't had 15 minutes mark yet");
                        }else{
                            if(fortyFiveMinutesMarkCount == 0){
                                warningDialog("This student hasn't had 45 minutes mark yet");
                            }else{
                                if(finalExamMarkCount == 0){
                                   warningDialog("This student hasn't had final exam mark yet");
                                }else{
                                    if(subjectSummaryMarkCount == 0){
                                        marking.setMarkValue(subjectSummaryMark);
                                        if(db.calculateSubjectSummaryMark(marking) != 0){
                                            Toast.makeText(ClassDetailsActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(ClassDetailsActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        warningDialog("This student already has mark");
                                    }
                                }
                            }
                        }
                        break;
                    case "Semester Summary Mark":
                        calculateSemesterSummaryMark(db);
                        break;
                    case "Final Mark":
                        calculateFinalMark(db);
                        break;
                }



                markingDialog.dismiss();

            }
        });

        btnCancelViewMark = (Button) dialogView.findViewById(R.id.btnCancelViewMark);
        btnCancelViewMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markingDialog.dismiss();
            }
        });

        markingDialog = dialogBuilder.create();
        markingDialog.show();
    }




    public void setObject(int position){
        //Student
        _student.setStudentId(studentList.get(position).getStudentId());
        _student.setStudentName(studentList.get(position).getStudentName());
        //Subject
        _subject.setSubjectSemester(Integer.parseInt(semester));
        _subject.setSubjectName(subject);
        _subject.setSubjectTypeOfMark(markType);
        //Classes
        currentClass.set_name(getIntent().getStringExtra("className"));
        currentClass.set_quantity(Integer.parseInt(getIntent().getStringExtra("classQuantity")));

        marking.setStudent(_student);
        marking.set_class(currentClass);
        marking.setSubject(_subject);
    }


    public void warningDialog(String message){
        final AlertDialog.Builder builder = new AlertDialog.Builder(ClassDetailsActivity.this);

        //set Title
        builder.setTitle("Warning");

        //set Message
        builder.setMessage(message);

        //set Icon
        builder.setIcon(R.drawable.warning);



        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    public void calculateSemesterSummaryMark(DatabaseHandler db){
        String studentType = "";
        /**GET**/
        //Maths summary mark
        double mathSummaryMark = db.getSummaryMark(_student.getStudentId(),_subject.getSubjectSemester()
                ,"Maths");
        //Physics summary mark
        double physicSummaryMark = db.getSummaryMark(_student.getStudentId(),_subject.getSubjectSemester()
                ,"Physics");
        //Chemistry summary mark
        double chemistrySummaryMark = db.getSummaryMark(_student.getStudentId(),_subject.getSubjectSemester()
                ,"Chemistry");
        //Biology summary mark
        double biologySummaryMark = db.getSummaryMark(_student.getStudentId(),_subject.getSubjectSemester()
                ,"Biology");
        //History summary mark
        double historySummaryMark = db.getSummaryMark(_student.getStudentId(),_subject.getSubjectSemester()
                ,"History");
        //Geography summary mark
        double geographySummaryMark = db.getSummaryMark(_student.getStudentId(),_subject.getSubjectSemester()
                ,"Geography");
        //Literature summary mark
        double literatureSummaryMark = db.getSummaryMark(_student.getStudentId(),_subject.getSubjectSemester()
                ,"Literature");
        //English summary mark
        double englishSummaryMark = db.getSummaryMark(_student.getStudentId(),_subject.getSubjectSemester()
                ,"English");
        //Astronomy summary mark
        double astronomySummaryMark = db.getSummaryMark(_student.getStudentId(),_subject.getSubjectSemester()
                ,"Astronomy");


        //semester summary mark
        double semesterSummaryMark = (mathSummaryMark + physicSummaryMark + chemistrySummaryMark
                + biologySummaryMark + historySummaryMark + geographySummaryMark + literatureSummaryMark
                + englishSummaryMark + astronomySummaryMark)/9;



        /**COUNT**/
        //Maths summary mark count
        int mathSummaryMarkCount = db.getSubjectSummaryMarkCount(_student.getStudentId(),_subject.getSubjectSemester()
                ,"Maths");
        //Physics summary mark count
        int physicSummaryMarkCount = db.getSubjectSummaryMarkCount(_student.getStudentId(),_subject.getSubjectSemester()
                ,"Physics");
        //Chemistry summary mark count
        int chemistrySummaryMarkCount = db.getSubjectSummaryMarkCount(_student.getStudentId(),_subject.getSubjectSemester()
                ,"Chemistry");

        //Biology summary mark count
        int biologySummaryMarkCount = db.getSubjectSummaryMarkCount(_student.getStudentId(),_subject.getSubjectSemester()
                ,"Biology");
        //History summary mark count
        int historySummaryMarkCount = db.getSubjectSummaryMarkCount(_student.getStudentId(),_subject.getSubjectSemester()
                ,"History");
        //Geography summary mark count
        int geographySummaryMarkCount = db.getSubjectSummaryMarkCount(_student.getStudentId(),_subject.getSubjectSemester()
                ,"Geography");
        //Literature summary mark count
        int literatureSummaryMarkCount = db.getSubjectSummaryMarkCount(_student.getStudentId(),_subject.getSubjectSemester()
                ,"Literature");
        //English summary mark count
        int englishSummaryMarkCount = db.getSubjectSummaryMarkCount(_student.getStudentId(),_subject.getSubjectSemester()
                ,"English");
        //Astronomy summary mark count
        int astronomySummaryMarkCount = db.getSubjectSummaryMarkCount(_student.getStudentId(),_subject.getSubjectSemester()
                ,"Astronomy");


        if(mathSummaryMarkCount == 0){
            warningDialog("This student hasn't had Maths Summary Mark yet!!!");
        }else{
            if(physicSummaryMarkCount == 0){
                warningDialog("This student hasn't had Physics Summary Mark yet!!!");
            }else{
                if(chemistrySummaryMarkCount == 0){
                    warningDialog("This student hasn't had Chemistry Summary Mark yet!!!");
                }else{
                    if(biologySummaryMarkCount == 0){
                        warningDialog("This student hasn't had Biology Summary Mark yet!!!");
                    }else{
                        if(historySummaryMarkCount == 0){
                            warningDialog("This student hasn't had History Summary Mark yet!!!");
                        }else{
                            if(geographySummaryMarkCount == 0){
                                warningDialog("This student hasn't had Geography Summary Mark yet!!!");
                            }else{
                                if(literatureSummaryMarkCount == 0){
                                    warningDialog("This student hasn't had Literature Summary Mark yet!!!");
                                }else{
                                    if(englishSummaryMarkCount == 0){
                                        warningDialog("This student hasn't had English Summary Mark yet!!!");
                                    }else{
                                        if(astronomySummaryMarkCount == 0){
                                            warningDialog("This student hasn't had Astronomy Summary Mark yet!!!");
                                        }else{
                                            if(semesterSummaryMark >= 5 && semesterSummaryMark < 6.5){
                                                studentType = "Average";
                                            }else if(semesterSummaryMark >= 6.5 && semesterSummaryMark < 8){
                                                studentType = "Good";
                                            }else if(semesterSummaryMark >= 8){
                                                if(mathSummaryMark >= 6.5 && physicSummaryMark >= 6.5
                                                        && chemistrySummaryMark >= 6.5 && biologySummaryMark >= 6.5
                                                        && historySummaryMark >= 6.5 && geographySummaryMark >= 6.5
                                                        && literatureSummaryMark >= 6.5 && englishSummaryMark >= 6.5
                                                        && astronomySummaryMark >= 6.5){
                                                    studentType = "Excellent";
                                                }else{
                                                    studentType = "Good";
                                                }
                                            }else{
                                                studentType = "Fail";
                                            }

                                            int count = db.getSummaryMarkCount(_student.getStudentId(),_subject.getSubjectSemester()
                                                    ,_subject.getSubjectTypeOfMark());
                                            String role = db.getRoleByEmail(getIntent().getStringExtra("email"));
                                            if(role.equals("Head Teacher") || role.equals("Administrator")){
                                                if(count == 0){
                                                    //insert into DB
                                                    marking.setMarkValue(semesterSummaryMark);
                                                    marking.setStudentType(studentType);
                                                    if(db.calculateSummaryMark(marking) != 0){
                                                        Toast.makeText(ClassDetailsActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                                    }else{
                                                        Toast.makeText(ClassDetailsActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                                                    }
                                                }else{
                                                    warningDialog("This student already has mark");
                                                }
                                            }else{
                                                warningDialog("You are not allowed to do this");
                                            }

                                        }
                                    }
                                }
                            }



                        }
                    }
                }
            }
        }


    }

    //TODO: implement final mark
    public void calculateFinalMark(DatabaseHandler db){
        String studentType = "";

        /**  GET  **/
        //get semester 1 summary mark values
        double semesterOneSummaryMark = db.getSemesterSummaryMark(_student.getStudentId(),1);
        //get semester 2 summary mark values
        double semesterTwoSummaryMark = db.getSemesterSummaryMark(_student.getStudentId(),2);

        double finalMark = (semesterOneSummaryMark + (semesterTwoSummaryMark * 2))/3;

        /**  COUNT  **/
        //count semester 1 summary mark
        int semesterOneSummaryMarkCount = db.getSummaryMarkCount(_student.getStudentId(),1,"Semester Summary Mark");
        //count semester 2 summary mark
        int semesterTwoSummaryMarkCount = db.getSummaryMarkCount(_student.getStudentId(),2,"Semester Summary Mark");

        if(semesterOneSummaryMarkCount == 0){
            warningDialog("This student hasn't had Semester 1 Summary Mark yet!!!");
        }else{
            if(semesterTwoSummaryMarkCount == 0){
                warningDialog("This student hasn't had Semester 2 Summary Mark yet!!!");
            }else{
                if(finalMark >= 5 && finalMark < 6.5){
                    studentType = "Average";
                }else if(finalMark >= 6.5 && finalMark < 8){
                    studentType = "Good";
                }else if(finalMark >= 8){
                    studentType = "Excellent";
                }else{
                    studentType = "Fail";
                }

                int count = db.getFinalMarkCount(_student.getStudentId());
                String role = db.getRoleByEmail(getIntent().getStringExtra("email"));
                if(role.equals("Head Teacher") || role.equals("Administrator")){
                    if(count == 0){
                        marking.setMarkValue(finalMark);
                        marking.setStudentType(studentType);
                        if(db.calculateFinalMark(marking) != 0){
                            Toast.makeText(ClassDetailsActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ClassDetailsActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        warningDialog("This student already has mark");
                    }
                }else{
                    warningDialog("You are not allowed to do this");
                }

            }
        }
    }


}
