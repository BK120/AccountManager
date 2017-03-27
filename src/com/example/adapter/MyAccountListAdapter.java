package com.example.adapter;
/**
 * 给选择账户用户消费记录的Activity里面的ListView添加Adapter
 */
import java.util.List;

import com.example.bean.Account;
import com.example.moneymanager.R;
import com.example.utils.SharePreferenceUtils;

import android.content.Context;
import android.opengl.Visibility;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAccountListAdapter extends BaseAdapter {
	private Context context;
	private List<Account> list;
	private Handler myHandler;
	private SharePreferenceUtils spu;
	//每次Adapter的宿主开启时默认从-1开始，即不选择，载气宿主的OnPalus方法中重新设为-1
	private int account_listview_index=-1;
	public  MyAccountListAdapter(Context c,List<Account> list,Handler myHandler) {
		this.context=c;
		this.list=list;
		this.myHandler=myHandler;
		spu=new SharePreferenceUtils(c);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView=View.inflate(context, R.layout.accountlist_item, null);
			viewHolder.iv_type=(ImageView) convertView.findViewById(R.id.accountlist_item_type_iv);
			viewHolder.iv_okay=(ImageView) convertView.findViewById(R.id.accountlist_item_okay_iv);
			viewHolder.tv_type=(TextView) convertView.findViewById(R.id.accountlist_item_type_tv);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		Account a = list.get(position);
		int type = a.getType();
		switch (type) {
		case 0:
			viewHolder.iv_type.setImageResource(R.drawable.ft_cash1);
			break;
		case 1:
			viewHolder.iv_type.setImageResource(R.drawable.ft_chuxuka1);
			break;
		case 2:
			viewHolder.iv_type.setImageResource(R.drawable.ft_creditcard1);
			break;
		case 3:
			viewHolder.iv_type.setImageResource(R.drawable.ft_shiwuka1);
			break;
		case 4:
			viewHolder.iv_type.setImageResource(R.drawable.ft_wangluochongzhi1);
			break;
		case 5:
			viewHolder.iv_type.setImageResource(R.drawable.ft_yingshouqian1);
			break;
		}
		viewHolder.tv_type.setText(a.getDes());
		account_listview_index=spu.getInt("account_listview_index");
		if(account_listview_index==position){
			viewHolder.iv_okay.setImageResource(R.drawable.ic_ok);
			viewHolder.iv_okay.setVisibility(View.VISIBLE);
		}else{
			viewHolder.iv_okay.setVisibility(View.INVISIBLE);
		}
		//设置监听，看那一项被选中
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Message m=Message.obtain();
				m.what=0x123;
				m.arg1=position;
				myHandler.sendMessage(m);
				spu.putInt("account_listview_index", position);
			}
		});
		return convertView;
	}
 static class ViewHolder{
	 private ImageView iv_type,iv_okay;
	 private TextView tv_type;
 }
}
