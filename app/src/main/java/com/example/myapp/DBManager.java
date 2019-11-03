package com.example.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DBManager {
    private DBHelper dbHelper;
    private String TBNAME;

    public DBManager(Context context){
        dbHelper=new DBHelper(context);
        TBNAME=DBHelper.TB_NAME;
    }
    public void add (RecordItem item ){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        Cursor cursor =db.query(TBNAME,null,null,null,null,null,null);
        values.put("kind",item.getKind());
        values.put("money",item.getMoney());
        values.put("detail",item.getDetail());
        values.put("date",item.getDate());
        Log.i("aaa","on DBManager kind:"+item.getKind());
        float in=0,out=0;
        if(cursor!=null) {
            while (cursor.moveToNext()) {
                out = cursor.getFloat(cursor.getColumnIndex("OUT_SUM"));
                in = cursor.getFloat(cursor.getColumnIndex("IN_SUM"));
            }
            cursor.close();
        }
        if (item.getKind() == 1) {
            values.put("out_sum", out + item.getMoney());
            values.put("in_sum", in);
        }
        if (item.getKind() == 2) {
            values.put("out_sum", out);
            values.put("in_sum", in+ item.getMoney());
        }

        db.insert(TBNAME,null,values);
        db.close();
    }

    public List<RecordItem> listAll(){
        List<RecordItem> recordList =  null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor =db.query(TBNAME,null,null,null,null,null,null);
        if(cursor!=null){
            recordList=new ArrayList<RecordItem>();
            while (cursor.moveToNext()){
                RecordItem item = new RecordItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setKind(cursor.getInt(cursor.getColumnIndex("KIND")));
                item.setMoney(cursor.getFloat(cursor.getColumnIndex("MONEY")));
                item.setDetail(cursor.getString(cursor.getColumnIndex("DETAIL")));
                item.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
                recordList.add(item);
            }
            cursor.close();
        }
        db.close();
        return recordList;
    }

    public void addAll(List<RecordItem> list) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor =db.query(TBNAME,null,null,null,null,null,null);
        for(RecordItem item:list){
            ContentValues values=new ContentValues();
            values.put("kind",item.getKind());
            values.put("money",item.getMoney());
            values.put("detail",item.getDetail());
            values.put("date",item.getDate());
            if(item.getKind()==1){
                values.put("out_sum",cursor.getFloat(cursor.getColumnIndex("out_sum"))+item.getMoney());
            }
            if(item.getKind()==2){
                values.put("in_sum",cursor.getFloat(cursor.getColumnIndex("in_sum"))+item.getMoney());
            }
            db.insert(TBNAME,null,values);
        }
        db.close();
    }
    public void deleteAll() {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(TBNAME,null,null);
        db.close();
    }

    public float getOut_sum() {
        float sum=0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor =db.query(TBNAME,null,null,null,null,null,null);
        if(cursor!=null){
            while (cursor.moveToNext()) {
                sum = cursor.getFloat(cursor.getColumnIndex("OUT_SUM"));
            }
            cursor.close();
        }
        db.close();
        return sum;
    }

    public float getIn_sum() {
        float sum=0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor =db.query(TBNAME,null,null,null,null,null,null);
        if(cursor!=null){
            while (cursor.moveToNext()) {
                sum = cursor.getFloat(cursor.getColumnIndex("IN_SUM"));
            }
            cursor.close();
        }
        db.close();
        return sum;
    }

    public void delete(int click_id) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(TBNAME,"ID=?",new String[] {String.valueOf(click_id)});
        db.close();
    }
}

