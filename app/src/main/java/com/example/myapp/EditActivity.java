package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditActivity extends AppCompatActivity {
    private int id;
    private int kk;
    private Float mm;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Log.i("aaa","on EditActivity");

        RadioGroup radioGroup;
        String date_show=getIntent().getStringExtra("date_show");
        String kind_show= getIntent().getStringExtra("kind_show");
        String mon_show=getIntent().getStringExtra("mon_show");
        String detail_show=getIntent().getStringExtra("detail_show");
        String id_show= getIntent().getStringExtra("id_show");
        id= Integer.parseInt(id_show);
        Log.i("aaa",date_show+"  "+kind_show+"  "+ mon_show+"  "+detail_show+"  "+id_show);

        EditText money =findViewById(R.id.money_sum_edit);
        TextView detail =findViewById(R.id.add_detail_edit);
        radioGroup=findViewById(R.id.bottomKind_edit);
        money.setText(mon_show);
        mm= Float.valueOf(mon_show);
        detail.setText(detail_show);

        if(kind_show.equals("支出")) {
            kk=1;
            radioGroup.check(R.id.radio_out_edit);
        }
        else if(kind_show.equals("收入")){
            kk=2;
            radioGroup.check(R.id.radio_in_edit);
        }
    }

    public void edit(View view) {
        RadioGroup radioGroup1=findViewById(R.id.bottomKind_edit);
        int flag=-1;
        int kind=0;
        switch (radioGroup1.getCheckedRadioButtonId()){
            case R.id.radio_out_edit:
                kind=1;
                break;
            case R.id.radio_in_edit:
                kind=2;
                break;
            default:
                flag=1;
                break;
        }
        EditText money =findViewById(R.id.money_sum_edit);
        TextView detail =findViewById(R.id.add_detail_edit);
        if (money.length() == 0) {
            Toast.makeText(this, "输入不能为空！", Toast.LENGTH_SHORT).show();
        }
        else if(flag==1){
            Toast.makeText(this, "请选择财务类型！", Toast.LENGTH_SHORT).show();
        }
        else {
            Float m = Float.parseFloat(money.getText().toString());
            String d = detail.getText().toString();
            Log.i("aaa", "类型：" + kind + " 金额：" + m + " 备注：" + d);
            //  修改时默认取当前时间，还可以再继续改进为可自定义时间
            Date now = Calendar.getInstance().getTime();
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            String date = sd.format(now);


            RecordItem db = new RecordItem(kind, m, d, date);
            DBManager manager = new DBManager(this);
            manager.delete(id);
            manager.add(db);//修改

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(getResources().getString(R.string.mess));

            builder.setMessage(getResources().getString(R.string.success));
            builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {  //这个是设置确定按钮

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    //刷新
                    Hide();
                    Intent intent = new Intent(EditActivity.this, FrameActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
            AlertDialog b = builder.create();
            b.show();
        }
    }

    public void Hide() {
        InputMethodManager inputmethodmanager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputmethodmanager.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                inputmethodmanager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    }
