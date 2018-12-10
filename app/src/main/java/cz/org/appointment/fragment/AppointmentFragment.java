package cz.org.appointment.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.qfdqc.views.seattable.SeatTable;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cz.org.appointment.R;
import cz.org.appointment.entity.Seat;


public class AppointmentFragment extends LazyFragment {

    @BindView(R.id.seatView)
    SeatTable seatTableView;

    //该方法名和 变量名不能改动，否则懒加载失效
    public void onLazyLoadViewCreated(Bundle savedInstanceState) {
        //do something in here
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    protected int getLayout() {
        return R.layout.seat_view;
    }

    @Override
    protected void initViews(View view) {

        seatTableView.setScreenName("实验室");//设置屏幕名称
        seatTableView.setMaxSelected(1);//设置最多选中

        Map<Integer, Map<Integer, Boolean>> map = new HashMap<>();
        Map<Integer, Boolean> value = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            value.put(i, true);
        }
        map.put(0, value);

        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                Map<Integer, Boolean> rmp = map.get(row);
                if (rmp == null) return false;
                if (rmp.get(column) == null) return false;
                return rmp.get(column).booleanValue();
            }

            @Override
            public void checked(int row, int column) {
                Log.d(TAG, "checked:  " + row + " col : " + column);
                ArrayList<String> selectedSeat = seatTableView.getSelectedSeat();
                Log.d(TAG, "selectSeat " + selectedSeat.toString());
            }

            @Override
            public void unCheck(int row, int column) {

            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(4, 15);


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event() {
    }


}
