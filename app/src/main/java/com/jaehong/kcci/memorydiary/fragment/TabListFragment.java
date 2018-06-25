package com.jaehong.kcci.memorydiary.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaehong.kcci.memorydiary.CustomDateFormat;
import com.jaehong.kcci.memorydiary.DiaryReadActivity;
import com.jaehong.kcci.memorydiary.R;
import com.jaehong.kcci.memorydiary.adapter.TabListAdapter;
import com.jaehong.kcci.memorydiary.dbhelper.DiaryDBHelper;
import com.jaehong.kcci.memorydiary.vo.DiaryVO;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TabListFragment extends Fragment implements View.OnClickListener{
    private int mStartDateYear, mStartDateMonth, mStartDateDay, mEndDateYear, mEndDateMonth, mEndDateDay;
    private int dateType;
    private int startDate, endDate;

    private TextView startDateText, endDateText;
    private Calendar calendar;
    private ListView listView;
    private  Button btnBetweenDiary;

    private int listSearchCount = 0; //이미 한번 검색 되었을 때는 startDate를 클릭했을 때도 검색을 하게 만들어준다.
    private ArrayList<DiaryVO> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_list,null);

        startDateText = view.findViewById(R.id.txt_start_date);
        endDateText = view.findViewById(R.id.txt_end_date);
        listView = view.findViewById(R.id.list_view);
        btnBetweenDiary = view.findViewById(R.id.btn_between_diary);

        calender();

        setTxtStartDate();
        setTxtEndDate();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DiaryReadActivity.class);
                intent.putExtra("date",list.get(position).getDiaryDate());
                startActivity(intent);
            }
        });

        startDateText.setOnClickListener( this );
        endDateText.setOnClickListener( this );
        btnBetweenDiary.setOnClickListener( this );

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

                if(listSearchCount == 0)
                    return;
                else
                    diaryListSearch();

            }

            if (dateType == 2){
                mEndDateYear = year;
                mEndDateMonth = month;
                mEndDateDay = dayOfMonth;
                setTxtEndDate();

                diaryListSearch();
            }

        }
    };

    private boolean dateCheck() throws ParseException {
       startDate = new CustomDateFormat().dateStringToInteger(startDateText.getText().toString());
       endDate = new CustomDateFormat().dateStringToInteger(endDateText.getText().toString());

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

    private void diaryListSearch(){
        try {
            if(dateCheck()) {
                list = new DiaryDBHelper(getContext()).getBetweenDiary(startDate,endDate);
                TabListAdapter listAdapter = new TabListAdapter(getContext(), R.layout.tab_list_item,list);
                listView.setAdapter(listAdapter);

                if(list.size() == 0)
                    btnBetweenDiary.setVisibility(View.GONE);
                else
                    btnBetweenDiary.setVisibility(View.VISIBLE);

                Toast.makeText(getActivity().getApplicationContext(),"추억 검색 완료",Toast.LENGTH_SHORT).show();

                listSearchCount = 1;
            }
            else
                Toast.makeText(getActivity().getApplicationContext(),"날짜를 확인해주세요.",Toast.LENGTH_SHORT).show();

        } catch (ParseException e) { e.printStackTrace(); }
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

        if (v == btnBetweenDiary){
            Intent intent = new Intent(getContext(), DiaryReadActivity.class);
            intent.putExtra("startDate", startDate);
            intent.putExtra("endDate", endDate);
            startActivity(intent);
        }

    }
}
