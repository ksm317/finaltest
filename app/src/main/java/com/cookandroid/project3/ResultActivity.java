package com.cookandroid.project3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import javax.xml.transform.Result;

import static com.cookandroid.project3.NameCardActivity.StringToBitMap;

public class ResultActivity extends AppCompatActivity {

    private TextView sc_name, sc_id, sc_ncCode, sc_phone, sc_email, sc_company;
    private Button btn_past;
    Bitmap limg;
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        sc_id=findViewById(R.id.sc_id);
        sc_ncCode=findViewById(R.id.sc_ncCode);
        sc_name=findViewById(R.id.sc_name);
        sc_phone=findViewById(R.id.sc_phone);
        sc_email=findViewById(R.id.sc_email);
        sc_company=findViewById(R.id.sc_company);
        btn_past=findViewById(R.id.btn_past);
        imageView2 =(ImageView)findViewById(R.id.imageView2);
        btn_past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ResultActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });


        Intent intent=getIntent();
        String ncCode=intent.getStringExtra("ncCode");
        //String userID = intent.getStringExtra("userID");
        /*
        String userID=intent.getStringExtra("userID");
        String ncCode=intent.getStringExtra("ncCode");

        String userPhone=intent.getStringExtra("userPhone");
        String useremail=intent.getStringExtra("useremail");
        String userCompany=intent.getStringExtra("userCompany");
        sc_name.setText(userName);*/
        //sc_id.setText(userID);
        /*
        sc_ncCode.setText(ncCode);

        sc_phone.setText(userPhone);
        sc_email.setText(useremail);
        sc_company.setText(userCompany);
*/
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
                        imageView2.setImageBitmap(limg);
                        sc_id.setText(userID);
                        sc_ncCode.setText(ncCode);
                        sc_name.setText(userName);
                        sc_email.setText(useremail);
                        sc_phone.setText(userPhone);
                        sc_company.setText(userCompany);
                        sc_phone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String tel = "tel:"+sc_phone.getText().toString();
                                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
                            }
                        });
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
        ResultRequest resultRequest=new ResultRequest(ncCode, responseListener);
        RequestQueue queue=Volley.newRequestQueue(ResultActivity.this);
        queue.add(resultRequest);

    }
}
