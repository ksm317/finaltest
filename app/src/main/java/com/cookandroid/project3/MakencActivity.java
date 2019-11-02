package com.cookandroid.project3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class MakencActivity extends AppCompatActivity {

    private EditText ed_id, ed_ncname, ed_ncemail, ed_ncphone, ed_company, ed_nccode;
    private Button btn_ncmaking;
    private ImageView userPhoto;
    private static final int REQUEST_CODE = 0;
    private Bitmap img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makenc);

        ed_id=findViewById(R.id.ed_id);
        ed_nccode=findViewById(R.id.ed_nccode);
        ed_ncname=findViewById(R.id.ed_ncname);
        ed_ncemail=findViewById(R.id.ed_ncemail);
        ed_ncphone=findViewById(R.id.ed_ncphone);
        ed_company=findViewById(R.id.ed_company);
        btn_ncmaking=findViewById(R.id.btn_ncmaking);
        userPhoto = findViewById(R.id.userPhoto);
        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        btn_ncmaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID=ed_id.getText().toString();
                String ncCode=ed_nccode.getText().toString();
                String userName=ed_ncname.getText().toString();
                String useremail=ed_ncemail.getText().toString();
                String userPhone=ed_ncphone.getText().toString();
                String userCompany=ed_company.getText().toString();
                String userPhoto = BitMapToByteArray(img);

                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success=jsonObject.getBoolean("success");
                            if(success){//명함 생성

                                Toast.makeText(getApplicationContext(),"명함 생성",
                                        Toast.LENGTH_SHORT).show();

                                        setResult(0);
                                        finish();
                                //Intent intent=new Intent(MakencActivity.this, MainActivity.class);

                                //startActivity(intent);
                            }else{//회원 등록 실패
                                Toast.makeText(getApplicationContext(),"생성 실패",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                };
                //서버로 volley를 이용해서 요청
                MakencRequest makencRequest=new MakencRequest(userID, ncCode, userName, useremail, userPhone, userCompany, userPhoto,responseListener);
                RequestQueue queue=Volley.newRequestQueue(MakencActivity.this);
                queue.add(makencRequest);

            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                try{
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    img = BitmapFactory.decodeStream(in);
                    in.close();
                    userPhoto.setImageBitmap(img);

                }catch (Exception e){}
            }
            else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this,"사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
    public String BitMapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] arr = baos.toByteArray();
        String image= Base64.encodeToString(arr, Base64.DEFAULT);

        return image;
    }
}
