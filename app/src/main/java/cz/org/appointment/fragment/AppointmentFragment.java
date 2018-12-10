package cz.org.appointment.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jkb.fragment.rigger.annotation.LazyLoad;
import com.jkb.fragment.rigger.annotation.Puppet;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cz.org.appointment.R;


public class AppointmentFragment extends LazyFragment {


    //该方法名和 变量名不能改动，否则懒加载失效
    public void onLazyLoadViewCreated(Bundle savedInstanceState) {
        //do something in here
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_appointment;
    }

    @Override
    protected void initViews(View view) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event() {
    }


}
