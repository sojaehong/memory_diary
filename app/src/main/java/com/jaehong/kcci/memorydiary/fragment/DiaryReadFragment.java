package com.jaehong.kcci.memorydiary.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaehong.kcci.memorydiary.CoustomDialog;
import com.jaehong.kcci.memorydiary.CustomDateFormat;
import com.jaehong.kcci.memorydiary.DiaryReadActivity;
import com.jaehong.kcci.memorydiary.DiaryWriteActivity;
import com.jaehong.kcci.memorydiary.ImageSaveAndRotation;
import com.jaehong.kcci.memorydiary.MainActivity;
import com.jaehong.kcci.memorydiary.R;
import com.jaehong.kcci.memorydiary.WeatherImageMatch;
import com.jaehong.kcci.memorydiary.dbhelper.DiaryDBHelper;
import com.jaehong.kcci.memorydiary.vo.DiaryVO;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by jeahong on 2018-05-01.
 */

public class DiaryReadFragment extends Fragment {
    private DiaryVO diaryVO;
    private DiaryDBHelper diaryDBHelper;

    private TextView txtDate, txtWakeupTime, txtSleepTime, txtContent;
    private ImageView imageWeather, imageView;

    private AlertDialog.Builder alt_bld;
    private Bitmap image;

    public DiaryReadFragment(){

    }

    @SuppressLint("ValidFragment")
    public DiaryReadFragment(DiaryVO diaryVO){
        this.diaryVO = diaryVO;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_read_fragment,null);

        txtDate = view.findViewById(R.id.txt_date);
        txtWakeupTime = view.findViewById(R.id.txt_wakeup_time);
        txtSleepTime = view.findViewById(R.id.txt_sleep_time);
        txtContent = view.findViewById(R.id.txt_content);
        imageWeather = view.findViewById(R.id.image_weather);
        imageView = view.findViewById(R.id.image_view);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CoustomDialog(getActivity(),R.layout.custom_dialog,image);
            }
        });

        setDiaryData();

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu2, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_update){
            Intent intent = new Intent(getContext(), DiaryWriteActivity.class);
            intent.putExtra("date", diaryVO.getDiaryDate());
            intent.putExtra("writeType","수정");
            startActivity(intent);
            return true;
        }

        if (item.getItemId() == R.id.action_delete){
            diaryDeleteMessage();
        }

        if (item.getItemId() == R.id.action_back){
            getActivity().finish();
            getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void diaryDeleteMessage(){
        alt_bld = new AlertDialog.Builder(getContext());

        alt_bld.setMessage("정말로 삭제하시겠습니까?").setCancelable(
                false).setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        diaryDBHelper = new DiaryDBHelper(getContext());
                        diaryDBHelper.diaryDelete(diaryVO);
                        Toast.makeText(getContext(), txtDate.getText().toString() + " 추억 삭제 완료", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action for 'NO' Button
                        dialog.cancel();
                    }
                });

        AlertDialog alert = alt_bld.create();

        alert.show();

    }
    private void setDiaryData(){
        try {
            txtDate.setText(new CustomDateFormat().dateIntegerToString(diaryVO.getDiaryDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        imageWeather.setImageResource(new WeatherImageMatch().WeatherImage(diaryVO.getDiaryWeather()));
        txtWakeupTime.setText(diaryVO.getDiaryWakeupTime());
        txtSleepTime.setText(diaryVO.getDiarySleepTime());
        txtContent.setText(diaryVO.getDiaryContent());

        if(diaryVO.getDiaryImagePath() != null){
            imageView.setVisibility(View.VISIBLE);
            image = BitmapFactory.decodeFile(diaryVO.getDiaryImagePath());
            try {
                image  = new ImageSaveAndRotation().bitmapRotation(diaryVO.getDiaryImagePath(), image);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(image);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setAdjustViewBounds(false);
        }

    }

}
