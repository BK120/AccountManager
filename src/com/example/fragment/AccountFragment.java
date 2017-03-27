package com.example.fragment;
/**
 * ��ҳ����һ��Fragment������չʾ���Ѽ�¼
 */
import java.util.List;

import com.example.activity.AddRecordActivity;
import com.example.adapter.MyRecordAdapter;
import com.example.bean.Record;
import com.example.bean.User;
import com.example.dao.MySQLiteDao;
import com.example.moneymanager.R;
import com.example.utils.MyUtils;
import com.example.utils.SharePreferenceUtils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AccountFragment extends Fragment implements OnClickListener{
	private User user;
	private View accountView;
	private ImageView myPen_iv;
	private TextView calendar_tv,shouru_yuefen_tv,zhichu_yuefen_tv,shouru_money_tv,zhichu_money_tv;
	private ListView listView;
	private List<Record> list;
	private MyUtils myUtils;
	private MySQLiteDao dao;
	private SharePreferenceUtils spu;
	private int month;
	private int year;
	private int day;//��ǰϵͳ������������
	private Listener lis;
	public interface Listener{
		public void refreshFormFragment();
	}
	private Handler myHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==0x456){
				//��ǰFragmentˢ��ͬʱˢ��Activity
				//��ˢ��ȫ��
				lis.refreshFormFragment();
			}
			//ֻˢ�µ�ǰչʾ��listview
			//����ѡ��record��
			if(msg.what==0x369){
				list=dao.selectAllRecord(month,user);
				initMonthChange();
				initListViewAdapter();
				initShouRuAndZhiChuTV();
			}
		};
	};
	public AccountFragment(User user) {
		// TODO Auto-generated constructor stub
		this.user=user;
	}
	//ת��ӿڣ�ˢ��FormFragment
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		lis=(Listener)activity;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		accountView=inflater.inflate(R.layout.activity_account, container,false);
		initView();
		myUtils=new MyUtils(getContext());
		spu=new SharePreferenceUtils(getContext());
		dao=new MySQLiteDao(getContext());
		return accountView;

	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		user=MyUtils.findOnLineUser();
		viewSetListener();
		initMonth();
		list=dao.selectAllRecord(month,user);
		initMonthChange();
		//��ʼ��ListView
		initListViewAdapter();
		initShouRuAndZhiChuTV();
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//�ظ�Ĭ��ѡ���recored��Ϊ��
		spu.putInt("record_listview_index", -1);
	}
	//�����·ݵĸı�
	private void initMonthChange() {
		// TODO Auto-generated method stub
		shouru_yuefen_tv.setText(month+"������");
		zhichu_yuefen_tv.setText(month+"��֧��");
	}
	private void initShouRuAndZhiChuTV() {
		// TODO Auto-generated method stub
		if(user.getState()==User.OFF_LINE){
			shouru_money_tv.setText(0.00+"");
			zhichu_money_tv.setText(0.00+"");
		}else{
			setMoney();
		}
	}
	private void setMoney() {
		// TODO Auto-generated method stub
		//Ĭ��Ϊ0.0
		double shouRu=0.00;
		double zhiChu=0.00;
		for(Record r:list){
			if(r.getInorout()==0){
				//֧��
				zhiChu+=r.getMoney();
			}else{
				//����
				shouRu+=r.getMoney();
			}
		}
		shouru_money_tv.setText(shouRu+"");
		zhichu_money_tv.setText(zhiChu+"");
	}
	private void initMonth() {
		// TODO Auto-generated method stub
		month=myUtils.findMonthOfYear();
		year=myUtils.findYear();
		day=myUtils.findDayOfMonth();
	}
	private void initListViewAdapter() {
		// TODO Auto-generated method stub
		if(user.getState()==User.OFF_LINE){
			Toast.makeText(getContext(), "��¼�û����ɲ�ѯ���Ѽ�¼��", 0).show();
			return;
		}
		if(list.size()==0){
			Toast.makeText(getContext(), "�㵱��û�����Ѽ�¼�أ�", 0).show();
			listView.setAdapter(new MyRecordAdapter(getActivity(),list,dao,myHandler));
			return;
		}
			listView.setAdapter(new MyRecordAdapter(getActivity(),list,dao,myHandler));
		
	}
	//��ʼ���ؼ�
	private void initView() {
		// TODO Auto-generated method stub
		myPen_iv=(ImageView) accountView.findViewById(R.id.AccountActivity_my_pen_iv);
		calendar_tv=(TextView) accountView.findViewById(R.id.AccountActivity_calendar_tv);
		shouru_yuefen_tv=(TextView) accountView.findViewById(R.id.AccountActivity_shouru_yuefen_tv);
		shouru_money_tv=(TextView) accountView.findViewById(R.id.AccountActivity_shouru_money_tv);
		zhichu_yuefen_tv=(TextView) accountView.findViewById(R.id.AccountActivity_zhichu_yuefen_tv);
		zhichu_money_tv=(TextView) accountView.findViewById(R.id.AccountActivity_zhichu_money_tv);
		listView=(ListView) accountView.findViewById(R.id.AccountActivity_listview);
	}
	private void viewSetListener() {
		//��������ʱ�䣬����
		calendar_tv.setText(myUtils.findDayOfMonth()+"");
		myPen_iv.setOnClickListener(this);
		calendar_tv.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.AccountActivity_my_pen_iv:
			if(user.getState()==User.ON_LINE){
				//���һ�����ݽ������ݿ⣬��ˢ�µ�ǰ
				Intent i=new Intent(getActivity(),AddRecordActivity.class);
				startActivity(i);
			}else{
				//δ��¼״̬����ʾ
				Toast.makeText(getContext(), "���ȵ�¼�û���", 0).show();
			}
			break;
		case R.id.AccountActivity_calendar_tv:	
			//���·ݵ�ˢ�²�ѯ����
			DatePickerDialog dpa=new DatePickerDialog(getContext(), new OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int newYear, int monthOfYear, int dayOfMonth) {
					// TODO Auto-generated method stub
					Toast.makeText(getContext(), newYear+";"+monthOfYear+";"+dayOfMonth, 0).show();
					month=monthOfYear+1;
					myHandler.sendEmptyMessage(0x369);
				}
			}, year, month-1, day);
			dpa.show();
			break;
		}
	}
}
