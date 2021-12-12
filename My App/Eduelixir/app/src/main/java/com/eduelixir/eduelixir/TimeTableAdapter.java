package com.eduelixir.eduelixir;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import static java.util.Calendar.FRIDAY;
import static java.util.Calendar.MONDAY;
import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.SUNDAY;
import static java.util.Calendar.THURSDAY;
import static java.util.Calendar.TUESDAY;
import static java.util.Calendar.WEDNESDAY;

/**
 * Created by Sandesh on 1/11/2017.
 */
public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.ViewHolder> {
    private Context context;
    private List<TimetableData> periodData;
    private List<TimetableData> timetableMonData;
    private List<TimetableData> timetableTueData;
    private List<TimetableData> timetableWedData;
    private List<TimetableData> timetableThuData;
    private List<TimetableData> timetableFriData;
    private List<TimetableData> timetableSatData;

    public TimeTableAdapter(Context context, List<TimetableData> periodData, List<TimetableData> timetableMonData, List<TimetableData> timetableTueData, List<TimetableData> timetableWedData, List<TimetableData> timetableThuData, List<TimetableData> timetableFriData, List<TimetableData> timetableSatData) {
        this.context = context;
        this.periodData = periodData;
        this.timetableMonData = timetableMonData;
        this.timetableTueData = timetableTueData;
        this.timetableWedData = timetableWedData;
        this.timetableThuData = timetableThuData;
        this.timetableFriData = timetableFriData;
        this.timetableSatData = timetableSatData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_timetable, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("TimeTable", "position" + position);
        holder.textViewPeriodStartingTime.setText(periodData.get(position).getSubName());
        holder.textViewPeriodEndingTime.setText(periodData.get(position).getStaffName());
        holder.textViewMonSub.setText(timetableMonData.get(position).getSubName());
        holder.textViewMonStaff.setText(timetableMonData.get(position).getStaffName());
        holder.textViewTueSub.setText(timetableTueData.get(position).getSubName());
        holder.textViewTueStaff.setText(timetableTueData.get(position).getStaffName());
        holder.textViewWedSub.setText(timetableWedData.get(position).getSubName());
        holder.textViewWedStaff.setText(timetableWedData.get(position).getStaffName());
        holder.textViewThuSub.setText(timetableThuData.get(position).getSubName());
        holder.textViewThuStaff.setText(timetableThuData.get(position).getStaffName());
        holder.textViewFriSub.setText(timetableFriData.get(position).getSubName());
        holder.textViewFriStaff.setText(timetableFriData.get(position).getStaffName());
        holder.textViewSatSub.setText(timetableSatData.get(position).getSubName());
        holder.textViewSatStaff.setText(timetableSatData.get(position).getStaffName());
    }

    @Override
    public int getItemCount() {
        return periodData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewPeriodStartingTime;
        public TextView textViewPeriodEndingTime;
        public TextView textViewMonSub;
        public TextView textViewMonStaff;
        public TextView textViewTueSub;
        public TextView textViewTueStaff;
        public TextView textViewWedSub;
        public TextView textViewWedStaff;
        public TextView textViewThuSub;
        public TextView textViewThuStaff;
        public TextView textViewFriSub;
        public TextView textViewFriStaff;
        public TextView textViewSatSub;
        public TextView textViewSatStaff;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewPeriodStartingTime = (TextView) itemView.findViewById(R.id.tvPeriodStartingTime);
            textViewPeriodEndingTime = (TextView) itemView.findViewById(R.id.tvPeriodEndingTime);
            textViewMonSub = (TextView) itemView.findViewById(R.id.tvMonSub);
            textViewMonStaff = (TextView) itemView.findViewById(R.id.tvMonStaff);
            textViewTueSub = (TextView) itemView.findViewById(R.id.tvTueSub);
            textViewTueStaff = (TextView) itemView.findViewById(R.id.tvTueStaff);
            textViewWedSub = (TextView) itemView.findViewById(R.id.tvWedSub);
            textViewWedStaff = (TextView) itemView.findViewById(R.id.tvWedStaff);
            textViewThuSub = (TextView) itemView.findViewById(R.id.tvThuSub);
            textViewThuStaff = (TextView) itemView.findViewById(R.id.tvThuStaff);
            textViewFriSub = (TextView) itemView.findViewById(R.id.tvFriSub);
            textViewFriStaff = (TextView) itemView.findViewById(R.id.tvFriStaff);
            textViewSatSub = (TextView) itemView.findViewById(R.id.tvSatSub);
            textViewSatStaff = (TextView) itemView.findViewById(R.id.tvSatStaff);
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            CardView cvTime = (CardView) itemView.findViewById(R.id.cvTime);
            CardView cvMon = (CardView) itemView.findViewById(R.id.cvMon);
            CardView cvTue = (CardView) itemView.findViewById(R.id.cvTue);
            CardView cvWed = (CardView) itemView.findViewById(R.id.cvWed);
            CardView cvThu = (CardView) itemView.findViewById(R.id.cvThu);
            CardView cvFri = (CardView) itemView.findViewById(R.id.cvFri);
            CardView cvSat = (CardView) itemView.findViewById(R.id.cvSat);

            switch (day) {                   // To identify current day
                case SUNDAY:
                    cvMon.setCardBackgroundColor(Color.parseColor("#99d6ff"));
                    break;
                case MONDAY:
                    cvMon.setCardBackgroundColor(Color.parseColor("#99d6ff"));
                    break;
                case TUESDAY:
                    cvTue.setCardBackgroundColor(Color.parseColor("#99d6ff"));
                    break;
                case WEDNESDAY:
                    cvWed.setCardBackgroundColor(Color.parseColor("#99d6ff"));
                    break;
                case THURSDAY:
                    cvThu.setCardBackgroundColor(Color.parseColor("#99d6ff"));
                    break;
                case FRIDAY:
                    cvFri.setCardBackgroundColor(Color.parseColor("#99d6ff"));
                    break;
                case SATURDAY:
                    cvSat.setCardBackgroundColor(Color.parseColor("#99d6ff"));
            }
        }
    }
}
