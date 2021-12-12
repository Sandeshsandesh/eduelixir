package com.eduelixir.eduelixir;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private String stud_id;
    private static final String URL_INSERT_TOKEN = "http://192.168.43.110/notification/insert_token.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setAnimation();
    }

    private void setAnimation() {
        findViewById(R.id.imagelogo1).setAlpha(1.0F);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate_center_to_top);
        findViewById(R.id.imagelogo1).startAnimation(anim);

        findViewById(R.id.userName).setAlpha(1.0F);
        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.translate_left_to_right);
        findViewById(R.id.userName).startAnimation(anim2);

        findViewById(R.id.password).setAlpha(1.0F);
        Animation anim3 = AnimationUtils.loadAnimation(this, R.anim.translate_right_to_left);
        findViewById(R.id.password).startAnimation(anim3);


        Animation anim4 = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        findViewById(R.id.button).startAnimation(anim4);


    }

    public void showpd(View view) {
        EditText etPassword = (EditText) findViewById(R.id.password);
        etPassword.setTransformationMethod(null);
    }

    public void clicked(View v) {

        final EditText etUsername = (EditText) findViewById(R.id.userName);
        final EditText etPassword = (EditText) findViewById(R.id.password);

        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();

        final ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        stud_id = jsonResponse.getString("stud_id");
                        Log.d("token", "stud_id :" + stud_id);
                        String first_name = jsonResponse.getString("first_name");
                        String last_name = jsonResponse.getString("last_name");
                        String dob = jsonResponse.getString("dob");
                        String guardian_name = jsonResponse.getString("guardian_name");
                        String guardian_occupation = jsonResponse.getString("guardian_occupation");
                        String address = jsonResponse.getString("address");
                        String city = jsonResponse.getString("city");
                        String state = jsonResponse.getString("state");
                        String zipcode = jsonResponse.getString("zipcode");
                        String country = jsonResponse.getString("country");
                        String guardian_landline = jsonResponse.getString("guardian_landline");
                        String guardian_mobile = jsonResponse.getString("guardian_mobile");
                        String doa = jsonResponse.getString("doa");
                        String class_name = jsonResponse.getString("class_name");
                        String section_name = jsonResponse.getString("section_name");
                        String usn = jsonResponse.getString("usn");
                        String class_id = jsonResponse.getString("class_id");
                        String sec_id = jsonResponse.getString("sec_id");

                        SharedPreferences userDetails = getSharedPreferences("userdetails", MODE_PRIVATE);
                        SharedPreferences.Editor edit = userDetails.edit();
                        edit.clear();
                        edit.putString("stud_id", stud_id);
                        edit.putString("first_name", first_name);
                        edit.putString("last_name", last_name);
                        edit.putString("dob", dob);
                        edit.putString("guardian_name", guardian_name);
                        edit.putString("guardian_occupation", guardian_occupation);
                        edit.putString("address", address);
                        edit.putString("city", city);
                        edit.putString("state", state);
                        edit.putString("zipcode", zipcode);
                        edit.putString("country", country);
                        edit.putString("guardian_landline", guardian_landline);
                        edit.putString("guardian_mobile", guardian_mobile);
                        edit.putString("doa", doa);
                        edit.putString("class_name", class_name);
                        edit.putString("section_name", section_name);
                        edit.putString("usn", usn);
                        edit.putString("class_id", class_id);
                        edit.putString("sec_id", sec_id);
                        edit.apply();

                        SharedPreferences fcmDetails = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
                        final String token = fcmDetails.getString(getString(R.string.FCM_TOKEN), "");
                        StringRequest insertTokenRequest = new StringRequest(Request.Method.POST, URL_INSERT_TOKEN, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("token", token);
                                Log.d("token", "inside getParams stud_id" + stud_id);
                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                intent.putExtra("update", 0);
                                LoginActivity.this.startActivity(intent);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("token", "Can't Insert tokens");
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("stu_id", stud_id);
                                params.put("token", token);
                                return params;
                            }
                        };

                        VolleySingleton.getInstance(LoginActivity.this).addToRequestQueue(insertTokenRequest);

                    } else {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Login Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                        spinner.setVisibility(View.INVISIBLE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spinner.setVisibility(View.INVISIBLE);
                Toast.makeText(LoginActivity.this, "Login Failed...!    Make sure you have a working internet connection", Toast.LENGTH_SHORT).show();
            }
        };


        LoginRequest loginRequest = new LoginRequest(username, password, responseListener, errorListener);
        VolleySingleton.getInstance(LoginActivity.this).addToRequestQueue(loginRequest);

    }

    int backPress = 0;

    @Override
    public void onBackPressed() {

        backPress = (backPress + 1);
        Toast.makeText(getApplicationContext(), " Press Back again to Exit ", Toast.LENGTH_SHORT).show();

        if (backPress > 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            } else
                super.onBackPressed();
        }
    }

}
