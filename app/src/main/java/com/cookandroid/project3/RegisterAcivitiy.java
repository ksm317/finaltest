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

public class RegisterAcivitiy extends AppCompatActivity {

    private EditText et_id,et_pass,et_name,et_email,et_birth;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//액티비티 시작시 처음으로 실행되는 생명주기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_acivitiy);

        et_id=findViewById(R.id.et_id);
        et_pass=findViewById(R.id.et_pass);
        et_name=findViewById(R.id.et_name);
        et_email=findViewById(R.id.et_email);
        et_birth=findViewById(R.id.et_birth);
        btn_register=findViewById(R.id.btn_register);

        //에디 텍스트 현재 입력 값 가져오기
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID=et_id.getText().toString();
                String userPass=et_pass.getText().toString();
                String userName=et_name.getText().toString();
                String useremail=et_email.getText().toString();
                int userbirth=Integer.parseInt(et_birth.getText().toString());

                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success=jsonObject.getBoolean("success");
                            if(success){//회원 등록 성공
                                Toast.makeText(getApplicationContext(),"회원등록 완료",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(RegisterAcivitiy.this,LoginActivity.class);
                                startActivity(intent);
                            }else{//회원 등록 실패
                                Toast.makeText(getApplicationContext(),"회원등록 실패",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                //서버로 volley를 이용해서 요청
                RegisterRequest registerRequest=new RegisterRequest(userID,userPass,userName,useremail,userbirth,
                        responseListener);
                RequestQueue queue=Volley.newRequestQueue(RegisterAcivitiy.this);
                queue.add(registerRequest);
            }
        });

    }
}