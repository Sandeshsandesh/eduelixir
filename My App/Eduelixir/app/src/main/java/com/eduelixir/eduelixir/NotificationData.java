package com.eduelixir.eduelixir;

/**
 * Created by Sandesh on 2/11/2017.
 */
public class NotificationData {
    private String title;
    private String message;
    private String date;

    public NotificationData(String title, String message, String date) {
        this.title = title;
        this.message = message;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
