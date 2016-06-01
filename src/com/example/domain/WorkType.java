package com.example.domain;

public class WorkType {
	public Long id;
	public String type;

	public WorkType(Long id, String type) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.type = type;
	}

	public WorkType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
