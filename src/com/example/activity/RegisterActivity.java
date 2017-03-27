package com.example.activity;
/**
 * ע����棺ջ������
 */
import com.example.bean.User;
import com.example.dao.MySQLiteDao;
import com.example.moneymanager.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener{
	private ImageView register_back_iv;
	private EditText register_username_et,register_password_et,
	register_confirmpwd_et;
	private Button register_btn;
	private MySQLiteDao dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		initView();
		dao=new MySQLiteDao(this);
	}

	private void initView() {
		// TODO Auto-generated method stub
		register_back_iv=(ImageView) this.findViewById(R.id.RegisterActivity_back);
		register_username_et=(EditText) this.findViewById(R.id.RegisterActivity_username_et);
		register_password_et=(EditText) this.findViewById(R.id.RegisterActivity_password);
		register_confirmpwd_et=(EditText) this.findViewById(R.id.RegisterActivity_password_confirm);
		register_btn=(Button) this.findViewById(R.id.RegisterActivity_register_btn);
		//���ü���
		register_back_iv.setOnClickListener(this);
		register_btn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.RegisterActivity_back:
			//���ص�¼���棬���ٵ�ǰ����
			Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
			startActivity(i);
			this.finish();
			break;
		case R.id.RegisterActivity_register_btn:
			//���������ж�
			String username=register_username_et.getText().toString().trim();
			String password=register_password_et.getText().toString().trim();
			String password_confirm=register_confirmpwd_et.getText().toString().trim();
			if(TextUtils.isEmpty(username)){
				Toast.makeText(RegisterActivity.this, "�û�������Ϊ�գ�", 0).show();
				return;
			}
			if(TextUtils.isEmpty(password)){
				Toast.makeText(RegisterActivity.this, "���벻��Ϊ�գ�", 0).show();
				return;
			}
			if(TextUtils.isEmpty(password_confirm)){
				Toast.makeText(RegisterActivity.this, "����ȷ������Ŷ��", 0).show();
				return;
			}
			if(!password.equals(password_confirm)){
				Toast.makeText(RegisterActivity.this, "�������벻��ͬ�����������룡", 0).show();
				register_confirmpwd_et.setText("");
				return;
			}
			//�Ȳ�ѯ�û����Ƿ����
			User user = dao.select_userInfo(username);
			if(user!=null){
				Toast.makeText(RegisterActivity.this, "�û��Ѿ����ڣ�����������һ���û�������", 0).show();
				register_username_et.setText("");
				register_password_et.setText("");
				register_confirmpwd_et.setText("");
				return;
			}
	        //ע��ɹ�
			User u=new User();
			u.setName(username);
			u.setPassword(password);
			dao.insert_userInfo(u);
			Intent in=new Intent(RegisterActivity.this,LoginActivity.class);
			in.putExtra("username", username);
			in.putExtra("password", password);
			startActivity(in);
			Toast.makeText(RegisterActivity.this, "ע��ɹ���", 0).show();
			this.finish();
			break;

		}
	}
}
