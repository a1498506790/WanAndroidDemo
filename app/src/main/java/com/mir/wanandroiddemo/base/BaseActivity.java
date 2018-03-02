package com.mir.wanandroiddemo.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.mir.wanandroiddemo.R;
import com.mir.wanandroiddemo.utils.ActivityStack;
import com.mir.wanandroiddemo.utils.MPermissionUtils;
import com.mir.wanandroiddemo.widget.slideback.SlideBackActivity;
import com.mir.wanandroiddemo.widget.status.MultiStatusLayout;

import butterknife.ButterKnife;

/**
 * @author by lx
 * @github https://github.com/a1498506790
 * @data 2018/3/2
 * @desc Activity基类
 */

public abstract class BaseActivity extends SlideBackActivity{

    protected static String TAG;
    protected Activity mContext;
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置 Activity 屏幕方向
        supportRequestWindowFeature(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //设置不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //隐藏ActionBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置TAG
        TAG = this.getClass().getSimpleName();
        super.onCreate(savedInstanceState);
        mContext = this;

        //设置布局
        setContentView(getLayoutRes());

        //绑定依赖注入框架
        ButterKnife.bind(this);
        onCreateActivity(savedInstanceState);

        //将 Activity 推入栈中
        ActivityStack.getInstance().push(this);
    }

    public abstract int getLayoutRes();
    public abstract void onCreateActivity(Bundle savedInstanceState);

    /**
     * 初始化标题栏
     * @param title
     * @return
     */
    public Toolbar initToolbar(String title){
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //取消原有左侧标题
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        //设置标题
        TextView txtTitle = findViewById(R.id.txt_title_title);
        txtTitle.setText(title);
        //设置左侧图标
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBack();
            }
        });
        return mToolbar;
    }

    /**
     * 设置标题栏标题
     * @param title
     */
    public void setTitle(String title){
        if (mToolbar != null) {
            TextView txtTitle = findViewById(R.id.txt_title_title);
            txtTitle.setText(title);
        }
    }

    /**
     * 设置标题栏标题颜色
     * @param id
     */
    public void setTitleTextColor(int id){
        if (mToolbar != null) {
            TextView txtTitle = findViewById(R.id.txt_title_title);
            txtTitle.setTextColor(id);
        }
    }

    public void setLeftTitle(String leftTitle){
        if (mToolbar != null && leftTitle != null) {
            mToolbar.setNavigationIcon(null);
            TextView txtLeftTitle = findViewById(R.id.txt_title_left);
            txtLeftTitle.setVisibility(View.VISIBLE);
            txtLeftTitle.setText(leftTitle);
            txtLeftTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBack();
                }
            });
        }
    }

    /**
     * 设置左侧标题栏颜色
     * @param id
     */
    public void setLeftTitleColor(int id){
        if (mToolbar != null) {
            TextView txtLeftTitle = findViewById(R.id.txt_title_left);
            txtLeftTitle.setTextColor(id);
        }
    }

    /**
     * 设置左侧标题字体大小
     * @param size
     */
    public void setLeftTitleSize(float size){
        if (mToolbar != null) {
            TextView txtLeftTitle = findViewById(R.id.txt_title_left);
            txtLeftTitle.setTextSize(size);
        }
    }

    /**
     * 显示加载中布局
     */
    public void showLoading(){
        MultiStatusLayout multiStatusLayout = (MultiStatusLayout) findViewById(R.id.layout_multi_status);
        if(multiStatusLayout != null){
            multiStatusLayout.setStatus(MultiStatusLayout.STATUS_LOADING);
        }
    }

    /**
     * 显示加载失败布局
     */
    public void showFail(View.OnClickListener listener){
        MultiStatusLayout multiStatusLayout = (MultiStatusLayout) findViewById(R.id.layout_multi_status);
        if(multiStatusLayout != null){
            multiStatusLayout.setStatus(MultiStatusLayout.STATUS_LOAD_FAIL, listener);
        }
    }

    /**
     * 显示内容
     */
    public void showContent(){
        MultiStatusLayout multiStatusLayout = (MultiStatusLayout) findViewById(R.id.layout_multi_status);
        if(multiStatusLayout != null){
            multiStatusLayout.setStatus(MultiStatusLayout.STATUS_HIDE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(null != this.getCurrentFocus()){
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    /**
     * 返回，默认退出当前 activity
     */
    protected void onBack() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 将 Activity 弹出栈
        ActivityStack.getInstance().pop();
    }

}
