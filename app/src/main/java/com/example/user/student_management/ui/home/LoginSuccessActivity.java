package com.example.user.student_management.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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

import com.example.user.student_management.R;
import com.example.user.student_management.db.DatabaseHandler;
import com.example.user.student_management.model.Account;
import com.example.user.student_management.other.SessionManager;
import com.example.user.student_management.ui.class_list.ClassDetailsActivity;
import com.example.user.student_management.ui.class_list.ClassesListActivity;
import com.example.user.student_management.ui.student_list.StudentsListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginSuccessActivity extends AppCompatActivity {

    @BindView(R.id.tvUsername) TextView tvUsername;
    @BindView(R.id.btnViewStudentList) Button btnViewStudentList;
    @BindView(R.id.btnViewClassList) Button btnViewClassList;
    @BindView(R.id.tvActions) TextView tvAction;
    @BindView(R.id.tvWelcome) TextView tvWelcome;
    @BindView(R.id.btnAddAccount) Button btnAddAccount;

    SessionManager session;
    private AlertDialog addAccountDialog;
    String email, selectedSubject, selectedRole, selectedItem;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.mnLogout:
                attemptLogout();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        ButterKnife.bind(this);
        Typeface typeFace= Typeface.createFromAsset(getAssets(),"CoolDots.ttf");
        tvWelcome.setTypeface(typeFace);
        tvAction.setTypeface(typeFace);


        session = new SessionManager(getApplicationContext());

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        if(user != null && user.containsKey(SessionManager.KEY_EMAIL)){
            /**get Email from SessionManager**/
            email = user.get(SessionManager.KEY_EMAIL);
            String role = db.getRoleByEmail(email);
            if(email.equals("admin@gmail.com") || role.equals("Administrator")){
                btnAddAccount.setVisibility(View.VISIBLE);
            }
            String username = email.split("@")[0];
            /**and set it into the EditText**/
            tvUsername.setText(username);
        }


    }


    void attemptLogout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginSuccessActivity.this);

        /**set title**/
        builder.setTitle("Log out");

        /**set message**/
        builder.setMessage("Are you sure you want to log out?");

        /**set icon**/
        builder.setIcon(R.drawable.logout_mdpi);

        /**set positive button**/
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                session.logoutUser();
            }
        });

        /**set negative button**/
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    @OnClick(R.id.btnViewStudentList)
    public void viewStudentList(){
        Intent i = new Intent(this,StudentsListActivity.class);
        i.putExtra(StudentsListActivity.EXTRA_IS_ADD_MODE, false);
        i.putExtra("email", email);
        startActivity(i);
    }

    @OnClick(R.id.btnViewClassList)
    public void viewClassList(){
        Intent i = new Intent(this,ClassesListActivity.class);
        i.putExtra("email", email);
        startActivity(i);
    }

    @OnClick(R.id.btnAddAccount)
    public void addAccount(){
        /**Init Layout inside Dialog**/
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginSuccessActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_add_account, null);
        dialogBuilder.setView(dialogView);

        addAccountDialog = dialogBuilder.create();

        final EditText edtRegisteredEmail = (EditText) dialogView.findViewById(R.id.edtRegisteredEmail);
        final EditText edtPassword = (EditText) dialogView.findViewById(R.id.edtPassword);
        final EditText edtReEnterPassword = (EditText) dialogView.findViewById(R.id.edtReEnterPassword);
        //Init Subject Spinner
        final Spinner subjectSpinner = (Spinner) dialogView.findViewById(R.id.subjectSpinner);
        //Init Role Spinner
        final Spinner roleSpinner = (Spinner) dialogView.findViewById(R.id.roleSpinner);

        //subject spinner
        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    case 9:
                        break;
                }
                selectedSubject = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
        subjects.add("None");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subjects);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        subjectSpinner.setAdapter(dataAdapter);


        //role spinner
        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = roleSpinner.getSelectedItem().toString();
                if(selectedItem.equals("Manager")){
                    subjectSpinner.setEnabled(false);
                    subjectSpinner.setClickable(false);
                }else if(selectedItem.equals("Administrator")){
                    subjectSpinner.setEnabled(false);
                    subjectSpinner.setClickable(false);
                }
                else{
                    subjectSpinner.setEnabled(true);
                    subjectSpinner.setClickable(true);
                }
                switch (position){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
                selectedRole = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<String> roles = new ArrayList<>();
        roles.add("Manager"); //Chủ nhiệm khối
        roles.add("Head Teacher"); //Giáo viên chủ nhiệm
        roles.add("Classroom Teacher"); //Giáo viên bộ môn
        roles.add("Administrator");


        // Creating adapter for spinner
        ArrayAdapter<String> roleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roles);

        // Drop down layout style - list view with radio button
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        roleSpinner.setAdapter(roleAdapter);


        //Add account button
        final Button btnAddAccount = (Button) dialogView.findViewById(R.id.btnAddAccount);
        btnAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String registeredEmail = edtRegisteredEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String reEnterPassword = edtReEnterPassword.getText().toString().trim();

                if(!TextUtils.isEmpty(registeredEmail) && !TextUtils.isEmpty(password)
                        && !TextUtils.isEmpty(reEnterPassword)){
                    if(isValidEmail(registeredEmail)){
                        if(reEnterPassword.equals(password)){
                            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                            if(db != null){
                                if(db.getAccountCount(registeredEmail) == 0){
                                    Account account = new Account(registeredEmail,password,selectedSubject,selectedRole);
                                    if(db.addNewAccount(account) != 0){
                                        Toast.makeText(LoginSuccessActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                        addAccountDialog.dismiss();
                                    }else{
                                        Toast.makeText(LoginSuccessActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else{
                                Toast.makeText(LoginSuccessActivity.this, "Can not add", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(LoginSuccessActivity.this, "Re-enter password is wrong", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(LoginSuccessActivity.this, "Wrong email syntax", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginSuccessActivity.this, "Can not leave blank", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button btnCancelAddAccount = (Button) dialogView.findViewById(R.id.btnCancelAddAccount);
        btnCancelAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAccountDialog.dismiss();
            }
        });

        addAccountDialog.show();

    }

    public boolean isValidEmail(CharSequence target){
        return target != null && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }



}
