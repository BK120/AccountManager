package com.example.bean;
/**
 * 账户类
 * @author Administrator
 *
 */
public class Account {
	private int id;
	private String des;
	private double money;
	/**
	 * 资金类型：默认为现金
	 * 有现金，存储卡，信用卡，购物卡，支付宝，应急钱款，六项
	 */
	private int type;
	/**
	 * 对选项卡的颜色：有6种
	 * 0,1,2,3,4,5,
	 * 默认0
	 */
	private int color;
	//与之关联的用户ID
	private int user_id;
	
	public Account() {
		super();
	}
	
	public Account(String des, double money, int type, int color, int user_id) {
		super();
		this.des = des;
		this.money = money;
		this.type = type;
		this.color = color;
		this.user_id = user_id;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	@Override
	public String toString() {
		return "Account [id=" + id + ", desc=" + des + ", money=" + money + ", type=" + type + ", color=" + color
				+ ", user_id=" + user_id + "]";
	}
	
}
