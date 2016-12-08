package com.Ace.net;

import java.util.HashMap;

import com.Ace.zuoye.ZuoyeApplication;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.GsonObj;
import com.android.volley.toolbox.GsonPostParamsRequest;

public class Transfer {
	// 网络请求上传参数和接收下传的数据 在这个函数里进行操作
	public static <T> void doTransfer(HashMap<String, String> params, Class<T> clazz, String url, GsonObj obj,
			Listener<T> listener, ErrorListener errorListener) {
		RequestQueue queue = ZuoyeApplication.getQueue();
		GsonPostParamsRequest<T> request = new GsonPostParamsRequest<T>(Method.POST, url, obj, listener, errorListener,
				clazz, params);
		queue.add(request);
		queue.start();
	}
}
