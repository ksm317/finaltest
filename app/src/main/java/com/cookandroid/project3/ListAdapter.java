package com.cookandroid.project3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<ItemData> data = null;
    private  int listCnt = 0;

    public ListAdapter(ArrayList<ItemData> odata){
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
            view=inflater.inflate(R.layout.listview_item,viewGroup,false);
        }
        TextView ooName = (TextView)view.findViewById(R.id.textName);
        TextView ooCom = (TextView)view.findViewById(R.id.textCom);
        Button btn_result1 = (Button)view.findViewById(R.id.btn_result1);
        btn_result1.setOnClickListener(data.get(i).onClickListener);
        ooName.setText(data.get(i).Name);
        ooCom.setText(data.get(i).Com);
        view.setTag(""+i);
        return view;
    }
}
