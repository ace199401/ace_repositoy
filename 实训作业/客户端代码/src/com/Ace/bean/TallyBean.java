package com.Ace.bean;

import java.io.Serializable;

public class TallyBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 日期
	private String date;
	// 余额
	private String balance;
	// 收入支出
	private String inorout;
	// 详情
	private String detail;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getInorout() {
		return inorout;
	}

	public void setInorout(String inorout) {
		this.inorout = inorout;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
