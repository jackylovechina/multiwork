package com.example.domain;

public class User {
	public Long id;
	public String userinfo;
	public int password;

	public User(Long id, String userinfo, int password) {
		super();
		this.id = id;
		this.userinfo = userinfo;
		this.password = password;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(String userinfo) {
		this.userinfo = userinfo;
	}

	public int getPassword() {
		return password;
	}

	public void setPassword(int password) {
		this.password = password;
	}

}
