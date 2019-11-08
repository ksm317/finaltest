package com.cookandroid.project3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.app.Dialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class RegisterAcivitiy extends AppCompatActivity {
    private EditText et_id,et_pass,et_name,et_email,et_birth;
    private Button btn_register;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//액티비티 시작시 처음으로 실행되는 생명주기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_acivitiy);

        et_id=findViewById(R.id.et_id);
        et_pass=findViewById(R.id.et_pass);
        et_name=findViewById(R.id.et_name);
        et_email=findViewById(R.id.et_email);
        et_birth=findViewById(R.id.et_birth);
        et_birth.setInputType(0);
        btn_register=findViewById(R.id.btn_register);

        //에디 텍스트 현재 입력 값 가져오기
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID=et_id.getText().toString();
                String userPass=et_pass.getText().toString();
                String userName=et_name.getText().toString();
                String useremail=et_email.getText().toString();
                //int userbirth=Integer.parseInt(et_birth.getText().toString());
                String userbirth2=(et_birth.getText().toString());
                if(userID.getBytes().length<=0 ||userPass.getBytes().length<=0||userName.getBytes().length<=0||useremail.getBytes().length<=0 ){

                    Toast.makeText(RegisterAcivitiy.this,"모든 정보를 입력해주세요!",Toast.LENGTH_SHORT).show();
                    return;

                } else{
                    //이메일형식체크
                    if(!android.util.Patterns.EMAIL_ADDRESS.matcher(useremail).matches())
                    {
                        Toast.makeText(RegisterAcivitiy.this,"이메일 형식이 아닙니다",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //아이디 유효성
                    if(!Pattern.matches("^[a-zA-Z0-9].{4,20}$", userID))
                    {
                        Toast.makeText(RegisterAcivitiy.this,"올바른 id가 아닙니다(영문, 숫자 4~20자).",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //비밀번호 유효성
                    if(!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{4,20}$", userPass))
                    {
                        Toast.makeText(RegisterAcivitiy.this,"비밀번호 형식(4~20자,특수 문자 포함)을 지켜주세요.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }



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
                if(userbirth2.getBytes().length<=0) {
                    userbirth2="20000101";
                }
                int userbirth = Integer.parseInt(userbirth2);
                RegisterRequest registerRequest=new RegisterRequest(userID,userPass,userName,useremail,userbirth,
                        responseListener);
                RequestQueue queue=Volley.newRequestQueue(RegisterAcivitiy.this);
                queue.add(registerRequest);
            }
        });
        //날짜칸 클릭시 자동으로 데이트 피커를 띄어줌
        et_birth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    showDate();
                }
            }
        });


    }
    void showDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                //날짜를 19990101 식으로 받아옴
                et_birth.setText(String.format("%d%02d%02d", year,month + 1, dayOfMonth));



            }
        },2000, 4, 1);

        datePickerDialog.show();


    }
}
