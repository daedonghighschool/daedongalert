package com.a000webhostapp.daedongalert.daedongalert;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MealRequest extends StringRequest {

    public static final String TAG = MealRequest.class.getSimpleName();

    final static private String URL = "https://gifamily.synology.me/meal_api_custom.php";
    private Map<String, String> parameters;

    public MealRequest(String countryCode, String schulCode, String insttNm, String schulCrseScCode, String schMmealScCode, String schYmd, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("countryCode", countryCode);
        parameters.put("schulCode", schulCode);
        parameters.put("insttNm", insttNm);
        parameters.put("schulCrseScCode", schulCrseScCode);
        parameters.put("schMmealScCode", schMmealScCode);
        parameters.put("schYmd", schYmd);
        Log.d(TAG, "parameter input!");
    }

    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        Log.d(TAG, "parameter get!");
        return parameters;
    }
}
