package cz.org.appointment.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cz.org.appointment.R;
import cz.org.appointment.activity.AppointmentActivity;
import cz.org.appointment.api.AppointmentService;
import cz.org.appointment.api.LaboratoryService;
import cz.org.appointment.api.Result;
import cz.org.appointment.entity.Appointment;
import cz.org.appointment.entity.Laboratory;
import cz.org.appointment.util.DateUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static cz.org.appointment.MyApplication.retrofit;
import static cz.org.appointment.MyApplication.user;


public class HomeFragment extends LazyFragment {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.lv_appoint)
    ListView listView;

    BaseAdapter adapter;

    List<Laboratory> laboratoryList = new ArrayList<>();

    LaboratoryService laboratoryService;

    static int pageSize = 10;
    static int pageNumber = 1;
    static long totalPage = 0;

    Result<Laboratory> result;


    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews(View view) {
        adapter = new CommonAdapter<Laboratory>(getActivity(), R.layout.adapter_labory_info, laboratoryList) {
            @Override
            protected void convert(ViewHolder viewHolder, Laboratory item, int position) {
                String title = item.getLaboratoryType().getName() + "——" + item.getName();
                String description = item.getDescription();
                int seatCount = item.getSeatCount();
                String availableType = "可用";
                String managerPerson = item.getUser().getName();
                String tel = item.getUser().getTel();
                viewHolder.setText(R.id.tv_title, title);
                viewHolder.setText(R.id.tx_description, description);
                viewHolder.setText(R.id.tx_seat_count, seatCount + "");
                viewHolder.setText(R.id.tx_available_type, availableType);
                viewHolder.setText(R.id.tv_manger, managerPerson);
                viewHolder.setText(R.id.tx_available_type, availableType);
                viewHolder.setText(R.id.tv_tel, tel);
            }
        };
        //初始化刷新，加载控件
        setSwipeRefreshInfo();
    }


    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            loadData();
        }
    }

    //fixme :切换tab时 ,会不会再次刷新
    private void loadData() {
        laboratoryService = retrofit.create(LaboratoryService.class);
        refreshLayout.autoRefresh();
    }

    private void setSwipeRefreshInfo() {
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            requestData(1);
            refreshlayout.finishRefresh(1500/*,false*/);//传入false表示刷新失败
        });
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            if (totalPage == 0 || pageNumber > totalPage) {
                Toast.makeText(getActivity(), "没有更多记录", Toast.LENGTH_SHORT).show();
            } else {
                requestData(pageNumber);
            }
            refreshlayout.finishLoadMore(1500/*,false*/);//传入false表示加载失败
        });
    }

    private void requestData(int pn) {

        Map<String, Integer> map = new HashMap<>();
        map.put("pageNum", pn);
        map.put("pageSize", pageSize);
        laboratoryService.getLaboratoryList(map).enqueue(new Callback<Result<Laboratory>>() {
            @Override
            public void onResponse(Call<Result<Laboratory>> call, Response<Result<Laboratory>> response) {
                Result<Laboratory> body = response.body();
                result = body;
                if (result != null) {
                    List<Laboratory> list = HomeFragment.this.result.getResult();
                    totalPage = body.getTotalPage();
                    pageNumber = body.getPageNumber() + 1;
                    if (pn == 1) {
                        laboratoryList.removeAll(laboratoryList);
                        laboratoryList.addAll(list);
                    } else {
                        laboratoryList.addAll(list);
                    }
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Result<Laboratory>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                if (pageNumber == 1) {
                    Toast.makeText(getActivity(), "刷新错误", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "加载更多错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
