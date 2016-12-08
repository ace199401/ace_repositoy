package com.Ace.zuoye;

import java.util.ArrayList;
import java.util.HashMap;

import com.Ace.adapter.ListAdtDairy;
import com.Ace.bean.DairyBean;
import com.Ace.constant.Constant;
import com.Ace.net.Transfer;
import com.Ace.resp.DairyResp;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Dairy extends Activity {

	private ImageView back;

	private TextView writeDairy;

	private ListView dairyLv;

	private ArrayList<DairyBean> dairyArray = new ArrayList<DairyBean>();

//	SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dairy);

		writeDairy = (TextView) findViewById(R.id.write_dairy);
		dairyLv = (ListView) findViewById(R.id.lv_dairy);
		back = (ImageView) findViewById(R.id.back);

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("name", MainActivity.accountStr);

		Transfer.doTransfer(params, DairyResp.class, Constant.GetDariyList, null, new Response.Listener<DairyResp>() {

			@Override
			public void onResponse(DairyResp response) {

				ArrayList<DairyBean> dairyContentList = response.getDairyList();
				if (dairyContentList.size() > 0) {

					for (int i = 0; i < dairyContentList.size(); i++) {
						// 接收到日记列表讲日记列表存到arrayList里面
						DairyBean dairy = new DairyBean();
						dairy.setDate(dairyContentList.get(i).getDate());
						dairy.setContent(dairyContentList.get(i).getContent());
						dairyArray.add(dairy);
					}
					// 将arrayList传给adt显示列表
					ListAdtDairy adt = new ListAdtDairy(Dairy.this, dairyArray, 1);
					dairyLv.setAdapter(adt);
				}

			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(Dairy.this, "请检查网络", 1000);
			}
		});

		// dbHelper = new DatabaseHlper(Dairy.this, "myDataBase");
		// db = dbHelper.getReadableDatabase();
		//
		// Cursor cursor = db.query("dairy", new String[] { "date", "content" },
		// "account = ?",
		// new String[] { MainActivity.accountStr }, null, null, null);
		//
		// back = (ImageView) findViewById(R.id.back);
		// writeDairy = (TextView) findViewById(R.id.write_dairy);
		// dairyLv = (ListView) findViewById(R.id.lv_dairy);
		//
		// while (cursor.moveToNext()) {
		// DairyBean dairy = new DairyBean();
		// dairy.setTime(cursor.getString(cursor.getColumnIndex("date")));
		// dairy.setContent(cursor.getString(cursor.getColumnIndex("content")));
		// dairyArray.add(dairy);
		// }
		//
		// ListAdtDairy adt = new ListAdtDairy(Dairy.this, dairyArray);
		// dairyLv.setAdapter(adt);
		// dairyLv.setOnItemClickListener(new itemListener());

		onTextViewClick listener = new onTextViewClick();
		writeDairy.setOnClickListener(listener);
		back.setOnClickListener(listener);
	}

	public class onTextViewClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back:
				finish();
				break;
			case R.id.write_dairy:
				Intent intent = new Intent();
				intent.setClass(Dairy.this, WriteDairy.class);
				Dairy.this.startActivity(intent);
				finish();
				break;
			default:
				break;
			}
		}
	}
}
