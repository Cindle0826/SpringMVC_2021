package com.mvc.entity;

public class ExamPay {
	private boolean  pid;
	private String pname;
	public ExamPay() {
		super();
	}
	public ExamPay(boolean pid, String pname) {
		super();
		this.pid = pid;
		this.pname = pname;
	}
	public boolean isPid() {
		return pid;
	}
	public void setPid(boolean pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}

	
}
