package cz.org.appointment.activity;

import android.support.v7.app.AppCompatActivity;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cz.org.appointment.R;
import cz.org.appointment.api.DefaultCallbackImpl;
import cz.org.appointment.api.Result;
import cz.org.appointment.entity.Comment;
import cz.org.appointment.adapter.MyCommentAdapter;
import retrofit2.Call;
import retrofit2.Response;

import static cz.org.appointment.MyApplication.commentService;
import static cz.org.appointment.MyApplication.user;

public class CommentActivity extends BaseActivity {

    @BindView(R.id.refreshLayout_comment)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.lv_comment)
    ListView listView;

    BaseAdapter adapter;
    MyCommentAdapter commentAdapter;

    List<Comment> list = new ArrayList<>();

    static int pageSize = 10;
    static int pageNumber = 1;
    static long totalPage = 0;
    Result<Comment> result = null;

    @Override
    public int getLayout() {
        return R.layout.activity_comment;
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void initViews() {
        adapter= new MyCommentAdapter(list,this);
//        adapter = new CommonAdapter<Comment>(this, R.layout.adapter_my_comment, list) {
//            @Override
//            protected void convert(ViewHolder viewHolder, Comment item, int position) {
//                Log.d(TAG, "convert: " + item.getCommentContent());
//                String time = DateUtil.DateToString(item.getTime());
//                String content = item.getCommentContent();
//                String from = item.getLaboratory().getName();
//                viewHolder.setText(R.id.tv_comment_date_my, time);
//                viewHolder.setText(R.id.tv_comment_content_my, content);
//                viewHolder.setText(R.id.tv_from_my, from);
//            }
//        };
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
        Map<String, String> map = new HashMap<>();
        map.put("user", user.getId() + "");
        map.put("pageNum", pn + "");
        map.put("pageSize", pageSize + "");
        commentService.getList(map).enqueue(new DefaultCallbackImpl<Result<Comment>>(this) {
            @Override
            public void onResponse(Call<Result<Comment>> call, Response<Result<Comment>> response) {
                Result<Comment> body = response.body();
                result = body;
                if (result != null && result.getResult() != null) {
                    List<Comment> repList = result.getResult();
                    totalPage = body.getTotalPage();
                    pageNumber = body.getPageNumber() + 1;
                    if (pn == 1) {
                        list.clear();
                        list.addAll(repList);
                    } else {
                        list.addAll(repList);
                    }

                    adapter.notifyDataSetChanged();
//                    listView.setAdapter(adapter);
                } else {
                    Toast.makeText(CommentActivity.this, "刷新结束，没有记录", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    @Override
    public void loadData() {
        refreshLayout.autoRefresh();

    }
}
