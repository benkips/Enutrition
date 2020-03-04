package com.mabnets.www.e_nutritoncare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import maes.tech.intentanim.CustomIntent;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_launcher);
        getSupportActionBar().hide();

        logo nutlogo=new logo();
        nutlogo.start();

    }
    private class logo extends Thread{
        @Override
        public void run() {
            try {
                sleep(1000*4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(Launcher.this,Login.class));
            Launcher.this.finish();
        }
    }
    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this,"right-to-left");
    }
}
