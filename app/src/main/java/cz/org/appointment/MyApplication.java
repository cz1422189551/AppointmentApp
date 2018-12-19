package cz.org.appointment;

import android.app.Application;
import android.content.Context;

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

import cz.org.appointment.entity.User;
import cz.org.appointment.util.JsonUtil;
import cz.org.appointment.util.SharedPreferencesUtil;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {
    public static Retrofit retrofit;

    public static User user;

    public static final int STUDENT=1;
    public static final int TEACHER=2;
    //static 代码段可以防止内存泄露
//    static {
//
//    }


    @Override
    public void onCreate() {
        super.onCreate();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.7.134:8080") //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .build();
        user = readLocalUserInfo();
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

    //读取本地用户信息
    private User readLocalUserInfo() {
        String json = (String) SharedPreferencesUtil.getData(this, "userInfo", "");
        if (json == "") return null;
        return (User) JsonUtil.fromJson(json, User.class);
    }
}
