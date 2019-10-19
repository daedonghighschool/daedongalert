package com.a000webhostapp.daedongalert.daedongalert;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    public static final String TAG = LoginRequest.class.getSimpleName();

    final static private String URL = "https://gifamily.synology.me/Login.php";
    private Map<String, String> parameters;

    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        Log.d(TAG, "parameter input!");
}

    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        Log.d(TAG, "parameter get!");
        return parameters;
    }
}
