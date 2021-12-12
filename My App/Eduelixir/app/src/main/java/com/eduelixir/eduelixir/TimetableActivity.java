package com.eduelixir.eduelixir;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TimetableActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TimeTableAdapter adapter;
    private List<TimetableData> periodList;
    private List<TimetableData> monList;
    private List<TimetableData> tueList;
    private List<TimetableData> wedList;
    private List<TimetableData> thuList;
    private List<TimetableData> friList;
    private List<TimetableData> satList;
    private String class_id;
    private String sec_id;
    private static final String TIMETABLE = "http://192.168.43.110/timetable.php";
    private static final String TIMETABLE_PERIOD = "http://192.168.43.110/timetablePeriod.php";
    private Map<String, String> params;
    private Context context;
    private int listSize;
    DatabaseHelper mDb;
    Cursor cursor;
    private int day;
    private int year;
    private int month;
    private Calendar calendar;
    private final String URL_DELETE_TOKEN = "http://192.168.43.110/notification/delete_token.php";
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_timetable);
        navigationView.setNavigationItemSelectedListener(this);

        mDb = new DatabaseHelper(this);

        this.calendar = Calendar.getInstance();
        this.day = calendar.get(Calendar.DATE);
        this.month = calendar.get(Calendar.MONTH);
        this.year = calendar.get(Calendar.YEAR);

        recyclerView = (RecyclerView) findViewById(R.id.rec);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_timetable);

        this.periodList = new ArrayList<>();
        this.monList = new ArrayList<>();
        this.tueList = new ArrayList<>();
        this.wedList = new ArrayList<>();
        this.thuList = new ArrayList<>();
        this.friList = new ArrayList<>();
        this.satList = new ArrayList<>();
        SharedPreferences userDetails = getSharedPreferences("userdetails", MODE_PRIVATE);
        this.class_id = userDetails.getString("class_id", "");
        this.sec_id = userDetails.getString("sec_id", "");

        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        Log.d("timetable","anim started on post");
                                        displayTimetable();
                                    }
                                }
        );
    }

    @Override
    public void onRefresh() {
        loadTimetablePeriodFromServer();
    }

    private void displayTimetable() {
        SharedPreferences timetableDetails = getSharedPreferences("timetabledetails", MODE_PRIVATE);
        Boolean isUpdated = timetableDetails.getBoolean("isUpdated", false);
        Log.d("timetable", "isUpdated: " + isUpdated);

        if (!isUpdated) {                           //If not updated
            loadTimetablePeriodFromServer();                //fetch from server

        } else {                                    //If Updated
            int updatedDay = timetableDetails.getInt("day", this.day);
            int updatedMonth = timetableDetails.getInt("month", this.month);
            int updatedYear = timetableDetails.getInt("year", this.year);
            if (updatedDay == this.day && updatedMonth == this.month && updatedYear == this.year) {     //If last updated date is today
                loadTimetableDataFromLocalDatabase();       //fetch from local database

            } else {                                                                                    //If last updated date is not today
                ConnectivityManager check = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = check.getActiveNetworkInfo();          //checking internet connection
                if (info != null && info.isConnected()) {
                    loadTimetablePeriodFromServer();                    //fetch from server if connection is available
                } else {
                    Toast.makeText(this, "Please make sure you have an active internet connection..! Last updated on: ", Toast.LENGTH_SHORT).show();
                    loadTimetableDataFromLocalDatabase();           //fetch from local database if connection is not available
                }
            }
        }
    }

    private void loadTimetableDataFromLocalDatabase() {
        Log.d("timetable", "fetching from local database...!");
        swipeRefreshLayout.setRefreshing(false);
        cursor = mDb.fetchPeriodData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Please make sure you have a working internet connection", Toast.LENGTH_SHORT).show();
            loadTimetablePeriodFromServer();
            return;
        }
        while (cursor.moveToNext()) {
            TimetableData PeriodData = new TimetableData(cursor.getString(1), cursor.getString(2));
            periodList.add(PeriodData);
        }
        listSize = periodList.size();
        Log.d("timetable", "After fetching period data: " + periodList.get(1).getSubName());


        cursor = mDb.fetchMonData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Please make sure you have a working internet connection", Toast.LENGTH_SHORT).show();
            loadTimetablePeriodFromServer();
            return;
        }
        while (cursor.moveToNext()) {
            TimetableData PeriodData = new TimetableData(cursor.getString(1), cursor.getString(2));
            monList.add(PeriodData);
        }
        while (monList.size() != listSize) {
            monList.add(new TimetableData("", ""));      //To match the size to the count of adapter
        }


        cursor = mDb.fetchTueData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Please make sure you have a working internet connection", Toast.LENGTH_SHORT).show();
            loadTimetablePeriodFromServer();
            return;
        }
        while (cursor.moveToNext()) {
            TimetableData PeriodData = new TimetableData(cursor.getString(1), cursor.getString(2));
            tueList.add(PeriodData);
        }
        while (tueList.size() != listSize) {
            tueList.add(new TimetableData("", ""));      //To match the size to the count of adapter
        }


        cursor = mDb.fetchWedData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Please make sure you have a working internet connection", Toast.LENGTH_SHORT).show();
            loadTimetablePeriodFromServer();
            return;
        }
        while (cursor.moveToNext()) {
            TimetableData PeriodData = new TimetableData(cursor.getString(1), cursor.getString(2));
            wedList.add(PeriodData);
        }
        while (wedList.size() != listSize) {
            wedList.add(new TimetableData("", ""));      //To match the size to the count of adapter
        }


        cursor = mDb.fetchThuData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Please make sure you have a working internet connection", Toast.LENGTH_SHORT).show();
            loadTimetablePeriodFromServer();
            return;
        }
        while (cursor.moveToNext()) {
            TimetableData PeriodData = new TimetableData(cursor.getString(1), cursor.getString(2));
            thuList.add(PeriodData);
        }
        while (thuList.size() != listSize) {
            thuList.add(new TimetableData("", ""));      //To match the size to the count of adapter
        }


        cursor = mDb.fetchFriData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Please make sure you have a working internet connection", Toast.LENGTH_SHORT).show();
            loadTimetablePeriodFromServer();
            return;
        }
        while (cursor.moveToNext()) {
            TimetableData PeriodData = new TimetableData(cursor.getString(1), cursor.getString(2));
            friList.add(PeriodData);
        }
        while (friList.size() != listSize) {
            friList.add(new TimetableData("", ""));      //To match the size to the count of adapter
        }


        cursor = mDb.fetchSatData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Please make sure you have a working internet connection", Toast.LENGTH_SHORT).show();
            loadTimetablePeriodFromServer();
            return;
        }
        while (cursor.moveToNext()) {
            TimetableData PeriodData = new TimetableData(cursor.getString(1), cursor.getString(2));
            satList.add(PeriodData);
        }
        while (satList.size() != listSize) {
            satList.add(new TimetableData("", ""));      //To match the size to the count of adapter
        }

        cursor.close();

        Log.d("timetable", "sat size: " + satList.size());
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TimeTableAdapter(context, periodList, monList, tueList, wedList, thuList, friList, satList);
        recyclerView.setAdapter(adapter);
    }

    private void loadTimetablePeriodFromServer() {
        Log.d("timetable", "fetching from server..!");
        swipeRefreshLayout.setRefreshing(true);

        StringRequest timetablePeriodRequest = new StringRequest(Request.Method.POST, TIMETABLE_PERIOD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    mDb.deleteAllTimeTables();                  //To clear local database
                    periodList.clear();
                    monList.clear();
                    tueList.clear();
                    wedList.clear();
                    thuList.clear();
                    friList.clear();
                    satList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String start_hour = jsonObject.getString("start_hour");
                        String start_minute = jsonObject.getString("start_minute");
                        String end_hour = jsonObject.getString("end_hour");
                        String end_minute = jsonObject.getString("end_minute");
                        TimetableData PeriodData = new TimetableData(start_hour + ":" + start_minute, end_hour + ":" + end_minute);
                        periodList.add(PeriodData);
                        mDb.insertPeriodData(start_hour + ":" + start_minute, end_hour + ":" + end_minute);
                        SharedPreferences timetableDetails = getSharedPreferences("timetabledetails", MODE_PRIVATE);
                        SharedPreferences.Editor edit = timetableDetails.edit();
                        edit.clear();

                        edit.putBoolean("isUpdated", true);
                        edit.putInt("day", day);
                        edit.putInt("month", month);
                        edit.putInt("year", year);
                        edit.apply();
                        Log.d("timetable", "updated date: " + day + " " + month + " " + year);
                    }
                    listSize = periodList.size();               //Maximum count of the items in adapter
                    loadTimetableMonDataFromServer();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(TimetableActivity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                params = new HashMap<>();
                params.put("class_id", class_id);
                params.put("sec_id", sec_id);
                return params;
            }
        };
        VolleySingleton.getInstance(TimetableActivity.this).addToRequestQueue(timetablePeriodRequest);
    }

    private void loadTimetableMonDataFromServer() {
        StringRequest timetableRequest = new StringRequest(Request.Method.POST, TIMETABLE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String first_name = jsonObject.getString("first_name");
                        String last_name = jsonObject.getString("last_name");
                        TimetableData monData = new TimetableData(name, first_name + " " + last_name);
                        monList.add(monData);
                        mDb.insertMonData(name, first_name + " " + last_name);
                    }
                    while (monList.size() != listSize) {
                        monList.add(new TimetableData("", ""));      //To match the size to the count of adapter
                    }
                    loadTimetableTueDataFromServer();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(TimetableActivity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                params = new HashMap<>();
                params.put("day", "mon");
                params.put("class_id", class_id);
                params.put("sec_id", sec_id);
                Log.d("timetable", "params: " + params);
                return params;
            }
        };
        VolleySingleton.getInstance(TimetableActivity.this).addToRequestQueue(timetableRequest);
    }

    private void loadTimetableTueDataFromServer() {
        StringRequest timetableRequest = new StringRequest(Request.Method.POST, TIMETABLE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String first_name = jsonObject.getString("first_name");
                        String last_name = jsonObject.getString("last_name");
                        TimetableData monData = new TimetableData(name, first_name + " " + last_name);
                        tueList.add(monData);
                        mDb.insertTueData(name, first_name + " " + last_name);

                    }
                    while (tueList.size() != listSize) {
                        tueList.add(new TimetableData("", ""));
                    }
                    loadTimetableWedDataFromServer();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(TimetableActivity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                params = new HashMap<>();
                params.put("day", "tue");
                params.put("class_id", class_id);
                params.put("sec_id", sec_id);
                Log.d("timetable", "params: " + params);
                return params;
            }
        };
        VolleySingleton.getInstance(TimetableActivity.this).addToRequestQueue(timetableRequest);
    }

    private void loadTimetableWedDataFromServer() {
        StringRequest timetableRequest = new StringRequest(Request.Method.POST, TIMETABLE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String first_name = jsonObject.getString("first_name");
                        String last_name = jsonObject.getString("last_name");
                        TimetableData monData = new TimetableData(name, first_name + " " + last_name);
                        wedList.add(monData);
                        mDb.insertWedData(name, first_name + " " + last_name);
                    }
                    while (wedList.size() != listSize) {
                        wedList.add(new TimetableData("", ""));
                    }
                    loadTimetableThuDataFromServer();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(TimetableActivity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                params = new HashMap<>();
                params.put("day", "wed");
                params.put("class_id", class_id);
                params.put("sec_id", sec_id);
                Log.d("timetable", "params: " + params);
                return params;
            }
        };
        VolleySingleton.getInstance(TimetableActivity.this).addToRequestQueue(timetableRequest);
    }

    private void loadTimetableThuDataFromServer() {
        StringRequest timetableRequest = new StringRequest(Request.Method.POST, TIMETABLE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String first_name = jsonObject.getString("first_name");
                        String last_name = jsonObject.getString("last_name");
                        TimetableData monData = new TimetableData(name, first_name + " " + last_name);
                        thuList.add(monData);
                        mDb.insertThuData(name, first_name + " " + last_name);

                    }
                    while (thuList.size() != listSize) {
                        thuList.add(new TimetableData("", ""));
                    }
                    loadTimetableFriDataFromServer();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(TimetableActivity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                params = new HashMap<>();
                params.put("day", "thu");
                params.put("class_id", class_id);
                params.put("sec_id", sec_id);
                Log.d("timetable", "params: " + params);
                return params;
            }
        };
        VolleySingleton.getInstance(TimetableActivity.this).addToRequestQueue(timetableRequest);
    }

    private void loadTimetableFriDataFromServer() {
        StringRequest timetableRequest = new StringRequest(Request.Method.POST, TIMETABLE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String first_name = jsonObject.getString("first_name");
                        String last_name = jsonObject.getString("last_name");
                        TimetableData monData = new TimetableData(name, first_name + " " + last_name);
                        friList.add(monData);
                        mDb.insertFriData(name, first_name + " " + last_name);
                    }
                    while (friList.size() != listSize) {
                        friList.add(new TimetableData("", ""));
                    }
                    loadTimetableSatDataFromServer();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(TimetableActivity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                params = new HashMap<>();
                params.put("day", "fri");
                params.put("class_id", class_id);
                params.put("sec_id", sec_id);
                Log.d("timetable", "params: " + params);
                return params;
            }
        };
        VolleySingleton.getInstance(TimetableActivity.this).addToRequestQueue(timetableRequest);
    }

    private void loadTimetableSatDataFromServer() {
        StringRequest timetableRequest = new StringRequest(Request.Method.POST, TIMETABLE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String first_name = jsonObject.getString("first_name");
                        String last_name = jsonObject.getString("last_name");
                        TimetableData monData = new TimetableData(name, first_name + " " + last_name);
                        satList.add(monData);
                        mDb.insertSatData(name, first_name + " " + last_name);
                    }
                    Log.d("timetable", "sat size: " + satList.size());
                    while (satList.size() != listSize) {
                        satList.add(new TimetableData("", ""));
                    }
                    Log.d("timetable", "sat size: " + satList.size());
                    linearLayoutManager = new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    adapter = new TimeTableAdapter(context, periodList, monList, tueList, wedList, thuList, friList, satList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(TimetableActivity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                params = new HashMap<>();
                params.put("day", "sat");
                params.put("class_id", class_id);
                params.put("sec_id", sec_id);
                Log.d("timetable", "params: " + params);
                return params;
            }
        };
        VolleySingleton.getInstance(TimetableActivity.this).addToRequestQueue(timetableRequest);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent i = new Intent(this, DashboardActivity.class);
            startActivity(i);
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
                            DatabaseHelper mDb = new DatabaseHelper(TimetableActivity.this);
                            mDb.deleteAllTimeTables();
                            Intent i = new Intent(TimetableActivity.this, LoginActivity.class);
                            startActivity(i);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(TimetableActivity.this, "Make sure you have a working internet connection..!", Toast.LENGTH_SHORT).show();
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
                    VolleySingleton.getInstance(TimetableActivity.this).addToRequestQueue(deleteTokenRequest);
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

                    Intent i = new Intent(TimetableActivity.this, TimetableActivity.class);
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

                    Intent i = new Intent(TimetableActivity.this, TimetableActivity.class);
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
            Toast.makeText(TimetableActivity.this, "Results have not been announced yet!!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_fees) {
            Toast.makeText(TimetableActivity.this, "Fee Structure have not been announced yet!!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_library) {
            Toast.makeText(TimetableActivity.this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_wallet) {
            Toast.makeText(TimetableActivity.this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_notification) {
            Intent browserIntent = new Intent(this, NotificationActivity.class);
            startActivity(browserIntent);
        } else if (id == R.id.nav_assignments) {
            Toast.makeText(TimetableActivity.this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_notes) {
            Toast.makeText(TimetableActivity.this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_chat) {
            Toast.makeText(TimetableActivity.this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
