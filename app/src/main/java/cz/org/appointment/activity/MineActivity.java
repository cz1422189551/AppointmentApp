package cz.org.appointment.activity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import cz.org.appointment.R;
import cz.org.appointment.api.UserService;
import cz.org.appointment.entity.User;
import cz.org.appointment.util.JsonUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static cz.org.appointment.MyApplication.user;

public class MineActivity extends BaseActivity {

    @BindView(R.id.et_name)
    MaterialEditText nameEt;
    @BindView(R.id.et_gender)
    MaterialEditText genderEt;
    @BindView(R.id.et_password)
    MaterialEditText passwordEt;
    @BindView(R.id.et_tel)
    MaterialEditText telEt;
    @BindView(R.id.et_department)
    MaterialEditText departmentEt;
    @BindView(R.id.et_address)
    MaterialEditText addressEt;
    @BindView(R.id.et_title)
    MaterialEditText titleEt;


    @BindView(R.id.ll_address)
    LinearLayout addressLinearLayout;
    @BindView(R.id.ll_title)
    LinearLayout titleLinearLayout;

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
            addressEt.setText(user.getAddress());
        } else {
            titleLinearLayout.setVisibility(View.VISIBLE);
            titleEt.setText(user.getTitle());
        }
        nameEt.setText(user.getName());
        passwordEt.setText(user.getPassword());
        departmentEt.setText(user.getDepartment());
        telEt.setText(user.getTel());
        genderEt.setText(user.getGender());
        saveBtn.setOnClickListener(v -> {

            int userType = user.getUserType();
            Date createTime = user.getCreateTime();
            int id = user.getId();
            String userName = user.getUserName();
            String classGrade = user.getClassGrade();
            //fixme: gender
            User u = new User(
                    id, nameEt.getText().toString(), userName, passwordEt.getText().toString(), telEt.getText().toString(),
                    1, userType, createTime, addressEt.getText().toString(), classGrade, departmentEt.getText().toString(),
                    titleEt.getText().toString()
            );
            Map<String, String> map = new HashMap<>();
            map.put("user", JsonUtil.toJson(u));
            userService.save(map).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        user = response.body();
                        Toast.makeText(MineActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MineActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                    Toast.makeText(MineActivity.this, "保存错误", Toast.LENGTH_SHORT).show();
                }
            });

        });
    }

    @Override
    public void loadData() {

    }
}
