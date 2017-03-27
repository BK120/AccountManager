package com.example.bean;
/**
 * 用户类
 * @author Administrator
 *
 */
public class User {
	private int id;
	private String name;
	private String password;
	private int state;//描述当前用户的状态：在线or离线
	public static int OFF_LINE=0,ON_LINE=1;
	public User() {
		// TODO Auto-generated constructor stub
		//创建用户时为给用户默认离线
		this.state=OFF_LINE;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", state=" + state + ", OFF_LINE="
				+ OFF_LINE + ", ON_LINE=" + ON_LINE + "]";
	}
	
}
