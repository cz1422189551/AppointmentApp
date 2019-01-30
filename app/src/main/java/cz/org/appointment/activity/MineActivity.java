package cz.org.appointment.activity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cz.org.appointment.MyApplication;
import cz.org.appointment.R;
import cz.org.appointment.api.DefaultCallbackImpl;
import cz.org.appointment.api.UserService;
import cz.org.appointment.entity.ResponseEntity;
import cz.org.appointment.entity.User;
import cz.org.appointment.util.JsonUtil;
import cz.org.appointment.util.SharedPreferencesUtil;
import cz.org.appointment.util.ValidateUtil;
import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static cz.org.appointment.MyApplication.USER_KEY;
import static cz.org.appointment.MyApplication.user;
import static cz.org.appointment.util.FomatUtil.getGenderByStr;
import static cz.org.appointment.util.ValidateUtil.isViewTextEmpty;

public class MineActivity extends BaseActivity {

    @BindView(R.id.et_name)
    MaterialEditText nameEt;
    //    @BindView(R.id.spinner_gender)
//    MaterialSpinner genderSpinner;
    @BindView(R.id.et_password)
    MaterialEditText passwordEt;

    @BindView(R.id.et_again_password)
    MaterialEditText twoPasswordEt;

    @BindView(R.id.et_tel)
    MaterialEditText telEt;
    @BindView(R.id.et_department)
    MaterialEditText departmentEt;
    @BindView(R.id.et_class_grade)
    MaterialEditText classGradeEt;
    @BindView(R.id.et_address)
    MaterialEditText addressEt;
    @BindView(R.id.et_title)
    MaterialEditText titleEt;
    @BindView(R.id.et_gender)
    MaterialEditText genderEt;


    @BindView(R.id.ll_address)
    LinearLayout addressLinearLayout;
    @BindView(R.id.ll_title)
    LinearLayout titleLinearLayout;
    @BindView(R.id.ll_class_grade)
    LinearLayout classGraldeLinearLyout;

    @BindView(R.id.btn_save)
    Button saveBtn;

    UserService userService;


    @Override
    public int getLayout() {
        return R.layout.activity_mine;
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void initViews() {
        if (user.getUserType() == 1) {
            addressLinearLayout.setVisibility(View.VISIBLE);
            classGraldeLinearLyout.setVisibility(View.VISIBLE);
            addressEt.setText(user.getAddress());
            classGradeEt.setText(user.getClassGrade());
        } else {
            titleLinearLayout.setVisibility(View.VISIBLE);
            titleEt.setText(user.getTitle());
        }
        nameEt.setText(user.getName());
        passwordEt.setText(user.getPassword());
        twoPasswordEt.setText(passwordEt.getText().toString());
        departmentEt.setText(user.getDepartment());
        telEt.setText(user.getTel());
        String genderText = "";
        switch (user.getGender()) {
            case 1:
                genderText = "男";
                break;
            case 2:
                genderText = "女";
                break;
        }
        genderEt.setText(genderText);
//        BaseAdapter adapter = new CommonAdapter<String>(this, R.layout.spinner_layout, Arrays.asList("男", "女")) {
//            @Override
//            protected void convert(ViewHolder viewHolder, String item, int position) {
//                viewHolder.setText(R.id.tv_laboratory_spinner, item);
//            }
//        };
//        genderSpinner.setAdapter(adapter);
//        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (i < 0) return;
//                String s = (String) adapter.getItem(i);
//                switch (s) {
//                    case "男":
//                        gender = 1;
//                        break;
//                    case "女":
//                        gender = 0;
//                        break;
//                    default:
//                        gender = 1;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


        saveBtn.setOnClickListener(v -> {

            int userType = user.getUserType();
            int id = user.getId();
            String userName = user.getUserName();
            String classGrade = user.getClassGrade();
            int gender = getGenderByStr(genderEt.getText().toString());

            String tel = telEt.getText().toString();

            if (isViewTextEmpty(nameEt) || isViewTextEmpty(twoPasswordEt)
                    || isViewTextEmpty(telEt) || isViewTextEmpty(genderEt) || isViewTextEmpty(departmentEt)
                    || isViewTextEmpty(passwordEt)) {
                Toast.makeText(this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!passwordEt.getText().toString().equals(twoPasswordEt.getText().toString())) {
                Toast.makeText(this, "两次密码不相同，请确认", Toast.LENGTH_SHORT).show();
                return;
            }
            //fixme: gender
            User u = new User(
                    id, nameEt.getText().toString(), userName, passwordEt.getText().toString(), telEt.getText().toString(),
                    gender, userType, addressEt.getText().toString(), classGradeEt.getText().toString(), departmentEt.getText().toString(),
                    titleEt.getText().toString()
            );
            Map<String, String> map = new HashMap<>();
            map.put("userType", userType + "");
            map.put("user", JsonUtil.toJson(u));
            userService.save(map).enqueue(new DefaultCallbackImpl<ResponseEntity<User>>(this) {
                @Override
                public void onResponse(Call<ResponseEntity<User>> call, Response<ResponseEntity<User>> response) {
                    ResponseEntity<User> body = response.body();
                    Toast.makeText(MineActivity.this, body.getMsg(), Toast.LENGTH_SHORT).show();
                    if (body.getData() != null) {
                        SharedPreferencesUtil.saveData(MineActivity.this, USER_KEY, JsonUtil.toJson(body.getData()));
                        user = body.getData();
                    }
                }
            });
        });
    }

    @Override
    public void loadData() {
        userService = MyApplication.retrofit.create(UserService.class);
    }
}
