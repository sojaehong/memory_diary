package com.jaehong.kcci.memorydiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
        finish();
    }

}
