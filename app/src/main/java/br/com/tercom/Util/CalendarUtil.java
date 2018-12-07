package br.com.tercom.Util;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.TimeZone;

public class CalendarUtil {


    public static Integer GetYear(){
        return  Calendar.getInstance().get(Calendar.YEAR);
    }

    public static Integer GetMonth(){
        return Calendar.getInstance().get(Calendar.MONTH);
    }



    public static String getTimeStampFormated(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
        cal.setTimeInMillis(System.currentTimeMillis());
        return DateFormat.format("yyyyMMddHHmm", cal).toString();
    }
    public static String getTimeStampOffer(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
        cal.setTimeInMillis(System.currentTimeMillis());
        return DateFormat.format("yyyy-MM-dd HH:mm:ss", cal).toString();
    }

    public static boolean DateCardValidator(String data) {
        int monthValidate = Integer.parseInt(data.substring(0, 2));
        int yearValidate = Integer.parseInt(data.substring(3));
        if (!data.isEmpty()) {
            if (monthValidate < GetMonth() && yearValidate == (GetYear() % 100)) {
                return false;
            } else {
                if (monthValidate <= 12 && yearValidate >= (GetYear() % 100)) {
                    return true;
                } else {
                    return false;
                }

            }
        } else {
            return false;
        }
    }


}
