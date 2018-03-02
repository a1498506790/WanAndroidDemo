package com.mir.wanandroiddemo.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.mir.wanandroiddemo.R;
import com.mir.wanandroiddemo.base.BaseActivity;
import com.mir.wanandroiddemo.ui.fragment.classify.ClassifyFragment;
import com.mir.wanandroiddemo.ui.fragment.home.HomePageFragment;
import com.mir.wanandroiddemo.utils.ActivityUtils;
import com.mir.wanandroiddemo.utils.ExitAppHelper;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.bottomBar)
    BottomNavigationView mBottomBar;
    private HomePageFragment mHomePageFragment;
    private ClassifyFragment mClassifyFragment;
    private ExitAppHelper mAppHelper;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreateActivity(Bundle savedInstanceState) {
        setSlideable(false);
        mAppHelper = new ExitAppHelper(this);
        initFragment();
        ActivityUtils.switchFragment(getSupportFragmentManager(), mHomePageFragment);
        mBottomBar.setOnNavigationItemSelectedListener(this);
        Log.e("test", "mBottomBar : " + mBottomBar);
    }

    private void initFragment() {
        mHomePageFragment = new HomePageFragment();
        mClassifyFragment = new ClassifyFragment();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_home:
                ActivityUtils.switchFragment(getSupportFragmentManager(), mHomePageFragment);
                break;
            case R.id.item_classify:
                ActivityUtils.switchFragment(getSupportFragmentManager(), mClassifyFragment);
                break;
        }
        return true;
    }

    /**
     * 双击退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (!mAppHelper.click()) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
