package com.example.user.student_management;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginSuccessActivity extends AppCompatActivity {

    @BindView(R.id.tvUsername) TextView tvUsername;
    @BindView(R.id.btnLogout) Button btnLogout;

    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        ButterKnife.bind(this);

        session = new SessionManager(getApplicationContext());

        /*Toast.makeText(LoginSuccessActivity.this, "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();*/

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

    @OnClick(R.id.btnLogout)
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
}
