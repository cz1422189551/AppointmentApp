package cz.org.appointment.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jkb.fragment.rigger.annotation.LazyLoad;
import com.jkb.fragment.rigger.annotation.Puppet;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import cz.org.appointment.R;
import cz.org.appointment.api.LaboratoryService;
import cz.org.appointment.entity.LaboratorySeat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        laboratoryService = retrofit.create(LaboratoryService.class);
        Call<List<LaboratorySeat>> laboratorySeatCall = laboratoryService.laboratorySeatList("1");
        textView.setText("hello world");
        laboratorySeatCall.enqueue(new Callback<List<LaboratorySeat>>() {
            @Override
            public void onResponse(Call<List<LaboratorySeat>> call, Response<List<LaboratorySeat>> response) {
                List<LaboratorySeat> body = response.body();
                body.size();
                Integer stateType = body.get(0).getStateType();
                textView.setText(stateType + "");
            }

            @Override
            public void onFailure(Call<List<LaboratorySeat>> call, Throwable t) {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event() {
    }


}
