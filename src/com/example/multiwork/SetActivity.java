package com.example.multiwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SetActivity extends Activity {
	Long userid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);
		Log.d("look", "SetActivity");
		Intent intent = getIntent();
		userid = intent.getLongExtra("userid", 0);
	}

	public void onClickBasic(View v) {
		Intent intent = new Intent();
		intent.putExtra("userid", userid);
		intent.setClass(SetActivity.this, BasicTypeActivity.class);
		startActivity(intent);

	}

	public void onClickOff(View v) {
		Intent logoutIntent = new Intent(SetActivity.this, UserLoginActivity.class);
		logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(logoutIntent);
		startActivity(logoutIntent);
	}

}
