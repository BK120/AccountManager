package com.example.adapter;
/**
 * 添加消费记录的收入支出的FragmentViewpagerAdapter
 */
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AddRecordViewPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> list;
	public AddRecordViewPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	public AddRecordViewPagerAdapter(FragmentManager fm,List<Fragment> list) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.list=list;
	}
	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
}
