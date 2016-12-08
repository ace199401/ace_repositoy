package com.Ace.zuoye;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class HomePage extends Activity {

	private ImageButton dairy, tallyBook, finish, set;
	
	SharedPreferences spf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);

		dairy = (ImageButton) findViewById(R.id.dairy);
		tallyBook = (ImageButton) findViewById(R.id.tally_book);
		finish = (ImageButton) findViewById(R.id.finish);
		set = (ImageButton) findViewById(R.id.set);

		onTextViewClick listener = new onTextViewClick();

		dairy.setOnClickListener(listener);
		finish.setOnClickListener(listener);
		set.setOnClickListener(listener);
		tallyBook.setOnClickListener(listener);

	}

	public class onTextViewClick implements OnClickListener {
		int i = 1;

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.dairy:
				intent.setClass(HomePage.this, Dairy.class);
				break;
			case R.id.tally_book:
				intent.setClass(HomePage.this, TallyBook.class);
				break;
			case R.id.set:
				intent.setClass(HomePage.this, ChangePassword.class);
				i = 0;
				break;
			case R.id.finish:
				
				// 清空spf里面的数据
				spf = getSharedPreferences("user", Activity.MODE_PRIVATE);
				SharedPreferences.Editor edit = spf.edit();
				edit.remove("name");
				edit.remove("password");
				edit.commit();
				
				intent.setClass(HomePage.this, MainActivity.class);
				i = 0;
				break;

			default:
				break;
			}
			HomePage.this.startActivity(intent);
			if (i == 0) {
				finish();
			}
		}

	}

}
