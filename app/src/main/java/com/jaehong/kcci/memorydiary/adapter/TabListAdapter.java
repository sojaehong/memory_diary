package com.jaehong.kcci.memorydiary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaehong.kcci.memorydiary.CustomDateFormat;
import com.jaehong.kcci.memorydiary.R;
import com.jaehong.kcci.memorydiary.WeatherImageMatch;
import com.jaehong.kcci.memorydiary.vo.DiaryVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TabListAdapter extends BaseAdapter{
    Context context;
    private ArrayList<DiaryVO> list;
    int layout;
    private LayoutInflater inflater;

    public TabListAdapter(Context context, int layout, ArrayList<DiaryVO> list){
        this.context = context;
        this.layout = layout;
        this.list = list;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        TextView txtDate = convertView.findViewById(R.id.txt_date);
        ImageView imageWeather = convertView.findViewById(R.id.image_weather);
        TextView txtContent = convertView.findViewById(R.id.txt_content);

        try {
            txtDate.setText(new CustomDateFormat().dateIntegerToString(list.get(position).getDiaryDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        imageWeather.setImageResource(new WeatherImageMatch().WeatherImage(list.get(position).getDiaryWeather()));
        txtContent.setText(list.get(position).getDiaryContent());

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
