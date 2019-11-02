package com.cookandroid.project3;

import android.support.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MakencRequest extends StringRequest {

    final static private String URL ="http://sg0265.dothome.co.kr/makenc.php";
    private Map<String, String> map;

    public MakencRequest(String userID, String ncCode, String userName, String useremail, String userPhone, String userCompany,String userPhoto,
                         Response.Listener<String> listener){
        super(Method.POST, URL, listener,null);

        map=new HashMap<>();
        map.put("userID", userID);
        map.put("ncCode", ncCode);
        map.put("userName",userName);
        map.put("useremail",useremail);
        map.put("userPhone",userPhone);
        map.put("userCompany",userCompany);
        map.put("userPhoto",userPhoto);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
