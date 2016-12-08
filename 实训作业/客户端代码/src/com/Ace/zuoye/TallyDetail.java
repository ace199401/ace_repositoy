package com.Ace.zuoye;

import java.util.ArrayList;
import java.util.HashMap;

import com.Ace.adapter.ListAdtDairy;
import com.Ace.bean.DairyBean;
import com.Ace.bean.TallyBean;
import com.Ace.constant.Constant;
import com.Ace.net.Transfer;
import com.Ace.resp.TallyResp;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class TallyDetail extends Activity {

	private ListView lvDetail;

	ListAdtDairy adt;

	ArrayList<DairyBean> DetailArray = new ArrayList<DairyBean>();

//	SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("name", MainActivity.accountStr);

		Transfer.doTransfer(params, TallyResp.class, Constant.GetTally, null, new Response.Listener<TallyResp>() {

			@Override
			public void onResponse(TallyResp response) {

				ArrayList<TallyBean> tallyList = response.getTallyList();
				if (tallyList.size() > 0) {
					for (int i = 0; i < tallyList.size(); i++) {
						DairyBean detail = new DairyBean();

						String mDetail = "";
						if (!tallyList.get(i).getDetail().equals("null")) {
							mDetail = tallyList.get(i).getDetail();
							System.out.println("000" + mDetail);
						}

						detail.setDate(tallyList.get(i).getDate() + "  " + tallyList.get(i).getInorout() + "  "
								+ mDetail);
						detail.setContent(tallyList.get(i).getBalance() + "元");
						DetailArray.add(detail);
					}
					lvDetail = (ListView) findViewById(R.id.tally_detail);
					adt = new ListAdtDairy(TallyDetail.this, DetailArray, 0);
					lvDetail.setAdapter(adt);
				}

			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(TallyDetail.this, "请检查网络", 1000);
			}
		});

		// dbHelper = new DatabaseHlper(TallyDetail.this, "myDataBase");
		// db = dbHelper.getReadableDatabase();
		// Cursor cursor = db.query("tally", new String[] { "date", "inorout",
		// "detail", "balance" }, "account = ?",
		// new String[] { MainActivity.accountStr }, null, null, null);
		// while (cursor.moveToNext()) {
		// DairyBean detail = new DairyBean();
		// detail.setTime(cursor.getString(cursor.getColumnIndex("date")) + "  "
		// + cursor.getString(cursor.getColumnIndex("inorout")) + "  "
		// + cursor.getString(cursor.getColumnIndex("detail")));
		// detail.setContent(cursor.getString(cursor.getColumnIndex("balance")));
		// DetailArray.add(detail);
		// }
		// lvDetail = (ListView) findViewById(R.id.tally_detail);
		// adt = new ListAdtDairy(TallyDetail.this, DetailArray);
		// lvDetail.setAdapter(adt);

	}
}
