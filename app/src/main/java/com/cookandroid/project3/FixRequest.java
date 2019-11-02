package com.cookandroid.project3;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FixRequest extends StringRequest {
    //서버 URL 설정(php파일 연동)
    final static private String URL ="http://sg0265.dothome.co.kr/Fix.php";
    private Map<String, String> map;

    public FixRequest(String userID, String ncCode, String userName, String useremail, String userPhone,
                      String userCompany,
                      Response.Listener<String> listener){
        super(Method.POST, URL, listener,null);

        map=new HashMap<>();
        map.put("userID",userID);
        map.put("ncCode",ncCode);
        map.put("useremail",useremail);
        map.put("userName",userName);
        map.put("userPhone",userPhone);
        map.put("userCompany",userCompany);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
