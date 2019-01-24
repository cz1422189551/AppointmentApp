package cz.org.appointment.fragment;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import com.rengwuxian.materialedittext.MaterialEditText;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cz.org.appointment.MyApplication;
import cz.org.appointment.R;
import cz.org.appointment.activity.AppointmentActivity;
import cz.org.appointment.activity.CommentActivity;
import cz.org.appointment.activity.LoginActivity;
import cz.org.appointment.activity.MineActivity;
import cz.org.appointment.adapter.AppointmentInfoAdapter;
import cz.org.appointment.api.DefaultCallbackImpl;
import cz.org.appointment.api.Result;
import cz.org.appointment.entity.Appointment;
import cz.org.appointment.util.DateUtilByAndroid;
import cz.org.appointment.util.IntentUtil;
import cz.org.appointment.util.SharedPreferencesUtil;
import cz.org.appointment.util.ValidateUtil;
import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Response;

import static cz.org.appointment.MyApplication.USER_KEY;
import static cz.org.appointment.MyApplication.appointmentService;
import static cz.org.appointment.activity.HomeActivity.homeTitle;


public class MineFragment extends LazyFragment {

    @BindView(R.id.ll_mine_info)
    LinearLayout myInfo;

    @BindView(R.id.ll_mine_appointment)
    LinearLayout myAppointment;

    @BindView(R.id.ll_mine_comment)
    LinearLayout myComment;

    @BindView(R.id.btn_exit)
    Button exitBtn;

    @BindView(R.id.spinner_date)
    MaterialSpinner dateSpinner;
    private List<String> dateList;
    private CommonAdapter<String> dateAdapter;

    @BindView(R.id.btn_query)
    Button queryBtn;

    @BindView(R.id.et_laboratory_name)
    MaterialEditText materialEditText;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.lv_appoint)
    ListView listView;
    boolean isFirst = true;



    @Override
    protected int getLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initViews(View view) {


        myInfo.setOnClickListener(v -> {
            IntentUtil intentUtil = IntentUtil.get();
            intentUtil.goActivity(getActivity(), MineActivity.class);
        });
        myAppointment.setOnClickListener(v -> {
            IntentUtil intentUtil = IntentUtil.get();
            intentUtil.goActivity(getActivity(), AppointmentActivity.class);
        });
        myComment.setOnClickListener(v -> {
            IntentUtil intentUtil = IntentUtil.get();
            intentUtil.goActivity(getActivity(), CommentActivity.class);
        });
        exitBtn.setOnClickListener(v -> {
            MyApplication.user = null;
            SharedPreferencesUtil.saveData(getActivity(), USER_KEY, "");
            IntentUtil.get().goActivity(getActivity(), LoginActivity.class);
        });
        //日期
        dateList = DateUtilByAndroid.initAvailableDate();
        dateAdapter = new CommonAdapter<String>(getActivity(), R.layout.spinner_date, dateList) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                viewHolder.setText(R.id.tv_date, item);
            }
        };
        dateSpinner.setAdapter(dateAdapter);
        adapter = new AppointmentInfoAdapter(appointmentList, getActivity(), refreshLayout);
        listView.setAdapter(adapter);
        setSwipeRefreshInfo();
        queryBtn.setOnClickListener(v -> {
            pageNumber = 1;
            totalPage = 0;
            requestData(pageNumber);
//            Map<String, String> map = new HashMap<>();
//            String laboratoryName = materialEditText.getText().toString();
//            map.put("laboratoryName", laboratoryName);
//            Object selectedItem = dateSpinner.getSelectedItem();
//            String dateStr = "";
//            if (!ValidateUtil.isNull(selectedItem)) {
//                dateStr = (String) selectedItem;
//            }
//            pageNumber = 1;
//            totalPage = 0;
//            map.put("date", dateStr);
//            appointmentService.findInfo(map).enqueue(new DefaultCallbackImpl<Result<Appointment>>() {
//                @Override
//                public void onResponse(Call<Result<Appointment>> call, Response<Result<Appointment>> response) {
//
//                }
//            });
        });

    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            homeTitle.setText("个人信息");
        }
    }

    BaseAdapter adapter;


    List<Appointment> appointmentList = new ArrayList<>();

    static int pageSize = 10;
    static int pageNumber = 1;
    static long totalPage = 0;
    Result<Appointment> result = null;


    private void setSwipeRefreshInfo() {
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            requestData(1);
            refreshlayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
        });
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            if (totalPage == 0 || pageNumber > totalPage) {
                Toast.makeText(getActivity(), "没有更多记录", Toast.LENGTH_SHORT).show();
            } else {
                requestData(pageNumber);
            }
            refreshlayout.finishLoadMore(1000/*,false*/);//传入false表示加载失败
        });
    }

    private void requestData(int pn) {
        Map<String, String> map = new HashMap<>();
        String laboratoryName = materialEditText.getText().toString();
        Object selectedItem = dateSpinner.getSelectedItem();
        String dateStr = "";
        if (!ValidateUtil.isNull(selectedItem)) {
            dateStr = (String) selectedItem;
        }
        map.put("date", dateStr);
        map.put("laboratoryName", laboratoryName);
        map.put("pageNum", pn + "");
        map.put("pageSize", pageSize + "");
        appointmentService.findInfo(map).enqueue(new DefaultCallbackImpl<Result<Appointment>>(getActivity()) {
            @Override
            public void onResponse(Call<Result<Appointment>> call, Response<Result<Appointment>> response) {
                Result<Appointment> body = response.body();
                result = body;
                if (result != null &&result.getResult()!=null && result.getResult().size()>0)  {
                    List<Appointment> list = result.getResult();
                    totalPage = body.getTotalPage();
                    pageNumber = body.getPageNumber() + 1;
                    if (pn == 1) {
                        appointmentList.removeAll(appointmentList);
                        appointmentList.addAll(list);
                    } else {
                        appointmentList.addAll(list);
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getActivity(), "该条件没有记录", Toast.LENGTH_SHORT).show();
                    appointmentList.removeAll(appointmentList);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


}
