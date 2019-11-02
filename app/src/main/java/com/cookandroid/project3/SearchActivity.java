package com.cookandroid.project3;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements  View.OnClickListener {

    private EditText ed_search;
    private Button btn_search;
    private ListView resultlv;
    private boolean sw;
    private String usID;
    ItemData oItem;
    SQLiteDatabase sqlDB;
    Sqllist sqllist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ed_search = findViewById(R.id.ed_search);
        btn_search = findViewById(R.id.btn_search);
        resultlv = findViewById(R.id.reultlv);
        final ArrayList<ItemData> oData = new ArrayList<>();
        oItem = new ItemData();

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = ed_search.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // String TAG_JSON = "";
                        //String TAG_com ="userCompany";
                        String TAG_name = "userName";
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            //  JSONArray jsonArray = new JSONArray(TAG_JSON);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                String userID = jsonObject.getString("userID");
                                String userName = jsonObject.getString("userName");
                                String useremail = jsonObject.getString("useremail");
                                String userPhone = jsonObject.getString("userPhone");
                                String userCompany = jsonObject.getString("userCompany");
                                //  for(int i=0;i<jsonArray.length();i++){
                                //   JSONObject item = jsonArray.getJSONObject(i);
                                //String name = item.getString(TAG_name);
                                //String com = item.getString(TAG_com);

                                oItem.Name = userName;
                                oItem.Com = userID;

                                // }
                                ListAdapter oAdapter = new ListAdapter(oData);
                                resultlv.setAdapter(oAdapter);
                                Toast.makeText(getApplicationContext(), "good",
                                        Toast.LENGTH_SHORT).show();
                           /*     Intent intent=new Intent(SearchActivity.this, ResultActivity.class);
                                intent.putExtra("userID",userID);
                                intent.putExtra("userName",userName);
                                intent.putExtra("useremail",useremail);
                                intent.putExtra("userPhone",userPhone);
                                intent.putExtra("userCompany",userCompany);
                                startActivity(intent);
                                */
                            } else {
                                Toast.makeText(getApplicationContext(), "fail",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                };
                SearchRequest searchRequest = new SearchRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SearchActivity.this);
                queue.add(searchRequest);
            }
        });
        oItem.onClickListener = this;
        oData.add(oItem);

    }

    @Override
    public void onClick(View view) {
        View ParentView = (View) view.getParent();
        final TextView oTextId = (TextView) ParentView.findViewById(R.id.textCom);
        String position = (String) ParentView.getTag();
        usID = oTextId.getText().toString();
        sw = false;
        sqllist = new Sqllist(this);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        String ncCode = jsonObject.getString("ncCode");

                        sqlDB = sqllist.getReadableDatabase();
                        Cursor cursor;
                        cursor = sqlDB.rawQuery("SELECT ncCode FROM friendTBL;", null);
                        while (cursor.moveToNext()) {
                            String NcCode = cursor.getString(0);
                            if (ncCode.equals(NcCode)) {
                                sw = true;
                                break;
                            } else {
                                Log.e("tag", "shit" + NcCode);
                            }
                        }
                        if (sw) {
                            Intent intent = new Intent(SearchActivity.this, ResultActivity.class);
                            intent.putExtra("userID", usID);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(),"접근 권한이 없습니다.",Toast.LENGTH_SHORT).show();
                            /*AlertDialog.Builder Dialog = new AlertDialog.Builder(getApplicationContext(), android.R.style.Theme_DeviceDefault_Light_Dialog);
                            String strMsg = "접근 권한이 없습니다.";
                            Dialog.setMessage(strMsg)
                                    .setPositiveButton("확인", null)
                                    .show();
                                    */
                        }
                        cursor.close();
                        sqlDB.close();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };
        NccodeCheckRequest nccodeCheckRequest = new NccodeCheckRequest(usID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(SearchActivity.this);
        queue.add(nccodeCheckRequest);
        // boolean on = CheckFriend(userID);
        /*if(on){
            Intent intent=new Intent(SearchActivity.this, ResultActivity.class);
            intent.putExtra("userID",userID);
            startActivity(intent);

        }else{
            AlertDialog.Builder Dialog = new AlertDialog.Builder(this,android.R.style.Theme_DeviceDefault_Light_Dialog);
            String strMsg = "접근 권한이 없습니다.";
            Dialog.setMessage(strMsg)
                    .setPositiveButton("확인",null)
                    .show();
        }
            }/*
    public boolean CheckFriend(String userId){
        String userID = userId;
        boolean sw = false;
        sqllist = new Sqllist(this);
        Response.Listener<String> responseListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    boolean success=jsonObject.getBoolean("success");
                    if(success){
                        String ncCode = jsonObject.getString("ncCode");

                        sqlDB = sqllist.getReadableDatabase();
                        Cursor cursor;
                        cursor = sqlDB.rawQuery("SELECT ncCode FROM friendTBL;",null);
                        while(cursor.moveToNext()){
                            String NcCode=cursor.getString(0);
                            if(ncCode.equals(NcCode)){
                                sw =true;
                                break;
                            }
                            else{
                                Log.e("tag","shit"+NcCode);
                            }

                        }
                        cursor.close();
                        sqlDB.close();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };
        NccodeCheckRequest nccodeCheckRequest=new NccodeCheckRequest(userID, responseListener);
        RequestQueue queue=Volley.newRequestQueue(SearchActivity.this);
        queue.add(nccodeCheckRequest);

        return sw;
    }
    */
    }
}
