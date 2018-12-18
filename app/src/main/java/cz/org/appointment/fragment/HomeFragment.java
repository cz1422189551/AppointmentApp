package cz.org.appointment.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import cz.org.appointment.R;
import cz.org.appointment.api.LaboratoryService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static cz.org.appointment.MyApplication.retrofit;


public class HomeFragment extends LazyFragment {

    @BindView(R.id.tv_name)
    TextView textView;

    LaboratoryService laboratoryService;

    //该方法名和 变量名不能改动，否则懒加载失效
    public void onLazyLoadViewCreated(Bundle savedInstanceState) {

    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews(View view) {



        textView.setText("hello world");

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event() {
    }


}
