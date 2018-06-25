package com.jaehong.kcci.memorydiary.dbhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.jaehong.kcci.memorydiary.vo.DiaryVO;

import java.util.ArrayList;

public class DiaryDBHelper extends SQLiteOpenHelper{
    private SQLiteDatabase db = null;
    private SQLiteStatement statement = null;
    private ArrayList<DiaryVO> list = null;
    private DiaryVO diaryVO = null;

    public DiaryDBHelper(Context context) {
        super(context, "memoryDiaryDB", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("memoryDiaryDB", "onCreate");
        StringBuffer sql = new StringBuffer();

        db.execSQL("DROP TABLE IF EXISTS diary;");

        sql.append("CREATE TABLE diary (");
        sql.append("diary_date INTEGER PRIMARY KEY NOT NULL,");
        sql.append("diary_weather TEXT NOT NULL,");
        sql.append("diary_wakeup_time TEXT NOT NULL,");
        sql.append("diary_sleep_time TEXT NOT NULL,");
        sql.append("diary_content TEXT NOT NULL,");
        sql.append("diary_image_path)");

        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("memoryDiaryDB", "onUpgrade");

        db.execSQL("DROP TABLE IF EXISTS diary;");
    }

    //다이어리 작성
    public void diaryWrite(DiaryVO diaryVO){
        StringBuffer sql = new StringBuffer();
        db = getWritableDatabase();

        sql.append("INSERT INTO diary(diary_date, diary_weather, diary_wakeup_time, diary_sleep_time");
        sql.append(", diary_content, diary_image_path) ");
        sql.append("VALUES(?,?,?,?,?,?);");

        statement = db.compileStatement(sql.toString());
        statement.bindLong(1, diaryVO.getDiaryDate());
        statement.bindString(2, diaryVO.getDiaryWeather());
        statement.bindString(3, diaryVO.getDiaryWakeupTime());
        statement.bindString(4, diaryVO.getDiarySleepTime());
        statement.bindString(5, diaryVO.getDiaryContent());
        if(diaryVO.getDiaryImagePath() == null)
            statement.bindNull(6);
        else
            statement.bindString(6, diaryVO.getDiaryImagePath());

        statement.execute();

        statement.close();
        db.close();
    }

    //다이어리 수정

    public void diaryUpdate(DiaryVO diaryVO){
        StringBuffer sql = new StringBuffer();
        db = getWritableDatabase();

        sql.append("UPDATE diary ");
        sql.append("SET diary_weather = ?, diary_wakeup_time = ?, diary_sleep_time = ?, diary_content = ?, diary_image_path = ?");
        sql.append(" WHERE diary_date = ?;");

        statement = db.compileStatement(sql.toString());
        statement.bindString(1, diaryVO.getDiaryWeather());
        statement.bindString(2, diaryVO.getDiaryWakeupTime());
        statement.bindString(3, diaryVO.getDiarySleepTime());
        statement.bindString(4, diaryVO.getDiaryContent());

        if(diaryVO.getDiaryImagePath() == null)
            statement.bindNull(5);
        else
            statement.bindString(5, diaryVO.getDiaryImagePath());

        statement.bindLong(6, diaryVO.getDiaryDate());

        statement.execute();

        statement.close();
        db.close();
    }

    public void diaryDelete(DiaryVO diaryVO){
        StringBuffer sql = new StringBuffer();
        db = getWritableDatabase();

        sql.append("Delete FROM diary");
        sql.append(" WHERE diary_date = ?;");

        statement = db.compileStatement(sql.toString());
        statement.bindLong(1, diaryVO.getDiaryDate());

        statement.execute();

        statement.close();
        db.close();
    }


    public DiaryVO diaryReadDate(int date){
        StringBuffer sql = new StringBuffer();

        diaryVO = new DiaryVO();

        db = getReadableDatabase();

        sql.append("SELECT diary_date FROM diary WHERE diary_date = ");
        sql.append(date);

        Cursor cursor = db.rawQuery(sql.toString(), null);

        if(cursor.moveToNext()){
            diaryVO.setDiaryDate(cursor.getInt(0));
        }

        db.close();
        return diaryVO;
    }

    //날짜에 따른 다이어리 하나 select
    public DiaryVO diaryRead(int date){
        StringBuffer sql = new StringBuffer();

        diaryVO = new DiaryVO();

        db = getReadableDatabase();

        sql.append("SELECT * FROM diary WHERE diary_date = ");
        sql.append(date);

        Cursor cursor = db.rawQuery(sql.toString(), null);

        if(cursor.moveToNext()){
            diaryVO.setDiaryDate(cursor.getInt(0));
            diaryVO.setDiaryWeather(cursor.getString(1));
            diaryVO.setDiaryWakeupTime(cursor.getString(2));
            diaryVO.setDiarySleepTime(cursor.getString(3));
            diaryVO.setDiaryContent(cursor.getString(4));
            diaryVO.setDiaryImagePath(cursor.getString(5));
        }

        db.close();
        return diaryVO;
    }

    //작성된 다이어리 전부 select
    public ArrayList<DiaryVO> getAllDiary(){
            StringBuffer sql = new StringBuffer();
            list = new ArrayList<>();

            db = getReadableDatabase();

            sql.append("SELECT * FROM diary");

            Cursor cursor = db.rawQuery(sql.toString(),null);

        while (cursor.moveToNext()){
            diaryVO = new DiaryVO();
            diaryVO.setDiaryDate(cursor.getInt(0));
            diaryVO.setDiaryWeather(cursor.getString(1));
            diaryVO.setDiaryWakeupTime(cursor.getString(2));
            diaryVO.setDiarySleepTime(cursor.getString(3));
            diaryVO.setDiaryContent(cursor.getString(4));
            diaryVO.setDiaryImagePath(cursor.getString(5));
            list.add(diaryVO);
        }

        db.close();
        return list;
    }

    //작성된 모든 날짜 select
    public ArrayList<DiaryVO> getAllDiaryDate(){
        StringBuffer sql = new StringBuffer();
        list = new ArrayList<>();

        db = getReadableDatabase();

        sql.append("SELECT diary_date FROM diary");

        Cursor cursor = db.rawQuery(sql.toString(),null);

        while (cursor.moveToNext()){
            diaryVO = new DiaryVO();
            diaryVO.setDiaryDate(cursor.getInt(0));
            list.add(diaryVO);
        }

        db.close();
        return list;
    }

    // 메소드명 변경해야함 / 텝리스트 리스트 불러오기 메소드 임.
    public ArrayList<DiaryVO> getBetweenDiary(int startDate, int endDate){
        StringBuffer sql = new StringBuffer();
        list = new ArrayList<>();

        db = getReadableDatabase();

        sql.append("SELECT diary_date, diary_weather, diary_content FROM diary WHERE diary_date BETWEEN ");
        sql.append(startDate);
        sql.append(" AND ");
        sql.append(endDate);

        Cursor cursor = db.rawQuery(sql.toString(), null);

        while(cursor.moveToNext()){
            diaryVO = new DiaryVO();
            diaryVO.setDiaryDate(cursor.getInt(0));
            diaryVO.setDiaryWeather(cursor.getString(1));
            diaryVO.setDiaryContent(cursor.getString(2));
            list.add(diaryVO);
        }

        db.close();

        return list;
    }

    //검색 기간 내의 다이어리 전부 select
    public ArrayList<DiaryVO> getBetweenAllDiary(int startDate, int endDate){
        StringBuffer sql = new StringBuffer();
        list = new ArrayList<>();

        db = getReadableDatabase();

        sql.append("SELECT * FROM diary WHERE diary_date BETWEEN ");
        sql.append(startDate);
        sql.append(" AND ");
        sql.append(endDate);

        Cursor cursor = db.rawQuery(sql.toString(), null);

        while(cursor.moveToNext()){
            diaryVO = new DiaryVO();
            diaryVO.setDiaryDate(cursor.getInt(0));
            diaryVO.setDiaryWeather(cursor.getString(1));
            diaryVO.setDiaryWakeupTime(cursor.getString(2));
            diaryVO.setDiarySleepTime(cursor.getString(3));
            diaryVO.setDiaryContent(cursor.getString(4));
            diaryVO.setDiaryImagePath(cursor.getString(5));
            list.add(diaryVO);
        }

        db.close();

        return list;
    }

}
