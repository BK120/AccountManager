package com.example.activity;
/**
 * ���û������Լ����˻���Activity
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
	private User user;//�ܴ����Activity���ض�����һ�����ߵ��û�
	private ImageView iv_back,iv_type,iv_color;
	private EditText et_account_des,et_account_money;
	private TextView tv_show_type;
	private RelativeLayout  rl_account_type,rl_account_color;
	private Button save_btn;
	private View typeView;//�˻�����View
	private View colorView;//��ɫ��ͼ��View
	//typeDialog����Ŀؼ�
	private AlertDialog dialog_type ;
	private Button type_dialog_cancle_btn;//ȡ����ť
	private RelativeLayout type_dialog_rl1,type_dialog_rl2,type_dialog_rl3,type_dialog_rl4,
	type_dialog_rl5,type_dialog_rl6;//typedialog�е�ÿһ��type
	private ImageView type_dialog_iv1,type_dialog_iv2,type_dialog_iv3,type_dialog_iv4,type_dialog_iv5,
	type_dialog_iv6;//dialog�е�ÿһ��okͼ��
	//colorDialog����Ŀؼ�
	private AlertDialog dialog_color;
	private RelativeLayout color_dialog_rl;//��ʾ����
	private RadioGroup color_dialog_rg;
	private Button color_dialog_cancle_btn,color_dialog_confirm_btn;//colorDialog�е�ȷ��ȡ����ť
	//Ĭ��ѡ�񣬱����ڲ�ѡ����ɫ��ʱ��Ϊ�ձ���
	private int color_dialog_rbId=R.color.my_color_red;//��ѡ��ť��������ɫѡ���Id,Ĭ��ѡ���һ��
	//ȷ������.Ĭ��Ϊ�ֽ𣬷�ֹ��ѡ�����ͱ���
	private String type="�ֽ�";
	private MySQLiteDao dao;
	private MyUtils myUtils;
	//�����Dialog����������Ϣ
	private Handler myHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==0x011){
				tv_show_type.setText("�ֽ�");
				//����Typeֵ
				type="�ֽ�";
				iv_type.setImageResource(R.drawable.ft_cash1);
			}
			if(msg.what==0x012){
				tv_show_type.setText("���");
				type="���";
				iv_type.setImageResource(R.drawable.ft_chuxuka1);
			}
			if(msg.what==0x013){
				tv_show_type.setText("���ÿ�");
				type="���ÿ�";
				iv_type.setImageResource(R.drawable.ft_creditcard1);
			}
			if(msg.what==0x014){
				tv_show_type.setText("���￨");
				type="���￨";
				iv_type.setImageResource(R.drawable.ft_shiwuka1);
			}
			if(msg.what==0x015){
				tv_show_type.setText("֧����");
				type="֧����";
				iv_type.setImageResource(R.drawable.ft_wangluochongzhi1);
			}
			if(msg.what==0x016){
				tv_show_type.setText("Ӧ��Ǯ��");
				type="Ӧ��Ǯ��";
				iv_type.setImageResource(R.drawable.ft_yingshouqian1);
			}
			if(msg.what==0x101){
				int id=msg.arg1;
				switch (id) {
				case R.id.AddAccountActivity_dialog_color_rb1:
					//��RadioButton��Idֵת��Ϊ��ɫֵ
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
	//��ÿһ���ؼ����ü���
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
			//���˵���Activity����Ҫˢ��
			AddAccountActivity.this.finish();
			break;
		case R.id.AddAccountActivity_type_rl:
			//��һ��type��Dialog
			openTypeDialog();
			break;
		case R.id.AddAccountActivity_color_rl:
			//����ɫѡ��Dialog
			openColorDialog();
			break;
		case R.id.AddAccountActivity_save_btn:
			//���浱ǰ��Account���˳���ǰActivity
			//�Ե�ǰmoney���˻������ж�
			String des=et_account_des.getText().toString().trim();
			String moneyStr=et_account_money.getText().toString().trim();
			if(TextUtils.isEmpty(des)){
				Toast.makeText(this, "�û�������Ϊ�գ�", 0).show();
				return;
			}
			if(TextUtils.isEmpty(moneyStr)){
				Toast.makeText(this, "�˻�����Ϊ�գ�", 0).show();
				return;
			}
			//��⵱ǰ�û��Ƿ��Ѿ����ڸ��˻������˻�
			List<Account> list = dao.selectAllAccount_tab(user);
			for(Account a:list){
				if(a.getDes().equals(des)){
					Toast.makeText(this, "��ǰ�˻��Ѿ����ڣ������������˻�����", 0).show();
					et_account_des.setText("");
					return;
				}
			}
			double money=Double.valueOf(moneyStr);
			int newType=myUtils.changeTypeToNumber(type);
			int color=myUtils.changeColorToNumber(color_dialog_rbId);
			Account a=new Account(des,money,newType,color,user.getId());
			dao.insertAccount_tab(a);
			Toast.makeText(this, "�˻�����ɹ���", 0).show();
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
	//��ColorView���ü���
	private void colorViewSetListener() {
		// TODO Auto-generated method stub
		initColorDialogView();
		colordialogviewsetlistener();
	}
	//��ColorDialog��������ü���
	private void colordialogviewsetlistener() {
		// TODO Auto-generated method stub
		color_dialog_cancle_btn.setOnClickListener(new MyDialogListener());
		color_dialog_confirm_btn.setOnClickListener(new MyDialogListener());
		color_dialog_rg.setOnCheckedChangeListener(new MyCheckedListener());
	}
	//��ʼ��ColorViewDialog����Ŀؼ�
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
	//��typeView�������ü���
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
	//��ʼ��typedialog�����view
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
	//����colorDialog�����RadioButton��ѡ�����
	class MyCheckedListener implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			//��ȡѡ�����ɫ��
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
	//����dialog����ĵ������
	class MyDialogListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.AddAccount_type_dialog_cancle_btn:
				//�رյ�ǰTypeDialog
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
				//�ر�colorDialog
				dialog_color.dismiss();
				break;
			case R.id.AddAccountActivity_dialog_color_confirm_btn:
				//֪ͨActivity�ı���ɫ
				Message m = Message.obtain();
				m.what=0x101;
				//��ѡ���Rb��ɫֵ��Id����ȥ
				m.arg1=color_dialog_rbId;
				myHandler.sendMessage(m);
				dialog_color.dismiss();
				break;
			}
		}
		
	}
}
