package jp.gr.procon.proconapp.util;

import java.util.Calendar;

import timber.log.Timber;

public class DateUtil {
    private static final long MAX_SECOND = 60;
    private static final long MAX_MIN = 60;
    private static final long MAX_HOUR = 24;

    public static String timeToPostDate(long targetTime) {
        if (targetTime < 0) {
            return "";
        }

        long currentTime = System.currentTimeMillis();

        long diff = currentTime - targetTime;
        // TODO targetTimeが現在時刻より後の時の文字列
        if (diff < 0) {
            return "";
        }

        long day = diff / (MAX_SECOND * MAX_MIN * MAX_HOUR * 1000);
        long hour = diff / (MAX_SECOND * MAX_MIN * 1000);
        long min = diff / (MAX_SECOND * 1000);
        long sec = diff / 1000;
        Timber.d("timeToPostDate: " + day + " " + hour + " " + min + " " + sec);

        if (day > 0) {
            return day + "日前";
        } else if (hour > 0) {
            return hour + "時間前";
        } else if (min > 0) {
            return min + "分前";
        } else {
            return sec + "秒前";
        }
    }
}
