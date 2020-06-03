package com.blaqueyard.code.veges;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button login, register;
    private TextView resetpassword;

//    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.button);
        register = (Button) findViewById(R.id.register);
        resetpassword = (TextView) findViewById(R.id.resetPass);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                launchDashboardScreen();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                launchRegisterScreen();
            }
        });

        resetpassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                launchResetPasswordScreen();
            }
        });

    }

    private void launchDashboardScreen() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    private void launchRegisterScreen() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        finish();
    }

    private void launchResetPasswordScreen() {
        startActivity(new Intent(this, ResetpassActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {

    }
}
