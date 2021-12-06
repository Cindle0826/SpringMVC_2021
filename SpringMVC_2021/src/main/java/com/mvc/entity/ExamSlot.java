package com.mvc.entity;

public class ExamSlot {
	private String sid;
	private String sname;
	public ExamSlot() {
		super();
	}
	public ExamSlot(String sid, String sname) {
		super();
		this.sid = sid;
		this.sname = sname;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}

	
	
}
