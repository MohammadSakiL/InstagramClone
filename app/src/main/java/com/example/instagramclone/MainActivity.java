package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        btnUserLogin = findViewById(R.id.btnUserLogin);
        btnUserSignup = findViewById(R.id.btnUserSignup);

        btnUserSignup.setOnClickListener(this);
        btnUserLogin.setOnClickListener(this);

        if(ParseUser.getCurrentUser() != null)
        {
            ParseUser.logOut();
        }
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnUserLogin:
                ParseUser.logInInBackground(edtLoginEmail.getText().toString(), edtLoginpassword.getText().toString(), (parseUser, e) -> {

                    if (parseUser != null) {
                        FancyToast.makeText(MainActivity.this, "Login successfully",Toast.LENGTH_LONG,FancyToast.SUCCESS,true).show();

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
}