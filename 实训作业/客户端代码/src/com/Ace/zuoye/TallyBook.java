package com.Ace.zuoye;

import java.util.ArrayList;
import java.util.HashMap;

import com.Ace.bean.TallyBean;
import com.Ace.constant.Constant;
import com.Ace.net.Transfer;
import com.Ace.resp.TallyResp;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TallyBook extends Activity {

	private TextView balance;

	String balanceStr = "0";

	private Button detail, expand, income;


//	SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.tallybook);

		balance = (TextView) findViewById(R.id.balance);
		detail = (Button) findViewById(R.id.detail);
		expand = (Button) findViewById(R.id.expand);
		income = (Button) findViewById(R.id.income);

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("name", MainActivity.accountStr);

		Transfer.doTransfer(params, TallyResp.class, Constant.GetTally, null, new Response.Listener<TallyResp>() {

			@Override
			public void onResponse(TallyResp response) {

				ArrayList<TallyBean> tallyList = response.getTallyList();
				if (tallyList.size() > 0) {
					System.out.println("t=" + tallyList.size());
					System.out.println(tallyList.get(tallyList.size() - 1).getBalance());
					balanceStr = tallyList.get(tallyList.size() - 1).getBalance();
					balance.setText(balanceStr+"元");
				}

			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(TallyBook.this, "请检查网络", 1000);
			}
		});

		// dbHelper = new DatabaseHlper(TallyBook.this, "myDataBase");
		// db = dbHelper.getReadableDatabase();
		// Cursor cursor = db.query("tally", new String[] { "balance" },
		// "account = ?",
		// new String[] { MainActivity.accountStr }, null, null, null);
		// if (cursor.getCount() > 0) {
		// cursor.moveToLast();
		// balanceStr = cursor.getString(cursor.getColumnIndex("balance"));
		// }
		

		onButtonClick listener = new onButtonClick();

		expand.setOnClickListener(listener);
		income.setOnClickListener(listener);
		detail.setOnClickListener(listener);
	}

	public class onButtonClick implements OnClickListener {

		Intent intent = new Intent();

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.detail:
				intent.setClass(TallyBook.this, TallyDetail.class);
				TallyBook.this.startActivity(intent);
				break;
			case R.id.expand:
				Dialog.flag = 1;
				intent.setClass(TallyBook.this, Dialog.class);
				intent.putExtra("balance", balanceStr);
				TallyBook.this.startActivityForResult(intent, 1);
				break;
			case R.id.income:
				Dialog.flag = 0;
				intent.setClass(TallyBook.this, Dialog.class);
				intent.putExtra("balance", balanceStr);
				TallyBook.this.startActivityForResult(intent, 1);
				break;

			default:
				break;
			}

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case 1:
			balance.setText(data.getStringExtra("balance"));
			balanceStr = data.getStringExtra("balance");
			break;
		default:
			break;
		}
	}

}
