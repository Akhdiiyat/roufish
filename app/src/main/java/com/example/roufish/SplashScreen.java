package com.example.roufish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(400);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally{
                    startActivity(new Intent(SplashScreen.this, Homepage.class));
                    finish();
                }
            }
        };
        thread.start();

    }
}