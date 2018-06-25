package com.jaehong.kcci.memorydiary.vo;

public class DiaryVO {
    private int diaryDate;
    private String diaryWeather;
    private  String diaryWakeupTime;
    private String diarySleepTime;
    private  String diaryContent;
    private String diaryImagePath;

    public int getDiaryDate() { return diaryDate; }

    public void setDiaryDate(int diaryDate) { this.diaryDate = diaryDate; }

    public String getDiaryWeather() { return diaryWeather; }

    public void setDiaryWeather(String diaryWeather) { this.diaryWeather = diaryWeather; }

    public String getDiaryWakeupTime() { return diaryWakeupTime; }

    public void setDiaryWakeupTime(String diaryWakeupTime) { this.diaryWakeupTime = diaryWakeupTime; }

    public String getDiarySleepTime() { return diarySleepTime; }

    public void setDiarySleepTime(String diarySleepTime) { this.diarySleepTime = diarySleepTime; }

    public String getDiaryContent() { return diaryContent; }

    public void setDiaryContent(String diaryContent) { this.diaryContent = diaryContent; }

    public String getDiaryImagePath() { return diaryImagePath; }

    public void setDiaryImagePath(String diaryImagePath) { this.diaryImagePath = diaryImagePath; }
}
