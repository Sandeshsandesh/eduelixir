package com.eduelixir.eduelixir;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final String URL_DELETE_TOKEN = "http://192.168.43.110/notification/delete_token.php";
    private String stud_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "We are sorry. You cannot write feedback...!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        int update = intent.getIntExtra("update", 0);

        SharedPreferences userDetails = getSharedPreferences("userdetails", MODE_PRIVATE);
        String usn = userDetails.getString("usn", "");

        if (update == 1) {                                             //update the contents
            ConnectivityManager check = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = check.getActiveNetworkInfo();          //checking internet connection
            if (info != null && info.isConnected()) {
                Toast.makeText(this, "Updating...!", Toast.LENGTH_SHORT).show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                stud_id = jsonResponse.getString("stud_id");
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

                            } else {
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DashboardActivity.this);
                                builder.setMessage("Failed to fetch data from server...!  Displaying saved data")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DashboardActivity.this, "Login Failed...!    Make sure you have a working internet connection", Toast.LENGTH_SHORT).show();
                    }
                };

                LoginRequest loginRequest = new LoginRequest(usn, responseListener,errorListener);
                VolleySingleton.getInstance(DashboardActivity.this).addToRequestQueue(loginRequest);

            } else {
                Toast.makeText(this, "No network connction...!  Displaying saved data", Toast.LENGTH_SHORT).show();
            }
        }
        TextView tvFirstName = (TextView) findViewById(R.id.tvFirstName);
        TextView tvLastName = (TextView) findViewById(R.id.tvLastName);
        TextView tvDob = (TextView) findViewById(R.id.tvDob);
        TextView tvGuardianName = (TextView) findViewById(R.id.tvGuardianName);
        TextView tvGuardianOccupation = (TextView) findViewById(R.id.tvGuardianOccupation);
        TextView tvAddress = (TextView) findViewById(R.id.tvAddress);
        TextView tvCity = (TextView) findViewById(R.id.tvCity);
        TextView tvState = (TextView) findViewById(R.id.tvState);
        TextView tvZipcode = (TextView) findViewById(R.id.tvZipCode);
        TextView tvCountry = (TextView) findViewById(R.id.tvCountry);
        TextView tvGuardianLandline = (TextView) findViewById(R.id.tvGuardianLandline);
        TextView tvGuardianMobile = (TextView) findViewById(R.id.tvGuardianMobile);
        TextView tvDoa = (TextView) findViewById(R.id.tvDoa);
        TextView tvClass = (TextView) findViewById(R.id.tvClass);
        TextView tvSection = (TextView) findViewById(R.id.tvSection);
        TextView tvUsn = (TextView) findViewById(R.id.tvUsn);


        stud_id = userDetails.getString("stud_id", "");
        String first_name = userDetails.getString("first_name", "");
        String last_name = userDetails.getString("last_name", "");
        String dob = userDetails.getString("dob", "");
        String guardian_name = userDetails.getString("guardian_name", "");
        String guardian_occupation = userDetails.getString("guardian_occupation", "");
        String address = userDetails.getString("address", "");
        String city = userDetails.getString("city", "");
        String state = userDetails.getString("state", "");
        String zipcode = userDetails.getString("zipcode", "");
        String country = userDetails.getString("country", "");
        String guardian_landline = userDetails.getString("guardian_landline", "");
        String guardian_mobile = userDetails.getString("guardian_mobile", "");
        String doa = userDetails.getString("doa", "");
        String class_name = userDetails.getString("class_name", "");
        String section_name = userDetails.getString("section_name", "");
        usn = userDetails.getString("usn", "");


        tvFirstName.setText(first_name);
        tvLastName.setText(last_name);
        tvDob.setText(dob);
        tvGuardianName.setText(guardian_name);
        tvGuardianOccupation.setText(guardian_occupation);
        tvAddress.setText(address);
        tvCity.setText(city);
        tvState.setText(state);
        tvZipcode.setText(zipcode);
        tvCountry.setText(country);
        tvGuardianLandline.setText(guardian_landline);
        tvGuardianMobile.setText(guardian_mobile);
        tvDoa.setText(doa);
        tvClass.setText(class_name);
        tvSection.setText(section_name);
        tvUsn.setText(usn);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_notification);
        navigationView.setNavigationItemSelectedListener(this);
        setAnimation();
    }

    public void setAnimation() {

        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        findViewById(R.id.ivProfilePic).startAnimation(anim2);

        Animation anim4 = AnimationUtils.loadAnimation(this, R.anim.translate_center_to_top);
        findViewById(R.id.ll_scroll).startAnimation(anim4);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
            localBuilder.setTitle("OOPS...!!!");
            localBuilder.setMessage(
                    "Do you wish to exit application?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        finishAffinity();
                    }

                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                }
            });
            localBuilder.create().show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_info) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.eduelixir.com/"));
            startActivity(browserIntent);
        }
        if (id == R.id.action_logout) {


            AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
            localBuilder.setTitle("OOPS...!!!");
            localBuilder.setMessage(
                    "Do you wish to Log Out...?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    StringRequest deleteTokenRequest = new StringRequest(Request.Method.POST, URL_DELETE_TOKEN, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            SharedPreferences userDetails = getSharedPreferences("userdetails", MODE_PRIVATE);
                            SharedPreferences.Editor edit = userDetails.edit();
                            edit.clear();
                            edit.apply();

                            SharedPreferences timetableDetails = getSharedPreferences("timetabledetails", MODE_PRIVATE);
                            SharedPreferences.Editor edit1 = timetableDetails.edit();
                            edit1.clear();
                            edit1.apply();
                            DatabaseHelper mDb = new DatabaseHelper(DashboardActivity.this);
                            mDb.deleteAllTimeTables();
                            Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
                            startActivity(i);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(DashboardActivity.this, "Make sure you have a working internet connection..!", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("stu_id", stud_id);
                            Log.d("token","inside params stud_id: "+stud_id);
                            return params;
                        }
                    };

                    VolleySingleton.getInstance(DashboardActivity.this).addToRequestQueue(deleteTokenRequest);
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                }
            });
            localBuilder.create().show();


        }
        if (id == R.id.action_notification) {
            Intent browserIntent = new Intent(this, NotificationActivity.class);
            startActivity(browserIntent);
        }
        if (id == R.id.action_lang) {

            AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
            localBuilder.setTitle("Language");
            localBuilder.setMessage("Choose Your Language").setPositiveButton("English", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    String lang = "en";


                    Locale locale = new Locale(lang);

                    Locale.setDefault(locale);
                    Configuration config = new Configuration(getResources().getConfiguration());
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,
                            getBaseContext().getResources().getDisplayMetrics());

                    Intent i = new Intent(DashboardActivity.this, DashboardActivity.class);
                    startActivity(i);

                }
            }).setNegativeButton("Kannada", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    String lang = "kn";

                    String country = "IN";
                    Locale locale = new Locale(lang, country);

                    Locale.setDefault(locale);
                    Configuration config = new Configuration(getResources().getConfiguration());
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,
                            getBaseContext().getResources().getDisplayMetrics());

                    Intent i = new Intent(DashboardActivity.this, DashboardActivity.class);
                    startActivity(i);

                }
            });
            localBuilder.create().show();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            Intent i = new Intent(this, DashboardActivity.class);
            startActivity(i);
        }
        if (id == R.id.nav_attendance) {
            Intent i = new Intent(this, Attendance2Activity.class);
            startActivity(i);
        } else if (id == R.id.nav_timetable) {
            Intent i = new Intent(this, TimetableActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_exam) {
            Intent i = new Intent(this, TimetableActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_results) {
            Toast.makeText(DashboardActivity.this, "Results have not been announced yet!!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_fees) {
            Toast.makeText(DashboardActivity.this, "Fee Structure have not been announced yet!!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_library) {
            Toast.makeText(DashboardActivity.this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_wallet) {
            Toast.makeText(DashboardActivity.this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_notification) {
            Intent browserIntent = new Intent(this, NotificationActivity.class);
            startActivity(browserIntent);
        } else if (id == R.id.nav_assignments) {
            Toast.makeText(DashboardActivity.this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_notes) {
            Toast.makeText(DashboardActivity.this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_chat) {
            Toast.makeText(DashboardActivity.this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
