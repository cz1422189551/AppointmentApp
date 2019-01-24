package cz.org.appointment;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import cz.org.appointment.entity.Appointment;
import cz.org.appointment.util.DateUtilByAndroid;
import cz.org.appointment.util.JsonUtil;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Test
    public void test () throws ParseException {
        String time = "2018-12-25  9:00";
        Date date = DateUtilByAndroid.stringToDateWithTime(time);
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(new Date());
        String s = JsonUtil.toJson(appointment);
        Appointment o = (Appointment) JsonUtil.fromJson(s, Appointment.class);
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}