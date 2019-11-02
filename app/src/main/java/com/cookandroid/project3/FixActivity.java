package com.cookandroid.project3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class FixActivity extends AppCompatActivity {

    private EditText fix_ncCode, fix_name, fix_email, fix_phone, fix_company;
    private TextView wh_id;
    private Button btn_fix;
    String ncCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix);

        wh_id=findViewById(R.id.wh_id);
        fix_ncCode=(EditText)findViewById(R.id.fix_ncCode1);
        fix_name=(EditText)findViewById(R.id.fix_name1);
        fix_email=(EditText)findViewById(R.id.fix_email1);
        fix_phone=(EditText)findViewById(R.id.fix_phone1);
        fix_company=(EditText)findViewById(R.id.fix_company1);
        btn_fix=findViewById(R.id.btn_fix1);

        Intent intent=getIntent();
        String userID=intent.getStringExtra("userID");
        wh_id.setText(userID);

        btn_fix.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //에디텍스트에 현재 입력되어 있는 값을 가져(get)온다
                String userID=wh_id.getText().toString();
                String ncCode=fix_ncCode.getText().toString();
                String userName=fix_name.getText().toString();
                String useremail=fix_email.getText().toString();
                String userPhone=fix_phone.getText().toString();
                String userCompany=fix_company.getText().toString();
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success=jsonObject.getBoolean("success");
                            if(success){//로그인 성공
                                Toast.makeText(getApplicationContext(),"수정 완료",
                                        Toast.LENGTH_SHORT).show();
                                        setResult(0);
                                finish();
                                //Intent intent=new Intent(FixActivity.this, MainActivity.class);
                                //startActivity(intent);
                            }else{//로그인 실패
                                Toast.makeText(getApplicationContext(),"수정 실패",
                                        Toast.LENGTH_SHORT).show();
                                // Intent intent=new Intent(FixActivity.this, MainActivity.class);
                                //startActivity(intent);
                                setResult(0);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                FixRequest fixRequest=new FixRequest(userID, ncCode, userName, useremail, userPhone,
                        userCompany,responseListener);
                RequestQueue queue=Volley.newRequestQueue(FixActivity.this);
                queue.add(fixRequest);

            }
        });
    }
}
