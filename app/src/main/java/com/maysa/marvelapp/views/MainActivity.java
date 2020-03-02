package com.maysa.marvelapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.maysa.marvelapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }


    private void initViews(){
         Thread Timer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent i = new Intent(MainActivity.this, HomePage.class);
                    startActivity(i);

                }
            }

        };
        Timer.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


}
