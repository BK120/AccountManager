package com.example.utils;
/**
 * 工具类：
 * 1：获取当前登录系统的用户
 * 2：将颜色值，或账户类型转化成为能传入数据库的int值
 * 3：获取当前日期：年月日
 */
import java.util.Calendar;
import java.util.List;

import com.example.bean.User;
import com.example.dao.MySQLiteDao;
import com.example.moneymanager.R;

import android.content.Context;

public class MyUtils {
	private static MySQLiteDao dao;
	private static Context context;
	public MyUtils(Context context){
		this.context=context;
		dao=new MySQLiteDao(context);
	}
	/**
	 * 查找当前登录系统的用户
	 * @return
	 */
	public static User findOnLineUser(){
		//判断哪个用户处于登录状态
			// TODO Auto-generated method stub
			List<User> userList = dao.selectAll_userInfo();
			User user=new User();
			if(userList!=null){
			for(User u:userList){
				if(u.getState()==User.ON_LINE){
					user=u;
					break;
				}
			 }
			}
			return user;
	}
	/**
	 * 将颜色值转变成Number值：0,1,2,3,4,5
	 * @param color
	 * @return
	 */
	public static int changeColorToNumber(int color){
		//设置默认色
		int i=0;
		switch (color) {
		case R.color.my_color_red:
			i=0;
			break;
		case R.color.my_color_blue:
			i=1;
			break;
		case R.color.my_color_brown:
			i=2;
			break;
		case R.color.my_color_green:
			i=3;
			break;
		case R.color.my_color_yale:
			i=4;
			break;
		case R.color.my_color_yellow:
			i=5;
			break;
		}
		return i;
	}
	/**
	 * 将类型字符串转变为Number值：0,1,2,3,4,5
	 */
	public static int changeTypeToNumber(String type){
		int i=0;
		if(type.equals("现金")){
			i=0;
		}
		if(type.equals("储蓄卡")){
			i=1;
		}
		if(type.equals("信用卡")){
			i=2;
		}
		if(type.equals("购物卡")){
			i=3;
		}
		if(type.equals("支付宝")){
			i=4;
		}
		if(type.equals("应急钱款")){
			i=5;
		}
		return i;
	}
	/**
	 * 获取当前的天数几号
	 * @return
	 */
	public int findDayOfMonth(){
		int day=0;
		Calendar c=Calendar.getInstance();
		day=c.get(Calendar.DAY_OF_MONTH);
		return day;
	}
	/**
	 * 获取当前的年份
	 * @return
	 */
	public int findYear(){
		int year=0;
		Calendar c=Calendar.getInstance();
		year=c.get(Calendar.YEAR);
		return year;
	}
	/**
	 * 获取当前的月份
	 * 0~11
	 * @return
	 */
	public int findMonthOfYear(){
		int month=1;
		Calendar c=Calendar.getInstance();
		month=c.get(Calendar.MONTH)+1;
		return month;
	}
}
