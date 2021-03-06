package com.eduelixir.eduelixir;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Sandesh on 1/28/2017.
 */

public class FcmInstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String recent_token = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences fcmDetails= getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = fcmDetails.edit();
        editor.putString(getString(R.string.FCM_TOKEN),recent_token);
        editor.commit();
    }
}
