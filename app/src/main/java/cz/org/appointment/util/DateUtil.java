package cz.org.appointment.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
    static String TAG = DateUtil.class.getSimpleName();
    static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd ");
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    static SimpleDateFormat sdf3 = new SimpleDateFormat("MM-dd HH:mm");
    static SimpleDateFormat sdf4 = new SimpleDateFormat("HH:mm");

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
            resultList.add(sdf2.format(time));
        }
        return resultList;
    }

    private static List<String> stringList = new ArrayList<>();

    static {
        List<Integer> timeList = new ArrayList<>();
        for (int i = 1; i < (end - start + 1) * 2; i++) {
            int value = startTime + (baseTime * i);
            int rest = value / (baseTime) / 2;
            if (rest == 12 || rest == 13 || rest == 14) continue;
            timeList.add(value);
        }
        for (Integer integer : timeList) {
            int value = integer / baseTime / 2;
            if (integer / baseTime % 2 != 0) {

                stringList.add(value + ":" + "30");
            } else {
                stringList.add(value + ":" + "00");
            }
        }
    }

    //初始化可用的时间
    public static List<String> initAvailableTime() {
        return stringList;
    }

    private static final String closeTimeStr = "17:00";
    private static final String openTimeStr = "8:00";

    //当前日期可用时间
    public static List<String> currentAvailableTime(Date currentDate, String dateStr) {
        String dateTimeStr = dateStr + closeTimeStr;
        try {
            Date closeDate = DateUtil.stringToDateWithTime(dateTimeStr);
            long minute = ((closeDate.getTime() - currentDate.getTime()) / 1000) / 60;
            String s = DateUtil.DateToStringOnlyHourMinute(currentDate);
            String[] split = s.split(":");
            //控制开始时间startTime
            int startTime = (Integer.valueOf(split[0]));
            startTime = startTime * baseTime * 2;
            int startMinute = (Integer.valueOf(split[1]) % baseTime);
            //控制结束时间count
            int count = (int) (minute / baseTime);
            int tmp = (int) (minute % baseTime);
            if (tmp >= baseTime / 2) {
                count++;
            } else if (tmp != 0) {
                count--;
            }

            int startValue = (startMinute % baseTime);
            if (startValue > (baseTime / 2)) {
                startTime = startTime + baseTime *3;
            } else  {
                startTime = startTime + baseTime;
            }

            List<Integer> tmpList = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                int value = startTime + (baseTime * i);
                int rest = value / (baseTime) / 2;
                if (rest < 8 || rest == 12 || rest == 13 || rest == 14) continue;
                tmpList.add(value);
            }

            List<String> currentTimeList = new ArrayList<>();

            for (Integer integer : tmpList) {
                int value = integer / baseTime / 2;
                if (integer / baseTime % 2 != 0) {
                    currentTimeList.add(value + ":" + "30");
                } else {
                    currentTimeList.add(value + ":" + "00");
                }
            }
            return currentTimeList;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }


    public static Date longTimeToDate(long time) {
        return new Date(time);
    }


//    public static List<String> initAvailableTimeCurrent() {
////        int start
////        //当前时间
////        Date current = new Date();
////        if(current)
//    }

    public static List<Integer> initAvailableMinutes() {
        List<Integer> integerList = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            integerList.add(i * baseTime);
        }
        return integerList;
    }

    //字符串转Date 带时间
    public static Date stringToDateWithTime(String dateStr) throws ParseException {

        return sdf.parse(dateStr);
    }

    //字符串转Date
    public static Date stringToDate(String dateStr) throws ParseException {
        return sdf2.parse(dateStr);
    }

    //字符串转Date
    public static String stringToDateOnyDate(Date date) throws ParseException {
        return sdf2.format(date);
    }

    //Date转字符串
    public static String DateToString(Date date) {
        return sdf.format(date);
    }

    public static String DateToStringWithoutYear(Date appointmentDate) {
        return sdf3.format(appointmentDate);
    }

    public static String DateToStringOnlyHourMinute(Date appointmentDate) {
        return sdf4.format(appointmentDate);
    }
}
