package com.jaehong.kcci.memorydiary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.jaehong.kcci.memorydiary.R;

import java.util.ArrayList;

public class CustomSpinnerAdapter extends BaseAdapter {
    ArrayList<Integer> spinnerList;
    Context context;
    LayoutInflater inflter;

    public CustomSpinnerAdapter(ArrayList<Integer> spinnerList, Context context){
        this.spinnerList = spinnerList;
        this.context = context;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return spinnerList.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflter.inflate(R.layout.diary_spinner_item,null);
        ImageView weather = view.findViewById(R.id.weather);
        weather.setImageResource(spinnerList.get(position));
        return view;
    }

    @Override
    public Object getItem(int position) { return null; }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
