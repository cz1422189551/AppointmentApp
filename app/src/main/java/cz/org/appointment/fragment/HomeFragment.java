package cz.org.appointment.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cz.org.appointment.R;
import cz.org.appointment.activity.LaboratoryInfoActivity;
import cz.org.appointment.api.LaboratoryService;
import cz.org.appointment.api.Result;
import cz.org.appointment.entity.Laboratory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static cz.org.appointment.MyApplication.STUDENT;
import static cz.org.appointment.MyApplication.retrofit;
import static cz.org.appointment.activity.HomeActivity.homeTitle;


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

    //标记是否是第一次进入， 第一次进入才刷新， 之后切换到主页不刷新
    boolean isFirst = true;

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
                String availableType = item.getAvailableType() == STUDENT ? "可用" : "不可用";
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
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Log.d(TAG, "onItemClick: " + position);
            Intent intent = new Intent(getActivity(), LaboratoryInfoActivity.class);
            Bundle bundle = new Bundle();
            Laboratory laboratory = (Laboratory) adapter.getItem(position);
            bundle.putSerializable("laboratory", laboratory);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtras(bundle);
            getActivity().startActivity(intent);
        });
        //初始化刷新，加载控件
        setSwipeRefreshInfo();
    }


    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            homeTitle.setText("实验室信息");
            loadData();
        }
    }

    private void loadData() {
        laboratoryService = retrofit.create(LaboratoryService.class);
        if (isFirst) {
            refreshLayout.autoRefresh();
            isFirst = false;
        }
    }

    @Override
    public void onStop() {
        isFirst = false;
        super.onStop();
    }

    private void setSwipeRefreshInfo() {
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            requestData(1);
            refreshlayout.finishRefresh(500/*,false*/);//传入false表示刷新失败
        });
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            if (totalPage == 0 || pageNumber > totalPage) {
                Toast.makeText(getActivity(), "没有更多记录", Toast.LENGTH_SHORT).show();
            } else {
                requestData(pageNumber);
            }
            refreshlayout.finishLoadMore(500/*,false*/);//传入false表示加载失败
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
