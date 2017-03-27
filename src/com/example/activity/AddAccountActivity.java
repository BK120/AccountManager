package com.example.activity;
/**
 * 个用户创建自己的账户的Activity
 */
import java.util.List;

import com.example.bean.Account;
import com.example.bean.User;
import com.example.dao.MySQLiteDao;
import com.example.moneymanager.R;
import com.example.moneymanager.R.color;
import com.example.moneymanager.R.drawable;
import com.example.moneymanager.R.id;
import com.example.moneymanager.R.layout;
import com.example.utils.MyUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddAccountActivity extends Activity implements OnClickListener{
	private User user;//能打开这个Activity，必定存在一个在线的用户
	private ImageView iv_back,iv_type,iv_color;
	private EditText et_account_des,et_account_money;
	private TextView tv_show_type;
	private RelativeLayout  rl_account_type,rl_account_color;
	private Button save_btn;
	private View typeView;//账户类型View
	private View colorView;//颜色视图的View
	//typeDialog里面的控件
	private AlertDialog dialog_type ;
	private Button type_dialog_cancle_btn;//取消按钮
	private RelativeLayout type_dialog_rl1,type_dialog_rl2,type_dialog_rl3,type_dialog_rl4,
	type_dialog_rl5,type_dialog_rl6;//typedialog中的每一个type
	private ImageView type_dialog_iv1,type_dialog_iv2,type_dialog_iv3,type_dialog_iv4,type_dialog_iv5,
	type_dialog_iv6;//dialog中的每一个ok图标
	//colorDialog里面的控件
	private AlertDialog dialog_color;
	private RelativeLayout color_dialog_rl;//显示背景
	private RadioGroup color_dialog_rg;
	private Button color_dialog_cancle_btn,color_dialog_confirm_btn;//colorDialog中的确认取消按钮
	//默认选择，避免在不选择颜色的时候为空报错
	private int color_dialog_rbId=R.color.my_color_red;//单选按钮传出的颜色选择的Id,默认选择第一项
	//确定类型.默认为现金，防止不选择类型报错。
	private String type="现金";
	private MySQLiteDao dao;
	private MyUtils myUtils;
	//处理从Dialog传过来的信息
	private Handler myHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==0x011){
				tv_show_type.setText("现金");
				//更改Type值
				type="现金";
				iv_type.setImageResource(R.drawable.ft_cash1);
			}
			if(msg.what==0x012){
				tv_show_type.setText("储蓄卡");
				type="储蓄卡";
				iv_type.setImageResource(R.drawable.ft_chuxuka1);
			}
			if(msg.what==0x013){
				tv_show_type.setText("信用卡");
				type="信用卡";
				iv_type.setImageResource(R.drawable.ft_creditcard1);
			}
			if(msg.what==0x014){
				tv_show_type.setText("购物卡");
				type="购物卡";
				iv_type.setImageResource(R.drawable.ft_shiwuka1);
			}
			if(msg.what==0x015){
				tv_show_type.setText("支付宝");
				type="支付宝";
				iv_type.setImageResource(R.drawable.ft_wangluochongzhi1);
			}
			if(msg.what==0x016){
				tv_show_type.setText("应急钱款");
				type="应急钱款";
				iv_type.setImageResource(R.drawable.ft_yingshouqian1);
			}
			if(msg.what==0x101){
				int id=msg.arg1;
				switch (id) {
				case R.id.AddAccountActivity_dialog_color_rb1:
					//将RadioButton的Id值转变为颜色值
					iv_color.setImageResource(R.color.my_color_red);
					color_dialog_rbId=R.color.my_color_red;
					break;
				case R.id.AddAccountActivity_dialog_color_rb2:
					iv_color.setImageResource(R.color.my_color_blue);
					color_dialog_rbId=R.color.my_color_blue;
					break;
				case R.id.AddAccountActivity_dialog_color_rb3:
					iv_color.setImageResource(R.color.my_color_brown);
					color_dialog_rbId=R.color.my_color_brown;
					break;
				case R.id.AddAccountActivity_dialog_color_rb4:
					iv_color.setImageResource(R.color.my_color_green);
					color_dialog_rbId=R.color.my_color_green;
					break;
				case R.id.AddAccountActivity_dialog_color_rb5:
					iv_color.setImageResource(R.color.my_color_yale);
					color_dialog_rbId=R.color.my_color_yale;
					break;
				case R.id.AddAccountActivity_dialog_color_rb6:
					iv_color.setImageResource(R.color.my_color_yellow);
					color_dialog_rbId=R.color.my_color_yellow;
					break;
					
				}
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_account);
		dao=new MySQLiteDao(this);
		myUtils=new MyUtils(this);
		user=myUtils.findOnLineUser();
		initView();
		setListener();
	}
	private void initView() {
		iv_back=(ImageView) this.findViewById(R.id.AddAccountActivity_back_iv);
		iv_type=(ImageView) this.findViewById(R.id.AddAccountActivity_rl_type_iv);
		iv_color=(ImageView) this.findViewById(R.id.AddAccountActivity_rl_color_iv);
		et_account_des=(EditText) this.findViewById(R.id.AddAccountActivity_des_et);
		et_account_money=(EditText) this.findViewById(R.id.AddAccountActivity_money_et);
		tv_show_type=(TextView) this.findViewById(R.id.AddAccountActivity_rl_type_tv);
		rl_account_type=(RelativeLayout) this.findViewById(R.id.AddAccountActivity_type_rl);
		rl_account_color=(RelativeLayout) this.findViewById(R.id.AddAccountActivity_color_rl);
		save_btn=(Button) this.findViewById(R.id.AddAccountActivity_save_btn);
	}
	//对每一个控件设置监听
	public void setListener(){
		iv_back.setOnClickListener(this);
		rl_account_type.setOnClickListener(this);
		rl_account_color.setOnClickListener(this);
		save_btn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.AddAccountActivity_back_iv:
			//回退到主Activity，需要刷新
			AddAccountActivity.this.finish();
			break;
		case R.id.AddAccountActivity_type_rl:
			//打开一个type的Dialog
			openTypeDialog();
			break;
		case R.id.AddAccountActivity_color_rl:
			//打开颜色选择Dialog
			openColorDialog();
			break;
		case R.id.AddAccountActivity_save_btn:
			//保存当前的Account并退出当前Activity
			//对当前money即账户名的判断
			String des=et_account_des.getText().toString().trim();
			String moneyStr=et_account_money.getText().toString().trim();
			if(TextUtils.isEmpty(des)){
				Toast.makeText(this, "用户名不能为空！", 0).show();
				return;
			}
			if(TextUtils.isEmpty(moneyStr)){
				Toast.makeText(this, "账户余额不能为空！", 0).show();
				return;
			}
			//检测当前用户是否已经存在该账户名的账户
			List<Account> list = dao.selectAllAccount_tab(user);
			for(Account a:list){
				if(a.getDes().equals(des)){
					Toast.makeText(this, "当前账户已经存在，请重新输入账户名！", 0).show();
					et_account_des.setText("");
					return;
				}
			}
			double money=Double.valueOf(moneyStr);
			int newType=myUtils.changeTypeToNumber(type);
			int color=myUtils.changeColorToNumber(color_dialog_rbId);
			Account a=new Account(des,money,newType,color,user.getId());
			dao.insertAccount_tab(a);
			Toast.makeText(this, "账户插入成功！", 0).show();
			AddAccountActivity.this.finish();
			
			break;
		}
		
	}
	private void openColorDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder=new Builder(this);
		colorView=LayoutInflater.from(this).inflate(R.layout.addaccount_dialog_color, null);
		builder.setView(colorView);
		builder.create();
		dialog_color = builder.show();
		colorViewSetListener();
	}
	//给ColorView设置监听
	private void colorViewSetListener() {
		// TODO Auto-generated method stub
		initColorDialogView();
		colordialogviewsetlistener();
	}
	//给ColorDialog里面的设置监听
	private void colordialogviewsetlistener() {
		// TODO Auto-generated method stub
		color_dialog_cancle_btn.setOnClickListener(new MyDialogListener());
		color_dialog_confirm_btn.setOnClickListener(new MyDialogListener());
		color_dialog_rg.setOnCheckedChangeListener(new MyCheckedListener());
	}
	//初始化ColorViewDialog里面的控价
	private void initColorDialogView() {
		color_dialog_rl=(RelativeLayout) colorView.findViewById(R.id.AddAccountActivity_dialog_color_rl);
		color_dialog_cancle_btn=(Button) colorView.findViewById(R.id.AddAccountActivity_dialog_color_cancle_btn);
		color_dialog_confirm_btn=(Button) colorView.findViewById(R.id.AddAccountActivity_dialog_color_confirm_btn);
		color_dialog_rg=(RadioGroup) colorView.findViewById(R.id.AddAccountActivity_dialog_color_rg);
	}
	private void openTypeDialog() {
		AlertDialog.Builder builder=new Builder(this);
		typeView=LayoutInflater.from(this).inflate(R.layout.addaccount_dialog_type, null);
		builder.setView(typeView);
		builder.create();
		dialog_type = builder.show();
		typeViewSetListener();
	}
	//给typeView里面设置监听
	private void typeViewSetListener() {
		// TODO Auto-generated method stub
		initTypeDialogView();
		typedialogviewsetListener();
	}
	private void typedialogviewsetListener() {
		// TODO Auto-generated method stub
		type_dialog_cancle_btn.setOnClickListener(new MyDialogListener());
		type_dialog_rl1.setOnClickListener(new MyDialogListener());
		type_dialog_rl2.setOnClickListener(new MyDialogListener());
		type_dialog_rl3.setOnClickListener(new MyDialogListener());
		type_dialog_rl4.setOnClickListener(new MyDialogListener());
		type_dialog_rl5.setOnClickListener(new MyDialogListener());
		type_dialog_rl6.setOnClickListener(new MyDialogListener());
	}
	//初始化typedialog里面的view
	public void initTypeDialogView(){
		type_dialog_cancle_btn=(Button) typeView.findViewById(R.id.AddAccount_type_dialog_cancle_btn);
		type_dialog_rl1=(RelativeLayout) typeView.findViewById(R.id.account_type_dialog_xianjing_rl);
		type_dialog_rl2=(RelativeLayout) typeView.findViewById(R.id.account_type_dialog_chuxuka_rl);
		type_dialog_rl3=(RelativeLayout) typeView.findViewById(R.id.account_type_dialog_xingyongka_rl);
		type_dialog_rl4=(RelativeLayout) typeView.findViewById(R.id.account_type_dialog_gouwuka_rl);
		type_dialog_rl5=(RelativeLayout) typeView.findViewById(R.id.account_type_dialog_zhifubao_rl);
		type_dialog_rl6=(RelativeLayout) typeView.findViewById(R.id.account_type_dialog_yingshouqiankuan_rl);
		type_dialog_iv1=(ImageView) typeView.findViewById(R.id.account_type_dialog_xianjing_iv);
		type_dialog_iv2=(ImageView) typeView.findViewById(R.id.account_type_dialog_chuxuka_iv);
		type_dialog_iv3=(ImageView) typeView.findViewById(R.id.account_type_dialog_xingyongka_iv);
		type_dialog_iv4=(ImageView) typeView.findViewById(R.id.account_type_dialog_gouwuka_iv);
		type_dialog_iv5=(ImageView) typeView.findViewById(R.id.account_type_dialog_zhifubao_iv);
		type_dialog_iv6=(ImageView) typeView.findViewById(R.id.account_type_dialog_yingshouqiankuan_iv);
	}
	//设置colorDialog里面的RadioButton的选择监听
	class MyCheckedListener implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			//获取选择的颜色：
			color_dialog_rbId= group.getCheckedRadioButtonId();
			switch (color_dialog_rbId) {
			case R.id.AddAccountActivity_dialog_color_rb1:
				color_dialog_rl.setBackgroundResource(R.color.my_color_red);
				break;
			case R.id.AddAccountActivity_dialog_color_rb2:
				color_dialog_rl.setBackgroundResource(R.color.my_color_blue);
				break;
			case R.id.AddAccountActivity_dialog_color_rb3:
				color_dialog_rl.setBackgroundResource(R.color.my_color_brown);
				break;
			case R.id.AddAccountActivity_dialog_color_rb4:
				color_dialog_rl.setBackgroundResource(R.color.my_color_green);
				break;
			case R.id.AddAccountActivity_dialog_color_rb5:
				color_dialog_rl.setBackgroundResource(R.color.my_color_yale);
				break;
			case R.id.AddAccountActivity_dialog_color_rb6:
				color_dialog_rl.setBackgroundResource(R.color.my_color_yellow);
				break;
				
			}
		}
		
	}
	//设置dialog里面的点击监听
	class MyDialogListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.AddAccount_type_dialog_cancle_btn:
				//关闭当前TypeDialog
				dialog_type.dismiss();
				break;
			case R.id.account_type_dialog_xianjing_rl:
				type_dialog_iv1.setImageResource(R.drawable.ic_ok);
				myHandler.sendEmptyMessage(0x011);
				dialog_type.dismiss();
				break;
			case R.id.account_type_dialog_chuxuka_rl:
				type_dialog_iv2.setImageResource(R.drawable.ic_ok);
				myHandler.sendEmptyMessage(0x012);
				dialog_type.dismiss();
				break;
			case R.id.account_type_dialog_xingyongka_rl:
				type_dialog_iv3.setImageResource(R.drawable.ic_ok);
				myHandler.sendEmptyMessage(0x013);
				dialog_type.dismiss();
				break;
			case R.id.account_type_dialog_gouwuka_rl:
				type_dialog_iv4.setImageResource(R.drawable.ic_ok);
				myHandler.sendEmptyMessage(0x014);
				dialog_type.dismiss();
				break;
			case R.id.account_type_dialog_zhifubao_rl:
				type_dialog_iv5.setImageResource(R.drawable.ic_ok);
				myHandler.sendEmptyMessage(0x015);
				dialog_type.dismiss();
				break;
			case R.id.account_type_dialog_yingshouqiankuan_rl:
				type_dialog_iv6.setImageResource(R.drawable.ic_ok);
				myHandler.sendEmptyMessage(0x016);
				dialog_type.dismiss();
				break;
			case R.id.AddAccountActivity_dialog_color_cancle_btn:
				//关闭colorDialog
				dialog_color.dismiss();
				break;
			case R.id.AddAccountActivity_dialog_color_confirm_btn:
				//通知Activity改变颜色
				Message m = Message.obtain();
				m.what=0x101;
				//将选择的Rb颜色值的Id传出去
				m.arg1=color_dialog_rbId;
				myHandler.sendMessage(m);
				dialog_color.dismiss();
				break;
			}
		}
		
	}
}
