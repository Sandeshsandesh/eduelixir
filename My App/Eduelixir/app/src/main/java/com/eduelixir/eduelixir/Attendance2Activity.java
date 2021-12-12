package com.eduelixir.eduelixir;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.YEAR;

public class Attendance2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GestureOverlayView.OnGesturePerformedListener {

    GestureLibrary GLibrary;
    private Calendar _calendar;
    private int month;
    private int year;
    private TextView currentMonth;
    private GridView calendarView;
    String[] attendanceDetails = new String[31];
    private CalendarAdapter calendarAdapter;
    ImageView previousImage;
    ImageView nextImage;
    private final String URL_DELETE_TOKEN = "http://192.168.43.110/notification/delete_token.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance2);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);

        this._calendar = Calendar.getInstance(Locale.getDefault());
        this.month = (this._calendar.get(Calendar.MONTH) + 1);
        this.year = this._calendar.get(YEAR);
        this.previousImage = (ImageView) findViewById(R.id.prevMonth);
        this.nextImage = (ImageView) findViewById(R.id.nextMonth);
        this.currentMonth = ((TextView) findViewById(R.id.currentMonth));
        this.currentMonth.setText(DateFormat.format("MMMM yyyy", this._calendar.getTime()));
        this.calendarView = (GridView) findViewById(R.id.calendar);

        attendanceDetails = new String[]{"H", "A", "L", "H", "P", "A", "L", "H", "A", "L", "P", "A", "L", "P", "H", "", "P", "A", "L", "P", "A", "H", "P", "A", "L", "P", "A", "L", "H", "A", "H"};
        this.calendarAdapter = new CalendarAdapter(this, getApplicationContext(), R.id.calendar_day_gridcell, this.month, this.year);
        this.calendarAdapter.notifyDataSetChanged();
        this.calendarView.setAdapter(this.calendarAdapter);
        startGestures();
    }

    public void startGestures() {
        this.GLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if (!this.GLibrary.load()) {
            finish();
        }
        GestureOverlayView gestureOverlayView = (GestureOverlayView) findViewById(R.id.gestures1);
        gestureOverlayView.setGestureColor(Color.TRANSPARENT);
        gestureOverlayView.setUncertainGestureColor(Color.TRANSPARENT);
        gestureOverlayView.addOnGesturePerformedListener(this);
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = GLibrary.recognize(gesture);
        if (predictions.size() > 0) {
            if (predictions.get(0).score > 1.0) {
                if (predictions.get(0).name.equals("swipe left")) {
                    nextMonth();
                    Log.d("Main", "Inside swipe left");
                }
                if (predictions.get(0).name.equals("swipe right")) {
                    Log.d("Main", "Inside swipe right");
                    previousMonth();
                }
            }
        }
        Log.d("Main", "Inside Gesture");
    }

    public void imageClicked(View view) {
        Log.d("Main", "Inside imageClicked" + view);
        if (view == this.previousImage)
            previousMonth();
        if (view == this.nextImage)
            nextMonth();
    }

    public void previousMonth() {
        Log.d("Main", "inside previous");
        Log.d("Main", "this.month: " + this.month);
        if (this.month == 1) {         //if January
            this.month = 12;
            this.year--;
        } else
            this.month--;

        //CODE TO FETCH DATA FOR THIS MONTH AND SET this.calendarDetails
        Log.d("Main", "this.month" + this.month);
        Attendance2Activity.this.updateCalendarAdapter(Attendance2Activity.this.month, Attendance2Activity.this.year);

    }

    private void updateCalendarAdapter(int month, int year) {
        this.calendarAdapter = new CalendarAdapter(this, getApplicationContext(), R.id.calendar_day_gridcell, month, year);
        this._calendar.set(year, month - 1, this._calendar.get(DAY_OF_MONTH));
        TextView currentMonth1 = this.currentMonth;
        currentMonth1.setText(DateFormat.format("MMMM yyyy", this._calendar.getTime()));
        this.calendarAdapter.notifyDataSetChanged();
        this.calendarView.setAdapter(this.calendarAdapter);
    }

    public void nextMonth() {
        Log.d("Main", "inside next");
        int curMonth = Calendar.getInstance().get(Calendar.MONTH);
        int curYear = Calendar.getInstance().get(YEAR);
        if ((this.month == (curMonth + 1)) && (this.year == curYear)) {
            Toast.makeText(this, "You are currently viewing the present month attendance", Toast.LENGTH_SHORT).show();
            return;
        }
        if (this.month == 12) {
            this.month = 1;
            this.year++;
        } else
            this.month++;

        //CODE TO FETCH NEXT MONTH ATTENDANCE DATA

        Attendance2Activity.this.updateCalendarAdapter(Attendance2Activity.this.month, Attendance2Activity.this.year);
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
                            DatabaseHelper mDb = new DatabaseHelper(Attendance2Activity.this);
                            mDb.deleteAllTimeTables();
                            Intent i = new Intent(Attendance2Activity.this, LoginActivity.class);
                            startActivity(i);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Attendance2Activity.this, "Make sure you have a working internet connection..!", Toast.LENGTH_SHORT).show();
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
                    VolleySingleton.getInstance(Attendance2Activity.this).addToRequestQueue(deleteTokenRequest);

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

                    Intent i = new Intent(Attendance2Activity.this, Attendance2Activity.class);
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

                    Intent i = new Intent(Attendance2Activity.this, Attendance2Activity.class);
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
            Toast.makeText(Attendance2Activity.this, "Results have not been announced yet!!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_fees) {
            Toast.makeText(Attendance2Activity.this, "Fee Structure have not been announced yet!!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_library) {
            Toast.makeText(Attendance2Activity.this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_wallet) {
            Toast.makeText(Attendance2Activity.this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_notification) {
            Intent browserIntent = new Intent(this, NotificationActivity.class);
            startActivity(browserIntent);
        } else if (id == R.id.nav_assignments) {
            Toast.makeText(Attendance2Activity.this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_notes) {
            Toast.makeText(Attendance2Activity.this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_chat) {
            Toast.makeText(Attendance2Activity.this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
