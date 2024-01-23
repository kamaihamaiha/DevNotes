package cn.kk.base.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimeHelper {

    private static final SimpleDateFormat SMF = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS");
    private static final SimpleDateFormat sdf_ymd_hm = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);

    /**
     * 将 ms 格式化成 时:分
     * @param duration 时长，单位 ms
     * @return
     */
    public static String getDurationFormat(long duration) {
        return String.format(Locale.getDefault(), "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }

    public static String getTimeSecond(){
        return SMF.format(new Date());
    }

    public static String getTime(long time) {
        if (time <= 0) {
            return "0000:00:00";
        }
        return sdf_ymd_hm.format(new Date(time));
    }
}
