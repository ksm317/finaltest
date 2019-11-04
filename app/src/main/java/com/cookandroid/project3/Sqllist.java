package com.cookandroid.project3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Sqllist extends SQLiteOpenHelper {
    public Sqllist(Context context){
        super(context, "project33DB",null,1); //데이터베이스 생성
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //테이블 생성
        db.execSQL("CREATE TABLE friendTBL(ncCode VARCHAR(20) PRIMARY KEY, userName VARCHAR(10), userID VARCHAR(20), userCompany VARCHAR(20));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //groupTBL이라는 테이블이 이미 존재한다면
        //삭제하고 다시 onCreate()메소드로 생성
        //db.execSQL("DROP TABLE IF EXISTS friendTBL");
        //onCreate(db);
    }
}