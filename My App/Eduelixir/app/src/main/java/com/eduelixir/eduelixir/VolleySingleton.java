package com.eduelixir.eduelixir;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Sandesh on 1/21/2017.
 */

public class VolleySingleton {

    private static VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private static Context context;

    private VolleySingleton(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (volleySingleton == null) {
            volleySingleton = new VolleySingleton(context);
        }
        return volleySingleton;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        Log.d("timetable", "Inside add to request Q");
        requestQueue.add(request);
        Log.d("timetable", "after add to request Q");
    }
}
