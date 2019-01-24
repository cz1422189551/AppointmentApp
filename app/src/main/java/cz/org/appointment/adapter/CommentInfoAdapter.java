package cz.org.appointment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cz.org.appointment.R;
import cz.org.appointment.entity.Comment;
import cz.org.appointment.util.DateUtilByAndroid;


/**
 * Created by Administrator on 2016-11-30.
 */

public class CommentInfoAdapter extends BaseAdapter {

    private List<Comment> list;
    private LayoutInflater mInflater;


    Context context;

    public CommentInfoAdapter(List<Comment> list, Context context) {
        this.list = list;
        mInflater = LayoutInflater.from(context);

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

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        viewHolder = new ViewHolder();
        convertView = mInflater.inflate(R.layout.adapter_laboratory_comment, null);

        viewHolder.tvTitle = convertView.findViewById(R.id.tv_name);
        viewHolder.tvContent = convertView.findViewById(R.id.tv_comment_content);
        viewHolder.tvTime = convertView.findViewById(R.id.tv_comment_date);

        convertView.setTag(viewHolder);


        viewHolder.tvTitle.setText(list.get(position).getUser().getName());
        viewHolder.tvContent.setText(list.get(position).getCommentContent());
        viewHolder.tvTime.setText(DateUtilByAndroid.DateToString(list.get(position).getTime()));


        return convertView;
    }


    class ViewHolder {
        TextView tvTitle, tvContent, tvTime;

    }
}