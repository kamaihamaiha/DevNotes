package cn.kk.base.utils;

import java.text.SimpleDateFormat;
import java.util.*;

public class DateHelper {

    private volatile boolean good = false;
    private static final SimpleDateFormat sdf_ymd_hm = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);

    /**
     * 根据地区和语言，获取本地化的日期。
     * @param language
     * @param date
     * @return
     */
    public static String localizeMonthDay(String language, Date date) {
        SimpleDateFormat format;
        if ("es".equals(language)) {//d m
            format = new SimpleDateFormat("d MMM", new Locale("Es", "es"));
        } else if ("fr".equals(language)) {//d m.
            format = new SimpleDateFormat("d MMM", Locale.FRENCH);
        } else if ("de".equals(language)) {//d. m
            format = new SimpleDateFormat("d. MMM", Locale.GERMAN);
        } else {//d m
            format = new SimpleDateFormat("d MMM", Locale.ENGLISH);
        }
        return format.format(date);
    }


    /**
     * 获取今天是周几
     * @return 1 - 7
     */
    public static int getWeekDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.setFirstDayOfWeek(Calendar.MONDAY); // 这个设置后也没什么效果
        // day 说明，因此需要转换
        // 1  2  3 4  5  6 7
        // 日 一 二 三 四 五 六
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        day = day - 1;
        if (day == 0) day = 7; // 因为星期天会返回 1，经过上面的操作后，是 0，实际应该是 7 因此加了这行判断.

        // 1  2  3 4  5  6 7
        // 一 二 三 四 五 六 日

        return day;
    }

    /**
     * 获取当前一周的所有日期（day of month）
     * @return map key: 周几 value: day of month
     */
    public static HashMap<Integer, Integer> getAllDaysOfWeek(){
        int todayInWeekDay = getWeekDay();

        LinkedHashMap<Integer, Integer> days = new LinkedHashMap<>();
        final int week_day_count = 7;
        for (int i = 1; i <= week_day_count; i++) {
            days.put(i, getDayOfMonth(i - todayInWeekDay));
        }
        return days;
    }

    public static String getDay(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    public static int getDayOfMonth(int diffDay){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(new Date().getTime() + 86400 * 1000L * diffDay));
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static String getCurrentTimezone() {
        String utc = String.valueOf(TimeZone.getDefault().getRawOffset() / 60 / 60 / 1000);
        return utc;
    }

    public static String getTime(long time) {
        if (time <= 0) {
            return "0000:00:00";
        }
        return sdf_ymd_hm.format(new Date(time));
    }


    public static void main(String[] args) {

        /*HashMap<Integer, Integer> dayOfWeek = getAllDaysOfWeek();
        for (Map.Entry<Integer, Integer> entry : dayOfWeek.entrySet()) {
            System.out.println("星期 " + entry.getKey() + " -> " + entry.getValue());
        }*/
        byte[] data = new byte[4];
        System.out.println(getDay());

    }
}
