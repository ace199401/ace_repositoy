package com.Ace.resp;

import java.io.Serializable;

public class BaseResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 状态 1为成功 0为失败
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
