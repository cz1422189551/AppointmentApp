package cz.org.appointment.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
    static String TAG = DateUtil.class.getSimpleName();
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
    //默认时间
    static int baseTime = 30;
    static int start = 8;
    static int end = 17;

    //8点
    static int startTime = start * baseTime * 2;
    //17点
    static int endTime = end * baseTime * 2;


    //初始化可用的日期
    public static List<String> initAvailableDate() {
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.DATE, i);
            boolean isWeekend = instance.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || instance.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
            if (isWeekend) continue;
            Date time = instance.getTime();
            resultList.add(sdf.format(time));
        }
        return resultList;
    }

    //初始化可用的时间
    public static List<String> initAvailableTime() {
        List<Integer> timeList = new ArrayList<>();
        for (int i = 1; i < (end - start + 1) * 2; i++) {
            int value = startTime + (baseTime * i);
            int rest = value / (baseTime) / 2;
            if (rest == 12 || rest == 13 || rest == 14) continue;
            timeList.add(value);
        }
        List<String> stringList = new ArrayList<>();
        for (Integer integer : timeList) {
            int value = integer / baseTime / 2;
            if (integer / baseTime % 2 != 0) {

                stringList.add(value + ":" + "30");
            } else {
                stringList.add(value + ":" + "00");
            }
        }
        return stringList;
    }

    public static List<Integer> initAvailableMinutes() {
        List<Integer> integerList = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            integerList.add(i * baseTime);
        }
        return integerList;
    }


}
