package com.cookandroid.project3;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.database.Cursor;
import android.widget.Toast;


import java.util.ArrayList;

public class FriendAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<ItemData> data = null;
    private  int listCnt = 0;

    public FriendAdapter(ArrayList<ItemData> odata){
        data = odata;
        listCnt = data.size();
    }

    @Override
    public int getCount() {

        return listCnt;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            final Context context = viewGroup.getContext();
            if(inflater == null){
                inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            view=inflater.inflate(R.layout.friend_item,viewGroup,false);
        }
        final TextView ooName = (TextView)view.findViewById(R.id.textName);
        TextView ooCom = (TextView)view.findViewById(R.id.textCom);
        TextView ooCom1 = (TextView)view.findViewById(R.id.textCom1);
        TextView ooCode = (TextView)view.findViewById(R.id.textCode);
        Button btn_result = (Button)view.findViewById(R.id.btn_result);
        Button btn_del = (Button)view.findViewById(R.id.btn_del);
        btn_del.setOnClickListener(data.get(i).onClickListener);
        btn_result.setOnClickListener(data.get(i).onClickListener);
        ooName.setText("이름:"+data.get(i).Name);
        ooCom.setText("ID:"+data.get(i).Com);
        ooCom1.setText("회사:"+data.get(i).Com1);
        ooCode.setText(data.get(i).ncCode);
        btn_del.setTag("1");
        btn_result.setTag("2");
        view.setTag(""+i);
        return view;
    }

}

