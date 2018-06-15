package com.example.kcci.memorydiary.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kcci.memorydiary.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TabListFragment extends Fragment implements View.OnClickListener{
    private int mStartDateYear, mStartDateMonth, mStartDateDay, mEndDateYear, mEndDateMonth, mEndDateDay;
    private int dateType;

    private TextView startDateText, endDateText;
    private Button btnSelect;
    private Calendar calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_list,null);

        startDateText = view.findViewById(R.id.txt_start_date);
        endDateText = view.findViewById(R.id.txt_end_date);
        btnSelect = view.findViewById(R.id.btn_select);

        calender();

        setTxtStartDate();
        setTxtEndDate();

        startDateText.setOnClickListener( this );
        endDateText.setOnClickListener( this );
        btnSelect.setOnClickListener( this );

        return view;
    }


    private void calender(){
        calendar = new GregorianCalendar();

        mStartDateYear = calendar.get(Calendar.YEAR);
        mStartDateMonth = calendar.get(Calendar.MONTH);
        mStartDateDay = calendar.get(Calendar.DAY_OF_MONTH);

        mEndDateYear = calendar.get(Calendar.YEAR);
        mEndDateMonth = calendar.get(Calendar.MONTH);
        mEndDateDay = calendar.get(Calendar.DAY_OF_MONTH);
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            if(dateType == 1){
                mStartDateYear = year;
                mStartDateMonth = month;
                mStartDateDay = dayOfMonth;
                setTxtStartDate();
            }

            if (dateType == 2){
                mEndDateYear = year;
                mEndDateMonth = month;
                mEndDateDay = dayOfMonth;
                setTxtEndDate();
            }

        }
    };

    private boolean dateCheck() throws ParseException {
        Date date;

        date = new SimpleDateFormat("yyyy년MM월dd일").parse(startDateText.getText().toString());
        int startDate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(date));
        date = new SimpleDateFormat("yyyy년MM월dd일").parse(endDateText.getText().toString());
        int endDate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(date));

        if(startDate <= endDate)
            return true;
        else
            return false;
    }

    private void setTxtStartDate(){
        startDateText.setText(String.format("%d년 %d월 %d일", mStartDateYear, mStartDateMonth + 1, mStartDateDay));
    }

    private void setTxtEndDate(){
        endDateText.setText(String.format("%d년 %d월 %d일", mEndDateYear, mEndDateMonth + 1, mEndDateDay));
    }

    // 클릭 리스너
    @Override
    public void onClick(View v) {
        //
        if(v == startDateText){
            dateType = 1;
            new DatePickerDialog(getContext(), onDateSetListener, mStartDateYear, mStartDateMonth, mStartDateDay).show();
        }//

        //
        if (v == endDateText){
            dateType = 2;
            new DatePickerDialog(getContext(), onDateSetListener, mEndDateYear, mEndDateMonth, mEndDateDay).show();
        }//

        //
        if(v == btnSelect){
            try {
                if(dateCheck())
                    Toast.makeText(getContext(),"true",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(),"false",Toast.LENGTH_SHORT).show();
            } catch (ParseException e) { e.printStackTrace(); }
        } //

    }
}
