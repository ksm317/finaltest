package com.cookandroid.project3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText et_id,et_pass;
    private Button btn_login,btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id=findViewById(R.id.et_id);
        et_pass=findViewById(R.id.et_pass);
        btn_login=findViewById(R.id.btn_login);
        btn_register=findViewById(R.id.btn_register);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterAcivitiy.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //에디텍스트에 현재 입력되어 있는 값을 가져(get)온다
                final String userID=et_id.getText().toString();
                String userPass=et_pass.getText().toString();

                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success=jsonObject.getBoolean("success");
                            if(success){//로그인 성공
                                String userID=jsonObject.getString("userID");
                                String userPass=jsonObject.getString("userPassword");

                                Toast.makeText(getApplicationContext(),"로그인 완료",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                intent.putExtra("userID",userID);
                                intent.putExtra("userPass",userPass);
                                startActivity(intent);
                            }else{//로그인 실패
                                Toast.makeText(getApplicationContext(),"로그인 실패",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest=new LoginRequest(userID,userPass, responseListener);
                RequestQueue queue=Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });



    }
         public void onBackPressed() {
            et_pass.setText(null);

            et_id.setText(null);
            /* 뒤로가기가 불가능해지며 입력한 ID, 패스워드를 지워준다. */
    }
}
