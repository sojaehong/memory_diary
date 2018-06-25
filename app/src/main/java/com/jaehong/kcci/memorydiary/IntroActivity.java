package com.jaehong.kcci.memorydiary;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class IntroActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // 2초 후 인트로 액티비티 제거
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startApp();

                finish();
            }
        }, 1000);
        
    }

    private void startApp(){
        if(prefs.getBoolean("is_password",false) == true)
        {
            googleADstart();
            Intent intent = new Intent(IntroActivity.this, PasswordActivity.class);
            intent.putExtra("type","비밀번호 확인");
            startActivity(intent);
        }else{
            googleADstart();
            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    //구글광고
    private void googleADstart() {
        //구글광고
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5743192063444801/1507046309");
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        //테스트전면광고 ca-app-pub-3940256099942544/1033173712
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.e("admob", "loaded");
                mInterstitialAd.show();
            }
        });
        //구글광고
    }
}
