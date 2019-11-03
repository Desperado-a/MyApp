package com.example.myapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class AddFragment extends Fragment {
    private RadioButton radio_out,radio_in;
    private RadioGroup radioGroup;
    private Button sumbit;
    private TextView submittv;

    int kind;
    float m;
    String date,d;
    int id;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        return inflater.inflate(R.layout.frame_add,container);
    }
    public void  onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);

        final DBManager manager = new  DBManager(getContext());
        submittv=getView().findViewById(R.id.submittv);
        submittv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("aaa","on  textview click");
                radioGroup = getView().findViewById(R.id.bottomKind);
                int flag=-1;
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.radio_out:
                        kind = 1;
                        break;
                    case R.id.radio_in:
                        kind = 2;
                        break;
                    default:
                        flag=1;
                        break;
                }
                EditText money = getView().findViewById(R.id.money_sum);
                TextView detail = getView().findViewById(R.id.add_detail);
                if (money.length() == 0) {
                    Toast.makeText(getContext(), "输入不能为空！", Toast.LENGTH_SHORT).show();
                }
               else if(flag==1){
                  Toast.makeText(getContext(), "请选择财务类型！", Toast.LENGTH_SHORT).show();
                }
                else{
                m = Float.parseFloat(money.getText().toString());
                d = detail.getText().toString();
                Log.i("aaa", "类型：" + kind + " 金额：" + m + " 备注：" + d);
                //获取当前时间
                Date now = Calendar.getInstance().getTime();
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                date = sd.format(now);
                Log.i("aaa", "当前时间" + date);


                RecordItem db = new RecordItem(kind, m, d, date);
                manager.add(db);
                Log.i("aaa", "提交成功");

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle(getResources().getString(R.string.mess));

                builder.setMessage(getResources().getString(R.string.success));
                builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {  //这个是设置确定按钮

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //刷新
                        Hide();
                        Intent intent = new Intent(getContext(), FrameActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
                AlertDialog b = builder.create();
                b.show();
            }
            }
        });

    }

    public void Hide(){
        InputMethodManager inputmethodmanager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputmethodmanager.isActive() && getActivity().getCurrentFocus() != null) {
            if (getActivity().getCurrentFocus().getWindowToken() != null) {
                inputmethodmanager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

}
