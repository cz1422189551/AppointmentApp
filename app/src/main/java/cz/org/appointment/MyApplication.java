package cz.org.appointment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cz.org.appointment.api.AnnouncementService;
import cz.org.appointment.api.AppointmentService;
import cz.org.appointment.api.CommentService;
import cz.org.appointment.api.LaboratoryService;
import cz.org.appointment.api.UserService;
import cz.org.appointment.entity.Laboratory;
import cz.org.appointment.entity.User;
import cz.org.appointment.util.JsonUtil;
import cz.org.appointment.util.SharedPreferencesUtil;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {
    public static Retrofit retrofit;

    public static User user;

    public static final int STUDENT = 1;

    public static final int TEACHER = 2;

    public static final int ADMIN = 3;

    public static final int APPOINTING = 1;

    public static final int USING = 2;

    public static final int FINISH = 3;

    public static final int CANCEL = -1;

    public static final int SEAT_COUNT = 20;

    public static final String USER_KEY = "user";

    public static UserService userService;
    public static AppointmentService appointmentService;
    public static AnnouncementService announcementService;
    public static CommentService commentService;
    public static LaboratoryService laboratoryService;

        public static final String ip = "http://132.232.119.142:8080";

//    public static final String ip = "http://192.168.43.170:8080";

    //    public static final String ip = "http://192.168.7.134:8080";


    private static final OkHttpClient client = new OkHttpClient.Builder().
            connectTimeout(30, TimeUnit.SECONDS).
            readTimeout(30, TimeUnit.SECONDS).
            writeTimeout(30, TimeUnit.SECONDS).build();

    @Override
    public void onCreate() {
        Log.d("MyApplication", "onCreate: ");
        super.onCreate();


        retrofit = new Retrofit.Builder()
                .baseUrl(ip) //设置网络请求的Url地址
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .build();
        userService = retrofit.create(UserService.class);
        appointmentService = retrofit.create(AppointmentService.class);
        announcementService = retrofit.create(AnnouncementService.class);
        commentService = retrofit.create(CommentService.class);
        laboratoryService = retrofit.create(LaboratoryService.class);
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
            //设置 Header 为 贝塞尔雷达 样式
            return new BezierRadarHeader(MyApplication.this).setEnableHorizontalDrag(true);
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //设置 Footer 为 球脉冲 样式
            return new BallPulseFooter(MyApplication.this).setSpinnerStyle(SpinnerStyle.Scale);
        });
    }

    public static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void finish() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

//    //读取本地用户信息
//    private User readLocalUserInfo() {
//        String json = (String) SharedPreferencesUtil.getData(this, "userInfo", "");
//        if (json == "") return null;
//        return (User) JsonUtil.fromJson(json, User.class);
//    }
}
