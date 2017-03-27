package com.example.activity;
/**
 * 个人账户的账户列表的Activity
 */
import java.util.List;

import com.example.adapter.MyAccountListAdapter;
import com.example.bean.Account;
import com.example.bean.User;
import com.example.dao.MySQLiteDao;
import com.example.moneymanager.R;
import com.example.utils.MyUtils;
import com.example.utils.SharePreferenceUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Toast;
public class AccountsListActivity extends Activity {
	private ListView listView;
	private MyUtils myUtils;
	private MySQLiteDao dao;
	private User user;
	private List<Account> list;
	private MyAccountListAdapter adapter;
	private int account_id=0;
	private SharePreferenceUtils spu;
	private Handler myHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==0x123){
				//onResume();
				account_id=msg.arg1;
				Toast.makeText(AccountsListActivity.this, list.get(account_id).getDes(), 1).show();
				listView.setAdapter(adapter);
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accounts_list);
		dao=new MySQLiteDao(this);
		myUtils=new MyUtils(this);
		spu=new SharePreferenceUtils(this);
		listView=(ListView) this.findViewById(R.id.AccountListActivity_listview);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		user=myUtils.findOnLineUser();
		list=dao.selectAllAccount_tab(user);
		adapter=new MyAccountListAdapter(this, list, myHandler);
		listView.setAdapter(adapter);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		spu.putInt("account_listview_index", -1);
	}
	//关闭当前Activity
	public void cancle(View v){
		this.finish();
	}
	/**
	 * 选择账户，或关闭activity
	 */
	public void confirm(View v){
		spu.putInt("account_type", account_id);
		this.finish();
	}
}
