package com.jaehong.kcci.memorydiary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaehong.kcci.memorydiary.CustomDateFormat;
import com.jaehong.kcci.memorydiary.DiaryReadActivity;
import com.jaehong.kcci.memorydiary.DiaryWriteActivity;
import com.jaehong.kcci.memorydiary.R;
import com.jaehong.kcci.memorydiary.dbhelper.DiaryDBHelper;
import com.jaehong.kcci.memorydiary.decorators.StampDecorator;
import com.jaehong.kcci.memorydiary.decorators.SaturdayDecorator;
import com.jaehong.kcci.memorydiary.decorators.SundayDecorator;
import com.jaehong.kcci.memorydiary.decorators.TodayDecorator;
import com.jaehong.kcci.memorydiary.vo.DiaryVO;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;


public class TabCalendarFragment extends Fragment {
    private MaterialCalendarView materialCalendarView;
    private HashSet<CalendarDay> dates = new HashSet<>();
    private CalendarDay eventDay = null;
    private ArrayList<DiaryVO> list = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_calendar, null);
        materialCalendarView = view.findViewById(R.id.calendar_view);

        materialCalendarView.addDecorators(new SundayDecorator(), new SaturdayDecorator(), new TodayDecorator());

        setAllStamp();

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
                DiaryDBHelper diaryDBHelper = new DiaryDBHelper(getContext());

                DiaryVO diaryVO = diaryDBHelper.diaryReadDate(new CustomDateFormat().dateDateToInteger(date.getDate()));

                if (diaryVO.getDiaryDate() == 0 ) {
                    Intent intent = new Intent(getContext(), DiaryWriteActivity.class);
                    intent.putExtra("date", new SimpleDateFormat("yyyy년 MM월 dd일").format(date.getDate()).toString());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), DiaryReadActivity.class);
                    intent.putExtra("date", new CustomDateFormat().dateDateToInteger(date.getDate()));
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    /***
     *  읽기장의 날짜를 불러와서 달력에 도장을 찍어주는 기능 메서드
     */
    private void setAllStamp() {
        list = new DiaryDBHelper(getContext()).getAllDiaryDate();
        for (DiaryVO diaryVO : list) {
            String date = String.valueOf(diaryVO.getDiaryDate());
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(4, 6));
            int day = Integer.parseInt(date.substring(6, 8));
            eventDay = CalendarDay.from(year, (month) - 1, day);
            dates.add(eventDay);
        }
        materialCalendarView.addDecorator(new StampDecorator(dates, getContext()));
    }

}
