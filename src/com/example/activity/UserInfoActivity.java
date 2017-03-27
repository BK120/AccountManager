package com.example.activity;
/**
 * 登录成功后，点击头像跳转到当前Activity
 * 修改当前登录用户的信息
 */
import java.util.List;

import com.example.bean.User;
import com.example.dao.MySQLiteDao;
import com.example.moneymanager.R;
import com.example.utils.MyUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserInfoActivity extends Activity implements OnClickListener{
	private ImageView iv_back;
	private RelativeLayout updateUsername_rl,updatePwd_rl;
	private Button quit_btn;
	private TextView showusername_tv;
	private MySQLiteDao dao;
	private MyUtils myUtils;
	private User user;
	private AlertDialog username_dialog;
	private AlertDialog password_dialog;
	//给Usernamedialog
	private EditText username_dialog_et;
	private Button username_dialog_cancle,username_dialog_confirm;
	//配置Passworddialog
	private EditText password_dialog_et,password_new_dialog_et;
	private Button password_dialog_cancle,password_dialog_confirm;
	//myHandler刷新当前状态
	private Handler myHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==0x401){
				onResume();
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_info);
		dao=new MySQLiteDao(this);
		myUtils=new MyUtils(this);
		//查找当前登录的用户
		initView();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		user=myUtils.findOnLineUser();
		initListener();
		showusername_tv.setText(user.getName());
	}
	private void initListener() {
		// TODO Auto-generated method stub
		//设置监听
		iv_back.setOnClickListener(this);
		updateUsername_rl.setOnClickListener(this);
		updatePwd_rl.setOnClickListener(this);
		quit_btn.setOnClickListener(this);
	}
	private void initView() {
		// TODO Auto-generated method stub
		iv_back=(ImageView) this.findViewById(R.id.userInfoActivity_back_iv);
		updateUsername_rl=(RelativeLayout) this.findViewById(R.id.userInfoActivity_update_username_rl);
		updatePwd_rl=(RelativeLayout) this.findViewById(R.id.userInfoActivity_update_password_rl);
		quit_btn=(Button) this.findViewById(R.id.userInFoActivity_tuichudenglu_btn);
		showusername_tv=(TextView) this.findViewById(R.id.userInfoActivity_showusername_tv);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.userInfoActivity_back_iv:
			this.finish();
			break;
		case R.id.userInfoActivity_update_username_rl:
			//弹出警告框修改信息
			getusername_dialog();
			break;
		case R.id.userInfoActivity_update_password_rl:
			//弹出警告框修改信息
			getpassword_dialog();
			break;
		case R.id.userInFoActivity_tuichudenglu_btn:
			//退出登录，修改当前用户的状态
			user.setState(User.OFF_LINE);
			dao.update_UserInfo(user);
			this.finish();
			break;
		}
	}
	//获取一个修改密码的dialog
	private void getpassword_dialog() {
		System.out.println(user.getPassword());
		AlertDialog.Builder builder=new Builder(this);
		View view=View.inflate(this, R.layout.userinfo_password_dialog, null);
		password_dialog_et=(EditText) view.findViewById(R.id.userInfoActivity_password_dialog_et);
		password_new_dialog_et=(EditText) view.findViewById(R.id.userInfoActivity_password_new_dialog_et);
		password_dialog_cancle=(Button) view.findViewById(R.id.userInfoActivit_password_dialog_cancle);
		password_dialog_confirm=(Button) view.findViewById(R.id.userInfoActivit_password_dialog_confirm);
		builder.setView(view);
		builder.create();
		password_dialog =builder.show();
		//给dialog设置监听
		password_dialog_cancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				password_dialog.dismiss();
			}
		});
		password_dialog_confirm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String pwd=password_dialog_et.getText().toString().trim();
				String newpwd=password_new_dialog_et.getText().toString().trim();
				if(TextUtils.isEmpty(pwd)||TextUtils.isEmpty(newpwd)){
					Toast.makeText(UserInfoActivity.this, "密码不能为空！", 0).show();
					return;
				}
				if(!user.getPassword().equals(pwd)){
					Toast.makeText(UserInfoActivity.this, "第一个密码错误！", 0).show();
					return;
				}
				user.setPassword(newpwd);
				dao.update_UserInfo(user);
				myHandler.sendEmptyMessage(0x401);
				Toast.makeText(UserInfoActivity.this, "修改密码成功", 0).show();
				password_dialog.dismiss();
			}
		});
	}
	//获取一个修改昵称的dialog
	public void getusername_dialog(){
		AlertDialog.Builder builder=new Builder(this);
		View view=View.inflate(this, R.layout.userinfo_username_dialog, null);
		username_dialog_et=(EditText) view.findViewById(R.id.userInfoActivity_username_dialog_et);
		username_dialog_cancle=(Button) view.findViewById(R.id.userInfoActivit_username_dialog_cancle);
		username_dialog_confirm=(Button) view.findViewById(R.id.userInfoActivit_username_dialog_confirm);
		username_dialog_et.setText(user.getName());
		builder.setView(view);
		builder.create();
		username_dialog =builder.show();
		//给dialog设置监听
		username_dialog_cancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				username_dialog.dismiss();
			}
		});
		username_dialog_confirm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name=username_dialog_et.getText().toString().trim();
				if(TextUtils.isEmpty(name)){
					Toast.makeText(UserInfoActivity.this, "用户名不能为空！", 0).show();
					return;
				}
				user.setName(name);
				dao.update_UserInfo(user);
				myHandler.sendEmptyMessage(0x401);
				Toast.makeText(UserInfoActivity.this, "修改用户名成功！", 0).show();
				username_dialog.dismiss();
			}
		});
	}
}
