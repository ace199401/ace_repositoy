package com.Ace.resp;

import java.io.Serializable;
import java.util.ArrayList;

import com.Ace.bean.TallyBean;

public class TallyResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 记账本列表
	private ArrayList<TallyBean> tallyList = new ArrayList<TallyBean>();

	public ArrayList<TallyBean> getTallyList() {
		return tallyList;
	}

	public void setTallyList(ArrayList<TallyBean> tallyList) {
		this.tallyList = tallyList;
	}

}
