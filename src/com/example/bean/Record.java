package com.example.bean;
/**
 * ��¼����bean�࣬
 * @author Administrator
 *
 */
public class Record {
	//��¼Id�������뻹��֧�����·ݣ��������û�Id,���ʽType����¼������name
	//inorout:֧��0������1;type,�����˻���id��name������֧���ľ�������0~5,Ĭ��5
	private int id, inorout,month,user_id,type,name;
	//�����֧����money
	private double money;
	//�Լ�¼�ľ�������des,���п���,����date
	private String des,date;
	public Record() {
		super();
	}
	public Record(int inorout, int name, int type, double money, String des, String date, int month,int user_id) {
		super();
		this.inorout = inorout;
		this.month = month;
		this.user_id = user_id;
		this.type = type;
		this.name = name;
		this.money = money;
		this.des = des;
		this.date = date;
	}
	@Override
	public String toString() {
		return "Record [id=" + id + ", inorout=" + inorout + ", month=" + month + ", user_id=" + user_id + ", type="
				+ type + ", name=" + name + ", money=" + money + ", des=" + des + ", date=" + date + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInorout() {
		return inorout;
	}
	public void setInorout(int inorout) {
		this.inorout = inorout;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getName() {
		return name;
	}
	public void setName(int name) {
		this.name = name;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
