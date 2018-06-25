package com.jaehong.kcci.memorydiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jaehong.kcci.memorydiary.adapter.DiaryReadPagerAdapter;
import com.jaehong.kcci.memorydiary.dbhelper.DiaryDBHelper;
import com.jaehong.kcci.memorydiary.vo.DiaryVO;

import java.util.ArrayList;

public class DiaryReadActivity extends AppCompatActivity {

    private Toolbar myToolbar;
    private DiaryVO diaryVO;
    private DiaryDBHelper diaryDBHelper;
    private ArrayList<DiaryVO> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_read);

        //툴바에 액션바 셋팅
        myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setTitle("");
        setSupportActionBar(myToolbar);

        diarySearch();

        //viewpager에 어댑터 셋팅
        ViewPager viewPager = findViewById(R.id.view_pager_read);
        DiaryReadPagerAdapter diaryReadPagerAdapter = new DiaryReadPagerAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(diaryReadPagerAdapter);

    }

    private void diarySearch(){
        Intent intent = getIntent();

        diaryDBHelper = new DiaryDBHelper(getApplicationContext());
        if (intent.getIntExtra("startDate",0) == 0){
            list = new ArrayList<>();
            diaryVO = diaryDBHelper.diaryRead(intent.getIntExtra("date", 0));
            list.add(diaryVO);
        }else{
            list = diaryDBHelper.getBetweenAllDiary(intent.getIntExtra("startDate",0),intent.getIntExtra("endDate",0));
        }
    }

}
