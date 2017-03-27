package com.example.bean;
/**
 * 记录消费bean类，
 * @author Administrator
 *
 */
public class Record {
	//记录Id，是收入还是支出，月份，关联的用户Id,付款方式Type，记录的事由name
	//inorout:支出0，收入1;type,操作账户的id；name：收入支出的具体事项0~5,默认5
	private int id, inorout,month,user_id,type,name;
	//收入或支出的money
	private double money;
	//对记录的具体描述des,可有可无,日期date
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
