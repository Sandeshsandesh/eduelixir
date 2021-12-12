package com.eduelixir.eduelixir;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    private final String URL_DELETE_TOKEN = "http://192.168.43.110/notification/delete_token.php";
    private final String URL_DISPLAY_NOTIFICATION = "http://192.168.43.110/notification/display_notification.php";
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private NotificationAdapter notificationAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String usn;
    private List<NotificationData> notificationList;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_notification);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences userDetails = getSharedPreferences("userdetails", MODE_PRIVATE);
        this.usn = userDetails.getString("usn", "");

        this.notificationList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.NotificationRecycler);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_notification);

        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        Log.d("notif", "anim started on post");
                                        displayNotification();
                                    }
                                }
        );
    }

    private void displayNotification() {
        swipeRefreshLayout.setRefreshing(true);
        StringRequest notificationRequest = new StringRequest(Request.Method.POST, URL_DISPLAY_NOTIFICATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    notificationList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String title = jsonObject.getString("title");
                        String message = jsonObject.getString("message");
                        String date = jsonObject.getString("date");
                        NotificationData notificationData = new NotificationData(title, message, date);
                        notificationList.add(notificationData);
                    }
                    linearLayoutManager = new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    notificationAdapter = new NotificationAdapter(context, notificationList);
                    recyclerView.setAdapter(notificationAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(NotificationActivity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("usn", usn);
                params.put("not_id", "0");
                return params;
            }
        };
        VolleySingleton.getInstance(NotificationActivity.this).addToRequestQueue(notificationRequest);
    }

    @Override
    public void onRefresh() {
        displayNotification();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent i = new Intent(this, DashboardActivity.class);
            i.putExtra("update", 0);
            startActivity(i);
            ;
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
                            DatabaseHelper mDb = new DatabaseHelper(NotificationActivity.this);
                            mDb.deleteAllTimeTables();
                            Intent i = new Intent(NotificationActivity.this, LoginActivity.class);
                            startActivity(i);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(NotificationActivity.this, "Make sure you have a working internet connection..!", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            SharedPreferences userDetails = getSharedPreferences("userdetails", MODE_PRIVATE);
                            String stud_id = userDetails.getString("stud_id", "");
                            params.put("stu_id", stud_id);
                            Log.d("token", "stud_id  " + stud_id);
                            return params;
                        }
                    };
                    VolleySingleton.getInstance(NotificationActivity.this).addToRequestQueue(deleteTokenRequest);
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

                    Intent i = new Intent(NotificationActivity.this, Attendance2Activity.class);
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

                    Intent i = new Intent(NotificationActivity.this, Attendance2Activity.class);
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
            Toast.makeText(NotificationActivity.this, "Results have not been announced yet!!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_fees) {
            Toast.makeText(NotificationActivity.this, "Fee Structure have not been announced yet!!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_library) {
            Toast.makeText(NotificationActivity.this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_wallet) {
            Toast.makeText(NotificationActivity.this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_notification) {
            Intent browserIntent = new Intent(this, NotificationActivity.class);
            startActivity(browserIntent);
        } else if (id == R.id.nav_assignments) {
            Toast.makeText(NotificationActivity.this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_notes) {
            Toast.makeText(NotificationActivity.this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_chat) {
            Toast.makeText(NotificationActivity.this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
