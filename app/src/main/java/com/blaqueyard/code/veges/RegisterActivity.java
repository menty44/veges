package com.blaqueyard.code.veges;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.androidnetworking.AndroidNetworking;

public class RegisterActivity extends AppCompatActivity {

    private Button login, register;

//    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final Context context = getApplicationContext();

        AndroidNetworking.initialize(getApplicationContext());

        String url = "https://soccer.sportmonks.com/api/v2.0/continents?api_token=5dYjNZydXtmnrG4wIklsafJEbF1dzRKlfkNbcRnUwF3GXleIvDVtXJswbhsx";

        register = (Button) findViewById(R.id.reg);

    }


}
