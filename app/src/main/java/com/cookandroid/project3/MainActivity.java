package com.cookandroid.project3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Blob;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private TextView tv_id;
    private ImageView iv,iv1;
    private Button btn_make, btn_search, btn_logout, btn_my, btn_friend;
    EditText et;
    Button testBtn,loadBtn;
    String useriD;
    String userName;
    String useremail;
    String userPhone;
    String userCompany;
    String ncCode;
    String load;
    String userPhoto;
    // private static final int REQUEST_CODE = 0;
    Bitmap iimg,limg;
    Bitmap img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_id = findViewById(R.id.tv_id);
        btn_search=findViewById(R.id.btn_search);
        btn_make=findViewById(R.id.btn_make);
        btn_logout=findViewById(R.id.btn_logout);
        btn_my=findViewById(R.id.btn_my);
        btn_friend = findViewById(R.id.btn_friend);

        // tv_pass = findViewById(R.id.tv_pass);
        //iv = findViewById(R.id.iv);
        /*//iv1 = findViewById(R.id.iv1);
       // testBtn = findViewById(R.id.testBtn);
        //loadBtn = findViewById(R.id.loadBtn);
        //et = findViewById(R.id.et);
        useriD = "14";
        userName = "1";
        useremail = "1";
        userPhone = "1";
        userCompany = "1";
        ncCode = "14";
*/
        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        String userID1 = userID;
        tv_id.setText(userID1);
        //tv_pass.setText(userPass);
        btn_make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MakencActivity.class);
                startActivityForResult(intent, 0);

            }
        });
        btn_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,FrientActivity.class);
                startActivity(intent);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

      /*  Intent intent=getIntent();
        String userID=intent.getStringExtra("userID");
        tv_id.setText(userID);
*/
        btn_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID=tv_id.getText().toString();


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
                                Intent intent=new Intent(MainActivity.this, NameCardActivity.class);
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
                MainRequest mainRequest=new MainRequest(userID, responseListener);
                RequestQueue queue=Volley.newRequestQueue(MainActivity.this);
                queue.add(mainRequest);
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });


       /* iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
           testBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userPhoto =BitMapToByteArray(iimg);
                    Response.Listener<String> responseListener=new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                boolean success=jsonObject.getBoolean("success");
                                if(success){//명함 등록 성공
                                    Toast.makeText(getApplicationContext(),"명함등록 완료",
                                            Toast.LENGTH_SHORT).show();

                                }else{//명함 등록 실패
                                    Toast.makeText(getApplicationContext(),"명함등록 실패",
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    //서버로 volley를 이용해서 요청
                    NamecardRequest namecardRequest=new NamecardRequest(useriD,userName, useremail,userPhone,userCompany,ncCode,userPhoto,
                            responseListener);
                    RequestQueue queue=Volley.newRequestQueue(MainActivity.this);
                    queue.add(namecardRequest);
                }

            });
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //에디텍스트에 현재 입력되어 있는 값을 가져(get)온다
                final String ncCode=et.getText().toString();

                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success=jsonObject.getBoolean("success");
                            if(success){
                                load=jsonObject.getString("userPhoto");
                                limg = StringToBitMap(load);
                                iv1.setImageBitmap(limg);
                                Toast.makeText(getApplicationContext(),"로드 성공",
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"로드 실패",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoadRequest loadRequest=new LoadRequest(ncCode, responseListener);
                RequestQueue queue=Volley.newRequestQueue(MainActivity.this);
                queue.add(loadRequest);
            }
        });
        */
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                try{
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    img = BitmapFactory.decodeStream(in);
                    in.close();
                    iv.setImageBitmap(img);
                    iimg = img;
                    iv1.setImageBitmap(iimg);

                }catch (Exception e){}
            }
            else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this,"사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
  /*  public String BitMapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] arr = baos.toByteArray();
        String image= Base64.encodeToString(arr, Base64.DEFAULT);

        return image;
    }
    public static Bitmap StringToBitMap(String image){
        Log.e("StringToBitMap","StringToBitMap");
        String temp="";
        try{
            //temp = URLDecoder.decode(image,"");
            byte [] encodeByte=Base64.decode(image,Base64.NO_WRAP);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            Log.e("StringToBitMap","good");
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }*/
}
