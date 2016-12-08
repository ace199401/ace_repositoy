package com.Ace.zuoye;

import java.util.HashMap;

import com.Ace.constant.Constant;
import com.Ace.net.Transfer;
import com.Ace.resp.BaseResp;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePassword extends Activity {

	private EditText oldPassword, newPassword, rPassword;

	private TextView ok;


//	SQLiteDatabase db, dbW;

	String dbPassword = "";

	SharedPreferences spf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chang_password);


		oldPassword = (EditText) findViewById(R.id.oldpassword);
		newPassword = (EditText) findViewById(R.id.newpassword);
		rPassword = (EditText) findViewById(R.id.rechack_password);
		ok = (TextView) findViewById(R.id.changepassword_ok);

		ok.setOnClickListener(new onTextViewClick());

	}

	public class onTextViewClick implements OnClickListener {
		String oldPasswordStr, newPasswordStr, rPasswordStr;

		@Override
		public void onClick(View v) {
			oldPasswordStr = oldPassword.getText().toString();
			newPasswordStr = newPassword.getText().toString();
			rPasswordStr = rPassword.getText().toString();

			//判断新老密码是否一致
			if (oldPasswordStr.equals(MainActivity.passwordStr)) {
				//判断两次输入的密码是否一致
				if (newPasswordStr.equals(rPasswordStr)) {

					HashMap<String, String> params = new HashMap<String, String>();
					params.put("name", MainActivity.accountStr);
					params.put("password", rPasswordStr);

					Transfer.doTransfer(params, BaseResp.class, Constant.ChangePassword, null,
							new Response.Listener<BaseResp>() {

								@Override
								public void onResponse(BaseResp response) {
									String status = response.getStatus();
									if (status.equals("1")) {
										Toast.makeText(ChangePassword.this, "成功！", 1000).show();

										//清空spf里面的数据
										spf = getSharedPreferences("user", Activity.MODE_PRIVATE);
										SharedPreferences.Editor edit = spf.edit();
										edit.remove("name");
										edit.remove("password");
										edit.commit();

										Intent intent = new Intent();
										intent.setClass(ChangePassword.this, MainActivity.class);
										ChangePassword.this.startActivity(intent);
										finish();
									} 
								}
							}, new Response.ErrorListener() {

								@Override
								public void onErrorResponse(VolleyError error) {
									Toast.makeText(ChangePassword.this, "请检查网络", 1000);
								}
							});

					// db.execSQL("delete from user where account='" +
					// MainActivity.accountStr + "'");
					// dbW.insert("user", null, values);

					// Toast.makeText(ChangePassword.this, "成功！", 1000).show();
					// Intent intent = new Intent();
					// intent.setClass(ChangePassword.this, MainActivity.class);
					// ChangePassword.this.startActivity(intent);
					// finish();
				} else {
					Toast.makeText(ChangePassword.this, "确认密码与新密码不同！请重新输入！", 1000).show();
				}
			} else {
				Toast.makeText(ChangePassword.this, "修改密码失败！", 1000).show();
			}
		}
	}
}
