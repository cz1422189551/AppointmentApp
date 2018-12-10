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

@LazyLoad(true)
@Puppet
public class HomeFragment extends LazyFragment {

    @BindView(R.id.tv_name)
    TextView textView;

    //该方法名和 变量名不能改动，否则懒加载失效
    public void onLazyLoadViewCreated(Bundle savedInstanceState) {
        //do something in here
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews(View view) {
        // 第2部分：在创建Retrofit实例时通过.baseUrl()设置
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.7.134:8080") //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .build();
        LaboratoryService laboratoryService = retrofit.create(LaboratoryService.class);
        Call<List<LaboratorySeat>> laboratorySeatCall = laboratoryService.laboratorySeatList("1");
        textView.setText("hello world");
        laboratorySeatCall.enqueue(new Callback<List<LaboratorySeat>>() {
            @Override
            public void onResponse(Call<List<LaboratorySeat>> call, Response<List<LaboratorySeat>> response) {
                List<LaboratorySeat> body = response.body();
                body.size();
                Integer stateType = body.get(0).getStateType();
                textView.setText(stateType+"");
            }

            @Override
            public void onFailure(Call<List<LaboratorySeat>> call, Throwable t) {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event() {
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
    }
}
