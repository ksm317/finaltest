package com.cookandroid.project3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FrientActivity extends AppCompatActivity implements  View.OnClickListener {
    private EditText ncCode_et;
    private Button addFBtn;
    private ListView list_lv;
    SQLiteDatabase sqlDB;
    Sqllist sqllist;
    ArrayList<ItemData> oData;
    FriendAdapter oAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frient);
        ncCode_et = (EditText)findViewById(R.id.ncCode_et);
        addFBtn = (Button) findViewById(R.id.addFBtn);
        list_lv = (ListView)findViewById(R.id.list_lv);

        sqllist = new Sqllist(this);

        addFBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ncCode = ncCode_et.getText().toString();
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success=jsonObject.getBoolean("success");
                            if(success){
                                String userName=jsonObject.getString("userName");
                                String userID=jsonObject.getString("userID");
                                sqlDB = sqllist.getWritableDatabase();
                                sqlDB.execSQL("INSERT INTO friendTBL VALUES('"+ncCode+"','"+userName+"','"+userID+"');");
                                Toast.makeText(getApplicationContext(),"입력됨",Toast.LENGTH_LONG).show();
                                oAdapter.notifyDataSetChanged();
                                onResume();
                                Toast.makeText(getApplicationContext(),"good",
                                        Toast.LENGTH_SHORT).show();
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
                FriendRequest friendRequest=new FriendRequest(ncCode, responseListener);
                RequestQueue queue=Volley.newRequestQueue(FrientActivity.this);
                queue.add(friendRequest);


                //sqlDB.execSQL("INSERT INTO friendTBL VALUES");
            }
        });
        sqlDB = sqllist.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM friendTBL;",null);
        //String strName="이름";
        //String strCom="회사";

        oData = new ArrayList<>();
        while(cursor.moveToNext()){
            ItemData oItem = new ItemData();
            oItem.Name=cursor.getString(1);
            oItem.Com=cursor.getString(2);
            oItem.onClickListener = this;
            oData.add(oItem);
        }
        oAdapter = new FriendAdapter(oData);
        list_lv.setAdapter(oAdapter);
        cursor.close();
        sqlDB.close();
    }

    @Override
    public void onClick(View view) {
        int Vtag = Integer.parseInt((String)view.getTag());
        View ParentView = (View) view.getParent();
        final TextView oTextId = (TextView)ParentView.findViewById(R.id.textCom);
        String position = (String)ParentView.getTag();
        switch (Vtag){
            case 1:
                AlertDialog.Builder Dialog = new AlertDialog.Builder(this,android.R.style.Theme_DeviceDefault_Light_Dialog);
                String strMsg = "정말 삭제 하시겠습니까?";
                Dialog.setMessage(strMsg)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sqlDB = sqllist.getWritableDatabase();
                                sqlDB.execSQL("DELETE FROM friendTBL WHERE userID = '"+oTextId.getText().toString()+"';");
                                Toast.makeText(getApplicationContext(),"삭제됨",Toast.LENGTH_LONG).show();
                                sqlDB.close();
                                oAdapter.notifyDataSetChanged();
                                onResume();
                            }
                        })
                        .setNegativeButton("취소",null)
                        .show();

            case 2:
                String userID = oTextId.getText().toString();
                Intent intent=new Intent(FrientActivity.this, ResultActivity.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
        }
    }

   /* public void list_view(){
        sqlDB = sqllist.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM friendTBL;",null);
        //String strName="이름";
        //String strCom="회사";
        ItemData oItem = new ItemData();
        while(cursor.moveToNext()){
            oItem.Name=cursor.getString(1);
            oItem.Com=cursor.getString(2);
            oData.add(oItem);
        }
        FriendAdapter oAdapter = new FriendAdapter(oData);
        list_lv.setAdapter(oAdapter);

    }
    */
}
