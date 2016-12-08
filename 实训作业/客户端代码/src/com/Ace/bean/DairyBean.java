package com.Ace.bean;

import java.io.Serializable;

public class DairyBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 日期
	private String date;
	// 内容
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
