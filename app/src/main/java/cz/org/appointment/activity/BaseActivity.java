package cz.org.appointment.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;



import butterknife.ButterKnife;
import butterknife.Unbinder;



public abstract  class BaseActivity extends AppCompatActivity{



    private Unbinder unbinder ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadActivity();

    }

    private  void loadActivity(){
        setContentView(getLayout());
        ButterKnife.bind(getActivity());
        initViews();
    }


    public abstract int getLayout();

    public abstract AppCompatActivity getActivity();

    public abstract void initViews();

    public abstract void loadData();

    public static void  startToActivity(Activity activity, Class intentActivity){
        Intent intent = new Intent();
        intent.setClass(activity,intentActivity);
        activity.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        if(unbinder!=null) {
            Log.d(this.getActivity().getClass().getSimpleName(), "onDestroy: 解绑");
            unbinder.unbind();
        }
        super.onDestroy();
    }


    /**
     *携带数据的页面跳转
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public int getScreenWidth(Context context) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;

        return w_screen;
    }

    public int getScreenHeight(Context context) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int h_screen = dm.heightPixels;
        return h_screen;
    }


    public View getContentView() {
        ViewGroup view = (ViewGroup) this.getWindow().getDecorView();
        FrameLayout content = view.findViewById(android.R.id.content);
        return content.getChildAt(0);
    }

}
