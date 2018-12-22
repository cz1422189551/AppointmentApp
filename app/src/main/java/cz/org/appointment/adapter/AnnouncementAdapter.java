package cz.org.appointment.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cz.org.appointment.R;
import cz.org.appointment.entity.Announcement;
import cz.org.appointment.entity.Comment;
import cz.org.appointment.util.DateUtil;
import me.codeboy.android.aligntextview.AlignTextView;


/**
 * Created by Administrator on 2016-11-30.
 */

public class AnnouncementAdapter extends BaseQuickAdapter<Announcement,BaseViewHolder> {



    public AnnouncementAdapter(int layoutResId, @Nullable List<Announcement> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Announcement item) {
        String title = item.getTitle();
        String pushDate = DateUtil.DateToString(item.getPushDate());
        String content = item.getContent();
        String pushMan = item.getPushMan();
        helper.setText(R.id.tv_title, title);
        helper.setText(R.id.tv_push_date, pushDate);
        helper.setText(R.id.tv_announcement_content, content);
        helper.setText(R.id.tv_push_man, pushMan);
    }
}