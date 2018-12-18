package cz.org.appointment.fragment;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jzxiang.pickerview.TimePickerDialog;

import com.qfdqc.views.seattable.SeatTable;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cz.org.appointment.MyApplication;
import cz.org.appointment.R;
import cz.org.appointment.api.AppointmentService;
import cz.org.appointment.api.LaboratoryService;
import cz.org.appointment.entity.Appointment;

import cz.org.appointment.entity.Laboratory;
import cz.org.appointment.entity.LaboratoryType;

import cz.org.appointment.util.DateUtil;
import cz.org.appointment.util.JsonUtil;
import fr.ganfra.materialspinner.MaterialSpinner;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static cz.org.appointment.MyApplication.user;


public class AppointmentFragment extends LazyFragment {


    @BindView(R.id.spinner_type)
    MaterialSpinner typeSpinner;

    @BindView(R.id.spinner_laboratory)
    MaterialSpinner laboratorySpinner;

    @BindView(R.id.spinner_date)
    MaterialSpinner dateSpinner;

    @BindView(R.id.spinner_time)
    MaterialSpinner timeSpinner;

    @BindView(R.id.spinner_minute)
    MaterialSpinner minuteSpinner;

    @BindView(R.id.ll_laboratory)
    LinearLayout linearLayout;

    @BindView(R.id.btn_available)
    Button availableBtn;

    @BindView(R.id.tx_available)
    TextView availableTextView;

    @BindView(R.id.et_username)
    MaterialEditText etUser;
    @BindView(R.id.et_tel)
    MaterialEditText etTel;

    @BindView(R.id.btn_submit)
    Button submitBtn;

    //选择的时间
    LocalDateTime chooseDate;

    List<LaboratoryType> typeEntityList = new ArrayList<>();

    List<Laboratory> entityList = new ArrayList<>();

    List<String> dateList = new ArrayList<>();

    List<String> timeList = new ArrayList<>();

    List<Integer> minuteList = new ArrayList<>();

    //当前选中的类型Type
    LaboratoryType currentType = null;
    //当前选中的Entity
    Laboratory currentEntity = null;
    //当前选中的日期
    String date;
    //当前选中的时段
    String time;
    //当前选中的时长
    int minute;

    //可用数量
    int availCount = 0;

    BaseAdapter typeAdapter;

    BaseAdapter laboratoryAdapter;

    BaseAdapter dateAdapter;

    BaseAdapter timeAdapter;

    BaseAdapter minuteAdapter;

//    SeatCheckerImpl seatChecker = new SeatCheckerImpl(null);

    LaboratoryService laboratoryService;
    AppointmentService appointmentService;


    //该方法名和 变量名不能改动，否则懒加载失效
    public void onLazyLoadViewCreated(Bundle savedInstanceState) {
        //do something in here
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_appointment;
    }

    TimePickerDialog mDialogAll;

    @Override
    protected void initViews(View view) {
        //初始化下拉列表
        initSpinner();
        initBtn();
        initEdit();
        //加载网络
        laboratoryService = MyApplication.retrofit.create(LaboratoryService.class);
        appointmentService = MyApplication.retrofit.create(AppointmentService.class);
        laboratoryService.laboratoryAllType().enqueue(new Callback<List<LaboratoryType>>() {
            @Override
            public void onResponse(Call<List<LaboratoryType>> call, Response<List<LaboratoryType>> response) {
                Log.d(TAG, "onResponse: " + response.body());
                typeEntityList.addAll(response.body());
                typeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<LaboratoryType>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    private void setVisibility() {
        if (currentType == null) {
            linearLayout.setVisibility(View.GONE);
        } else {
            if (currentType.getLaboratoryList() == null) {
                linearLayout.setVisibility(View.GONE);
            } else {
                linearLayout.setVisibility(View.VISIBLE);
            }
        }

    }

    private void resetVisibility() {
        Log.d(TAG, "resetVisibility: 重置可见性 ：Gone");
        currentType = null;
        entityList.removeAll(entityList);
        laboratoryAdapter.notifyDataSetChanged();

        linearLayout.setVisibility(View.GONE);
    }


    //初始化下拉列表

    private void initSpinner() {
        //类型
        typeAdapter = new CommonAdapter<LaboratoryType>(getActivity(), R.layout.type_layout, typeEntityList) {
            @Override
            protected void convert(ViewHolder viewHolder, LaboratoryType item, int position) {
                viewHolder.setText(R.id.tv_type_spinner, item.getName());
            }
        };
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i < 0) return;
                currentType = (LaboratoryType) typeAdapter.getItem(i);
                entityList.removeAll(entityList);
                entityList.clear();
                entityList.addAll(currentType.getLaboratoryList());
                setVisibility();
                laboratoryAdapter.notifyDataSetChanged();
                laboratorySpinner.setAdapter(laboratoryAdapter);
                Log.d(TAG, "Type -> onItemSelected: " + currentType.getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //实验室
        laboratoryAdapter = new CommonAdapter<Laboratory>(getActivity(), R.layout.spinner_layout, entityList) {
            @Override
            protected void convert(ViewHolder viewHolder, Laboratory item, int position) {
                viewHolder.setText(R.id.tv_laboratory_spinner, item.getName());
            }
        };
        laboratorySpinner.setAdapter(laboratoryAdapter);
        laboratorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i < 0) return;
                currentEntity = (Laboratory) laboratoryAdapter.getItem(i);
//                setSeatTable(currentEntity);
                Log.d(TAG, "onItemSelected: " + currentEntity.getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //日期
        dateList = DateUtil.initAvailableDate();
        dateAdapter = new CommonAdapter<String>(getActivity(), R.layout.spinner_date, dateList) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                viewHolder.setText(R.id.tv_date, item);
            }
        };
        dateSpinner.setAdapter(dateAdapter);
        //时间
        timeList = DateUtil.initAvailableTime();
        timeAdapter = new CommonAdapter<String>(getActivity(), R.layout.spinner_time, timeList) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                viewHolder.setText(R.id.tv_time, item);
            }
        };
        timeSpinner.setAdapter(timeAdapter);
        //分钟
        minuteList = DateUtil.initAvailableMinutes();
        minuteAdapter = new CommonAdapter<Integer>(getActivity(), R.layout.spinner_minute, minuteList) {
            @Override
            protected void convert(ViewHolder viewHolder, Integer item, int position) {
                viewHolder.setText(R.id.tv_minute, item + "");
            }
        };
        minuteSpinner.setAdapter(minuteAdapter);

    }

    private void initBtn() {
        availableBtn.setOnClickListener(view -> {
            Map<String, String> map = new HashMap<>();
            map.put("laboratoryId", ((Laboratory) laboratorySpinner.getSelectedItem()).getId() + "");
            map.put("date", dateSpinner.getSelectedItem().toString());
            map.put("startDate", map.get("date") + " " + timeSpinner.getSelectedItem().toString());
            map.put("minute", minuteSpinner.getSelectedItem().toString());
            appointmentService.findAvailableInfo(map).enqueue(new Callback<List<Appointment>>() {
                @Override
                public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                    Log.d(TAG, "onResponse: ");
                    List<Appointment> body = response.body();
                    if (body != null && body.size() > 0)
                        availCount = body.get(0).getLaboratory().getSeatCount() - body.size();
                    else {
                        availCount = ((Laboratory) laboratorySpinner.getSelectedItem()).getSeatCount();
                    }
                    availableTextView.setText(availCount + "/ " + ((Laboratory) laboratorySpinner.getSelectedItem()).getSeatCount());
                }

                @Override
                public void onFailure(Call<List<Appointment>> call, Throwable t) {
                    Toast.makeText(getActivity(), "查询失败", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onFailure: " + t.getMessage());
                }
            });
        });
        submitBtn.setOnClickListener(view -> {
            Laboratory laboratory = (Laboratory) laboratorySpinner.getSelectedItem();
            date = dateSpinner.getSelectedItem().toString();
            time = timeSpinner.getSelectedItem().toString();
            time = date + " " + time;
            minute = (int) minuteSpinner.getSelectedItem();
            try {
                Map<String, String> map = new HashMap<>();
                map.put("appointment",
                        JsonUtil.toJson(new Appointment(user, laboratory, new Date(), DateUtil.stringToDateWithTime(time), null, DateUtil.stringToDate(date), minute, 1))
                );

//                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonUtil.toJson(appointment));
                appointmentService.appointment(map).enqueue(new Callback<Appointment>() {
                    @Override
                    public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                        Log.d(TAG, "onResponse: " + response.toString());
                        Appointment body = response.body();
                        if (body == null) {
                            Toast.makeText(getActivity(), "没有空闲位置，预约失败", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(), "预约成功", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Appointment> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        Toast.makeText(getActivity(), "预约错误", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    private void initEdit() {
        etUser.setText(user.getName());
        etTel.setText(user.getTel());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event() {
    }


}
