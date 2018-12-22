package cz.org.appointment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.org.appointment.R;
import cz.org.appointment.entity.Appointment;
import cz.org.appointment.entity.ResponseEntity;
import cz.org.appointment.util.DateUtil;
import cz.org.appointment.util.JsonUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static cz.org.appointment.MyApplication.APPOINTING;
import static cz.org.appointment.MyApplication.CANCEL;
import static cz.org.appointment.MyApplication.appointmentService;


/**
 * Created by Administrator on 2016-11-30.
 */

public class AppointmentInfoAdapter extends BaseAdapter {

    private List<Appointment> list;
    private LayoutInflater mInflater;

    SmartRefreshLayout smartRefreshLayout;

    Context context;

    public AppointmentInfoAdapter(List<Appointment> list, Context context, SmartRefreshLayout smartRefreshLayout) {
        this.list = list;
        mInflater = LayoutInflater.from(context);
        this.smartRefreshLayout = smartRefreshLayout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        Appointment item = list.get(position);

        viewHolder = new ViewHolder();
        convertView = mInflater.inflate(R.layout.adapter_appoint_detail, null);


        String title = item.getLaboratory().getLaboratoryType().getName() + "——" + item.getLaboratory().getName();
        String startTime = DateUtil.DateToStringWithoutYear(item.getAppointmentDate());
        String endTime = DateUtil.DateToStringOnlyHourMinute(item.getEndDate());
        String createTime = DateUtil.DateToString(item.getCreateDate());
        String state = "";

        viewHolder.tvTitle = convertView.findViewById(R.id.tv_title);
        viewHolder.tvStartDate = convertView.findViewById(R.id.tv_start_date);
        viewHolder.tvEndDate = convertView.findViewById(R.id.tx_end_date);
        viewHolder.tvCreateDate = convertView.findViewById(R.id.tv_create_date);
//        viewHolder.tvState = convertView.findViewById(R.id.tv_state);
        convertView.setTag(viewHolder);
//        switch (item.getState()) {
//            case APPOINTING:
//                state = "预约中";
//                break;
//            case 3:
//                state = "已完成";
//                break;
//            case CANCEL:
//                state = "已取消";
//
//                viewHolder.tvState.setTextColor(R.color.ColorDividerColor2);
//                break;
//        }
//        viewHolder.tvState.setText(state);




        viewHolder.tvTitle.setText(title);
        viewHolder.tvStartDate.setText(startTime);
        viewHolder.tvEndDate.setText(endTime);
        viewHolder.tvCreateDate.setText(createTime);

        return convertView;
    }


    class ViewHolder {
        public TextView tvStartDate;
        public TextView tvEndDate;
        public TextView tvCreateDate;
        public TextView tvState;
        TextView tvTitle, tvContent, tvTime;

    }
}