package cz.org.appointment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cz.org.appointment.R;
import cz.org.appointment.entity.Laboratory;

import static cz.org.appointment.MyApplication.STUDENT;


/**
 * Created by Administrator on 2016-11-30.
 */

public class LaboratoryAdapter extends BaseAdapter {

    private List<Laboratory> list;
    private LayoutInflater mInflater;


    Context context;

    public LaboratoryAdapter(List<Laboratory> list, Context context) {
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
        Laboratory item = list.get(position);
        viewHolder = new ViewHolder();
        convertView = mInflater.inflate(R.layout.adapter_labory_info, null);

        String title = item.getLaboratoryType().getName() + "——" + item.getName();
        String description = item.getDescription();
        int seatCount = item.getSeatCount();
        String availableType = item.getAvailableType() == STUDENT ? "可用" : "不可用";
        String managerPerson = item.getUser().getName();
        String tel = item.getUser().getTel();
        viewHolder.tvTitle=convertView.findViewById(R.id.tv_title);
        viewHolder.tvDescrip=convertView.findViewById(R.id.tx_description);
        viewHolder.tvSeat=convertView.findViewById(R.id.tx_seat_count);
        viewHolder.tvAav=convertView.findViewById(R.id.tx_available_type);
        viewHolder.tvManager=convertView.findViewById(R.id.tv_manger);
        viewHolder.tvTel=convertView.findViewById(R.id.tv_tel);

        viewHolder.tvTitle.setText(title);
        viewHolder.tvDescrip.setText(description);
        viewHolder.tvSeat.setText(seatCount+"");
        viewHolder.tvAav.setText(availableType);
        viewHolder.tvManager.setText(managerPerson);
        viewHolder.tvTel.setText(tel);


        return convertView;
    }


    class ViewHolder {
        TextView tvTitle, tvDescrip, tvSeat, tvAav, tvManager, tvTel;

    }
}