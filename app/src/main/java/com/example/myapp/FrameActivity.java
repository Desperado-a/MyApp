package com.example.myapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FrameActivity extends FragmentActivity {
    private Fragment mFragment[];
    private RadioGroup radioGroup;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RadioButton rbtrecord,rbtadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        Log.i("aaa","on MyFragment");


        mFragment=new Fragment[2];
        fragmentManager=getSupportFragmentManager();
        mFragment[0]=fragmentManager.findFragmentById(R.id.frag_record);
        mFragment[1]=fragmentManager.findFragmentById(R.id.frag_add);
        fragmentTransaction=fragmentManager.beginTransaction().hide(mFragment[0]).hide(mFragment[1]);
        fragmentTransaction.show(mFragment[0]).commit();
        rbtrecord=findViewById(R.id.radiorecord);
        rbtadd=findViewById(R.id.radioadd);
        rbtrecord.setTextColor(Color.parseColor("#2C8F31"));

        RadioButton[] rbutton = new RadioButton[2];
        rbutton[0] =rbtadd;
        rbutton[1] = rbtrecord;
        for (RadioButton rb : rbutton) {
            Drawable[] drawables = rb.getCompoundDrawables();
            Rect r = new Rect(0, 0, 70, 70);
            drawables[1].setBounds(r);
            rb.setCompoundDrawables(null,drawables[1],null,null);
        }

        radioGroup=findViewById(R.id.bottomGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                fragmentTransaction=fragmentManager.beginTransaction().hide(mFragment[0]).hide(mFragment[1]);
                rbtrecord.setTextColor(Color.parseColor("#FF111100"));
                rbtadd.setTextColor(Color.parseColor("#FF111100"));
                switch (i){
                    case R.id.radiorecord:
                        Log.i("aaa","on chick1");
                        mFragment[0]=fragmentManager.findFragmentById(R.id.frag_record);
                        fragmentTransaction.show(mFragment[0]).commit();
                        rbtrecord.setTextColor(Color.parseColor("#2C8F31"));
                        break;
                    case R.id.radioadd:
                        Log.i("aaa","on chick2");
                        fragmentTransaction.show(mFragment[1]).commit();
                        rbtadd.setTextColor(Color.parseColor("#2C8F31")); //#66CDAA
                        break;
                    default:break;
                }
            }
        });



    }

}
