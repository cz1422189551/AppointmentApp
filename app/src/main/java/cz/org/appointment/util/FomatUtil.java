package cz.org.appointment.util;

public class FomatUtil {

    public static  int getGenderByStr(String genderStr) {
        if ("男".contains(genderStr)) return 1;
        else if ("女".contains(genderStr)) return 0;
        else return 1;
    }
}
