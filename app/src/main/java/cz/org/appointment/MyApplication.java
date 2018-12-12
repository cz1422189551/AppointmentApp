package cz.org.appointment;

import android.app.Application;

import cz.org.appointment.entity.User;
import cz.org.appointment.util.JsonUtil;
import cz.org.appointment.util.SharedPreferencesUtil;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {
    public static Retrofit retrofit;

    public static User user;

    @Override
    public void onCreate() {
        super.onCreate();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.7.134:8080") //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .build();
        user = readLocalUserInfo();
    }

    //读取本地用户信息
    private User readLocalUserInfo() {
        String json = (String) SharedPreferencesUtil.getData(this, "userInfo", "");
        if (json == "") return null;
        return (User) JsonUtil.fromJson(json, User.class);
    }
}
