package cz.org.appointment.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.jkb.fragment.rigger.annotation.LazyLoad;
import com.qfdqc.views.seattable.SeatTable;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cz.org.appointment.MyApplication;
import cz.org.appointment.R;
import cz.org.appointment.api.LaboratoryService;
import cz.org.appointment.entity.core.LaboratoryEntity;
import cz.org.appointment.entity.core.LaboratoryTypeEntity;
import cz.org.appointment.ui.SeatCheckerImpl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AppointmentFragment extends LazyFragment {

    @BindView(R.id.seatView)
    SeatTable seatTableView;

    @BindView(R.id.spinner_type)
    Spinner typeSpinner;

    @BindView(R.id.spinner_laboratory)
    Spinner laboratorySpinner;

    @BindView(R.id.btn_choose)
    Button choose;

    @BindView(R.id.ll_seat_table)
    LinearLayout linearLayout;


    List<LaboratoryTypeEntity> typeEntityList = new ArrayList<>();

    List<LaboratoryEntity> entityList = new ArrayList<>();

    //当前选中的类型Type
    LaboratoryTypeEntity currentType = null;

    //当前选中的Entity
    LaboratoryEntity currentEntity = null;


    BaseAdapter typeAdapter;

    BaseAdapter laboratoryAdapter;

    SeatCheckerImpl seatChecker = new SeatCheckerImpl(null);

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
        return R.layout.fragment_appointment;
    }

    @Override
    protected void initViews(View view) {
        //初始化下拉列表
        initSpinner();
        LaboratoryService laboratoryService = MyApplication.retrofit.create(LaboratoryService.class);
        laboratoryService.laboratoryAllType().enqueue(new Callback<List<LaboratoryTypeEntity>>() {
            @Override
            public void onResponse(Call<List<LaboratoryTypeEntity>> call, Response<List<LaboratoryTypeEntity>> response) {
                typeEntityList.addAll(response.body());
                typeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<LaboratoryTypeEntity>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
        setSeatTable(null);
    }

    private void setSeatTable(LaboratoryEntity entity) {
        if (entity == null) {
            resetVisibility();
            return;
        }
        seatTableView.setScreenName(entity.getName());//设置屏幕名称
        seatTableView.setMaxSelected(1);//设置最多选中

        seatTableView.setSeatChecker(new SeatCheckerImpl(entity));
        seatTableView.setData(entity.getRow(), entity.getCol());
        setVisibility();
    }

    private void setVisibility() {
        if (currentEntity == null) {
            Log.d(TAG, "setVisibility: +不可见");
            linearLayout.setVisibility(View.GONE);
        } else {
            Log.d(TAG, "setVisibility: 可见");
            linearLayout.setVisibility(View.VISIBLE);
        }
    }

    private void resetVisibility() {
        linearLayout.setVisibility(View.GONE);
        Log.d(TAG, "resetVisibility:  不可见");
    }

    //初始化下拉列表
    private void initSpinner() {
        //将可选内容与ArrayAdapter连接起来
        typeAdapter = new CommonAdapter<LaboratoryTypeEntity>(getActivity(), R.layout.type_layout, typeEntityList) {
            @Override
            protected void convert(ViewHolder viewHolder, LaboratoryTypeEntity item, int position) {
                viewHolder.setText(R.id.tv_type_spinner, item.getName());
            }
        };
        //将adapter 添加到spinner中
        typeSpinner.setAdapter(typeAdapter);
        //添加事件Spinner事件监听
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentType = (LaboratoryTypeEntity) typeAdapter.getItem(i);
                entityList.removeAll(entityList);
                entityList.addAll(currentType.getEntities());
                laboratoryAdapter.notifyDataSetChanged();
//                resetVisibility();
//                seatTableView.setSeatChecker(new SeatCheckerImpl(currentEntity));
                Log.d(TAG, "Type -> onItemSelected: " + currentType.getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //设置默认值
        typeSpinner.setVisibility(View.VISIBLE);

        //将可选内容与ArrayAdapter连接起来
        laboratoryAdapter = new CommonAdapter<LaboratoryEntity>(getActivity(), R.layout.laboratory_entity_layout, entityList) {
            @Override
            protected void convert(ViewHolder viewHolder, LaboratoryEntity item, int position) {
                viewHolder.setText(R.id.tv_laboratory_entity_spinner, item.getName());
            }
        };
        //将adapter 添加到spinner中
        laboratorySpinner.setAdapter(laboratoryAdapter);
        //添加事件Spinner事件监听
        laboratorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentEntity = (LaboratoryEntity) laboratoryAdapter.getItem(i);

                setSeatTable(currentEntity);
                Log.d(TAG, "onItemSelected: " + currentEntity.getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event() {
    }


}
