package cz.org.appointment.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cz.org.appointment.MyApplication;
import cz.org.appointment.R;
import cz.org.appointment.adapter.FragmentAdapter;
import cz.org.appointment.fragment.AppointmentFragment;
import cz.org.appointment.fragment.HomeFragment;
import cz.org.appointment.fragment.MineFragment;
import cz.org.appointment.util.IntentUtil;

import static cz.org.appointment.MyApplication.user;


public class HomeActivity extends BaseActivity {

    FragmentAdapter fragmentAdapter;

    @Nullable
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.ll_news)
    LinearLayout newsLinearLayout;
    @BindView(R.id.ll_subscribe)
    LinearLayout subScribeLinearLayout;
    @BindView(R.id.ll_my)
    LinearLayout mineLinearLayout;
    @BindView(R.id.tv_announcement)
    TextView announcementTx;


    public static TextView homeTitle;

    @BindView(R.id.iv_news)
    ImageView newsImageView;
    @BindView(R.id.iv_subscribe)
    ImageView subScribeImageView;
    @BindView(R.id.iv_my)
    ImageView mineImageView;

    AppointmentFragment appointmentFragment;

    MineFragment mineFragment;

    HomeFragment homeFragment;

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void initViews() {

        homeTitle = findViewById(R.id.tv_home_title);
        //初始化切换Bottom的Tab的点击事件
        LinearLayoutListener linearLayoutListener = new LinearLayoutListener();
        newsLinearLayout.setOnClickListener(linearLayoutListener);
        subScribeLinearLayout.setOnClickListener(linearLayoutListener);
        mineLinearLayout.setOnClickListener(linearLayoutListener);
        announcementTx.setOnClickListener(v -> IntentUtil.get().goActivity(HomeActivity.this, AnnouncementActivity.class));
        //bottomViewPager
        homeFragment = new HomeFragment();
        appointmentFragment = new AppointmentFragment();
        mineFragment = new MineFragment();
        //初始化首页三个Fragment
        List<Fragment> bottomFragment = new ArrayList<>();
        bottomFragment.add(homeFragment);
        bottomFragment.add(appointmentFragment);
        bottomFragment.add(mineFragment);
        //创建Fragment适配器
        fragmentAdapter = new FragmentAdapter(this, bottomFragment, getSupportFragmentManager());
//        将三个Fragment加载至Activity并且可点击下方Tab按钮左右切换。
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                resetPic();
                switch (position) {
                    case 0:
                        resetPic();
                        newsImageView.setBackgroundResource(R.drawable.shouye);
                        break;
                    case 1:

                        resetPic();
                        subScribeImageView.setBackgroundResource(R.drawable.shenqing);
                        break;
                    case 2:

                        resetPic();
                        mineImageView.setBackgroundResource(R.drawable.wode);
                        break;
                }
            }

            @Override
            public void onPageScrolled(int position, float offset, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    public void loadData() {
        if (user == null) {
            IntentUtil.get().goActivity(this, LoginActivity.class);
        }
        Log.d(TAG, "loadData: " + user);
    }


    private void resetPic() {
        newsImageView.setBackgroundResource(R.drawable.shouye_grey);
        subScribeImageView.setBackgroundResource(R.drawable.shenqing_grey);
        mineImageView.setBackgroundResource(R.drawable.wode_grey);
    }


    private class LinearLayoutListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_news:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.ll_subscribe:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.ll_my:
                    viewPager.setCurrentItem(2);
                    break;
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",Toast.LENGTH_SHORT).show();
                    //利用handler延迟发送更改状态信息
                    handler.sendEmptyMessageDelayed(0, 2000);
        } else {
            MyApplication.finish();
//            System.exit(0);
            System.exit(0);
        }
    }

    //定义一个变量，来标识是否退出
    private static boolean isExit = false;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
}
