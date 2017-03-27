package com.example.db;
/**
 * 数据库帮助类，用于创建表和数据库
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

	public MyOpenHelper(Context context) {
		super(context, "moneymanager.db", null, 1, null);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		/**
		 * 创建用户表：
		 * 姓名：
		 * 密码：
		 * 登录状态：0或1
		 */
		String create_userTable="create table user_info(_id Integer "
				+ "primary key autoincrement,username,password,state)";
		db.execSQL(create_userTable);
		/**
		 * 账户表
		 * des：账户名
		 * money：账户余额
		 * type：账户类型，6种：现金，信用卡，储蓄卡，购物卡，支付宝，应急钱款
		 * color：颜色，对账户的边框描述
		 */
		String create_userAccount="create table account_tab(_id Integer"
				+ " primary key autoincrement,des,money,type,color,"
				+ "user_id references user_info(_id))";
		db.execSQL(create_userAccount);
		/**
		 * 收入支出记录表：
		 * inorout：是收入还是支出：1或0
		 * name：消费类别
		 * type：付款方式即付款账号，此处用账号Id来表示
		 * des：消费描述
		 * date：消费日期
		 * month：消费月份，便于查询
		 */
		String create_userRecord="create table record_tab(_id Integer "
				+ "primary key autoincrement,inorout,name,type,money,des,date,"
				+ "month,user_id references user_info(_id))";
		db.execSQL(create_userRecord);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
