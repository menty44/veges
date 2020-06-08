package com.blaqueyard.code.veges;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blaqueyard.code.veges.model.SpektraToken;
import com.blaqueyard.code.veges.spektrastk.Spektrapayment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import co.spektra.checkout.Spektra;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class DashboardActivity extends AppCompatActivity {

    private Button pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
//            @Override
//            public void onDestinationChanged(@NonNull NavController controller,
//                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
//                if(destination.getId() == R.id.navigation_home) {
//                    toolbar.setVisibility(View.GONE);
//                    bottomNavigationView.setVisibility(View.GONE);
//                } else {
//                    toolbar.setVisibility(View.VISIBLE);
//                    bottomNavigationView.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        pay = (Button) findViewById(R.id.pay);

        pay.setOnClickListener(new View.OnClickListener() {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_LONG;
            @Override
            public void onClick(View v) {

//                String publickey = "a9fa137cb5a54e56868c57a126364f2e";
//                String secretkey = "1582823216f3476a893b52dea6e18791ab4ac97e727741f1a1de42a88773c69c";
//                String encodedString = Base64.getEncoder().encodeToString((publickey+":"+secretkey).getBytes());

//                try {
//                    HttpResponse<JsonNode> jsonResponse = Unirest.post("https://api-test.spektra.co/oauth/token?grant_type=client_credentials").
//                            header("accept", "application/json").
//                            header("Content-Type", "application/json").
//                            header("Authorization", "Basic "+encodedString).
//                            asJson();
//                    System.out.println(jsonResponse.getBody().toString());
//
//                    Toast toast = Toast.makeText(context, "Please Pay " + jsonResponse.getBody().toString(), duration);
//                    toast.show();
//
//                    Unirest.shutdown();
//                } catch (UnirestException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                //stage
                //String url = "https://api-test.spektra.co/oauth/token?grant_type=client_credentials";

                //live
                String url = "https://api.spektra.co/oauth/token?grant_type=client_credentials";

                Context context = getApplicationContext();

                RequestQueue queue = Volley.newRequestQueue(context);

                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                int duration = Toast.LENGTH_LONG;

                                Gson gson = new Gson();
                                SpektraToken spektraToken = gson.fromJson(response, SpektraToken.class);

                                try {

                                   pay(spektraToken.getAccess_token());

                                     } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Log.d("Error.Response", error.getMessage());
                                Log.d("Error ", error.toString());
                                Log.d("Error 2", error.networkResponse.toString());

                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError
                    {
                        //live
                        String publickey = "a9fa137cb5a54e56868c57a126364f2e";
                        String secretkey = "1582823216f3476a893b52dea6e18791ab4ac97e727741f1a1de42a88773c69c";

                        //staging
//                        String publickey = "73898047f25345a88eb79672fd7a8b35";
//                        String secretkey = "40d5f89dd2f14b6fbe88f2c383e46b6e4206c05bd7394c96a59da34adf8e8383";

                        String encodedString = Base64.getEncoder().encodeToString((publickey+":"+secretkey).getBytes());

                        Log.d("basic ", encodedString);
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("Content-Type", "application/json");
//                        params.put("Authorization", "Basic "+encodedString);
                        params.put("Authorization", "Basic "+encodedString);

                        return params;
                    }
                };
                //queue.add(postRequest);
                // Add the request to the queue
                Volley.newRequestQueue(context).add(postRequest);
            }
        });
    }

    public String pay(final String bearer) throws JSONException {

        //stage
        //String url = "https://api-test.spektra.co/api/v1/checkout/initiate";

        //live
        String url = "https://api.spektra.co/api/v1/payments/pay-in";


//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        JSONObject jsonBody = new JSONObject();

        jsonBody.put("amount","1");
        jsonBody.put("currency", "KES");
        jsonBody.put("account","254720106420");
//        jsonBody.put("successURL","http://t.com");
//        jsonBody.put("cancelURL","http://t.com");


//request a json object response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Gson gson = new Gson();
                SpektraToken spektraToken = gson.fromJson(response.toString(), SpektraToken.class);

                if (spektraToken.getStatus().equals("00")) {
                    //now handle the response
                    Toast.makeText(DashboardActivity.this, spektraToken.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("myError", error.toString());
                //handle the error
                Toast.makeText(DashboardActivity.this, "An error occurred", Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {

                Map<String, String>  headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer "+bearer);

                return headers;
            }
        };

// Add the request to the queue
        Volley.newRequestQueue(this).add(jsonRequest);
        return "paid";
    }

}
