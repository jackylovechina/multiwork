package com.example.domain;

public class Mission {
	public int id;
	public Long userid;
	public Long typeid;
	public Long time;
	public String content;

	public Mission() {
		super();
	}

	public Mission(int id, Long typeid, Long time, String content) {
		super();
		this.id = id;
		this.typeid = typeid;
		this.time = time;
		this.content = content;
	}

	public Mission(int id, Long userid, Long typeid, Long time, String content) {
		super();
		this.id = id;
		this.userid = userid;
		this.typeid = typeid;
		this.time = time;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getTypeid() {
		return typeid;
	}

	public void setTypeid(Long typeid) {
		this.typeid = typeid;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
