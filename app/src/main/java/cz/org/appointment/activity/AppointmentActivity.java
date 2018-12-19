package cz.org.appointment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import cz.org.appointment.R;
import cz.org.appointment.api.AppointmentService;
import cz.org.appointment.api.Result;
import cz.org.appointment.entity.Appointment;
import cz.org.appointment.util.IntentUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static cz.org.appointment.MyApplication.retrofit;
import static cz.org.appointment.MyApplication.user;

public class AppointmentActivity extends BaseActivity {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.lv_appoint)
    ListView listView;

    BaseAdapter adapter;

    AppointmentService appointmentService;

    List<Appointment> appointmentList = new ArrayList<>();

    static int pageSize = 10;
    static int pageNumber = 1;
    static long totalPage = 0;
    Result<Appointment> result = null;

    @Override
    public int getLayout() {
        return R.layout.activity_appoint;
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void initViews() {
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Appointment appointment = (Appointment) adapter.getItem(i );
            Intent intent = new Intent(getActivity(), AppointmentInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("appoint", appointment);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        appointmentService = retrofit.create(AppointmentService.class);
        adapter = new CommonAdapter<Appointment>(this, R.layout.adapter_appoint, appointmentList) {
            @Override
            protected void convert(ViewHolder viewHolder, Appointment item, int position) {
                viewHolder.setText(R.id.tv_name, item.getAppointmentDate().toString());
            }
        };
        setSwipeRefreshInfo();

    }

    private void setSwipeRefreshInfo() {
        //设置 Header 为 贝塞尔雷达 样式
        refreshLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            requestData(1);
            refreshlayout.finishRefresh(1500/*,false*/);//传入false表示刷新失败
        });
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            if (totalPage == 0 || pageNumber > totalPage) {
                Toast.makeText(this, "没有更多记录", Toast.LENGTH_SHORT).show();
            } else {
                requestData(pageNumber);
            }
            refreshlayout.finishLoadMore(1500/*,false*/);//传入false表示加载失败
        });
    }

    private void requestData(int pn) {


        Map<String, Integer> map = new HashMap<>();
        map.put("userId", user.getId());
        map.put("pageNum", pn);
        map.put("pageSize", pageSize);
        appointmentService.appointmentList(map).enqueue(new Callback<Result<Appointment>>() {
            @Override
            public void onResponse(Call<Result<Appointment>> call, Response<Result<Appointment>> response) {
                Result<Appointment> body = response.body();
                result = body;
                if (result != null) {
                    List<Appointment> result = AppointmentActivity.this.result.getResult();
                    totalPage = body.getTotalPage();
                    pageNumber = body.getPageNumber() + 1;
                    if (pn == 1) {
                        appointmentList.removeAll(appointmentList);
                        appointmentList.addAll(result);
                    } else {
                        appointmentList.addAll(result);
                    }
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Result<Appointment>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                if (pageNumber == 1) {
                    Toast.makeText(AppointmentActivity.this, "刷新错误", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AppointmentActivity.this, "加载更多错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void loadData() {
    }
}
