package com.blaqueyard.code.veges;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.blaqueyard.code.veges.internetstatus.Internet;
import com.blaqueyard.code.veges.model.LoginStatus;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.concurrent.ThreadLocalRandom;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button login, register;
    private TextView resetpassword;
    private EditText username, password;
    private Context mContext;


    //@SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login = (Button) findViewById(R.id.reg);
        register = (Button) findViewById(R.id.register);
        resetpassword = (TextView) findViewById(R.id.resetPass);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.editText3);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {

                    //live
                    String url = "https://menty44-prod.apigee.net/saclogin";

                    JSONObject jsonBody = new JSONObject();

                    jsonBody.put("password", password.getText().toString());
                    jsonBody.put("phone",username.getText().toString());


                    //request a json object response
                    JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Gson gson = new Gson();

                            LoginStatus status = gson.fromJson(response.toString(), LoginStatus.class);

                            Log.d("LOGINRES", "onResponse: "+response.toString());
                            int duration = Toast.LENGTH_SHORT;


                            if (status.getFail().equals(false)) {
                                Log.d("STATUS", "onResponse: "+status.getFail());
                                final Context context = getApplicationContext();
                                Toast toast = Toast.makeText(context, "Welcome", duration);
                                toast.show();

                                int id = ThreadLocalRandom.current().nextInt(1, 1 + 1);

                                Log.d("UUID", "id: "+id);
//                                User user = new User();
//                                user.setPhone("254720106420");
//                                user.setUid(id);
//                                db.userDao().insertAll(user);

                                SQLiteDatabase mydatabase = openOrCreateDatabase("matatu", MODE_PRIVATE, null);

                                mydatabase.execSQL("CREATE TABLE IF NOT EXISTS user(phone VARCHAR);");
                                mydatabase.execSQL("INSERT INTO user VALUES("+username.getText()+");");

                                launchDashboardScreen();
                            }else {
                                final Context context = getApplicationContext();
                                Log.d("STATUSERR", "onResponse: "+status.getFail());
                                    Toast toast1 = Toast.makeText(context, "Incorrect credentials", duration);
                                    toast1.show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("myError", error.toString());
                            //handle the error
                            final Context context = getApplicationContext();
                            Toast.makeText(context, "An error occurred", Toast.LENGTH_LONG).show();
                            error.printStackTrace();

                        }
                    });
                    //                {
                    //                    @Override
                    //                    public Map<String, String> getHeaders() throws AuthFailureError
                    //                    {
                    //
                    //                        Map<String, String>  headers = new HashMap<>();
                    //                        headers.put("Content-Type", "application/json");
                    //                        headers.put("Authorization", "Bearer "+bearer);
                    //
                    //                        return headers;
                    //                    }
                    //                };

                    final Context context = getApplicationContext();
                    // Add the request to the queue
                    Volley.newRequestQueue(context).add(jsonRequest);
                }catch (Exception e){
                    e.printStackTrace();
                }

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
