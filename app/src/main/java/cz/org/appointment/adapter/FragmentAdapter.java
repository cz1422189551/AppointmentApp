package cz.org.appointment.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsp on 2016/11/27.
 */

public class FragmentAdapter extends FragmentPagerAdapter {


    List<Fragment> fragmentList = new ArrayList<>();

    List<String> titles = new ArrayList<>();


    Context context;


    public FragmentAdapter(Context context, List<Fragment> fragmentList, List<String> titles, FragmentManager fm) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titles = titles;
        this.context = context;
    }

    public FragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }


    public FragmentAdapter(Context context, List<Fragment> fragmentList, FragmentManager fm) {
        super(fm);
        this.context = context;
        this.fragmentList = fragmentList;
    }

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * 是为了在viewpager中，因为下一界面有类似于新闻类的标签功能，
     * 根据选择的标签，返回本页以后，动态替换viewpager中的fragment，
     * 解决notifyDataSetChanged（）不刷新的问题
     *
     * @param object
     * @return
     */
    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }


}
