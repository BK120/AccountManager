package com.example.fragment;
/**
 * �������Ѽ�¼��Fragment����ĵڶ���Fragment
 * ֧��
 * ����Ӧ���˺������ݼ���
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

public class AddRecordZhiChuFragment extends Fragment implements OnClickListener{
	private View zhiChuView;
	private ImageView name_iv,type_iv,save_iv;
	private EditText money_et,des_et;//des_et:���ڼ�¼des�͸ı䱳��
	private TextView chi_tv,chuan_tv,zhu_tv,xing_tv,yule_tv,shenghuofuwu_tv,
		type_tv,date_tv,month_tv;
	private RelativeLayout money_type_rl;//���ڸı䱳����ɫ
	private User user;
	private MySQLiteDao dao;
	private MyUtils myUtils;
	private List<Account> accountList;//���û����ʽ��˻�
	private SharePreferenceUtils spu;
	private int year,month,day,nowYear,nowMonth,nowDay;//ѡ��ʱ���뵱ǰʱ��
	private int inorout=0;//����Ϊ1
	private int newType;//�������˺�Id
	private int name=5;//��������,Ĭ��5
	private Account account;//��ǰ�������˺�
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
		zhiChuView=inflater.inflate(R.layout.addrecordactivity_zhichu, container, false);
		dao=new MySQLiteDao(getContext());
		myUtils=new MyUtils(getContext());
		spu=new SharePreferenceUtils(getContext());
		initView();
		return zhiChuView;
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
		//��ʼ���뼴Ĭ��������ɫ��ͼƬ����
		money_type_rl.setBackgroundResource(R.color.my_color_yellow);
		money_et.setBackgroundResource(R.color.my_color_yellow);
		des_et.setBackgroundResource(R.color.my_color_yellow);
		name_iv.setImageResource(R.drawable.bt_service);
		//��ѡ����ü���
		chi_tv.setOnClickListener(this);
		chuan_tv.setOnClickListener(this);
		zhu_tv.setOnClickListener(this);
		xing_tv.setOnClickListener(this);
		yule_tv.setOnClickListener(this);
		shenghuofuwu_tv.setOnClickListener(this);
		//
		type_tv.setOnClickListener(this);
		date_tv.setOnClickListener(this);
	}
	private void initView() {
		// TODO Auto-generated method stub
		name_iv=(ImageView) zhiChuView.findViewById(R.id.AddRecored_zhichu_Frament_name_iv);
		type_iv=(ImageView) zhiChuView.findViewById(R.id.AddRecored_zhichu_Frament_type_iv);
		save_iv=(ImageView) zhiChuView.findViewById(R.id.AddRecored_zhichu_Frament_save_iv);
		money_et=(EditText) zhiChuView.findViewById(R.id.AddRecored_zhichu_Frament_money_et);
		des_et=(EditText) zhiChuView.findViewById(R.id.AddRecored_zhichu_Frament_des_et);
		chi_tv=(TextView) zhiChuView.findViewById(R.id.AddRecored_zhichu_Frament_chi_tv);
		chuan_tv=(TextView) zhiChuView.findViewById(R.id.AddRecored_zhichu_Frament_chuan_tv);
		zhu_tv=(TextView) zhiChuView.findViewById(R.id.AddRecored_zhichu_Frament_zhu_tv);
		xing_tv=(TextView) zhiChuView.findViewById(R.id.AddRecored_zhichu_Frament_xing_tv);
		yule_tv=(TextView) zhiChuView.findViewById(R.id.AddRecored_zhichu_Frament_yule_tv);
		shenghuofuwu_tv=(TextView) zhiChuView.findViewById(R.id.AddRecored_zhichu_Frament_shenghuofuwu_tv);
		type_tv=(TextView) zhiChuView.findViewById(R.id.AddRecored_zhichu_Frament_type_tv);
		date_tv=(TextView) zhiChuView.findViewById(R.id.AddRecored_zhichu_Frament_date_tv);
		month_tv=(TextView) zhiChuView.findViewById(R.id.AddRecored_zhichu_Frament_month_tv);
		money_type_rl=(RelativeLayout) zhiChuView.findViewById(R.id.AddRecored_zhichu_fragment_rl);
	}
	//�Ե�ǰfragment�ı���������
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.AddRecored_zhichu_Frament_save_iv:
			//���浱ǰ���Ѽ�¼,���˳���ǰ��Activity
//**************************************************************************************************			
			String money=money_et.getText().toString().trim();
			//�����ֶο��п���
			String des=des_et.getText().toString().trim();
			if(TextUtils.isEmpty(money)){
				Toast.makeText(getContext(), "֧��Ϊ0���¼ʲô����", 0).show();
				return;
			}
			if(year>nowYear){
				Toast.makeText(getContext(), "δ�������㶼֪����", 0).show();
				return;
			}
			if(year==nowYear&&month>nowMonth){
				Toast.makeText(getContext(), "δ�������㶼֪����", 0).show();
				return;
			}
			if(year==nowYear&&month==nowMonth&&day>nowDay){
				Toast.makeText(getContext(), "δ�������㶼֪����", 0).show();
				return;
			}
			double money1=Double.valueOf(money);
			//�Ե�ǰ�˻�Ǯ�Ĳ�ѯ�����Ƿ��ܳе��ñ�֧��
			//���޸��˻�
			double money2=account.getMoney();
			if(money2<money1){
				Toast.makeText(getContext(), "�˻��ʽ���������������ѡ���˻�", 0).show();
				return;
			}
			double money3=money2-money1;
			account.setMoney(money3);
			dao.updateAccount_tab(account);
			String date=year+"-"+month+"-"+day;
			Record r=new Record(inorout, name, newType, money1, des, date, month, user.getId());
			//����һ������
			dao.insertRecord_tab(r);
			Toast.makeText(getContext(), "����Ǯ��������֧��һ�ʣ�", 0).show();
			getActivity().finish();
			break;
		//��ѡ��ı���ɫ�ı�������Ӧ
		case R.id.AddRecored_zhichu_Frament_chi_tv:
			money_type_rl.setBackgroundResource(R.color.my_color_blue);
			des_et.setBackgroundResource(R.color.my_color_blue);
			name_iv.setImageResource(R.drawable.bt_food);
			money_et.setBackgroundResource(R.color.my_color_blue);
			name=0;
			break;
		case R.id.AddRecored_zhichu_Frament_chuan_tv:
			money_type_rl.setBackgroundResource(R.color.my_color_brown);
			des_et.setBackgroundResource(R.color.my_color_brown);
			money_et.setBackgroundResource(R.color.my_color_brown);
			name_iv.setImageResource(R.drawable.bt_shopping);
			name=1;
			break;
		case R.id.AddRecored_zhichu_Frament_zhu_tv:
			money_type_rl.setBackgroundResource(R.color.my_color_green);
			des_et.setBackgroundResource(R.color.my_color_green);
			name_iv.setImageResource(R.drawable.bt_house);
			money_et.setBackgroundResource(R.color.my_color_green);
			name=2;
			break;
		case R.id.AddRecored_zhichu_Frament_xing_tv:
			money_type_rl.setBackgroundResource(R.color.my_color_red);
			des_et.setBackgroundResource(R.color.my_color_red);
			money_et.setBackgroundResource(R.color.my_color_red);
			name_iv.setImageResource(R.drawable.bt_car);
			name=3;
			break;
		case R.id.AddRecored_zhichu_Frament_yule_tv:
			money_type_rl.setBackgroundResource(R.color.my_color_yale);
			des_et.setBackgroundResource(R.color.my_color_yale);
			money_et.setBackgroundResource(R.color.my_color_yale);
			name_iv.setImageResource(R.drawable.bt_yule);
			name=4;
			break;
		case R.id.AddRecored_zhichu_Frament_shenghuofuwu_tv:
			money_type_rl.setBackgroundResource(R.color.my_color_yellow);
			des_et.setBackgroundResource(R.color.my_color_yellow);
			money_et.setBackgroundResource(R.color.my_color_yellow);
			name_iv.setImageResource(R.drawable.bt_service);
			name=5;
			break;
			//���˻���ϢDialogѡ���˻�����Ǯ��
		case R.id.AddRecored_zhichu_Frament_type_tv:
			//��һ���û����˻��Ի���--ʵ��Ϊһ��Activity
			Intent i=new Intent(getActivity(),AccountsListActivity.class);
			startActivity(i);
			break;
		case R.id.AddRecored_zhichu_Frament_date_tv:
			//����һ���Ի���
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
