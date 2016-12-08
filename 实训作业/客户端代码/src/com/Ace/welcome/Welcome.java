package com.Ace.welcome;

import java.util.HashMap;

import com.Ace.constant.Constant;
import com.Ace.net.Transfer;
import com.Ace.resp.BaseResp;
import com.Ace.zuoye.HomePage;
import com.Ace.zuoye.MainActivity;
import com.Ace.zuoye.R;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class Welcome extends Activity {

	SharedPreferences spf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		System.out.println("111111111111111111");
		
		// 欢迎界面持续两秒然后跳转到
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				System.out.println("22222222222");
				spf = getSharedPreferences("user", Activity.MODE_PRIVATE);
				login();
			}
		}, 1200);
	}

	public void login() {

		final Intent intent = new Intent();
		HashMap<String, String> params = new HashMap<String, String>();

		System.out.println("3333333333333333");
		
		params.put("name", spf.getString("name", ""));
		params.put("password", spf.getString("password", ""));
		Transfer.doTransfer(params, BaseResp.class, Constant.Login, null, new Response.Listener<BaseResp>() {

			@Override
			public void onResponse(BaseResp response) {
				String status = response.getStatus();
				System.out.println(status+"---------------------");
				if (status.equals("1")) {
					// 已保存密码就直接跳过登录界面
					MainActivity.accountStr=spf.getString("name", "");
					intent.setClass(Welcome.this, HomePage.class);
					Welcome.this.startActivity(intent);
					finish();
				} else {
					// 密码如果有改动就清空本地存储的密码
					SharedPreferences.Editor edit = spf.edit();
					edit.remove("name");
					edit.remove("password");
					edit.commit();
					// 跳转到登录界面
					intent.setClass(Welcome.this, MainActivity.class);
					Welcome.this.startActivity(intent);
					finish();
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(Welcome.this, "请检查网络", 1000);
			}
		});
	}
}
