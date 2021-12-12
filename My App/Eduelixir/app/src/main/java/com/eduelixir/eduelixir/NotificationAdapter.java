package com.eduelixir.eduelixir;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sandesh on 2/11/2017.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{
    private Context context;
    private List<NotificationData> notificationList;

    public NotificationAdapter(Context context, List<NotificationData> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_notification, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("notif", "position" + position);
        holder.textViewTitle.setText(notificationList.get(position).getTitle());
        holder.textViewMessage.setText(notificationList.get(position).getMessage());
        holder.textViewDate.setText(notificationList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTitle;
        public TextView textViewMessage;
        public TextView textViewDate;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            textViewMessage = (TextView) itemView.findViewById(R.id.tvMessage);
            textViewDate = (TextView) itemView.findViewById(R.id.tvDate);

        }
    }
}
