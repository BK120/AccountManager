package com.example.adapter;
/**
 * 主界面的用户控制四个Fragment的AdapterViewPager
 */
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentViewPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> fragments;
	public MyFragmentViewPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	public MyFragmentViewPagerAdapter(FragmentManager fm,List<Fragment> fragments) {
		super(fm);
		this.fragments=fragments;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragments.size();
	}

}
