package com.blaqueyard.code.veges.ui.home;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blaqueyard.code.veges.DashboardActivity;
import com.blaqueyard.code.veges.R;
import com.blaqueyard.code.veges.model.SpektraToken;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Context mContext;

    private Button pay;
    private TextView uname;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pay = (Button) view.findViewById(R.id.payspektra);

        uname = (TextView) view.findViewById(R.id.textView3);

//        SQLiteDatabase mydatabase = openOrCreateDatabase("matatu", MODE_PRIVATE, null);
//        Cursor resultSet;
//        try (SQLiteDatabase mydatabase = SQLiteDatabase.openOrCreateDatabase()) {
//            resultSet = mydatabase.rawQuery("Select * from user", null);
//        }
//
//        resultSet.moveToFirst();

        uname.setText("JJJJJ");

        pay.setOnClickListener(new View.OnClickListener() {
            //Context context = ;
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

//                Context context = mContext.getApplicationContext();

                //RequestQueue queue = Volley.newRequestQueue(mContext);

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
                Volley.newRequestQueue(requireContext()).add(postRequest);
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

        jsonBody.put("amount","5");
        jsonBody.put("currency", "KES");
        jsonBody.put("account","254706398516");
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
                    Toast.makeText(requireContext(), spektraToken.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("myError", error.toString());
                //handle the error
                Toast.makeText(requireContext(), "An error occurred", Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(requireContext()).add(jsonRequest);
        return "paid";
    }
}