package com.example.activity;
/**
 * 主界面的activity，包括四个Fragement
 */
import java.util.ArrayList;
import java.util.List;

import com.example.adapter.MyFragmentViewPagerAdapter;
import com.example.bean.User;
import com.example.dao.MySQLiteDao;
import com.example.fragment.AccountFragment;
import com.example.fragment.AccountFragment.Listener;
import com.example.fragment.FormFragment;
import com.example.fragment.FoundFragment;
import com.example.fragment.MineFragment;
import com.example.moneymanager.R;
import com.example.utils.MyUtils;
import com.example.utils.SharePreferenceUtils;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class HomeActivity extends FragmentActivity implements Listener{
	private ViewPager viewPager;
	private List<Fragment> viewList;
	private TextView account_tv,found_tv,form_tv,mine_tv;
	private FragmentManager fm;
	private AccountFragment accf;
	private FormFragment formf;
	private FoundFragment foundf;
	private MineFragment minef;
	private User user;
	private SharePreferenceUtils spu;
	//用于记录退出时的Fragment的页码
	private int fragment_index;
	//SharePreference的一个字段名常量
	private String INDEX="fragment_index";
	//处理不同Fragment之间的刷新,全局性的待定
	private Handler MainHandler=new Handler(){
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		MyUtils myUtils=new MyUtils(this);
		fm = this.getSupportFragmentManager();
		spu=new SharePreferenceUtils(this);
		//开始进入主页时，查询用户状态，刷新各个Fragment
		initView();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		user=MyUtils.findOnLineUser();
		super.onResume();
		//设置监听
		viewSetListener();
		//拿取上一次关闭时记录的Fragment_index,并设置默认为0
		fragment_index=spu.getInt(INDEX);
		viewPager.setCurrentItem(fragment_index);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//当前Activity被阻塞时记录当前的Fragment坐标
		spu.putInt("fragment_index",fragment_index);
	}
	private void initView() {
		// TODO Auto-generated method stub
		account_tv=(TextView) this.findViewById(R.id.tab_account_tv);
		form_tv=(TextView) this.findViewById(R.id.tab_form_tv);
		found_tv=(TextView) this.findViewById(R.id.tab_founds_tv);
		mine_tv=(TextView) this.findViewById(R.id.tab_mine_tv);
		viewPager=(ViewPager) this.findViewById(R.id.viewpager);
	}
	private void viewSetListener() {
		// TODO Auto-generated method stub
		initviewList();
		account_tv.setOnClickListener(new MyPagerChangeListener(0));
		form_tv.setOnClickListener(new MyPagerChangeListener(1));
		found_tv.setOnClickListener(new MyPagerChangeListener(2));
		mine_tv.setOnClickListener(new MyPagerChangeListener(3));
		viewPager.setAdapter(new MyFragmentViewPagerAdapter(fm,viewList));
		viewPager.addOnPageChangeListener(new MyPagerChangeListener());;
	}
	private void initviewList() {
		// TODO Auto-generated method stub
		viewList=new ArrayList<Fragment>();
		accf=new AccountFragment(user);
		formf=new FormFragment(user);
		foundf=new FoundFragment(user);
		minef=new MineFragment(user);
		viewList.add(accf);
		viewList.add(formf);
		viewList.add(foundf);
		viewList.add(minef);
	}
	/**
	 * ViewPager的监听
	 * @author Administrator
	 */
	private class MyPagerChangeListener implements OnPageChangeListener,OnClickListener{
		//用于记录传入的标号View
		private int Tag;
		public MyPagerChangeListener() {
			// TODO Auto-generated constructor stub
		}
		public MyPagerChangeListener(int id) {
			// TODO Auto-generated constructor stub
			this.Tag=id;
		}
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
		}
		/**
		 * 选择了的ViewPager对Tab的改变
		 * @param arg0
		 */
		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			switch (arg0) {
			case 0:
				//记录修改当前的Fragment坐标
				fragment_index=0;
				account_change();
				break;
			case 1:
				fragment_index=1;
				form_change();
				break;
			case 2:
				fragment_index=2;
				found_change();
				break;
			case 3:
				fragment_index=3;
				mine_change();
				break;
			}
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.tab_account_tv:
				account_change();
				viewPager.setCurrentItem(Tag);
				break;
			case R.id.tab_form_tv:
				form_change();
				viewPager.setCurrentItem(Tag);
				break;
			case R.id.tab_founds_tv:
				found_change();
				viewPager.setCurrentItem(Tag);
				break;
			case R.id.tab_mine_tv:
				mine_change();
				viewPager.setCurrentItem(Tag);
				break;
			}
		}
		public void mine_change() {
			textViewChangeHui();
			Drawable d4=getResources().getDrawable(R.drawable.tab_mine);
			d4.setBounds(0, 0, d4.getMinimumWidth(), d4.getMinimumHeight());
			mine_tv.setCompoundDrawables(null, d4, null, null);
			mine_tv.setTextColor(Color.parseColor("#3FC6B6"));
		}
		public void found_change() {
			textViewChangeHui();
			Drawable d3=getResources().getDrawable(R.drawable.tab_founds);
			d3.setBounds(0, 0, d3.getMinimumWidth(), d3.getMinimumHeight());
			found_tv.setCompoundDrawables(null, d3, null, null);
			found_tv.setTextColor(Color.parseColor("#3FC6B6"));
		}
		public void form_change() {
			textViewChangeHui();
			Drawable d2=getResources().getDrawable(R.drawable.tab_form);
			d2.setBounds(0, 0, d2.getMinimumWidth(), d2.getMinimumHeight());
			form_tv.setCompoundDrawables(null, d2, null, null);
			form_tv.setTextColor(Color.parseColor("#3FC6B6"));
		}
		public void account_change() {
			textViewChangeHui();
			//对上边的图标的改变和对文字的改变
			Drawable d1=getResources().getDrawable(R.drawable.tab_accounte);
			d1.setBounds(0, 0, d1.getMinimumWidth(), d1.getMinimumHeight());
			account_tv.setCompoundDrawables(null, d1, null, null);
			account_tv.setTextColor(Color.parseColor("#3FC6B6"));
		}
		/**
		 * 让所有的颜色都设置成普通颜色
		 */
		public void textViewChangeHui(){
			//account页
			Drawable d1=getResources().getDrawable(R.drawable.tab_accounte2);
			d1.setBounds(0, 0, d1.getMinimumWidth(), d1.getMinimumHeight());
			account_tv.setCompoundDrawables(null, d1, null, null);
			account_tv.setTextColor(Color.parseColor("#000000"));
			//form页
			Drawable d2=getResources().getDrawable(R.drawable.tab_form2);
			d2.setBounds(0, 0, d2.getMinimumWidth(), d2.getMinimumHeight());
			form_tv.setCompoundDrawables(null, d2, null, null);
			form_tv.setTextColor(Color.parseColor("#000000"));
			//found页
			Drawable d3=getResources().getDrawable(R.drawable.tab_founds2);
			d3.setBounds(0, 0, d3.getMinimumWidth(), d3.getMinimumHeight());
			found_tv.setCompoundDrawables(null, d3, null, null);
			found_tv.setTextColor(Color.parseColor("#000000"));
			//account页
			Drawable d4=getResources().getDrawable(R.drawable.tab_mine2);
			d4.setBounds(0, 0, d4.getMinimumWidth(), d4.getMinimumHeight());
			mine_tv.setCompoundDrawables(null, d4, null, null);
			mine_tv.setTextColor(Color.parseColor("#000000"));
		}
		
	}
	//通过AccountFragment刷新主Activity
	@Override
	public void refreshFormFragment() {
		// TODO Auto-generated method stub
		onResume();
	} 
	
}
