package cz.org.appointment.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import cz.org.appointment.MyApplication;
import cz.org.appointment.R;
import cz.org.appointment.activity.AppointmentActivity;
import cz.org.appointment.activity.CommentActivity;
import cz.org.appointment.activity.LoginActivity;
import cz.org.appointment.activity.MineActivity;
import cz.org.appointment.util.IntentUtil;
import cz.org.appointment.util.SharedPreferencesUtil;


public class MineFragment extends LazyFragment {

    @BindView(R.id.ll_mine_info)
    LinearLayout myInfo;

    @BindView(R.id.ll_mine_appointment)
    LinearLayout myAppointment;

    @BindView(R.id.ll_mine_comment)
    LinearLayout myComment;

    @BindView(R.id.btn_exit)
    Button exitBtn;


    @Override
    protected int getLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initViews(View view) {
        myInfo.setOnClickListener(v -> {
            IntentUtil intentUtil = IntentUtil.get();
            intentUtil.goActivity(getActivity(), MineActivity.class);
        });
        myAppointment.setOnClickListener(v -> {
            IntentUtil intentUtil = IntentUtil.get();
            intentUtil.goActivity(getActivity(), AppointmentActivity.class);
        });
        myComment.setOnClickListener(v -> {
            IntentUtil intentUtil = IntentUtil.get();
            intentUtil.goActivity(getActivity(), CommentActivity.class);
        });
        exitBtn.setOnClickListener(v -> {
            MyApplication.user = null;
            SharedPreferencesUtil.saveData(getActivity(), "userInfo", "");
            IntentUtil.get().goActivity(getActivity(), LoginActivity.class);
        });
    }


}
