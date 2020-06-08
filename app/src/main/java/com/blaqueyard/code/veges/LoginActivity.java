package com.blaqueyard.code.veges;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blaqueyard.code.veges.internetstatus.Internet;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button login, register;
    private TextView resetpassword;
    private EditText username, password;

//    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.button);
        register = (Button) findViewById(R.id.register);
        resetpassword = (TextView) findViewById(R.id.resetPass);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.editText3);

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
        startActivity(new Intent(this, DashboardActivity.class));
//        finish();
    }

    private void launchRegisterScreen() {
        Log.v("username :: ", username.getText().toString());

        Context context = getApplicationContext();

        boolean internet = new Internet().checkConnection(context);


        if (internet) {
            // Its Available...
            Log.d("net available ", (String.valueOf(internet)));


            CharSequence text = String.valueOf(internet);
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            CharSequence text2 = username.getText().toString() + " " + password.getText().toString();
            int duration2 = Toast.LENGTH_LONG;

            Toast toast2 = Toast.makeText(context, text2, duration2);
            toast2.show();

            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

        } else {
            // Not Available...
            Log.d("net not available ",  (String.valueOf(internet)));
            CharSequence text = String.valueOf(internet);
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }


//        finish();
    }

    private void launchResetPasswordScreen() {
        startActivity(new Intent(this, ResetpassActivity.class));
//        finish();
    }

    @Override
    public void onClick(View view) {

    }
}
