package com.Ace.zuoye;

import java.util.HashMap;

import com.Ace.constant.Constant;
import com.Ace.net.Transfer;
import com.Ace.resp.BaseResp;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends Activity {

	private Button login, register;

	private EditText userAccount, password;

	SQLiteDatabase dbR, dbW;

	private static SharedPreferences spf;

	// accountStr 设置成静态的全局变量在多个activity中取值
	public static String accountStr, passwordStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		spf = getSharedPreferences("user", Activity.MODE_PRIVATE);

		login = (Button) findViewById(R.id.login);
		register = (Button) findViewById(R.id.register);
		userAccount = (EditText) findViewById(R.id.user_account);
		password = (EditText) findViewById(R.id.password);

		onBUttonListener listener = new onBUttonListener();

		login.setOnClickListener(listener);
		register.setOnClickListener(listener);

	}

	public class onBUttonListener implements OnClickListener {

		@Override
		public void onClick(View v) {

			accountStr = userAccount.getText().toString();
			passwordStr = password.getText().toString();

			switch (v.getId()) {
			case R.id.login:

				login();
				// Cursor cursor = dbR.query("user", new String[] { "password"
				// }, "account = ?",
				// new String[] { accountStr }, null, null, null);
				// if (cursor.getCount() > 0) {
				// cursor.moveToLast();
				// dbPassword =
				// cursor.getString(cursor.getColumnIndex("password"));
				// if (dbPassword.equals(passwordStr)) {
				// intent.setClass(MainActivity.this, HomePage.class);
				// } else {
				// flag = 0;
				// Toast.makeText(MainActivity.this, "密码错误！", 1000).show();
				// }
				// } else {
				// flag = 0;
				// Toast.makeText(MainActivity.this, "密码错误！", 1000).show();
				// }
				// cursor.close();
				break;
			case R.id.register:
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, Register.class);
				MainActivity.this.startActivity(intent);
				break;

			default:
				break;
			}
		}

	}

	public void login() {

		final Intent intent = new Intent();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("name", accountStr);
		params.put("password", passwordStr);
		Transfer.doTransfer(params, BaseResp.class, Constant.Login, null, new Response.Listener<BaseResp>() {

			@Override
			public void onResponse(BaseResp response) {
				String status = response.getStatus();
			
				if (status.equals("1")) {

					SharedPreferences.Editor edit = spf.edit();
					edit.putString("name", accountStr);
					edit.putString("password", passwordStr);
					edit.commit();
					
					intent.setClass(MainActivity.this, HomePage.class);
					MainActivity.this.startActivity(intent);
					finish();
				} else {
					Toast.makeText(MainActivity.this, "密码错误！", 1000).show();
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(MainActivity.this, "请检查网络", 1000);
			}
		});
	}
}
