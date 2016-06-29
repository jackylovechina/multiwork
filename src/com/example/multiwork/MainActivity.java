package com.example.multiwork;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends TabActivity {
	private TabHost tabHost;
	private RadioGroup radioGroup;
	private RadioButton radioButton_query, radioButton_insert, radioButton_set;
//test
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		radioButton_query = (RadioButton) findViewById(R.id.radioButton_query);
		radioButton_insert = (RadioButton) findViewById(R.id.radioButton_insert);
		radioButton_set = (RadioButton) findViewById(R.id.radioButton_set);

		Intent intent = getIntent();
		Long userid = intent.getLongExtra("id", 0);
		String userinfo = intent.getStringExtra("userinfo");

		Intent queryIntent = new Intent();
		queryIntent.putExtra("userid", userid);
		queryIntent.setClass(MainActivity.this, QueryMissionActivity.class);

		Intent insertIntent = new Intent();
		insertIntent.putExtra("userid", userid);
		insertIntent.putExtra("userinfo", userinfo);
		insertIntent.setClass(MainActivity.this, InsertMissionActivity.class);

		Intent setIntent = new Intent();
		setIntent.putExtra("userid", userid);
		setIntent.setClass(MainActivity.this, SetActivity.class);

		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		tabHost.addTab(tabHost.newTabSpec("tag1").setIndicator("0").setContent(queryIntent));
		tabHost.addTab(tabHost.newTabSpec("tag2").setIndicator("1").setContent(insertIntent));
		tabHost.addTab(tabHost.newTabSpec("tag3").setIndicator("2").setContent(setIntent));

		CheckListener checkRadio = new CheckListener();
		radioGroup.setOnCheckedChangeListener(checkRadio);
		UserLoginActivity.instance.finish();
	}

	public class CheckListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.radioButton_query:
				tabHost.setCurrentTab(0);
				break;
			case R.id.radioButton_insert:
				tabHost.setCurrentTab(1);
				break;
			case R.id.radioButton_set:
				tabHost.setCurrentTab(2);
				break;

			}

		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		onDestroy();
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
}
