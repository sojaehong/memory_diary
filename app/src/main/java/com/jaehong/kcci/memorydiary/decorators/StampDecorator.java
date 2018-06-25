package com.jaehong.kcci.memorydiary.decorators;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.jaehong.kcci.memorydiary.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.HashSet;

/**
 * Decorate a day by making the text big and bold
 */
public class StampDecorator implements DayViewDecorator {

    private HashSet<CalendarDay> dates;
    private Drawable drawable;

    public StampDecorator(HashSet<CalendarDay> dates, Context context) {
        this.dates = dates;
        this.drawable = ContextCompat.getDrawable(context, R.drawable.oldstamp);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(drawable);
    }

}
