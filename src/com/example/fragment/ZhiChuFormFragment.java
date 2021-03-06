package com.example.fragment;
/**
 * 报表页：用于展示支出明细情况的图表
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

public class ZhiChuFormFragment extends Fragment {
	private View formZhiChuView;
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
		formZhiChuView=inflater.inflate(R.layout.form_zhichu, container, false);
		myUtils=new MyUtils(getContext());
		dao=new MySQLiteDao(getContext());
		initView();
		return formZhiChuView;
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
				tv.setText(month+"(当前月)月支出明细：");
			}else{
				tv.setText("当月没有消费记录哦！");
			}
		}else{
			tv.setText("请先登录哦！");
		}
	}
	private void initView() {
		// TODO Auto-generated method stub
		ll=(LinearLayout) formZhiChuView.findViewById(R.id.form_zhiChu_rl);
		tv=(TextView) formZhiChuView.findViewById(R.id.form_zhichu_tv_showdate);
	}
	private void initGV() {
		double[] allZhiChuMoney=new double[6];
		for(Record r:list){
			if(r.getInorout()==0){
				switch (r.getName()) {
				case 0:
					allZhiChuMoney[0]+=r.getMoney();
					break;
				case 1:
					allZhiChuMoney[1]+=r.getMoney();
					break;
				case 2:
					allZhiChuMoney[2]+=r.getMoney();
					break;
				case 3:
					allZhiChuMoney[3]+=r.getMoney();
					break;
				case 4:
					allZhiChuMoney[4]+=r.getMoney();
					break;
				case 5:
					allZhiChuMoney[5]+=r.getMoney();
					break;
				}
			}
		}
		CategorySeries dataset=buildCategoryDataset("测试饼图", allZhiChuMoney);
		int[] colors={Color.parseColor("#FE8A65"),Color.parseColor("#FFB944"),Color.parseColor("#66CC99"),
				Color.parseColor("#04B9F5"),Color.parseColor("#A0F26C"),Color.parseColor("#0F9850")};
		DefaultRenderer renderer=buildCategoryRenderer(colors);
		gv=ChartFactory.getPieChartView(getContext(), dataset, renderer);
		ll.addView(gv);
	}
	 protected CategorySeries buildCategoryDataset(String title, double[] values) {
		         CategorySeries series = new CategorySeries(title);
		          series.add("吃", values[0]);
		          series.add("穿", values[1]);
		          series.add("住", values[2]);
		          series.add("行",values[3]);
		          series.add("娱乐",values[4]);
		          series.add("生活服务",values[5]);
		          return series;
		        }
	 
	   protected DefaultRenderer buildCategoryRenderer(int[] colors) {
	           DefaultRenderer renderer = new DefaultRenderer();
	          
	           renderer.setLegendTextSize(20);//设置左下角表注的文字大小
	         //renderer.setZoomButtonsVisible(true);//设置显示放大缩小按钮  
	           renderer.setZoomEnabled(false);//设置不允许放大缩小.  
	             renderer.setChartTitleTextSize(30);//设置图表标题的文字大小
	             renderer.setChartTitle("统计结果");//设置图表的标题  默认是居中顶部显示
	             renderer.setLabelsTextSize(20);//饼图上标记文字的字体大小
	            //renderer.setLabelsColor(Color.WHITE);//饼图上标记文字的颜色
	            renderer.setPanEnabled(false);//设置是否可以平移
	            renderer.setDisplayValues(true);//是否显示值
	            renderer.setClickEnabled(true);//设置是否可以被点击
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
