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
import android.widget.Toast;

import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtLoginEmail,edtLoginpassword;
    private Button btnUserLogin,btnUserSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtLoginEmail = findViewById(R.id.edtLoginEmail);
        edtLoginpassword = findViewById(R.id.edtLoginPassword);

        edtLoginpassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(btnUserLogin);
                }
                return false;
            }
        });

        btnUserLogin = findViewById(R.id.btnUserLogin);
        btnUserSignup = findViewById(R.id.btnUserSignup);

        btnUserSignup.setOnClickListener(this);
        btnUserLogin.setOnClickListener(this);

        if(ParseUser.getCurrentUser() != null)
        {
            transitionToHome();
        }
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnUserLogin:
                ParseUser.logInInBackground(edtLoginEmail.getText().toString(), edtLoginpassword.getText().toString(), (parseUser, e) -> {


                    if (parseUser != null) {
                        FancyToast.makeText(MainActivity.this, "Login successfully",Toast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                        transitionToHome();

                    } else {
                        ParseUser.logOut();
                        FancyToast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    }

                });
                break;
            case R.id.btnUserSignup:
                Intent intent = new Intent(MainActivity.this,Signup.class);
                startActivity(intent);
                break;
        }

    }

    public void rootLoginLayoutClicked(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void transitionToHome(){
        Intent intent = new Intent(MainActivity.this,Home.class);
        startActivity(intent);
    }
}