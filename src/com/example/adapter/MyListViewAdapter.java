package com.example.adapter;
/**
 * ��ʾ�����˻��б��Listview��Adapter
 */
import java.util.List;

import com.example.bean.Account;
import com.example.dao.MySQLiteDao;
import com.example.moneymanager.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyListViewAdapter extends BaseAdapter {
	private Context context;
	private List<Account> list;
	private MySQLiteDao dao;
	private Handler myHandler;
	public MyListViewAdapter(Context context,List<Account> list,Handler handler){
		this.list=list;
		this.context=context;
		dao=new MySQLiteDao(context);
		myHandler=handler;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView=View.inflate(context, R.layout.found_listview_account_item, null);
			viewHolder.item_iv=(ImageView) convertView.findViewById(R.id.found_listview_account_item_iv);
			viewHolder.desc_tv=(TextView) convertView.findViewById(R.id.found_listview_account_item_tv);
			viewHolder.money_tv=(TextView) convertView.findViewById(R.id.found_listview_account_item_money_tv);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		//����˻���ʾ
		final Account a=list.get(position);
		viewHolder.money_tv.setText(a.getMoney()+"");
		viewHolder.desc_tv.setText(a.getDes());
		int type = a.getType();
		int color = a.getColor();
		//�Ա�����ɫ���ж�
		setColor(convertView,viewHolder,type);
		//��ͼƬ������ж�
		setImage(viewHolder,color);
		//��ÿһ��Item���ü���
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//�����Ի����޸���Ϣ
				updateAccountDialog(a);
			}
		});
		return convertView;
	}
	//�����Ի����޸��˻���Ϣ
	protected void updateAccountDialog(final Account a) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder=new Builder(context);
		View view=View.inflate(context, R.layout.found_listview_account_dialog,null);
		builder.setView(view);
		builder.create();
		final AlertDialog dialog = builder.show();
		TextView tv_showAccountDes=(TextView) view.findViewById(R.id.found_listview_account_dialog_tv);
		final EditText et_showAccountMoney=(EditText) view.findViewById(R.id.found_listview_account_dialog_et);
		Button listview_dialog_account_cancle_btn=(Button) view.findViewById(R.id.found_listview_account_dialog_cancle_btn);
		Button listview_dialog_account_confirm_btn=(Button) view.findViewById(R.id.found_listview_account_dialog_econfirm_btn);
		//��ÿ���ؼ����ü���
		tv_showAccountDes.setText(a.getDes());
		//����TextView������ɫ
		switch (a.getColor()) {
		case 0:
			tv_showAccountDes.setBackgroundResource(R.color.my_color_red);
			break;
		case 1:
			tv_showAccountDes.setBackgroundResource(R.color.my_color_blue);
			break;
		case 2:
			tv_showAccountDes.setBackgroundResource(R.color.my_color_brown);
			break;
		case 3:
			tv_showAccountDes.setBackgroundResource(R.color.my_color_green);
			break;
		case 4:
			tv_showAccountDes.setBackgroundResource(R.color.my_color_yale);
			break;
		case 5:
			tv_showAccountDes.setBackgroundResource(R.color.my_color_yellow);
			break;
		}
		//���ñ༭�������
		et_showAccountMoney.setText(a.getMoney()+"");
		//��������ť���ü���
		listview_dialog_account_cancle_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		listview_dialog_account_confirm_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String newMoney = et_showAccountMoney.getText().toString().trim();
				if(TextUtils.isEmpty(newMoney)){
					Toast.makeText(context, "�����ʽ���Ϊ�գ�", 0).show();
					return;
				}
				a.setMoney(Double.parseDouble(newMoney));
				dao.updateAccount_tab(a);
				myHandler.sendEmptyMessage(0x301);
				Toast.makeText(context, "�ʽ��޸ĳɹ���", 0).show();
				dialog.dismiss();
			}
		});
	}
	//��ÿһ��item������ɫ�ı�
	private void setColor(View convertView,ViewHolder viewHolder,int color) {
		// TODO Auto-generated method stub
		switch (color) {
		case 0:
			viewHolder.item_iv.setBackgroundResource(R.color.my_color_red);
			convertView.setBackgroundResource(R.drawable.found_listview_item_shape0);
			break;
		case 1:
			viewHolder.item_iv.setBackgroundResource(R.color.my_color_blue);
			convertView.setBackgroundResource(R.drawable.found_listview_item_shape1);
			break;
		case 2:
			viewHolder.item_iv.setBackgroundResource(R.color.my_color_brown);
			convertView.setBackgroundResource(R.drawable.found_listview_item_shape2);
			break;
		case 3:
			viewHolder.item_iv.setBackgroundResource(R.color.my_color_green);
			convertView.setBackgroundResource(R.drawable.found_listview_item_shape3);
			break;
		case 4:
			viewHolder.item_iv.setBackgroundResource(R.color.my_color_yale);
			convertView.setBackgroundResource(R.drawable.found_listview_item_shape4);
			break;
		case 5:
			viewHolder.item_iv.setBackgroundResource(R.color.my_color_yellow);
			convertView.setBackgroundResource(R.drawable.found_listview_item_shape5);
			break;
		}
	}
	//��ÿһ��item��ͼƬ�ı�
	private void setImage(ViewHolder viewHolder,int type) {
		// TODO Auto-generated method stub
		switch (type) {
		case 0:
			viewHolder.item_iv.setImageResource(R.drawable.ft_cash);;
			break;
		case 1:
			viewHolder.item_iv.setImageResource(R.drawable.ft_chuxuka1);;
			break;
		case 2:
			viewHolder.item_iv.setImageResource(R.drawable.ft_creditcard1);;
			break;
		case 3:
			viewHolder.item_iv.setImageResource(R.drawable.ft_shiwuka1);;
			break;
		case 4:
			viewHolder.item_iv.setImageResource(R.drawable.ft_wangluochongzhi1);;
			break;
		case 5:
			viewHolder.item_iv.setImageResource(R.drawable.ft_yingshouqian1);;
			break;
		}
	}
	static class  ViewHolder{
		private ImageView item_iv;
		private TextView desc_tv,money_tv;
	}
}
