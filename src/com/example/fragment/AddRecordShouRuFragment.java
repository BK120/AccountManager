package com.example.fragment;
/**
 * 添加消费记录里面的第一个Fragment
 * 收入Fragment
 * 对相应的账号做数据真加
 */
import java.util.List;

import com.example.activity.AccountsListActivity;
import com.example.bean.Account;
import com.example.bean.Record;
import com.example.bean.User;
import com.example.dao.MySQLiteDao;
import com.example.moneymanager.R;
import com.example.utils.MyUtils;
import com.example.utils.SharePreferenceUtils;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddRecordShouRuFragment extends Fragment implements OnClickListener{
	private View shouRuView;
	private ImageView name_iv,type_iv,save_iv;
	private EditText money_et,des_et;//des_et:用于记录des和改变背景
	private TextView gongzhi_tv,jiangjing_tv,shenghuofei_tv,tuikuan_tv,jianzhi_tv,zaxiang_tv,
		type_tv,date_tv,month_tv;
	private RelativeLayout money_type_rl;//用于改变背景颜色
	private User user;
	private MySQLiteDao dao;
	private MyUtils myUtils;
	private List<Account> accountList;//该用户的资金账户
	private SharePreferenceUtils spu;
	private int year,month,day,nowYear,nowMonth,nowDay;//选择时间与当前时间
	private int inorout=1;//收入为1
	private int newType;//操作的账号Id
	private int name=5;//事务的类别,默认5
	private Account account;//当前操作的账号
	private Handler myHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==0x110){
				date_tv.setText(day+"");
				month_tv.setText(month+"");
			}
		};
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//return super.onCreateView(inflater, container, savedInstanceState);
		shouRuView=inflater.inflate(R.layout.addrecordactivity_shouru, container, false);
		dao=new MySQLiteDao(getContext());
		myUtils=new MyUtils(getContext());
		spu=new SharePreferenceUtils(getContext());
		initView();
		return shouRuView;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		user=myUtils.findOnLineUser();
		accountList=dao.selectAllAccount_tab(user);
		setTypeImageAndDes();
		setDate();
		setViewListener();
	}
	
	private void setDate() {
		// TODO Auto-generated method stub
		year=myUtils.findYear();
		nowYear=year;
		month=myUtils.findMonthOfYear();
		nowMonth=month;
		day=myUtils.findDayOfMonth();
		nowDay=day;
		date_tv.setText(day+"");
		month_tv.setText(month+"");
	}
	private void setTypeImageAndDes() {
		// TODO Auto-generated method stub
		int index;
		index=spu.getInt("account_type");
		account = accountList.get(index);
		newType=account.getId();
		type_tv.setText(account.getDes());
		int type=account.getType();
		setTypeImage(type);
	}
	private void setTypeImage(int type) {
		// TODO Auto-generated method stub
		switch (type) {
		case 0:
			type_iv.setImageResource(R.drawable.ft_cash1);
			break;
		case 1:
			type_iv.setImageResource(R.drawable.ft_chuxuka1);
			break;
		case 2:
			type_iv.setImageResource(R.drawable.ft_creditcard1);
			break;
		case 3:
			type_iv.setImageResource(R.drawable.ft_shiwuka1);
			break;
		case 4:
			type_iv.setImageResource(R.drawable.ft_wangluochongzhi1);
			break;
		case 5:
			type_iv.setImageResource(R.drawable.ft_yingshouqian1);
			break;
		}
	}
	private void setViewListener() {
		// TODO Auto-generated method stub
		save_iv.setOnClickListener(this);
		//开始进入即默认设置颜色即图片设置
		money_type_rl.setBackgroundResource(R.color.my_color_yellow);
		money_et.setBackgroundResource(R.color.my_color_yellow);
		des_et.setBackgroundResource(R.color.my_color_yellow);
		name_iv.setImageResource(R.drawable.bt_yiwai);
		//该选项卡设置监听
		gongzhi_tv.setOnClickListener(this);
		jiangjing_tv.setOnClickListener(this);
		jianzhi_tv.setOnClickListener(this);
		zaxiang_tv.setOnClickListener(this);
		shenghuofei_tv.setOnClickListener(this);
		tuikuan_tv.setOnClickListener(this);
		//
		type_tv.setOnClickListener(this);
		date_tv.setOnClickListener(this);
	}
	private void initView() {
		// TODO Auto-generated method stub
		name_iv=(ImageView) shouRuView.findViewById(R.id.AddRecored_shouru_Frament_name_iv);
		type_iv=(ImageView) shouRuView.findViewById(R.id.AddRecored_shouru_Frament_type_iv);
		save_iv=(ImageView) shouRuView.findViewById(R.id.AddRecored_shouru_Frament_save_iv);
		money_et=(EditText) shouRuView.findViewById(R.id.AddRecored_shouru_Frament_money_et);
		des_et=(EditText) shouRuView.findViewById(R.id.AddRecored_shouru_Frament_des_et);
		gongzhi_tv=(TextView) shouRuView.findViewById(R.id.AddRecored_shouru_Frament_gongzhi_tv);
		jiangjing_tv=(TextView) shouRuView.findViewById(R.id.AddRecored_shouru_Frament_jiangjing_tv);
		shenghuofei_tv=(TextView) shouRuView.findViewById(R.id.AddRecored_shouru_Frament_shenghuofei_tv);
		tuikuan_tv=(TextView) shouRuView.findViewById(R.id.AddRecored_shouru_Frament_tuikuan_tv);
		jianzhi_tv=(TextView) shouRuView.findViewById(R.id.AddRecored_shouru_Frament_jianzhi_tv);
		zaxiang_tv=(TextView) shouRuView.findViewById(R.id.AddRecored_shouru_Frament_zaxiang_tv);
		type_tv=(TextView) shouRuView.findViewById(R.id.AddRecored_shouru_Frament_type_tv);
		date_tv=(TextView) shouRuView.findViewById(R.id.AddRecored_shouru_Frament_date_tv);
		month_tv=(TextView) shouRuView.findViewById(R.id.AddRecored_shouru_Frament_month_tv);
		money_type_rl=(RelativeLayout) shouRuView.findViewById(R.id.AddRecored_shouru_fragment_rl);
	}
	//对当前fragment改变做出监听
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.AddRecored_shouru_Frament_save_iv:
			//保存当前消费记录,并退出当前的Activity
//**************************************************************************************************			
			String money=money_et.getText().toString().trim();
			//描述字段可有可无
			String des=des_et.getText().toString().trim();
			if(TextUtils.isEmpty(money)){
				Toast.makeText(getContext(), "收入为0你记录什么个鬼？", 0).show();
				return;
			}
			if(year>nowYear){
				Toast.makeText(getContext(), "未来的事你都知道？", 0).show();
				return;
			}
			if(year==nowYear&&month>nowMonth){
				Toast.makeText(getContext(), "未来的事你都知道？", 0).show();
				return;
			}
			if(year==nowYear&&month==nowMonth&&day>nowDay){
				Toast.makeText(getContext(), "未来的事你都知道？", 0).show();
				return;
			}
			double money1=Double.valueOf(money);
			//计算账户总的钱
			double allMoney=account.getMoney()+money1;
			account.setMoney(allMoney);
			//修改账户
			dao.updateAccount_tab(account);
			String date=year+"-"+month+"-"+day;
			Record r=new Record(inorout, name, newType, money1, des, date, month, user.getId());
			//插入一条收入
			dao.insertRecord_tab(r);
			Toast.makeText(getContext(), "成功进账一笔钱！", 0).show();
			getActivity().finish();
			break;
		//对选项改变颜色改变做出反应
		case R.id.AddRecored_shouru_Frament_gongzhi_tv:
			money_type_rl.setBackgroundResource(R.color.my_color_blue);
			des_et.setBackgroundResource(R.color.my_color_blue);
			name_iv.setImageResource(R.drawable.bt_wages);
			money_et.setBackgroundResource(R.color.my_color_blue);
			name=0;
			break;
		case R.id.AddRecored_shouru_Frament_jiangjing_tv:
			money_type_rl.setBackgroundResource(R.color.my_color_brown);
			des_et.setBackgroundResource(R.color.my_color_brown);
			money_et.setBackgroundResource(R.color.my_color_brown);
			name_iv.setImageResource(R.drawable.bt_bouns);
			name=1;
			break;
		case R.id.AddRecored_shouru_Frament_shenghuofei_tv:
			money_type_rl.setBackgroundResource(R.color.my_color_green);
			des_et.setBackgroundResource(R.color.my_color_green);
			name_iv.setImageResource(R.drawable.bt_shenghuofei);
			money_et.setBackgroundResource(R.color.my_color_green);
			name=2;
			break;
		case R.id.AddRecored_shouru_Frament_tuikuan_tv:
			money_type_rl.setBackgroundResource(R.color.my_color_red);
			des_et.setBackgroundResource(R.color.my_color_red);
			money_et.setBackgroundResource(R.color.my_color_red);
			name_iv.setImageResource(R.drawable.bt_tuikuan);
			name=3;
			break;
		case R.id.AddRecored_shouru_Frament_jianzhi_tv:
			money_type_rl.setBackgroundResource(R.color.my_color_yale);
			des_et.setBackgroundResource(R.color.my_color_yale);
			money_et.setBackgroundResource(R.color.my_color_yale);
			name_iv.setImageResource(R.drawable.bt_jianzhi);
			name=4;
			break;
		case R.id.AddRecored_shouru_Frament_zaxiang_tv:
			money_type_rl.setBackgroundResource(R.color.my_color_yellow);
			des_et.setBackgroundResource(R.color.my_color_yellow);
			money_et.setBackgroundResource(R.color.my_color_yellow);
			name_iv.setImageResource(R.drawable.bt_yiwai);
			name=5;
			break;
			//打开账户信息Dialog选择账户接受钱款
		case R.id.AddRecored_shouru_Frament_type_tv:
			//打开一个用户的账户对话框--实际为一个Activity
			Intent i=new Intent(getActivity(),AccountsListActivity.class);
			startActivity(i);
			break;
		case R.id.AddRecored_shouru_Frament_date_tv:
			//弹出一个对话框
			DatePickerDialog datedialog=new DatePickerDialog(getContext(), new OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int newyear, int monthOfYear, int dayOfMonth) {
					// TODO Auto-generated method stub
					monthOfYear+=1;
					year=newyear;
					month=monthOfYear;
					day=dayOfMonth;
					myHandler.sendEmptyMessage(0x110);
				}
			}, year, month-1, day);
			datedialog.show();
			break;
		}
	}
}
