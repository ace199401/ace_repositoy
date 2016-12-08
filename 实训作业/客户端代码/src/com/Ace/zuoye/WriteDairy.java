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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WriteDairy extends Activity {

	private TextView date;

	private EditText content;

	private Button save, cancle;

	String dateStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.write_dairy);

		date = (TextView) findViewById(R.id.dairy_time);
		content = (EditText) findViewById(R.id.dairy_content);
		save = (Button) findViewById(R.id.save_dairy);
		cancle = (Button) findViewById(R.id.cansle);

		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());
		dateStr = format.format(curDate);
		date.setText(dateStr);

		onButtonClick listener = new onButtonClick();

		save.setOnClickListener(listener);
		cancle.setOnClickListener(listener);
	}

	public class onButtonClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			final Intent intent = new Intent();
			intent.setClass(WriteDairy.this, Dairy.class);
			switch (v.getId()) {
			case R.id.save_dairy:
				String contentStr = content.getText().toString();

				HashMap<String, String> params = new HashMap<String, String>();
				params.put("date", dateStr);
				params.put("content", contentStr);
				params.put("name", MainActivity.accountStr);

				System.out.println(contentStr);
				System.out.println(dateStr);

				Transfer.doTransfer(params, BaseResp.class, Constant.WriteDairy, null,
						new Response.Listener<BaseResp>() {

							@Override
							public void onResponse(BaseResp response) {
								String status = response.getStatus();
								if (status.equals("1")) {
									Toast.makeText(WriteDairy.this, "保存成功~", 1000);
									WriteDairy.this.startActivity(intent);
									finish();
								}
							}
						}, new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {
								Toast.makeText(WriteDairy.this, "请检查网络", 1000);
							}
						});

				// ContentValues values = new ContentValues();
				// values.put("date", dateStr);
				// values.put("content", contentStr);
				// values.put("account", MainActivity.accountStr);
				// db.insert("dairy", null, values);
				// Toast.makeText(WriteDairy.this, "保存成功~", 1000);
				// WriteDairy.this.startActivity(intent);
				// finish();
				break;
			case R.id.cansle:
				WriteDairy.this.startActivity(intent);
				finish();
				break;
			default:
				break;
			}
		}
	}

}
