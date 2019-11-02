package com.cookandroid.project3;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class NameRequest extends StringRequest {

    final static private String URL ="http://sg0265.dothome.co.kr/Namecd.php";
    private Map<String, String> map;

    public NameRequest(String userID, Response.Listener<String> listener){
        super(Method.POST, URL, listener,null);

        map=new HashMap<>();
        map.put("userID",userID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
