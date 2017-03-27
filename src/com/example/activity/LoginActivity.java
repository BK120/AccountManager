package com.example.activity;
/**
 * 登录界面，来源
 * 1：主界面的点击信息登录
 * 2：从注册界面返回过来
 * 		返回键返回
 * 		注册成功返回
 */
import com.example.bean.User;
import com.example.dao.MySQLiteDao;
import com.example.moneymanager.R;

/**
 * 用于登录的Activity
 */
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener{
	private ImageView login_back_iv;//返回按钮
	private EditText login_username_et,login_password_et;//用户编辑框，密码编辑框
	private Button login_btn,register_btn;//登录和注册按钮
	private MySQLiteDao dao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		//初始化控件
		initView();
		dao=new MySQLiteDao(this);
		//从注册界面注册成功返回,设置值
		setDefaultUser();
	}
	private void setDefaultUser() {
		// TODO Auto-generated method stub
		Intent i=this.getIntent();
		if(i!=null){
		String username = i.getStringExtra("username");
		String pwd=i.getStringExtra("password");
		login_username_et.setText(username);
		login_password_et.setText(pwd);
		}
	}
	private void initView() {
		// TODO Auto-generated method stub
		login_back_iv=(ImageView) this.findViewById(R.id.loginActivity_back_iv);
		login_username_et = (EditText) this.findViewById(R.id.loginActivity_username_et);
		login_password_et = (EditText) this.findViewById(R.id.loginActivity_password_et);
		login_btn=(Button) this.findViewById(R.id.loginActivity_login_btn);
		register_btn=(Button) this.findViewById(R.id.loginActivity_register_btn);
		
		//添加监听
		login_back_iv.setOnClickListener(this);
		register_btn.setOnClickListener(this);
		login_btn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		/**
		 * 获取EditView上的值
		 */
		switch (v.getId()) {
		case R.id.loginActivity_back_iv:
			this.finish();
			break;
		case R.id.loginActivity_register_btn:
			Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
			startActivity(i);
			this.finish();
			break;
		case R.id.loginActivity_login_btn:
			String username=login_username_et.getText().toString().trim();
			String password=login_password_et.getText().toString().trim();
			/**
			 * 登录的判断及读取数据库资料
			 */
			if(TextUtils.isEmpty(username)){
				Toast.makeText(LoginActivity.this, "用户名不能为空！", 0).show();
				return;
			}
			if(TextUtils.isEmpty(password)){
				Toast.makeText(LoginActivity.this, "密码不能为空！", 0).show();
				return;
			}
			//查找数据库
			User u = dao.select_userInfo(username);
			if(u==null){
				Toast.makeText(LoginActivity.this, "用户不存在，请先注册！", 0).show();
				login_username_et.setText("");
				login_password_et.setText("");
				return;
			}
			if(!u.getPassword().equals(password)){
				Toast.makeText(LoginActivity.this, "密码输入错误，请重新输入！", 0).show();
				login_password_et.setText("");
				return;
			}
			//对状态的修改
			u.setState(User.ON_LINE);
			dao.update_UserInfo(u);
			Toast.makeText(LoginActivity.this, "登录成功！", 0).show();
			this.finish();
			break;
		}
	}
}
