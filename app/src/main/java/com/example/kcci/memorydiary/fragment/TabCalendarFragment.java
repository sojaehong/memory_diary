package com.example.kcci.memorydiary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kcci.memorydiary.DiaryActivity;
import com.example.kcci.memorydiary.R;
import com.example.kcci.memorydiary.decorators.StampDecorator;
import com.example.kcci.memorydiary.decorators.SaturdayDecorator;
import com.example.kcci.memorydiary.decorators.SundayDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.HashSet;


public class TabCalendarFragment extends Fragment{
    private MaterialCalendarView materialCalendarView;
    private HashSet<CalendarDay> dates = new HashSet<>();
    private CalendarDay eventDay = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_calendar,null);
        materialCalendarView = view.findViewById(R.id.calendar_view);

        materialCalendarView.addDecorators(new SundayDecorator(), new SaturdayDecorator());
        materialCalendarView.setDateSelected(CalendarDay.today(),true);

        eventDay = CalendarDay.from(2018,(04)-1,13);
        dates.add(eventDay);
        eventDay = CalendarDay.from(2018,(04)-1,18);
        dates.add(eventDay);

        materialCalendarView.addDecorator(new StampDecorator(dates,getContext()));

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Intent intent = new Intent(getContext(), DiaryActivity.class);
                intent.putExtra("date" , new SimpleDateFormat("yyyy년 MM월 dd일").format(date.getDate()).toString());
                startActivity(intent);
            }
        });

        return view;
    }



}
