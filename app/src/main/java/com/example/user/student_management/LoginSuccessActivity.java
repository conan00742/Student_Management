package com.example.user.student_management;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginSuccessActivity extends AppCompatActivity {

    @BindView(R.id.edtUsername) EditText edtUsername;
    @BindView(R.id.btnLogout) Button btnLogout;

    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        ButterKnife.bind(this);

        session = new SessionManager(getApplicationContext());

        Toast.makeText(LoginSuccessActivity.this, "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // email
        String email = user.get(SessionManager.KEY_EMAIL);

        /**Get Intent Extra**/
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String userValue = extras.getString(LoginActivity.USERNAME_TAG);
            edtUsername.setText(userValue);
        }
    }

    @OnClick(R.id.btnLogout)
    void attemptLogout(){
        session.logoutUser();

    }
}
