package com.example.multiwork;

import com.example.task.UserRegisterTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UserRegisterActivity extends Activity {
	private EditText et_userinfo_re;
	private EditText et_password_re;
	private EditText et_password_re_ag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		et_userinfo_re = (EditText) findViewById(R.id.edittext_userinfo_register);
		et_password_re = (EditText) findViewById(R.id.edittext_password_register);
		et_password_re_ag = (EditText) findViewById(R.id.edittext_password_register_ag);
	}

	public void onClickRe(View v) {
		Long id = System.currentTimeMillis();
		String userinfo = et_userinfo_re.getText().toString();
		String strpassword = et_password_re.getText().toString();
		String strpassword_ag = et_password_re_ag.getText().toString();

		if (strpassword.equals(strpassword_ag)) {
			new UserRegisterTask(UserRegisterActivity.this).execute(id + "", userinfo, strpassword);

		} else {
			Toast.makeText(UserRegisterActivity.this, "√‹¬Î≤ª∆•≈‰", 0).show();
		}

	}
}
