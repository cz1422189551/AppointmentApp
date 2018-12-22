package cz.org.appointment.activity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import cz.org.appointment.R;
import cz.org.appointment.adapter.CommentInfoAdapter;
import cz.org.appointment.api.DefaultCallbackImpl;
import cz.org.appointment.api.Result;
import cz.org.appointment.entity.Comment;
import cz.org.appointment.entity.Laboratory;
import cz.org.appointment.entity.ResponseEntity;
import cz.org.appointment.util.DateUtil;
import cz.org.appointment.util.JsonUtil;
import cz.org.appointment.util.ValidateUtil;
import retrofit2.Call;
import retrofit2.Response;

import static cz.org.appointment.MyApplication.STUDENT;
import static cz.org.appointment.MyApplication.commentService;
import static cz.org.appointment.MyApplication.user;

public class LaboratoryInfoActivity extends BaseActivity {

    Laboratory laboratory;

    @BindView(R.id.tv_title)
    TextView titleTx;
    @BindView(R.id.tx_laboratory_type)
    TextView typeTx;
    @BindView(R.id.tx_seat_count)
    TextView seatTx;
    @BindView(R.id.tx_laboratory_name)
    TextView nameTx;
    @BindView(R.id.tv_manger)
    TextView managerPersonTx;
    @BindView(R.id.tx_available_type)
    TextView availTypeTx;
    @BindView(R.id.tv_tel)
    TextView telTx;
    @BindView(R.id.tv_address)
    TextView addressTx;
    @BindView(R.id.tv_description)
    TextView descriptionTx;


    @BindView(R.id.ll_no_comment)
    LinearLayout noCommentLayout;
    @BindView(R.id.tx_no_comment)
    TextView noCommentTx;

    @BindView(R.id.ll_comment)
    LinearLayout commentLayout;

    @BindView(R.id.et_comment)
    MaterialEditText commentEt;

    @BindView(R.id.btn_comment)
    Button commentBtn;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.lv_appoint)
    ListView listView;

    BaseAdapter adapter;
    List<Comment> list = new ArrayList<>();
    static int pageSize = 10;
    static int pageNumber = 1;
    static long totalPage = 0;
    Result<Comment> result = null;

    @Override
    public int getLayout() {
        return R.layout.activity_laboratory_info;
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void initViews() {
        laboratory = (Laboratory) getIntent().getSerializableExtra("laboratory");
        Log.d(TAG, "initViews: " + laboratory);
//        adapter = new CommonAdapter<Comment>(this, R.layout.adapter_laboratory_comment, list) {
//            @Override
//            protected void convert(ViewHolder viewHolder, Comment item, int position) {
//                Log.d(TAG, "convert: "+item.getCommentContent());
//                String time = DateUtil.DateToString(item.getTime());
//                String content = item.getCommentContent();
//                String name = item.getUser().getName();
//                viewHolder.setText(R.id.tv_comment_date, time);
//                viewHolder.setText(R.id.tv_comment_content, content);
//                viewHolder.setText(R.id.tv_name, name);
//            }
//        };
        adapter = new CommentInfoAdapter(list, this);
        listView.setAdapter(adapter);
        setSwipeRefreshInfo();
        commentBtn.setOnClickListener(v -> {
            if (!ValidateUtil.isViewTextEmpty(commentEt)) {
                String commentStr = commentEt.getText().toString();
                Comment comment = new Comment(0, laboratory, user, commentStr, new Date());
                Map<String, String> map = new HashMap<>();
                map.put("comment", JsonUtil.toJson(comment));
                commentService.comment(map).enqueue(new DefaultCallbackImpl<ResponseEntity<Comment>>(this) {
                    @Override
                    public void onResponse(Call<ResponseEntity<Comment>> call, Response<ResponseEntity<Comment>> response) {
                        ResponseEntity<Comment> body = response.body();
                        if (body != null || body.getData() != null) {
                            Toast.makeText(LaboratoryInfoActivity.this, "留言成功", Toast.LENGTH_SHORT).show();
                            list.add(0, comment);
                            showCommentLayout();
                            adapter.notifyDataSetChanged();
                            commentEt.setText("");
                            listView.setAdapter(adapter);
                        }
                    }
                });
            }
        });
    }

    private void showLaboratoryInfo() {
        String title = laboratory.getLaboratoryType().getName() + "——" + laboratory.getName();
        String description = laboratory.getDescription();
        int seatCount = laboratory.getSeatCount();
        String availableType = laboratory.getAvailableType() == STUDENT ? "可用" : "不可用";
        String managerPerson = laboratory.getUser().getName();
        String tel = laboratory.getUser().getTel();
        addressTx.setText("" + laboratory.getAddress());
        titleTx.setText(title);
        descriptionTx.setText(description);
        seatTx.setText(seatCount + "");
        availTypeTx.setText(availableType);
        managerPersonTx.setText(managerPerson);
        telTx.setText(tel);
    }

    private void setSwipeRefreshInfo() {

        refreshLayout.setEnableRefresh(false);
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
        map.put("laboratory", laboratory.getId() + "");
        map.put("pageNum", pn + "");
        map.put("pageSize", pageSize + "");
        commentService.getListInLaboratory(map).enqueue(new DefaultCallbackImpl<Result<Comment>>(this) {
            @Override
            public void onResponse(Call<Result<Comment>> call, Response<Result<Comment>> response) {
                Result<Comment> body = response.body();
                result = body;
                if (result != null && result.getResult() != null) {
                    List<Comment> repList = result.getResult();
                    totalPage = body.getTotalPage();
                    pageNumber = body.getPageNumber() + 1;
                    if (pn == 1) {
                        list.removeAll(list);
                        list.addAll(repList);
                    } else {
                        list.addAll(repList);
                    }
                    showCommentLayout();
                    adapter.notifyDataSetChanged();
//                    listView.setAdapter(adapter);

                } else {
                    Toast.makeText(LaboratoryInfoActivity.this, "刷新结束，没有记录", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result<Comment>> call, Throwable t) {
                super.onFailure(call, t);
                noCommentTx.setText("网络连接失败");
                commentLayout.setVisibility(View.GONE);
                noCommentLayout.setVisibility(View.VISIBLE);

            }
        });

    }

    private void showCommentLayout() {
        if (list.size() < 1) {
            noCommentLayout.setVisibility(View.VISIBLE);
            commentLayout.setVisibility(View.GONE);
        } else {
            noCommentLayout.setVisibility(View.GONE);
            commentLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loadData() {
//        refreshLayout.autoRefresh();
        requestData(1);
        showLaboratoryInfo();
    }
}
