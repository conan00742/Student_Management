package com.example.user.student_management.ui.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.student_management.ui.home.LoginSuccessActivity;
import com.example.user.student_management.R;
import com.example.user.student_management.other.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edittext_username)
    EditText edittext_username;

    @BindView(R.id.edittext_password)
    EditText edittext_password;

    @BindView(R.id.button_login)
    Button button_login;

    /**Intent extra TAG**/
    final static String USERNAME_TAG = "user";


    SessionManager session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());
        if(session.isLoggedIn()){
            Intent loginIntent = new Intent(this, LoginSuccessActivity.class);
            startActivity(loginIntent);

            //close log in activity
            finish();
        }

        ButterKnife.bind(this);

        /**Check User Login Status**/
        /*Toast.makeText(LoginActivity.this, "User Login Status: "+session.isLoggedIn(), Toast.LENGTH_LONG).show();*/

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
     * LOGIN
     *
     * **/
    @OnClick(R.id.button_login)
    void attemptLogin(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);

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
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Can not leave blank");
            alertDialog.setIcon(R.drawable.delete_mdpi);
            alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }

        /**If PASSWORD blank**/
        else if(!TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Can not leave PASSWORD blank");
            alertDialog.setIcon(R.drawable.delete_mdpi);
            alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }

        /**If EMAIL blank**/
        else if(TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Can not leave EMAIL blank");
            alertDialog.setIcon(R.drawable.delete_mdpi);
            alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
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
            else if(email.equals("android@gmail.com")  && password.equals("123456")){

                session.createLoginSession(email);

                /**Login Success ---> LoginSuccessActivity**/
                Intent i = new Intent(getApplicationContext(),LoginSuccessActivity.class);
                startActivity(i);
                finish();
            }

            /**Wrong Account**/
            else{
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Username or Password is incorrect");
                alertDialog.setIcon(R.drawable.delete_mdpi);
                alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
            }
        }

        alertDialog.show();

    }

}
