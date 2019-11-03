package com.example.myapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class show extends AppCompatActivity {
    String id;
    String date;
    String kind;
    String mon;
    String detail;
    private Button btnedit,btndelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        date=getIntent().getStringExtra("date");
        kind=getIntent().getStringExtra("kind").substring(0,2);
        mon=getIntent().getStringExtra("mon").substring(1);
        detail=getIntent().getStringExtra("detail");
        id=getIntent().getStringExtra("id");

        ((TextView)findViewById(R.id.date)).setText(date);
        ((TextView)findViewById(R.id.kind)).setText(kind);
        ((TextView)findViewById(R.id.mon)).setText(mon);
        ((TextView)findViewById(R.id.detail)).setText(detail);

        btnedit=findViewById(R.id.btnedit);
        btndelete=findViewById(R.id.btndelete);



    }

    public void deletebyid(View view) {
        final DBManager manager=new DBManager(this);
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("确认删除").setMessage("删除后不可恢复！")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        manager.delete(Integer.parseInt(id));
                        Intent intent = new Intent(show.this,FrameActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }).setNegativeButton("取消",null);
        builder.create().show();
    }

    public void edit(View view) {
        Log.i("aaa","on edit");
        Intent intent = new Intent(this,EditActivity.class);
        intent.putExtra("date_show",date);
        intent.putExtra("mon_show",mon);
        intent.putExtra("kind_show",kind);
        intent.putExtra("detail_show",detail);
        intent.putExtra("id_show",id);
        Log.i("aaa","on 1");
        startActivity(intent);
        Log.i("aaa","on 2");
    }
}
