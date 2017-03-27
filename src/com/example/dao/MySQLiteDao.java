package com.example.dao;
/**
 * �������ݿ����࣬��ɾ�Ĳ�
 */
import java.util.ArrayList;
import java.util.List;

import com.example.bean.Account;
import com.example.bean.Record;
import com.example.bean.User;
/**
 * �����ݿ�Ĳ���������dao
 */
import com.example.db.MyOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MySQLiteDao {
	private MyOpenHelper moh;
	private Context context;
	private String USER_INFO="user_info";
	private String ACCOUNT_TAB="account_tab";
	private String RECORD_TAB="record_tab";
	/**
	 * �����������SQLiteOpenHelper
	 * @param context
	 */
  public MySQLiteDao(Context context) {
	// TODO Auto-generated constructor stub
	  this.context=context;
	  moh=new MyOpenHelper(context);
  } 
  /**
   * �û�������һ������
   * @param table
   * @param u
   */
  public void insert_userInfo(User u){
	  SQLiteDatabase db = moh.getWritableDatabase();
	  ContentValues cv=new ContentValues();
	  cv.put("username", u.getName());
	  cv.put("password", u.getPassword());
	  cv.put("state", u.getState());
	  db.insert(USER_INFO, null, cv);
	  /**
	   * ���û�Ĭ�ϲ�������˻�
	   * 1:�Ȼ�ȡ�û�Id
	   */
	  User user = select_userInfo(u.getName());
	  Account a1=new Account("�ֽ�", 0.00, 0, 0, user.getId());
	  Account a2=new Account("���", 0.00, 1, 1, user.getId());
	  Account a3=new Account("���ÿ�", 0.00, 2, 2, user.getId());
	  Account a4=new Account("���￨", 0.00, 3, 3, user.getId());
	  Account a5=new Account("֧����", 0.00, 4, 4, user.getId());
	  Account a6=new Account("Ӧ��Ǯ��", 0.00, 5, 5, user.getId());
	  insertAccount_tab(a1);
	  insertAccount_tab(a2);
	  insertAccount_tab(a3);
	  insertAccount_tab(a4);
	  insertAccount_tab(a5);
	  insertAccount_tab(a6);
	  db.close();
  }
  /**
   * �û����޸�һ������
   */
  public void update_UserInfo(User u){
	  SQLiteDatabase db = moh.getWritableDatabase();
	  ContentValues cv=new ContentValues();
	  cv.put("username", u.getName());
	  cv.put("password", u.getPassword());
	  cv.put("state", u.getState());
	  String id=String.valueOf(u.getId());
	  db.update(USER_INFO, cv, "_id=?", new String[]{id});
	  db.close();
  }
  /**
   * ��ѯ�û����һ������
   */
  public User select_userInfo(String username){
	  SQLiteDatabase db = moh.getReadableDatabase();
	  Cursor cursor = db.query(USER_INFO, new String[]{"_id","password","state"}, "username=?", new String[]{username}, null, null, null);
	  User user=null;
	  if(cursor.moveToNext()){
		  user=new User();
		  int id=cursor.getInt(0);
		  String pwd=cursor.getString(1);
		  int state=cursor.getInt(2);
		  user.setId(id);
		  user.setName(username);
		  user.setPassword(pwd);
		  user.setState(state);
	  }
	  db.close();
	  return user;
  }
  /**
   * ��ѯ�û��������û�
   */
  public List<User> selectAll_userInfo(){
	  List<User> userList=new ArrayList<User>();
	  SQLiteDatabase db = moh.getWritableDatabase();
	  Cursor cursor = db.query(USER_INFO, null, null, null, null, null, null, null);
	  while(cursor.moveToNext()){
		  User u=new User();
		  u.setId(cursor.getInt(0));
		  u.setName(cursor.getString(1));
		  u.setPassword(cursor.getString(2));
		  u.setState(cursor.getInt(3));
		  userList.add(u);
	  }
	  db.close();
	  return userList;
  }
  //���ʽ��˻��Ĳ���
  public void insertAccount_tab(Account account){
	  SQLiteDatabase db = moh.getWritableDatabase();
	  ContentValues cv=new ContentValues();
	  cv.put("des", account.getDes());
	  cv.put("money", account.getMoney());
	  cv.put("type", account.getType());
	  cv.put("color", account.getColor());
	  cv.put("user_id", account.getUser_id());
	  db.insert(ACCOUNT_TAB, null, cv);
	  db.close();
  }
  /**
   * ���ҵ�ǰ�û��������ʽ��˻�
   */
  public List<Account> selectAllAccount_tab(User u){
	  List<Account> list=new ArrayList<Account>();
	  String user_id=String.valueOf(u.getId());
	  SQLiteDatabase db = moh.getWritableDatabase();
	  /*Cursor cursor = db.query(ACCOUNT_TAB, new String[]{"_id","des","money","type","color"},
			  "user_id=?", new String[]{"1"},
			  null, null, null);*/
	  Cursor cursor = db.query(ACCOUNT_TAB, null, null, null, null, null, null);
	  Log.i("Cursor", cursor.getCount()+"");
	  while(cursor.moveToNext()){
		  int id1 = cursor.getInt(5);
		  if(id1==u.getId()){
			  int id=cursor.getInt(0);
			  String desc=cursor.getString(1);
			  double money=cursor.getDouble(2);
			  int type=cursor.getInt(3);
			  int color=cursor.getInt(4);
			  Account a=new Account(desc,money,type,color,u.getId());
			  a.setId(id);
			  list.add(a);
		  }
	  }
	  db.close();
	  return list;
  }
  /**
   * �޸�һ���˻���Ϣ
   */
  public void updateAccount_tab(Account a){
	  SQLiteDatabase db = moh.getWritableDatabase();
	  String id=String.valueOf(a.getId());
	  ContentValues cv=new ContentValues();
	  cv.put("des", a.getDes());
	  cv.put("money", a.getMoney());
	  cv.put("type", a.getType());
	  cv.put("color", a.getColor());
	  cv.put("user_id", a.getUser_id());
	  db.update(ACCOUNT_TAB, cv, "_id=?", new String[]{id});
	  db.close();
  }
  /**
   * ��Record_tab���������һ������
   * @param r
   */
  public void insertRecord_tab(Record r){
	  SQLiteDatabase db = moh.getWritableDatabase();
	  ContentValues cv=new ContentValues();
	  cv.put("inorout", r.getInorout());
	  cv.put("name", r.getName());
	  cv.put("type", r.getType());
	  cv.put("money", r.getMoney());
	  cv.put("des", r.getDes());
	  cv.put("date", r.getDate());
	  cv.put("month", r.getMonth());
	  cv.put("user_id", r.getUser_id());
	  System.out.println("����һ����¼��");
	  db.insert(RECORD_TAB, null, cv);
	  db.close();
  }
  /**
   * ��ѯ���û���ָ���µ��������Ѽ�¼
   */
  public List<Record> selectAllRecord(int month,User u){
	  List<Record> list=new ArrayList<Record>();
	//  String m=String.valueOf(month);
	  SQLiteDatabase db = moh.getReadableDatabase();
	  Cursor cursor = db.query(RECORD_TAB, null, null, null, null, null, null, null);
	  while(cursor.moveToNext()){
		int id=cursor.getInt(0);  
		int inorout=cursor.getInt(1);
		int name=cursor.getInt(2);
		int type=cursor.getInt(3);
		double money=cursor.getDouble(4);
		String des=cursor.getString(5);
		String date=cursor.getString(6);
		int month1=cursor.getInt(7);
		int user_id=cursor.getInt(8);
		
		Record r=new Record(inorout,name,type,money,des,date,month1,user_id);
		r.setId(id);
		if(month1==month&&u.getId()==user_id){
			//���㵱ǰ�û�����Ϊ��ǰ�·ݣ�����
			list.add(r);
		}
	  }
	  db.close();
	  return list;
  }
  /**
   * ɾ��һ����¼
   */
  public void deleteRecord_tab(int id){
	  SQLiteDatabase db = moh.getWritableDatabase();
	  String _id=String.valueOf(id);
	  db.delete(RECORD_TAB, "_id=?", new String[]{_id});
	  db.close();
  }
}
