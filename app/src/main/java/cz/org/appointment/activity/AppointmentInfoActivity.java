package cz.org.appointment.activity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import cz.org.appointment.R;
import cz.org.appointment.entity.Appointment;

public class AppointmentInfoActivity extends BaseActivity {

    Appointment appointment;


    @Override
    public int getLayout() {
        return R.layout.activity_appoint_info;
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void initViews() {
        appointment = (Appointment) getIntent().getSerializableExtra("appoint");
        Log.d(TAG, "initViews: " + appointment);
    }

    @Override
    public void loadData() {

    }
}
