package com.mir.wanandroiddemo.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mir.wanandroiddemo.R;
import com.mir.wanandroiddemo.base.BaseFragment;

/**
 * @author by lx
 * @github https://github.com/a1498506790
 * @data 2018/3/2
 * @desc 首页
 */

public class HomePageFragment extends BaseFragment{

    @Override
    public View getLayout(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onCreateFragment(@Nullable Bundle savedInstanceState) {

    }
}
