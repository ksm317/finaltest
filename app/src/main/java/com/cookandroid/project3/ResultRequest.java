package com.cookandroid.project3;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;

public class ResultRequest extends StringRequest {
    final static private String URL = "http://sg0265.dothome.co.kr/Result.php";
    private Map<String, String> map;

    public ResultRequest( String ncCode,
                          Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("ncCode", ncCode);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}