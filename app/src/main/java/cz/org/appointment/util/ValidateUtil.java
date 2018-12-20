package cz.org.appointment.util;

public class ValidateUtil {
    //字符串判空
    public static boolean isEmpty(String str) {
        if (str != null && !"".equals(str)) return false;
        return true;
    }

    public static boolean isNull(Object o) {
        if (o == null) return true;
        return false;
    }
}
