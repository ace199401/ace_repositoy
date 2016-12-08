package com.Ace.zuoye;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Ace.constant.Constant;
import com.Ace.net.Transfer;
import com.Ace.resp.BaseResp;
import com.android.volley.Response;
import com.android.volley.VolleyError;

public class Register extends Activity {

	private EditText account, password;

	private TextView ok;


//	SQLiteDatabase dbR, dbW;

	String accountStr, passwordStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		account = (EditText) findViewById(R.id.account);
		password = (EditText) findViewById(R.id.password);
		ok = (TextView) findViewById(R.id.register_ok);

		ok.setOnClickListener(new onTextClickListener());

	}

	public class onTextClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {

			accountStr = account.getText().toString();
			passwordStr = password.getText().toString();

			HashMap<String, String> params = new HashMap<String, String>();
			params.put("name", accountStr);
			params.put("password", passwordStr);

			Transfer.doTransfer(params, BaseResp.class, Constant.Register, null, new Response.Listener<BaseResp>() {

				@Override
				public void onResponse(BaseResp response) {
					String status = response.getStatus();
					if (status.equals("1")) {
						Toast.makeText(Register.this, "注册成功！", 1000).show();
						finish();
					} else {
						Toast.makeText(Register.this, "用户名已存在！", 1000).show();
					}
				}
			}, new Response.ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					Toast.makeText(Register.this, "请检查网络", 1000);
				}
			});

			// ContentValues values = new ContentValues();
			// values.put("account", accountStr);
			// values.put("password", passwordStr);
			//
			// Cursor cursor;
			// cursor = dbR.query("user", new String[] { "account" },
			// "account = ?", new String[] { accountStr }, null,
			// null, null);
			// if (cursor.getCount() > 0) {
			// Toast.makeText(Register.this, "用户名已存在！", 1000).show();
			// } else {
			// dbW.insert("user", null, values);
			// Toast.makeText(Register.this, "注册成功！", 1000).show();
			// cursor.close();
			// finish();
			// }
		}
	}
}
