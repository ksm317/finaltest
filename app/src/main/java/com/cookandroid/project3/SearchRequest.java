package com.cookandroid.project3;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SearchRequest extends StringRequest {

    final static private String URL ="http://sg0265.dothome.co.kr/Search.php";
    private Map<String, String> map;

    public SearchRequest(String userName, Response.Listener<String> listener){
        super(Method.POST, URL, listener,null);

        map=new HashMap<>();
        map.put("userName",userName);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
