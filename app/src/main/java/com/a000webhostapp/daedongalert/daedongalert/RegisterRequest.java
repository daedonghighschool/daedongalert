package com.a000webhostapp.daedongalert.daedongalert;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    public static final String TAG = RegisterActivity.class.getSimpleName();

    final static private String URL = "https://gifamily.synology.me/Register.php";
    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPassword, String userName, int userKind, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userName", userName);
        parameters.put("userKind", userKind + "");
        Log.d(TAG, "parameter input!");
    }

    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        Log.d(TAG, "parameter get!");
        return parameters;
    }
}
