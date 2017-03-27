package com.example.activity;
/**
 * ��¼���棬��Դ
 * 1��������ĵ����Ϣ��¼
 * 2����ע����淵�ع���
 * 		���ؼ�����
 * 		ע��ɹ�����
 */
import com.example.bean.User;
import com.example.dao.MySQLiteDao;
import com.example.moneymanager.R;

/**
 * ���ڵ�¼��Activity
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
	private ImageView login_back_iv;//���ذ�ť
	private EditText login_username_et,login_password_et;//�û��༭������༭��
	private Button login_btn,register_btn;//��¼��ע�ᰴť
	private MySQLiteDao dao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		//��ʼ���ؼ�
		initView();
		dao=new MySQLiteDao(this);
		//��ע�����ע��ɹ�����,����ֵ
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
		
		//��Ӽ���
		login_back_iv.setOnClickListener(this);
		register_btn.setOnClickListener(this);
		login_btn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		/**
		 * ��ȡEditView�ϵ�ֵ
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
			 * ��¼���жϼ���ȡ���ݿ�����
			 */
			if(TextUtils.isEmpty(username)){
				Toast.makeText(LoginActivity.this, "�û�������Ϊ�գ�", 0).show();
				return;
			}
			if(TextUtils.isEmpty(password)){
				Toast.makeText(LoginActivity.this, "���벻��Ϊ�գ�", 0).show();
				return;
			}
			//�������ݿ�
			User u = dao.select_userInfo(username);
			if(u==null){
				Toast.makeText(LoginActivity.this, "�û������ڣ�����ע�ᣡ", 0).show();
				login_username_et.setText("");
				login_password_et.setText("");
				return;
			}
			if(!u.getPassword().equals(password)){
				Toast.makeText(LoginActivity.this, "��������������������룡", 0).show();
				login_password_et.setText("");
				return;
			}
			//��״̬���޸�
			u.setState(User.ON_LINE);
			dao.update_UserInfo(u);
			Toast.makeText(LoginActivity.this, "��¼�ɹ���", 0).show();
			this.finish();
			break;
		}
	}
}
