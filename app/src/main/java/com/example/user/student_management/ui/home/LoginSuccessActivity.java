package com.example.user.student_management.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.student_management.R;
import com.example.user.student_management.other.SessionManager;
import com.example.user.student_management.ui.class_list.ClassDetailsActivity;
import com.example.user.student_management.ui.class_list.ClassesListActivity;
import com.example.user.student_management.ui.student_list.StudentsListActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginSuccessActivity extends AppCompatActivity {

    @BindView(R.id.tvUsername) TextView tvUsername;
    @BindView(R.id.btnViewStudentList) Button btnViewStudentList;
    @BindView(R.id.btnViewClassList) Button btnViewClassList;
    @BindView(R.id.tvActions) TextView tvAction;
    @BindView(R.id.tvWelcome) TextView tvWelcome;

    SessionManager session;




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
            String email = user.get(SessionManager.KEY_EMAIL);
            /**and set it into the EditText**/
            tvUsername.setText(email);
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
        startActivity(i);
    }

    @OnClick(R.id.btnViewClassList)
    public void viewClassList(){
        Intent i = new Intent(this,ClassesListActivity.class);
        startActivity(i);
    }
}
