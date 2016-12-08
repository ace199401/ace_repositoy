package com.Ace.resp;

import java.io.Serializable;
import java.util.ArrayList;

import com.Ace.bean.DairyBean;

public class DairyResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 日记列表
	private ArrayList<DairyBean> dairyList = new ArrayList<DairyBean>();

	public ArrayList<DairyBean> getDairyList() {
		return dairyList;
	}

	public void setDairyList(ArrayList<DairyBean> dairyList) {
		this.dairyList = dairyList;
	}

}
