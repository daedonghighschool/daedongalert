package com.a000webhostapp.daedongalert.daedongalert;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class noticeRequest extends StringRequest {

    public static final String TAG = MealRequest.class.getSimpleName();

    final static private String URL = "https://gifamily.synology.me/notice_daedong.php";

    public noticeRequest(Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
    }
}
