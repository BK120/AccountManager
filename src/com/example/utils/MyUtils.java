package com.example.utils;
/**
 * �����ࣺ
 * 1����ȡ��ǰ��¼ϵͳ���û�
 * 2������ɫֵ�����˻�����ת����Ϊ�ܴ������ݿ��intֵ
 * 3����ȡ��ǰ���ڣ�������
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
	 * ���ҵ�ǰ��¼ϵͳ���û�
	 * @return
	 */
	public static User findOnLineUser(){
		//�ж��ĸ��û����ڵ�¼״̬
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
	 * ����ɫֵת���Numberֵ��0,1,2,3,4,5
	 * @param color
	 * @return
	 */
	public static int changeColorToNumber(int color){
		//����Ĭ��ɫ
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
	 * �������ַ���ת��ΪNumberֵ��0,1,2,3,4,5
	 */
	public static int changeTypeToNumber(String type){
		int i=0;
		if(type.equals("�ֽ�")){
			i=0;
		}
		if(type.equals("���")){
			i=1;
		}
		if(type.equals("���ÿ�")){
			i=2;
		}
		if(type.equals("���￨")){
			i=3;
		}
		if(type.equals("֧����")){
			i=4;
		}
		if(type.equals("Ӧ��Ǯ��")){
			i=5;
		}
		return i;
	}
	/**
	 * ��ȡ��ǰ����������
	 * @return
	 */
	public int findDayOfMonth(){
		int day=0;
		Calendar c=Calendar.getInstance();
		day=c.get(Calendar.DAY_OF_MONTH);
		return day;
	}
	/**
	 * ��ȡ��ǰ�����
	 * @return
	 */
	public int findYear(){
		int year=0;
		Calendar c=Calendar.getInstance();
		year=c.get(Calendar.YEAR);
		return year;
	}
	/**
	 * ��ȡ��ǰ���·�
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
