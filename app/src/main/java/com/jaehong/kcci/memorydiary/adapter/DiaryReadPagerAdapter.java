package com.jaehong.kcci.memorydiary.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jaehong.kcci.memorydiary.fragment.DiaryReadFragment;
import com.jaehong.kcci.memorydiary.vo.DiaryVO;

import java.util.ArrayList;

/**
 * Created by jeahong on 2018-05-01.
 */

public class DiaryReadPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<DiaryVO> list;

    public DiaryReadPagerAdapter(FragmentManager fm, ArrayList<DiaryVO> list) {
        super(fm);
        this.list = list;
    }


    @Override
    public Fragment getItem(int position) {
        return new DiaryReadFragment(list.get(position));
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
