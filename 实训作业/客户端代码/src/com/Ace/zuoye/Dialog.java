package com.Ace.zuoye;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import com.Ace.constant.Constant;
import com.Ace.net.Transfer;
import com.Ace.resp.BaseResp;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Dialog extends Activity {

	private Button ok, cancle;

	private EditText money, remark;

	SQLiteDatabase db;

	String balance = "";

	// 区别是收入还是支出
	public static int flag = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog);

		Intent intent = getIntent();
		balance = intent.getStringExtra("balance");

		ok = (Button) findViewById(R.id.ok_imcome);
		cancle = (Button) findViewById(R.id.cancel_income);
		money = (EditText) findViewById(R.id.money);
		remark = (EditText) findViewById(R.id.remark);

		onButtonClick listener = new onButtonClick();

		ok.setOnClickListener(listener);
		cancle.setOnClickListener(listener);

	}

	public class onButtonClick implements OnClickListener {
		float fBalance = 0;

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ok_imcome:

				if (!money.getText().toString().isEmpty()) {
					if (flag == 0) {
						fBalance = Float.parseFloat(balance) + Float.parseFloat(money.getText().toString());
					} else {
						fBalance = Float.parseFloat(balance) - Float.parseFloat(money.getText().toString());
					}

					// 获取系统时间
					SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
					Date curDate = new Date(System.currentTimeMillis());
					String date = format.format(curDate);
					String remarkStr = remark.getText().toString();

					HashMap<String, String> params = new HashMap<String, String>();
					params.put("balance", fBalance + "");
					params.put("detail", remarkStr);
					params.put("date", date);
					params.put("name", MainActivity.accountStr);
					if (flag == 0) {
						params.put("inorout", "收入" + money.getText().toString() + "元");
					} else {
						params.put("inorout", "支出" + money.getText().toString() + "元");
					}
					Transfer.doTransfer(params, BaseResp.class, Constant.InorOut, null,
							new Response.Listener<BaseResp>() {

								@Override
								public void onResponse(BaseResp response) {
									String status = response.getStatus();
									if (status.equals("1")) {
										Toast.makeText(Dialog.this, "成功", 1000);
										Intent intent = new Intent();
										intent.setClass(Dialog.this, TallyBook.class);
										intent.putExtra("balance", fBalance + "");
										setResult(1, intent);
										finish();
									}
								}
							}, new Response.ErrorListener() {

								@Override
								public void onErrorResponse(VolleyError error) {
									Toast.makeText(Dialog.this, "请检查网络", 1000);
								}
							});

					// ContentValues values = new ContentValues();
					// values.put("balance", fBalance + "");
					// values.put("detail", remarkStr);
					// values.put("date", date);
					// values.put("account", MainActivity.accountStr);
					// if (flag == 0) {
					// values.put("inorout", "收入" + money.getText().toString() +
					// "元");
					// } else {
					// values.put("inorout", "支出" + money.getText().toString() +
					// "元");
					// }
					// db.insert("tally", null, values);

					// Intent intent = new Intent();
					// intent.setClass(Dialog.this, TallyBook.class);
					// intent.putExtra("balance", fBalance + "");
					// setResult(1, intent);
					// finish();
				}
				break;

			case R.id.cancel_income:
				finish();
				break;

			default:
				break;
			}
		}

	}

}
