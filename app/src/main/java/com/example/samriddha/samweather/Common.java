package com.example.samriddha.samweather;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {

    public static final String APP_ID = "a4680def7e63766deb0ce3db92a76191";

    public static Location current_location = null ;

    public static String ConvertUnixToDate(long dt) {

        Date date = new Date(dt*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm \n EEE \n dd/MM/yyyy");
        String format = sdf.format(date);

        return format ;
    }

    public static String ConvertUnixToHour(long sunrise) {

        Date date = new Date(sunrise*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String format = sdf.format(date);

        return format ;
    }
}
