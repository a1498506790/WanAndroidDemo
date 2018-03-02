package com.mir.wanandroiddemo.base;

import android.app.Application;
import android.content.Context;

/**
 * @author by lx
 * @github https://github.com/a1498506790
 * @data 2018/3/2
 * @desc
 */

public class App extends Application {

    public static final String TAG = App.class.getName();
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
