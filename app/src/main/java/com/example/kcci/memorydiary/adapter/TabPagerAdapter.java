package com.example.kcci.memorydiary.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.kcci.memorydiary.fragment.TabCalendarFragment;
import com.example.kcci.memorydiary.fragment.TabListFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {
    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        TabCalendarFragment tabCalendarFragment = new TabCalendarFragment();
        TabListFragment tabListFragment = new TabListFragment();

        if (position == 0)
            return  tabCalendarFragment;
        if(position == 1)
            return tabListFragment;

        return null;
    }

    @Override
    public int getCount() { return 2; }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return "달력";
        if(position == 1)
            return "리스트";

        return null;
    }
}
