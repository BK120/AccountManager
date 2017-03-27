package com.example.db;
/**
 * ���ݿ�����࣬���ڴ���������ݿ�
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
		 * �����û���
		 * ������
		 * ���룺
		 * ��¼״̬��0��1
		 */
		String create_userTable="create table user_info(_id Integer "
				+ "primary key autoincrement,username,password,state)";
		db.execSQL(create_userTable);
		/**
		 * �˻���
		 * des���˻���
		 * money���˻����
		 * type���˻����ͣ�6�֣��ֽ����ÿ�����������￨��֧������Ӧ��Ǯ��
		 * color����ɫ�����˻��ı߿�����
		 */
		String create_userAccount="create table account_tab(_id Integer"
				+ " primary key autoincrement,des,money,type,color,"
				+ "user_id references user_info(_id))";
		db.execSQL(create_userAccount);
		/**
		 * ����֧����¼��
		 * inorout�������뻹��֧����1��0
		 * name���������
		 * type�����ʽ�������˺ţ��˴����˺�Id����ʾ
		 * des����������
		 * date����������
		 * month�������·ݣ����ڲ�ѯ
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
