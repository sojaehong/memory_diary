package com.example.kcci.memorydiary;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.kcci.memorydiary.adapter.TabPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private Toolbar myToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //툴바에 액션바 셋팅
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //viewpager에 어댑터 셋팅
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabPagerAdapter);

        //tab
        TabLayout tab = findViewById(R.id.tab_layout);
        tab.setupWithViewPager(viewPager);


    }

}
