package com.eduelixir.eduelixir;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://192.168.43.110/data.php";
    private static final String REFRESH_DATA_URL = "http://192.168.43.110/refresh_data.php";
    private Map<String, String> params;

    public LoginRequest(String username, String password, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, errorListener);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
    }

    public LoginRequest(String usn, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, REFRESH_DATA_URL, listener, errorListener);
        params = new HashMap<>();
        params.put("usn", usn);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
