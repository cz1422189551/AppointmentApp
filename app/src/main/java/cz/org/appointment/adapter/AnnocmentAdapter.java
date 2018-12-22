package cz.org.appointment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cz.org.appointment.R;
import cz.org.appointment.entity.Announcement;
import cz.org.appointment.util.DateUtil;
import me.codeboy.android.aligntextview.AlignTextView;


/**
 * Created by hsp on 2017/3/27.
 */

public class AnnocmentAdapter extends BaseAdapter {
    private static final String TAG = "AnnocmentListAdapter";

    LayoutInflater layoutInflater;
    ViewHolder viewHolder = null;
    public List<Announcement> list = new ArrayList<>();

    public AnnocmentAdapter(List<Announcement> list, Context context) {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        viewHolder = new ViewHolder();
        convertView = layoutInflater.inflate(R.layout.adapter_announcement, null);
        viewHolder = new ViewHolder();
        viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
        viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_push_date);

        viewHolder.tvContent = (AlignTextView) convertView.findViewById(R.id.tv_announcement_content);

        viewHolder.tvAdmin = (TextView) convertView.findViewById(R.id.tv_push_man);
        convertView.setTag(viewHolder);

        viewHolder.tvTitle.setText(list.get(position).getTitle());
        viewHolder.tvTime.setText(DateUtil.DateToString(list.get(position).getPushDate()));
        viewHolder.tvContent.setText(list.get(position).getContent());
        viewHolder.tvAdmin.setText(list.get(position).getPushMan());

        return convertView;
    }


    private static class ViewHolder {
        TextView tvTitle, tvTime, tvAdmin;
        AlignTextView tvContent;
        LinearLayout llTitle, llContent;
    }


}
