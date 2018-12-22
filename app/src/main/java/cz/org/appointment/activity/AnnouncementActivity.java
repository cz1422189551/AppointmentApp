package cz.org.appointment.activity;

import android.support.v7.app.AppCompatActivity;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cz.org.appointment.R;
import cz.org.appointment.api.DefaultCallbackImpl;
import cz.org.appointment.api.ParamMapUtil;
import cz.org.appointment.api.Result;
import cz.org.appointment.entity.Announcement;
import cz.org.appointment.util.DateUtil;
import retrofit2.Call;
import retrofit2.Response;

import static cz.org.appointment.MyApplication.announcementService;

public class AnnouncementActivity extends BaseActivity {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.lv_appoint)
    ListView listView;

    BaseAdapter adapter;


    List<Announcement> list = new ArrayList<>();

    static int pageSize = 10;
    static int pageNumber = 1;
    static long totalPage = 0;
    Result<Announcement> result = null;

    @Override
    public int getLayout() {
        return R.layout.activity_announcement;
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void initViews() {
        adapter = new CommonAdapter<Announcement>(this, R.layout.adapter_announcement, list) {
            @Override
            protected void convert(ViewHolder viewHolder, Announcement item, int position) {
                String title = item.getTitle();
                String pushDate = DateUtil.DateToString(item.getPushDate());
                String content = item.getContent();
                String pushMan = item.getPushMan();
                viewHolder.setText(R.id.tv_title, title);
                viewHolder.setText(R.id.tv_push_date, pushDate);
                viewHolder.setText(R.id.tv_announcement_content, content);
                viewHolder.setText(R.id.tv_push_man, pushMan);
            }
        };
        listView.setAdapter(adapter);
        setSwipeRefreshInfo();
    }

    private void setSwipeRefreshInfo() {
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            requestData(1);
            refreshlayout.finishRefresh(500/*,false*/);//传入false表示刷新失败
        });
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            if (totalPage == 0 || pageNumber > totalPage) {
                Toast.makeText(this, "没有更多记录", Toast.LENGTH_SHORT).show();
            } else {
                requestData(pageNumber);
            }
            refreshlayout.finishLoadMore(500/*,false*/);//传入false表示加载失败
        });
    }

    private void requestData(int pn) {
        announcementService.getList(ParamMapUtil.pageMap(pn, pageSize)).enqueue(new DefaultCallbackImpl<Result<Announcement>>(this) {
            @Override
            public void onResponse(Call<Result<Announcement>> call, Response<Result<Announcement>> response) {
                Result<Announcement> body = response.body();
                result = body;
                if (result != null && result.getResult() != null) {
                    List<Announcement> repList = result.getResult();
                    totalPage = body.getTotalPage();
                    pageNumber = body.getPageNumber() + 1;
                    if (pn == 1) {
                        list.removeAll(list);
                        list.addAll(repList);
                    } else {
                        list.addAll(repList);
                    }
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                }
                Toast.makeText(AnnouncementActivity.this, "刷新结束，没有记录", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void loadData() {
        refreshLayout.autoRefresh();
    }
}
