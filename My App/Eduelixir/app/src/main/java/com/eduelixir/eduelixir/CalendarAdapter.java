package com.eduelixir.eduelixir;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.YEAR;

/**
 * Created by Sandesh on 1/27/2017.
 */
public class CalendarAdapter extends BaseAdapter {
    private Attendance2Activity attendance2Activity;
    private Context _context;
    private List<String> list;
    private int month;
    private int year;
    private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private int daysInMonth;
    private final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private int currentDayOfMonth;
    private TextView day_gridcell;

    public CalendarAdapter(Attendance2Activity attendance2Activity, Context applicationContext, int paramInt1, int paramInt2, int paramInt3) {
        this.attendance2Activity = attendance2Activity;
        this._context = applicationContext;
        this.list = new ArrayList();
        this.month = paramInt2;
        this.year = paramInt3;
        setCurrentDayOfMonth(Calendar.getInstance().get(DAY_OF_MONTH));   //todays date

        printMonth(paramInt2, paramInt3);

    }

    private void setCurrentDayOfMonth(int paramInt) {
        this.currentDayOfMonth = paramInt;
        Log.d("Main", "current day of month " + this.currentDayOfMonth);
    }

    private void printMonth(int paramInt1, int paramInt2) {                     //paramInt1 = month;   paramInt2 = year
        int i1 = paramInt1 - 1;                                                 //i1 = monthindex
        String str = getMonthAsString(i1);
        this.daysInMonth = getNumberOfDaysOfMonth(i1);
        GregorianCalendar localGregorianCalendar = new GregorianCalendar(paramInt2, i1, 1);     //( year, month, day_of_month)

        int prevMonth;
        int prevMonthDays;
        int nextMonth;
        int prevYear;
        int nextYear;
        if (i1 == 11) {             //if DECEMBER
            prevMonth = i1 - 1;
            prevMonthDays = getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 0;
            prevYear = paramInt2;
            nextYear = paramInt2 + 1;
            Log.d("Main", "*->PrevYear: " + prevYear + " PrevMonth:" + prevMonth + " NextMonth: " + i1 + " NextYear: " + nextYear);
        } else if (i1 == 0) {              //if January
            prevMonth = 11;
            prevYear = paramInt2 - 1;
            nextYear = paramInt2;
            prevMonthDays = getNumberOfDaysOfMonth(11);
            nextMonth = 1;
            Log.d("Main", "**--> PrevYear: " + prevYear + " PrevMonth:" + 11 + " NextMonth: " + 1 + " NextYear: " + nextYear);
        } else {                      //for other months
            prevMonth = i1 - 1;
            nextMonth = i1 + 1;
            nextYear = paramInt2;
            prevYear = paramInt2;
            prevMonthDays = getNumberOfDaysOfMonth(prevMonth);
            Log.d("Main", "***---> PrevYear: " + prevYear + " PrevMonth:" + prevMonth + " NextMonth: " + nextMonth + " NextYear: " + nextYear);
        }
        int i2 = localGregorianCalendar.get(DAY_OF_WEEK) - 1;               //i2 = First_Day_Of_Month

        if ((localGregorianCalendar.isLeapYear(localGregorianCalendar.get(YEAR))) && (paramInt1 == 2)) {
            this.daysInMonth += 1;
        }
        paramInt1 = 0;
        while (paramInt1 < i2) {        // To GREY out previous month days
            this.list.add(String.valueOf(prevMonthDays - i2 + 1 + paramInt1) + "-GREY" + "-" + getMonthAsString(prevMonth) + "-" + prevYear);
            //example list.add( 29-GREY-January-2017 )      : (31-3+1+0) = 29
            paramInt1 += 1;
        }

        paramInt1 = 1;
        while (paramInt1 <= this.daysInMonth) {         //To Mark current month dates
            Log.d("Main", String.valueOf(paramInt1) + " " + getMonthAsString(i1) + " " + paramInt2);
            if (paramInt1 == getCurrentDayOfMonth()) {          //To mark current date
                this.list.add(String.valueOf(paramInt1) + "-BLUE" + "-" + getMonthAsString(i1) + "-" + paramInt2);
            } else {
                this.list.add(String.valueOf(paramInt1) + "-WHITE" + "-" + getMonthAsString(i1) + "-" + paramInt2);
            }
            paramInt1 += 1;
        }

        Log.d("Main", "nextMonth" + nextMonth);
        paramInt1 = 0;
        while (paramInt1 < this.list.size() % 7) {          //To GREY out next month days
            this.list.add(String.valueOf(paramInt1 + 1) + "-GREY" + "-" + getMonthAsString(nextMonth) + "-" + nextYear);
            paramInt1 += 1;
        }
    }

    public int getCurrentDayOfMonth() {
        return this.currentDayOfMonth;
    }

    private int getNumberOfDaysOfMonth(int paramInt) {
        return this.daysOfMonth[paramInt];
    }

    private String getMonthAsString(int paramInt) {
        return this.months[paramInt];
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public String getItem(int paramInt) {
        return this.list.get(paramInt);
    }

    @Override
    public long getItemId(int paramInt) {
        return paramInt;
    }

    @Override
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        paramView = ((LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.calender_gridcell, paramViewGroup, false);
        this.day_gridcell = (TextView) paramView.findViewById(R.id.calendar_day_gridcell);
        String[] listContents = (this.list.get(paramInt)).split("-");      //ex 29-GREY-January-2017
        this.day_gridcell.setText(listContents[0]);

        if (listContents[1].equals("WHITE")) {
            this.day_gridcell.setTextColor(Color.parseColor("#000000"));
            this.day_gridcell.setTextSize(18.0F);
        }
        if (listContents[1].equals("BLUE")) {
            int curYear = Calendar.getInstance().get(YEAR);
            int curMonth = Calendar.getInstance().get(Calendar.MONTH);                  //current day should not be shown for previous years

            if (((this.month - 1) == curMonth) && (this.year == curYear)) {
                this.day_gridcell.setTextColor(Color.parseColor("#3498DB"));
                this.day_gridcell.setTextSize(30.0F);
            } else {
                this.day_gridcell.setTextColor(Color.parseColor("#000000"));
                this.day_gridcell.setTextSize(16.0F);
            }
        }

        if (!(listContents[1].equals("GREY"))) {
            int curDateIndex = Integer.parseInt(listContents[0]) - 1;
            if (attendance2Activity.attendanceDetails[curDateIndex].equalsIgnoreCase("P")) {
                this.day_gridcell.setBackgroundResource(R.drawable.calendar_present);
            }
            if (attendance2Activity.attendanceDetails[curDateIndex].equalsIgnoreCase("A")) {
                this.day_gridcell.setBackgroundResource(R.drawable.calendar_absent);
            }
            if (attendance2Activity.attendanceDetails[curDateIndex].equalsIgnoreCase("L")) {
                this.day_gridcell.setBackgroundResource(R.drawable.calendar_leave);
            }
            if (attendance2Activity.attendanceDetails[curDateIndex].equalsIgnoreCase("H")) {
                this.day_gridcell.setTextColor(Color.parseColor("#8B0000"));
            }
        }

        return paramView;
    }
}
