package cz.org.appointment.ui;

import android.view.View;
import android.widget.AdapterView;

//下拉列表每点击一次，则清空可用按钮。 true操作只有点击“查询”按钮才赋予
public class OnItemSelectedListenerImpl implements AdapterView.OnItemSelectedListener {

    boolean availAppoint;

    public OnItemSelectedListenerImpl(boolean availAppoint) {
        this.availAppoint = availAppoint;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        availAppoint = false;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
