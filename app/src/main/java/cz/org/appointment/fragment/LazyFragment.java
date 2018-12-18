package cz.org.appointment.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by dasu on 2016/9/27.
 * https://github.com/woshidasusu/Meizi
 * <p>
 * Viewpager + Fragment情况下，fragment的生命周期因Viewpager的缓存机制而失去了具体意义
 * 该抽象类自定义一个新的回调方法，当fragment可见状态改变时会触发的回调方法，介绍看下面
 *
 * @see #onFragmentVisibleChange(boolean)
 */

public abstract class LazyFragment extends Fragment {

    protected Unbinder unbinder;

    public String TAG = getClass().getSimpleName();


    /**
     * `
     * rootView是否初始化标志，防止回调函数在rootView为空的时候触发
     */
    private boolean hasCreateView;

    /**
     * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
     */
    private boolean isFragmentVisible;

    /**
     * onCreateView()里返回的view，修饰为protected,所以子类继承该类时，在onCreateView里必须对该变量进行初始化
     */
    protected View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayout(), container, false);
            unbinder = ButterKnife.bind(this, rootView);
            initViews(rootView);
            return rootView;
        }
        return rootView;
    }

    protected abstract int getLayout();

    protected abstract void initViews(View view);

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach: ");
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.w(getTag(), "setUserVisibleHint() -> isVisibleToUser: " + isVisibleToUser);
        if (rootView == null) {
            Log.w(getTag(), "setUserVisibleHint: ->rootView为null");
            return;
        }
        hasCreateView = true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            Log.w(getTag(), "setUserVisibleHint: isVisbleToUser=" + isVisibleToUser + " isFragmentVisible=" + isFragmentVisible);
            return;
        }
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
            Log.w(getTag(), "setUserVisibleHint: isVisbleToUser=" + isVisibleToUser + " isFragmentVisible=" + isFragmentVisible);
        }
        Log.w(getTag(), "setUserVisibleHint: isVisbleToUser=" + isVisibleToUser + " isFragmentVisible=" + isFragmentVisible);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.w(getTag(), "onViewCreated: getUserVisBleHint()= " + getUserVisibleHint() + "hasCreateview=" + hasCreateView);
        if (!hasCreateView && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        }
    }

    private void initVariable() {
        hasCreateView = false;
        isFragmentVisible = false;
    }

    /**************************************************************
     *  自定义的回调方法，子类可根据需求重写
     *************************************************************/

    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作，因为配合fragment的view复用机制，你不用担心在对控件操作中会报 null 异常
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {
        Log.w(getTag(), "onFragmentVisibleChange -> isVisible: " + isVisible);
    }


    @Override
    public void onDestroyView() {
        if (unbinder == null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }
}