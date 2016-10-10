package com.example.user.student_management;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;

public class LoginSuccessActivity extends AppCompatActivity {

    @BindView(R.id.edtUsername) EditText edtUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);


        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String userValue = extras.getString(LoginActivity.USERNAME_TAG);
            edtUsername.setText(userValue);
        }
    }
}
