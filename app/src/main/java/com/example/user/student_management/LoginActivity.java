package com.example.user.student_management;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText edittext_username;
    private EditText edittext_password;
    private Button button_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edittext_username = (EditText) findViewById(R.id.edittext_username);
        edittext_password = (EditText) findViewById(R.id.edittext_password);
        button_login = (Button) findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

    }

    /**Validation Email**/
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    /**Validation Password**/
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    /**
     *
     *
     * LOGIN FUNCTION
     *
     * **/
    private void attemptLogin(){

        /** Store values at the time of the login attempt.**/
        String email = edittext_username.getText().toString();
        String password = edittext_password.getText().toString();

        /**
         *
         * CHECK EMAIL AND PASSWORD
         *
         * **/

        /**If email AND password all blank**/
        if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "Can't leave blank", Toast.LENGTH_SHORT).show();
        }

        /**If PASSWORD blank**/
        else if(!TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "Can't leave PASSWORD blank", Toast.LENGTH_SHORT).show();
        }

        /**If EMAIL blank**/
        else if(TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Can't leave EMAIL blank", Toast.LENGTH_SHORT).show();
        }


        /**If EMAIL AND PASSWORD all filled in**/
        else if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

            /**If EMAIL AND PASSWORD validation is FALSE**/
            if(!isEmailValid(email) && !isPasswordValid(password)){
                edittext_username.setError("**Email is invalid");
                edittext_password.setError("**Password must be larger than 4 characters");
            }

            /**If EMAIL validation is FALSE**/
            else if(!isEmailValid(email)){
                edittext_username.setError("**Email is invalid");
            }

            /**If PASSWORD validation is FALSE**/
            else if(!isPasswordValid(password)){
                edittext_password.setError("**Password must be larger than 4 characters");
            }

            /**Correct Account**/
            else if(email.equals("conan00742@gmail.com") && password.equals("123456")){
                Toast.makeText(LoginActivity.this, "Welcome conan00742", Toast.LENGTH_SHORT).show();
            }

            /**Wrong Account**/
            else{
                Toast.makeText(LoginActivity.this, "Username or Password incorrect", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
