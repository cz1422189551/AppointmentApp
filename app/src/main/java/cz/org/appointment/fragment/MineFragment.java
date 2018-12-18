package cz.org.appointment.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import cz.org.appointment.R;
import cz.org.appointment.activity.AppointmentActivity;
import cz.org.appointment.activity.CommentActivity;
import cz.org.appointment.activity.MineActivity;
import cz.org.appointment.util.IntentUtil;


public class MineFragment extends LazyFragment {

    @BindView(R.id.ll_mine_info)
    LinearLayout myInfo;

    @BindView(R.id.ll_mine_appointment)
    LinearLayout myAppointment;

    @BindView(R.id.ll_mine_comment)
    LinearLayout myComment;

    //该方法名和 变量名不能改动，否则懒加载失效
    public void onLazyLoadViewCreated(Bundle savedInstanceState) {
        //do something in here
    }


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
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event() {
    }

}
