package com.example.fragment;
/**
 * 主页：之第二个Fragment，
 * 展示收入支出的报表的情况
 */
import com.example.bean.User;
import com.example.moneymanager.R;
import com.example.utils.MyUtils;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class FormFragment extends Fragment implements OnClickListener{
	private User user;
	private View formView;
	private TextView shouru_tab_tv,zhichu_tb_tv;
	private FrameLayout fl;
	private android.support.v4.app.FragmentManager fm;
	private ShouRuFormFragment spff;
	private ZhiChuFormFragment zcff;
	private MyUtils myUtils;
	public FormFragment(User user) {
		// TODO Auto-generated constructor stub
		this.user=user;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		formView=inflater.inflate(R.layout.activity_form, container,false);
		fm=getActivity().getSupportFragmentManager();
		myUtils=new MyUtils(getContext());
		initView();
		return formView;
	}
	private void initView() {
		// TODO Auto-generated method stub
		shouru_tab_tv=(TextView) formView.findViewById(R.id.formActivity_shouru_tab_tv);
		zhichu_tb_tv=(TextView) formView.findViewById(R.id.formActivity_zhichu_tab_tv);
		fl=(FrameLayout) formView.findViewById(R.id.formActivity_fl);
		spff=new ShouRuFormFragment();
		zcff=new ZhiChuFormFragment();
		FragmentTransaction tr = fm.beginTransaction();
		tr.add(R.id.formActivity_fl, spff);
		tr.add(R.id.formActivity_fl, zcff);
		tr.show(spff);
		tr.commit();
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		user=myUtils.findOnLineUser();
		setViewListener();
	}
	private void setViewListener() {
		// TODO Auto-generated method stub
		shouru_tab_tv.setOnClickListener(this);
		zhichu_tb_tv.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		FragmentTransaction tr = fm.beginTransaction();
		tr.hide(zcff);
		tr.hide(spff);
		switch (v.getId()) {
		case R.id.formActivity_shouru_tab_tv:
			tr.show(spff);
			shouru_tab_tv.setTextColor(Color.parseColor("#12D9C0"));
			zhichu_tb_tv.setTextColor(Color.parseColor("#000000"));
			break;
		case R.id.formActivity_zhichu_tab_tv:
			tr.show(zcff);
			shouru_tab_tv.setTextColor(Color.parseColor("#000000"));
			zhichu_tb_tv.setTextColor(Color.parseColor("#12d9c0"));
			break;
		}
		tr.commit();
	}
}
