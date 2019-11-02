package com.cookandroid.project3;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;

public class NamecardRequest extends StringRequest {
    final static private String URL ="http://sg0265.dothome.co.kr/makenc.php";
    private Map<String, String> map;

    public NamecardRequest(String useriD, String userName, String useremail,
                           String userPhone, String userCompany, String ncCode, String userPhoto ,Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener,null);

        map=new HashMap<>();
        map.put("userID",useriD);
        map.put("userName",userName);
        map.put("useremail",useremail);
        map.put("userPhone",userPhone);
        map.put("userCompany",userCompany);
        map.put("ncCode",ncCode);
        map.put("userPhoto", userPhoto+"");

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }


}
