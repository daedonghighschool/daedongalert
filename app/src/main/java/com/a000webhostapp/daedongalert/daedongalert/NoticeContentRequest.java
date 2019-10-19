package com.a000webhostapp.daedongalert.daedongalert;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class NoticeContentRequest extends StringRequest {

    public static final String TAG = NoticeContentRequest.class.getSimpleName();

    final static private String URL = "https://gifamily.synology.me/get_content.php";
    private Map<String, String> parameters;

    public NoticeContentRequest(String contentURL, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("url", contentURL);
        Log.d(TAG, "parameter input!");
    }

    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        Log.d(TAG, "parameter get!");
        return parameters;
    }
}
