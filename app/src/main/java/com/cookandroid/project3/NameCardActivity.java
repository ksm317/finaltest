package com.cookandroid.project3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class NameCardActivity extends AppCompatActivity {

    private TextView see_id, see_name, see_company, see_ncCode, see_email, see_phone;
    private Button btn_fix, btn_home;
    private ImageView iv1;
    Bitmap limg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_card);

        see_id=findViewById(R.id.see_id);
        see_name=findViewById(R.id.see_name);
        see_company=findViewById(R.id.see_company);
        see_ncCode=findViewById(R.id.see_ncCode);
        see_email=findViewById(R.id.see_email);
        see_phone=findViewById(R.id.see_phone);
        btn_fix=findViewById(R.id.btn_fix);
        btn_home=findViewById(R.id.btn_home);
        iv1=findViewById(R.id.iv1);

        Intent intent=getIntent();
        final String userID=intent.getStringExtra("userID");

        Response.Listener<String> responseListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        String userID=jsonObject.getString("userID");
                        String ncCode=jsonObject.getString("ncCode");
                        String userName=jsonObject.getString("userName");
                        String useremail=jsonObject.getString("useremail");
                        String userPhone=jsonObject.getString("userPhone");
                        String userCompany=jsonObject.getString("userCompany");
                        String userPhoto = jsonObject.getString("userPhoto");
                        limg = StringToBitMap(userPhoto);
                        iv1.setImageBitmap(limg);
                        see_id.setText(userID);
                        see_ncCode.setText(ncCode);
                        see_name.setText(userName);
                        see_email.setText(useremail);
                        see_phone.setText(userPhone);
                        see_company.setText(userCompany);
                        Toast.makeText(getApplicationContext(), "good",
                                Toast.LENGTH_SHORT).show();

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
        MainRequest nameRequest=new MainRequest(userID, responseListener);
        RequestQueue queue=Volley.newRequestQueue(NameCardActivity.this);
        queue.add(nameRequest);
        // String userPhoto=intent.getStringExtra("userPhoto");
        //limg = StringToBitMap(userPhoto);
        //iv1.setImageBitmap(limg);
       /* see_id.setText(userID);
        see_ncCode.setText(ncCode);
        see_name.setText(userName);
        see_email.setText(useremail);
        see_phone.setText(userPhone);
        see_company.setText(userCompany);
*/
        btn_fix.setOnClickListener(new View.OnClickListener() {
            //final String userID = see_id.getText().toString();
            public void onClick(View view) {
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success=jsonObject.getBoolean("success");
                            if(success){
                                String userID=jsonObject.getString("userID");
                                Toast.makeText(getApplicationContext(),"good",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(NameCardActivity.this, FixActivity.class);
                                intent.putExtra("userID",userID);
                                startActivityForResult(intent, 0);
                            }else{
                                Toast.makeText(getApplicationContext(),"fail",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                NameRequest nameRequest=new NameRequest(userID, responseListener);
                RequestQueue queue=Volley.newRequestQueue(NameCardActivity.this);
                queue.add(nameRequest);
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(0);
                finish();

            }
        });


    }
    public static Bitmap StringToBitMap(String image){
        Log.e("StringToBitMap","StringToBitMap");
        String temp="";
        try{
            //temp = URLDecoder.decode(image,"");
            byte [] encodeByte1=Base64.decode(image,Base64.NO_WRAP);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte1, 0, encodeByte1.length);
            Log.e("StringToBitMap","good");
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}
