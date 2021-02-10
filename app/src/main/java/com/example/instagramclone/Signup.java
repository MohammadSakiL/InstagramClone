package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Signup extends AppCompatActivity implements View.OnClickListener {
    private EditText edtSignupName,edtSignupEmail,edtSignupPassword;
    private Button btnNewSignup,btnNewLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        edtSignupName = findViewById(R.id.edtSignupName);
        edtSignupEmail = findViewById(R.id.edtSignupEmail);
        edtSignupPassword = findViewById(R.id.edtSignupPassword);
        edtSignupPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(btnNewSignup);
                }
                return false;
            }
        });



        btnNewSignup = findViewById(R.id.btnNewSignup);
        btnNewLogin = findViewById(R.id.btnNewLogin);

        btnNewSignup.setOnClickListener(this);
        btnNewLogin.setOnClickListener(this);
        if(ParseUser.getCurrentUser() != null) {
            ParseUser.getCurrentUser().logOut();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnNewSignup:

                if(edtSignupName.getText().toString().equals("") ||
                   edtSignupEmail.getText().toString().equals("") ||
                   edtSignupPassword.getText().toString().equals("")){
                    FancyToast.makeText(Signup.this," Name,Email,Password is required",Toast.LENGTH_LONG,FancyToast.SUCCESS,true).show();

                }
                ParseUser user = new ParseUser();

                user.setUsername(edtSignupName.getText().toString());
                user.setEmail(edtSignupEmail.getText().toString().trim());
                user.setPassword(edtSignupPassword.getText().toString());
                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Signing up " + edtSignupName.getText().toString());
                progressDialog.show();

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            FancyToast.makeText(Signup.this,user.getUsername() + "sign up successfully",Toast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                        } else {
                            ParseUser.logOut();
                            FancyToast.makeText(Signup.this,e.getMessage(),Toast.LENGTH_LONG,FancyToast.ERROR,true).show();
                        }
                        progressDialog.dismiss();
                    }
                });
                break;
            case R.id.btnNewLogin:
                Intent intent = new Intent(Signup.this,MainActivity.class);
                startActivity(intent);
        }

    }
    public void rootLayoutclicked(View view){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }
}