package com.example.adapter;
/**
 * ��AccountFragment���������չʾ�������ListView
 * ���Adapter
 */
import java.util.List;

import com.example.bean.Record;
import com.example.dao.MySQLiteDao;
import com.example.moneymanager.R;
import com.example.utils.SharePreferenceUtils;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyRecordAdapter extends BaseAdapter {
	private Context context;
	private List<Record> list;
	private MySQLiteDao dao;
	private Handler myHandler;
	private SharePreferenceUtils spu;
	//���������item�±꣬����������Onpaluse��������������Ϊ-1������ÿ�ο�������Ĭ��ѡ����Ĳ���
	/**
	 * Ȼ��������ʵ�֣�ԭ����Ϊ��ǰ��Fragnent��ȥˢ��������Activity���еڶ���Fragement��������ݸ���
	 * �Ӷ���������onpaluse����������ÿ��record_listview_index��Ϊ-1����
	 */
	private int record_listview_index=-1;
	public  MyRecordAdapter(Context c,List<Record> list,MySQLiteDao dao,Handler myHandler) {
		this.context=c;
		this.list=list;
		this.dao=dao;
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
			convertView=View.inflate(context, R.layout.my_record_listview_item, null);
			viewHolder.iv_delete=(ImageView) convertView.findViewById(R.id.record_delete_iv);
			viewHolder.iv_show_name=(ImageView) convertView.findViewById(R.id.show_record_type_iv);
			viewHolder.iv_edit=(ImageView) convertView.findViewById(R.id.record_edit_iv);
			viewHolder.tv_zuo=(TextView) convertView.findViewById(R.id.show_record_money_or_name_tv);
			viewHolder.tv_you=(TextView) convertView.findViewById(R.id.show_record_name_or_money_tv);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		final Record r = list.get(position);
		int name = r.getName();
		final String date = r.getDate();
		int inorout = r.getInorout();
		double money = r.getMoney();
		final String des = r.getDes();
		if(inorout==0){
			//֧��
			switch (name) {
			case 0:
				viewHolder.tv_zuo.setText("��");
				viewHolder.tv_you.setText(money+"");
				viewHolder.iv_show_name.setImageResource(R.drawable.bt_food);
				break;
			case 1:
				viewHolder.tv_zuo.setText("��");
				viewHolder.tv_you.setText(money+"");
				viewHolder.iv_show_name.setImageResource(R.drawable.bt_shopping);
				break;
			case 2:
				viewHolder.tv_zuo.setText("ס");
				viewHolder.tv_you.setText(money+"");
				viewHolder.iv_show_name.setImageResource(R.drawable.bt_house);
				break;
			case 3:
				viewHolder.tv_zuo.setText("��");
				viewHolder.tv_you.setText(money+"");
				viewHolder.iv_show_name.setImageResource(R.drawable.bt_car);
				break;
			case 4:
				viewHolder.tv_zuo.setText("����");
				viewHolder.tv_you.setText(money+"");
				viewHolder.iv_show_name.setImageResource(R.drawable.bt_yule);
				break;
			case 5:
				viewHolder.tv_zuo.setText("�������");
				viewHolder.tv_you.setText(money+"");
				viewHolder.iv_show_name.setImageResource(R.drawable.bt_service);
				break;
			}
		}else{
			//����
			switch (name) {
			case 0:
				viewHolder.tv_zuo.setText(money+"");
				viewHolder.tv_you.setText("����");
				viewHolder.iv_show_name.setImageResource(R.drawable.bt_wages);
				break;
			case 1:
				viewHolder.tv_zuo.setText(money+"");
				viewHolder.tv_you.setText("����");
				viewHolder.iv_show_name.setImageResource(R.drawable.bt_bouns);
				break;
			case 2:
				viewHolder.tv_zuo.setText(money+"");
				viewHolder.tv_you.setText("�����");
				viewHolder.iv_show_name.setImageResource(R.drawable.bt_shenghuofei);
				break;
			case 3:
				viewHolder.tv_zuo.setText(money+"");
				viewHolder.tv_you.setText("�˿�");
				viewHolder.iv_show_name.setImageResource(R.drawable.bt_tuikuan);
				break;
			case 4:
				viewHolder.tv_zuo.setText(money+"");
				viewHolder.tv_you.setText("��ְ");
				viewHolder.iv_show_name.setImageResource(R.drawable.bt_jianzhi);
				break;
			case 5:
				viewHolder.tv_zuo.setText(money+"");
				viewHolder.tv_you.setText("����");
				viewHolder.iv_show_name.setImageResource(R.drawable.bt_yiwai);
				break;
			}
		}
		//ɾ����ť��Ӽ���
		record_listview_index=spu.getInt("record_listview_index");
		if(position==record_listview_index){
			viewHolder.iv_delete.setVisibility(View.VISIBLE);
			viewHolder.iv_edit.setVisibility(View.VISIBLE);
		}else{
			viewHolder.iv_delete.setVisibility(View.INVISIBLE);
			viewHolder.iv_edit.setVisibility(View.INVISIBLE);
		}
		viewHolder.iv_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//ִ��һ��ɾ���¼�����ˢ��listview
				dao.deleteRecord_tab(r.getId());
				myHandler.sendEmptyMessage(0x456);
			}
		});
		//���item��ʾdes������ɾ����ť
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//viewHolder.iv_delete.setVisibility(View.VISIBLE);
				spu.putInt("record_listview_index", position);
				myHandler.sendEmptyMessage(0x369);
				Toast.makeText(context, date+":"+des, 0).show();
			}
		});
		return convertView;
	}
	static class ViewHolder{
		private ImageView iv_delete,iv_edit,iv_show_name;
		private TextView  tv_zuo,tv_you;
	}
}
