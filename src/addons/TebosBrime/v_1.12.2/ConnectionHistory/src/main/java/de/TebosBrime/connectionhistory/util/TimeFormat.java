package de.TebosBrime.connectionhistory.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormat {

    public static String getTimeStamp(long millis) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date resultDate = new Date(millis);
        return simpleDateFormat.format(resultDate);

    }

    public static String get(long millis, boolean full) {

        int seconds = (int) (millis / 1000) % 60 ;
        int minutes = (int) ((millis / (1000*60)) % 60);
        int hours = (int) (millis / (1000*60*60));

        if(!full){
            hours = hours % 24;
        }

        int days = (int) ((millis / (1000*60*60*24)));
        return
                (full ? days + " days " : "") +
                (hours > 9 ? hours : "0" + hours) + "h:" +
                (minutes > 9 ? minutes : "0" + minutes) + "m:" +
                (seconds > 9 ? seconds : "0" + seconds) + "s";

    }
}
