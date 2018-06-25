package com.jaehong.kcci.memorydiary;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jaehong.kcci.memorydiary.adapter.CustomSpinnerAdapter;
import com.jaehong.kcci.memorydiary.dbhelper.DiaryDBHelper;
import com.jaehong.kcci.memorydiary.vo.DiaryVO;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class DiaryWriteActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private final int GALLERY_CODE = 1112;
    private DiaryDBHelper diaryDBHelper;
    private ArrayList<Integer> spinnerList;
    private DiaryVO diaryVO;
    private int mWakeupHour, mWakeupMinute, mSleepHour, mSleepMinute;
    private int timeType;
    private String weather = "맑음";
    private String writeType = "쓰기";
    private Bitmap bitmap;
    private Intent intent;

    private Toolbar myToolbar;
    private Spinner spinnerWeather;
    private ImageView imageView;
    private TextView txtWakeupTime, txtSleepTime, txtDate;
    private EditText editContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_write);

        myToolbar = findViewById(R.id.my_toolbar);
        spinnerWeather = findViewById(R.id.spinner_weather);
        txtWakeupTime = findViewById(R.id.txt_wakeup_time);
        txtSleepTime = findViewById(R.id.txt_sleep_time);
        txtDate = findViewById(R.id.txt_date);
        imageView = findViewById(R.id.image_view);
        editContent = findViewById(R.id.edit_content);

        //툴바에 액션바 셋팅
        myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setTitle("");
        setSupportActionBar(myToolbar);

        setSpinnerWeather();

        intent = getIntent();

        if (intent.getStringExtra("writeType") == null) {
            writeType = "쓰기";
            setStartTime();
            setTxtDate();
        } else {
            writeType = intent.getStringExtra("writeType");
            setDiaryData(intent.getIntExtra("date", 0));
            spinnerWeather.setSelection(new WeatherImageMatch().WeatherToPosition(diaryVO.getDiaryWeather()));
        }

        txtWakeupTime.setOnClickListener(this);
        txtSleepTime.setOnClickListener(this);
        imageView.setOnClickListener(this);
        spinnerWeather.setOnItemSelectedListener(this);
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (timeType == 1) {
                mWakeupHour = hourOfDay;
                mWakeupMinute = minute;
                setTxtWakeupTime();
            }

            if (timeType == 2) {
                mSleepHour = hourOfDay;
                mSleepMinute = minute;
                setTxtSleepTime();
            }

        }
    };

    private void setDiaryData(int date) {
        diaryDBHelper = new DiaryDBHelper(getApplicationContext());

        diaryVO = diaryDBHelper.diaryRead(date);

        try {
            txtDate.setText(new CustomDateFormat().dateIntegerToString(diaryVO.getDiaryDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txtWakeupTime.setText(diaryVO.getDiaryWakeupTime());
        txtSleepTime.setText(diaryVO.getDiarySleepTime());
        editContent.setText(diaryVO.getDiaryContent());

        if (diaryVO.getDiaryImagePath() != null) {
            imageView.setVisibility(View.VISIBLE);
            bitmap = BitmapFactory.decodeFile(diaryVO.getDiaryImagePath());

            try {
                bitmap = new ImageSaveAndRotation().bitmapRotation(diaryVO.getDiaryImagePath(), bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            imageView.setImageBitmap(bitmap);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setAdjustViewBounds(false);
        }
    }

    private void setStartTime() {
        mWakeupHour = 00;
        mWakeupMinute = 00;
        mSleepHour = 12;
        mSleepMinute = 00;

        setTxtWakeupTime();
        setTxtSleepTime();
    }

    private void setTxtWakeupTime() {
        txtWakeupTime.setText(String.format("%d시 %d분", mWakeupHour, mWakeupMinute));
    }

    private void setTxtSleepTime() {
        txtSleepTime.setText(String.format("%d시 %d분", mSleepHour, mSleepMinute));
    }

    private void setTxtDate() {
        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        txtDate.setText(date);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {

            if (GALLERY_CODE == requestCode) {
                try {
                    imageView.setVisibility(View.VISIBLE);
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    bitmap = new ImageSaveAndRotation().bitmapRotation(getPath(data.getData()), bitmap);

//                    배치해놓은 ImageView에 이미지를 넣는다.
                    imageView.setImageBitmap(bitmap);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setAdjustViewBounds(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            DiaryVO diaryVO = new DiaryVO();
            try {
                diaryVO.setDiaryDate(new CustomDateFormat().dateStringToInteger(txtDate.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            diaryVO.setDiaryWeather(weather);
            diaryVO.setDiaryWakeupTime(txtWakeupTime.getText().toString());
            diaryVO.setDiarySleepTime(txtSleepTime.getText().toString());
            diaryVO.setDiaryContent(editContent.getText().toString());

            if (bitmap != null) {
                ImageSaveAndRotation imageSaveAndRotation =
                        new ImageSaveAndRotation(getApplicationContext(), bitmap,
                                txtDate.getText().toString().replace(" ", ""));
                diaryVO.setDiaryImagePath(imageSaveAndRotation.getImagePath());
            }

            diaryDBHelper = new DiaryDBHelper(getApplicationContext());

            if (writeType.equals("수정")) {
                diaryDBHelper.diaryUpdate(diaryVO);
                Toast.makeText(DiaryWriteActivity.this, txtDate.getText().toString() + " 추억 수정 완료", Toast.LENGTH_LONG).show();
            } else {
                diaryDBHelper.diaryWrite(diaryVO);
                Toast.makeText(DiaryWriteActivity.this, txtDate.getText().toString() + " 추억 저장 완료", Toast.LENGTH_LONG).show();
            }


            Intent intent = new Intent(DiaryWriteActivity.this, MainActivity.class);
            startActivity(intent);

            return true;
        }

        if (item.getItemId() == R.id.action_image) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
            intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, GALLERY_CODE);
            return true;
        }

        if (item.getItemId() == R.id.action_back) {
            finish();
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == txtWakeupTime) {
            timeType = 1;
            new TimePickerDialog(DiaryWriteActivity.this, onTimeSetListener, mWakeupHour, mWakeupMinute, false).show();
        }

        if (v == txtSleepTime) {
            timeType = 2;
            new TimePickerDialog(DiaryWriteActivity.this, onTimeSetListener, mSleepHour, mSleepMinute, false).show();
        }

        if(v == imageView){
            new CoustomDialog(DiaryWriteActivity.this,R.layout.custom_dialog,bitmap);
        }

    }

    private void setSpinnerWeather() {
        spinnerList = new ArrayList<>();
        spinnerList.add(R.drawable.if_cloudy);
        spinnerList.add(R.drawable.if_cloud);
        spinnerList.add(R.drawable.if_rain_cloud);
        spinnerList.add(R.drawable.if_snow_cloud);
        spinnerList.add(R.drawable.if_flash_cloud);

        spinnerWeather.setAdapter(new CustomSpinnerAdapter(spinnerList, getApplicationContext()));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        weather = new WeatherImageMatch().WeatherToString(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
