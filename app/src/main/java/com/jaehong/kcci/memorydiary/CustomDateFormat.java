package com.jaehong.kcci.memorydiary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jeahong on 2018-05-01.
 */

public class CustomDateFormat {

    public String dateIntegerToString(int dateInteger) throws ParseException {
        Date date;
        date = new SimpleDateFormat("yyyyMMdd").parse(""+dateInteger);
        String dataToString = new SimpleDateFormat("yyyy년 MM월 dd일").format(date);

        return dataToString;
    }

    public int dateStringToInteger(String stringDate) throws ParseException {
        Date date;
        date = new SimpleDateFormat("yyyy년 MM월 dd일").parse(stringDate);
        int integerDate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(date));

        return integerDate;
    }

    public int dateDateToInteger(Date date){
        int integerDate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(date));
        return integerDate;
    }
}
