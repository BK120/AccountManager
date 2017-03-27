package com.example.fragment;
/**
 * ��ҳ��֮������Fragment
 * ����չʾ�����˻���Ϣ�����޸��˻�
 */
import java.util.List;

import com.example.activity.AddAccountActivity;
import com.example.adapter.MyListViewAdapter;
import com.example.bean.Account;
import com.example.bean.User;
import com.example.dao.MySQLiteDao;
import com.example.moneymanager.R;
import com.example.utils.MyUtils;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FoundFragment extends Fragment implements OnClickListener{
	private View foundView;
	private TextView jieyumoney_tv;
	private LinearLayout tianjia_ll;
	private ListView showAccount_listview;
	private User user;//�������ʱ��������User��¼����
	private MySQLiteDao dao;
	private MyUtils myUtils;
	//myHandler����ǰ��Fragment��ˢ�¹���
	//ִ��OnResum����ķ����ͺ�
	private Handler myHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==0x301){
				user=myUtils.findOnLineUser();
				onResume();
			}
		};
	};
	public FoundFragment(User user) {
		// TODO Auto-generated constructor stub
		this.user=user;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		foundView=inflater.inflate(R.layout.activity_found, container,false);
		initView();
		dao=new MySQLiteDao(getContext());
		myUtils=new MyUtils(getContext());
		return foundView;
	}
	private void initView() {
		// TODO Auto-generated method stub
		jieyumoney_tv=(TextView) foundView.findViewById(R.id.found_jieyu_money_tv);
		tianjia_ll=(LinearLayout) foundView.findViewById(R.id.found_tianjiazhanghu_ll);
		showAccount_listview=(ListView) foundView.findViewById(R.id.found_listview);
	}
	public void setListener(){
		tianjia_ll.setOnClickListener(this);
		if(user.getState()==User.ON_LINE){
			List<Account> list = dao.selectAllAccount_tab(user);
			showAccount_listview.setAdapter(new MyListViewAdapter(getContext(),list,myHandler));
			//����ǰ�û����˻����н��չʾ
			double sumMoney=0.00;
			for(Account a:list){
				sumMoney+=a.getMoney();
			}
			jieyumoney_tv.setText(sumMoney+"");
		}else{
			//û�е�¼��ListView����
			showAccount_listview.setVisibility(View.GONE);
		}
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		user=myUtils.findOnLineUser();
		setListener();
		System.out.println("foundFragment_onresume");
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.found_tianjiazhanghu_ll:
			if(user.getState()==User.OFF_LINE){
				Toast.makeText(getContext(), "���ȵ�¼�û���", 0).show();
				return;
			}else{
				//�����û������˻�
				Toast.makeText(getContext(), "�����˻��У�", 0).show();
				Intent i=new Intent(getContext(),AddAccountActivity.class);
				startActivity(i);
			}
			break;
		}
	}
}
