package cz.org.appointment.activity;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import cz.org.appointment.MyApplication;
import cz.org.appointment.R;
import cz.org.appointment.api.UserService;
import cz.org.appointment.entity.ResponseEntity;
import cz.org.appointment.entity.User;
import cz.org.appointment.util.IntentUtil;
import cz.org.appointment.util.JsonUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static cz.org.appointment.MyApplication.STUDENT;
import static cz.org.appointment.MyApplication.user;
import static cz.org.appointment.MyApplication.userService;
import static cz.org.appointment.util.FomatUtil.getGenderByStr;
import static cz.org.appointment.util.ValidateUtil.isViewTextEmpty;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.et_name)
    MaterialEditText nameEt;

    @BindView(R.id.et_user_name)
    MaterialEditText userNamEt;

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

    @BindView(R.id.et_gender)
    MaterialEditText genderEt;


    @BindView(R.id.btn_register)
    Button registerBtn;


    @Override
    public int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void initViews() {

        String genderText = "男";

        genderEt.setText(genderText);

        registerBtn.setOnClickListener(v -> {

            int userType = STUDENT;

            String userName = userNamEt.getText().toString();
            String classGrade = classGradeEt.getText().toString();
            int gender = getGenderByStr(genderEt.getText().toString());

            if (isViewTextEmpty(userNamEt) || isViewTextEmpty(nameEt) || isViewTextEmpty(twoPasswordEt)
                    || isViewTextEmpty(telEt) || isViewTextEmpty(genderEt) || isViewTextEmpty(departmentEt)
                    || isViewTextEmpty(passwordEt) || isViewTextEmpty(classGradeEt) || isViewTextEmpty(addressEt)) {
                Toast.makeText(this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!passwordEt.getText().toString().equals(twoPasswordEt.getText().toString())) {
                Toast.makeText(this, "两次密码不相同，请确认", Toast.LENGTH_SHORT).show();
                return;
            }
            //fixme: gender
            User u = new User(
                    0, nameEt.getText().toString(), userName, passwordEt.getText().toString(), telEt.getText().toString(),
                    gender, userType, addressEt.getText().toString(), classGrade, departmentEt.getText().toString(), ""
            );
            Map<String, String> map = new HashMap<>();
            map.put("userType", userType + "");
            map.put("user", JsonUtil.toJson(u));
            userService.register(map).enqueue(new Callback<ResponseEntity<User>>() {
                @Override
                public void onResponse(Call<ResponseEntity<User>> call, Response<ResponseEntity<User>> response) {
                    ResponseEntity<User> body = response.body();
                    Toast.makeText(RegisterActivity.this, body.getMsg(), Toast.LENGTH_SHORT).show();
                    //注册成功
                    if (body.getCode() == 200)
                        IntentUtil.get().goActivity(RegisterActivity.this, LoginActivity.class);

                }

                @Override
                public void onFailure(Call<ResponseEntity<User>> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }


    @Override
    public void loadData() {
        userService = MyApplication.retrofit.create(UserService.class);
    }
}
