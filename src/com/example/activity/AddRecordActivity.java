package com.example.activity;
/**
 * 添加一条消费记录的Activity
 */
import java.util.ArrayList;
import java.util.List;

import com.example.adapter.AddRecordViewPagerAdapter;
import com.example.fragment.AddRecordShouRuFragment;
import com.example.fragment.AddRecordZhiChuFragment;
import com.example.moneymanager.R;
import com.example.utils.SharePreferenceUtils;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class AddRecordActivity extends FragmentActivity{
	private ImageView back_iv;
	private TextView shouru_tv,zhichu_tv;
	private ViewPager viewPager;
	private FragmentManager fm;
	private List<Fragment> list;
	private AddRecordShouRuFragment arsrf;
	private AddRecordZhiChuFragment arzcf;
	private AddRecordViewPagerAdapter apAdapter;
	private SharePreferenceUtils spu;
	private int index;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_record);
		fm=this.getSupportFragmentManager();
		spu=new SharePreferenceUtils(this);
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		back_iv=(ImageView) this.findViewById(R.id.AddRecordActivity_back_iv);
		shouru_tv=(TextView) this.findViewById(R.id.AddRecordActivity_shouru_tab_tv);
		zhichu_tv=(TextView) this.findViewById(R.id.AddRecordActivity_zhichu_tab_tv);
		viewPager=(ViewPager) this.findViewById(R.id.AddRecordActivity_viewPager);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		viewSetListener();
		index=spu.getInt("shouruzhichu_index");
		viewPager.setCurrentItem(index);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		spu.putInt("shouruzhichu_index", index);
	}
	private void viewSetListener() {
		// TODO Auto-generated method stub
		initListFragment();
		back_iv.setOnClickListener(new AddRecMyListener());
		shouru_tv.setOnClickListener(new AddRecMyListener());
		zhichu_tv.setOnClickListener(new AddRecMyListener());
		apAdapter=new AddRecordViewPagerAdapter(fm,list);
		viewPager.setAdapter(apAdapter);
		viewPager.addOnPageChangeListener(new AddRecMyPagerChangeListener());
	}
	private void initListFragment() {
		// TODO Auto-generated method stub
		list=new ArrayList<Fragment>();
		arsrf=new AddRecordShouRuFragment();
		arzcf=new AddRecordZhiChuFragment();
		list.add(arsrf);
		list.add(arzcf);
	}
	class AddRecMyListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.AddRecordActivity_shouru_tab_tv:
				viewPager.setCurrentItem(0);
				shouru_tv.setTextColor(Color.parseColor("#00D6BB"));
				zhichu_tv.setTextColor(Color.parseColor("#000000"));
				break;
			case R.id.AddRecordActivity_zhichu_tab_tv:
				viewPager.setCurrentItem(1);
				shouru_tv.setTextColor(Color.parseColor("#000000"));
				zhichu_tv.setTextColor(Color.parseColor("#00D6BB"));
				break;
			case R.id.AddRecordActivity_back_iv:
				//退出当前Activity，不做任何数据的保存
				AddRecordActivity.this.finish();
				break;

			}
		}
		
	}
	class AddRecMyPagerChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			switch (arg0) {
			case 0:
				shouru_tv.setTextColor(Color.parseColor("#00D6BB"));
				zhichu_tv.setTextColor(Color.parseColor("#000000"));
				index=0;
				break;
			case 1:
				shouru_tv.setTextColor(Color.parseColor("#000000"));
				zhichu_tv.setTextColor(Color.parseColor("#00D6BB"));
				index=1;
				break;
			}
		}
		
	}
}
