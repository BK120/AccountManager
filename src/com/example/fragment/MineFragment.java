package com.example.fragment;
/**
 * ��ҳ��֮���ĸ�Fragment
 * ���ڵ�¼�û������޸��û�������Ϣ��Fragment
 */
import com.example.activity.LoginActivity;
import com.example.activity.UserInfoActivity;
import com.example.bean.User;
import com.example.moneymanager.R;
import com.example.utils.MyUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MineFragment extends Fragment implements OnClickListener{
	private View mineView;
	private ImageView to_login_iv;//��ת����¼�����Activity
	private TextView showusername_tv;
	private User user;
	private MyUtils myUtils;
	public MineFragment(User user) {
		// TODO Auto-generated constructor stub
		this.user=user;
		System.out.println(user.toString());
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mineView=inflater.inflate(R.layout.activity_mine, container,false);
		myUtils=new MyUtils(getContext());
		initView();
		return mineView;
	}
	private void initView() {
		// TODO Auto-generated method stub
		to_login_iv=(ImageView) mineView.findViewById(R.id.mine_pic_nor_iv);
		showusername_tv=(TextView) mineView.findViewById(R.id.mine_showusername_tv);
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		user=myUtils.findOnLineUser();
		if(user.getState()==User.ON_LINE){
			to_login_iv.setImageResource(R.drawable.login_mine_pic);
			showusername_tv.setText(user.getName());
			to_login_iv.setOnClickListener(this);
		}else{
			to_login_iv.setImageResource(R.drawable.mine_pic_nor);
			showusername_tv.setText("������¼");
			to_login_iv.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mine_pic_nor_iv:
			//ͨ��User��state������������ת�Ľ���
			if(user.getState()==User.ON_LINE){
				//�������û�����Ϣ�޸Ľ���
				Intent intent=new Intent(getActivity(),UserInfoActivity.class);
				startActivity(intent);
			}else{
				Intent intent=new Intent(getActivity(),LoginActivity.class);
				startActivity(intent);
			}
			break;
		}
	}
}
