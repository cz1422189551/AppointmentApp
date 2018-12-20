package cz.org.appointment.util;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

    public static boolean isViewTextEmpty(EditText view) {
        Editable text = view.getText();
        if (text == null || "".equals(text.toString())) return true;
        return false;
    }
}
