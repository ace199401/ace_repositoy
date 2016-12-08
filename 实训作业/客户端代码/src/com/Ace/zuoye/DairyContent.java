package com.Ace.zuoye;

import com.Ace.bean.DairyBean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DairyContent extends Activity {

	private TextView date, content;

	DairyBean dairy = new DairyBean();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dairycontent);

		date = (TextView) findViewById(R.id.show_date);
		content = (TextView) findViewById(R.id.show_content);

		Intent intent = getIntent();
		dairy = (DairyBean) intent.getSerializableExtra("dairy");

		date.setText(dairy.getDate());
		content.setText(dairy.getContent());
	}

}
