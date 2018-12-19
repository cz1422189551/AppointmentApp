package cz.org.appointment.util;

public class ValidateUtil {
    //字符串判空
    public static boolean isEmpty(String str) {
        if (str != null && !"".equals(str)) return false;
        return true;
    }
}
