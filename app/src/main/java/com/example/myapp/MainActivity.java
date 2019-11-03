package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class MainActivity extends AppCompatActivity implements Runnable{
    private static String TAG = "on Main";
    Handler handler;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler=new Handler(){
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    Intent intent = new Intent(MainActivity.this, FrameActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                }
                super.handleMessage(msg);
            }
        };

        Thread t = new Thread(this);
        t.start();

    }

    @Override
    public void run() {
        try {
            Thread.sleep(1300);
            Message msg= handler.obtainMessage(1);
            handler.sendMessage(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
