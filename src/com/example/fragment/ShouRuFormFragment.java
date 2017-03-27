package com.example.fragment;
/**
 * ����ҳ����չʾ������ϸ�����ͼ��Fragment
 */
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import com.example.bean.Record;
import com.example.bean.User;
import com.example.dao.MySQLiteDao;
import com.example.moneymanager.R;
import com.example.utils.MyUtils;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShouRuFormFragment extends Fragment {
	private View formShouRuView;
	private GraphicalView gv;
	private LinearLayout ll;
	private TextView tv;
	private MyUtils myUtils;
	private User user;
	private MySQLiteDao dao;
	private List<Record> list;
	private int month;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		formShouRuView=inflater.inflate(R.layout.form_shouru, container, false);
		myUtils=new MyUtils(getContext());
		dao=new MySQLiteDao(getContext());
		initView();
		return formShouRuView;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		user=myUtils.findOnLineUser();
		setListener();
	}
	private void setListener() {
		// TODO Auto-generated method stub
		if(user.getState()==User.ON_LINE){
			month=myUtils.findMonthOfYear();
			list=dao.selectAllRecord(month, user);
			if(list.size()!=0){
				initGV();
				tv.setText(month+"(��ǰ��)��������ϸ��");
			}else{
				tv.setText("����û�����Ѽ�¼Ŷ��");
			}
		}else{
			tv.setText("���ȵ�¼Ŷ��");
		}
	}
	private void initView() {
		// TODO Auto-generated method stub
		ll=(LinearLayout) formShouRuView.findViewById(R.id.form_shouRu_rl);
		tv=(TextView) formShouRuView.findViewById(R.id.form_shouru_tv_showdate);
	}
	private void initGV() {
		double[] allShouRuMoney=new double[6];
		for(Record r:list){
			if(r.getInorout()==1){
				switch (r.getName()) {
				case 0:
					allShouRuMoney[0]+=r.getMoney();
					break;
				case 1:
					allShouRuMoney[1]+=r.getMoney();
					break;
				case 2:
					allShouRuMoney[2]+=r.getMoney();
					break;
				case 3:
					allShouRuMoney[3]+=r.getMoney();
					break;
				case 4:
					allShouRuMoney[4]+=r.getMoney();
					break;
				case 5:
					allShouRuMoney[5]+=r.getMoney();
					break;
					
				}
			}
		}
		CategorySeries dataset=buildCategoryDataset("���Ա�ͼ", allShouRuMoney);
		int[] colors={Color.parseColor("#FE8A65"),Color.parseColor("#FFB944"),Color.parseColor("#66CC99"),
				Color.parseColor("#04B9F5"),Color.parseColor("#A0F26C"),Color.parseColor("#0F9850")};
		DefaultRenderer renderer=buildCategoryRenderer(colors);
		gv=ChartFactory.getPieChartView(getContext(), dataset, renderer);
		ll.addView(gv);
	}
	 protected CategorySeries buildCategoryDataset(String title, double[] values) {
		         CategorySeries series = new CategorySeries(title);
		          series.add("����", values[0]);
		          series.add("����", values[1]);
		          series.add("�����", values[2]);
		          series.add("�˿�",values[3]);
		          series.add("��ְ",values[4]);
		          series.add("����",values[5]);
		          return series;
		        }
	 
	   protected DefaultRenderer buildCategoryRenderer(int[] colors) {
	           DefaultRenderer renderer = new DefaultRenderer();
	          
	           renderer.setLegendTextSize(20);//�������½Ǳ�ע�����ִ�С
	         //renderer.setZoomButtonsVisible(true);//������ʾ�Ŵ���С��ť  
	           renderer.setZoomEnabled(false);//���ò�����Ŵ���С.  
	             renderer.setChartTitleTextSize(30);//����ͼ���������ִ�С
	             renderer.setChartTitle("ͳ�ƽ��");//����ͼ��ı���  Ĭ���Ǿ��ж�����ʾ
	             renderer.setLabelsTextSize(20);//��ͼ�ϱ�����ֵ������С
	            //renderer.setLabelsColor(Color.WHITE);//��ͼ�ϱ�����ֵ���ɫ
	            renderer.setPanEnabled(false);//�����Ƿ����ƽ��
	            renderer.setDisplayValues(true);//�Ƿ���ʾֵ
	            renderer.setClickEnabled(true);//�����Ƿ���Ա����
	          renderer.setMargins(new int[] { 20, 30, 15,0 });
	          //margins - an array containing the margin size values, in this order: top, left, bottom, right
	          for (int color : colors) {
	            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
	            r.setColor(color);
	            renderer.addSeriesRenderer(r);
	          }
	          return renderer;
	        }
}
