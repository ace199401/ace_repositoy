package com.Ace.zuoye;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.Application;

public class ZuoyeApplication extends Application {
	private static RequestQueue sQueue;

	@Override
	public void onCreate() {
		super.onCreate();
		sQueue = Volley.newRequestQueue(this);
	}

	public static RequestQueue getQueue() {
		return sQueue;
	}

}
