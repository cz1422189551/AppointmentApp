package cz.org.appointment.activity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

import static cz.org.appointment.MyApplication.*;

import cz.org.appointment.MyApplication;
import cz.org.appointment.R;
import cz.org.appointment.api.DefaultCallbackImpl;
import cz.org.appointment.api.UserService;
import cz.org.appointment.entity.User;
import cz.org.appointment.util.IntentUtil;
import cz.org.appointment.util.JsonUtil;
import cz.org.appointment.util.SharedPreferencesUtil;
import cz.org.appointment.util.ValidateUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_username)
    MaterialEditText etUserName;

    @BindView(R.id.et_password)
    MaterialEditText etPassword;

    @BindView(R.id.btn_submit)
    Button button;

    @BindView(R.id.btn_register)
    Button register;

    UserService service;

    @Override
    public int getLayout() {
        return R.layout.login_activity;
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void initViews() {
        service = retrofit.create(UserService.class);
        button.setOnClickListener(view -> {
            String userName = etUserName.getText().toString();
            String password = etPassword.getText().toString();
            Map<String, String> map = new HashMap<>();
            map.put("userName", userName);
            map.put("password", password);
            login(map);
        });

        register.setOnClickListener(v -> IntentUtil.get().goActivity(LoginActivity.this, RegisterActivity.class));
    }

    private void login(Map<String, String> map) {
        service.login(map).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "检查账号密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                //保存用户信息
                user = response.body();
                SharedPreferencesUtil.saveData(LoginActivity.this, USER_KEY, JsonUtil.toJson(user));
                IntentUtil.get().goActivity(LoginActivity.this, HomeActivity.class);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "检查账号密码", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void loadData() {
        String userJson = (String) SharedPreferencesUtil.getData(this, USER_KEY, "");
        if (!ValidateUtil.isEmpty(userJson)) {
            User user = (User) JsonUtil.fromJson(userJson, User.class);
            Map<String, String> map = new HashMap<>();
            map.put("userName", user.getUserName());
            map.put("password", user.getPassword());
            userService.login(map).enqueue(new DefaultCallbackImpl<User>(this) {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "身份过期，重新输入账号密码", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        MyApplication.user = response.body();
                        IntentUtil.get().goActivity(LoginActivity.this, HomeActivity.class);
                    }
                }
            });
        }
    }
}
