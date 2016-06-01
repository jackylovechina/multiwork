package com.example.multiwork;

import com.example.multiwork.R;
import com.example.task.UserQueryTask;
import com.example.utils.UserDatabaseHelper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class UserLoginActivity extends Activity {
	private EditText et_userinfo;
	private EditText et_password;

	private CheckBox ck_remember;
	private CheckBox ck_autologin;

	private SharedPreferences sharepref;
	private String userNameValue, passwordValue;

	private UserDatabaseHelper userdbhelper;
	public static UserLoginActivity instance = null;

	Long id = (long) 0;
	String userinfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userlogin);
		instance = this;
		et_userinfo = (EditText) findViewById(R.id.edittext_userinfo_login);
		et_password = (EditText) findViewById(R.id.edittext_password_login);

		ck_remember = (CheckBox) findViewById(R.id.checkbox_remember_login);
		ck_autologin = (CheckBox) findViewById(R.id.checkbox_auto_login);

		sharepref = getSharedPreferences("sp_user", 0);
		String name = sharepref.getString("USERINFO", "");
		String pass = sharepref.getString("PASSWORD", "");

		boolean choseRemember = sharepref.getBoolean("remember", false);
		boolean choseAutoLogin = sharepref.getBoolean("autologin", false);
		// ����ϴ�ѡ�˼�ס���룬�ǽ����¼ҳ��Ҳ�Զ���ѡ��ס���룬�������û���������
		if (choseRemember) {
			et_userinfo.setText(name);
			et_password.setText(pass);
			ck_remember.setChecked(true);
		}
		// ����ϴε�¼ѡ���Զ���¼���ǽ����¼ҳ��Ҳ�Զ���ѡ�Զ���¼
		if (choseAutoLogin) {

			ck_autologin.setChecked(true);
		}

		userdbhelper = new UserDatabaseHelper(this, "UserData.db", null, 1);
		SQLiteDatabase db = userdbhelper.getReadableDatabase();
		if (CheckColumnExist(db, "user", "id")) {
			db.execSQL("delete from user");
			LoadUser();
		} else {

			LoadUser();
		}
	}

	@Override
	protected void onRestart() {
		SQLiteDatabase db = userdbhelper.getReadableDatabase();
		if (CheckColumnExist(db, "user", "id")) {
			db.execSQL("delete from user");
			LoadUser();
		} else {

			LoadUser();
		}
		// TODO Auto-generated method stub
		super.onRestart();
	}

	public void onClickLogin(View v) {
		userNameValue = et_userinfo.getText().toString();
		passwordValue = et_password.getText().toString();
		SharedPreferences.Editor editor = sharepref.edit();

		userinfo = userNameValue;

		int password = 0;
		SQLiteDatabase db = userdbhelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from user where userinfo = ?", new String[] { userinfo });
		if (cursor.moveToFirst()) {
			do {
				id = cursor.getLong(cursor.getColumnIndex("id"));
				password = cursor.getInt(cursor.getColumnIndex("password"));
			} while (cursor.moveToNext());
		}
		cursor.close();
		if (et_password.getTextSize() == 0) {
			Toast.makeText(UserLoginActivity.this, "���벻��Ϊ�գ���", 0).show();
		} else {
			String etpassword = passwordValue;

			if (etpassword.equals(password + "")) {
				Intent intent = new Intent();
				intent.putExtra("id", id);
				intent.putExtra("userinfo", userinfo);

				intent.setClass(UserLoginActivity.this, MainActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(UserLoginActivity.this, "������󣡣�", 0).show();
			}
		}
		editor.putString("USERINFO", userNameValue);
		editor.putString("PASSWORD", passwordValue);

		// �Ƿ��ס����
		if (ck_remember.isChecked()) {
			editor.putBoolean("remember", true);
		} else {
			editor.putBoolean("remember", false);
		}

		// �Ƿ��Զ���¼
		if (ck_autologin.isChecked()) {
			editor.putBoolean("autologin", true);
		} else {
			editor.putBoolean("autologin", false);
		}
		editor.commit();

	}

	public void onClickRegister(View v) {
		Intent intent = new Intent();
		intent.setClass(UserLoginActivity.this, UserRegisterActivity.class);
		startActivity(intent);
	}

	public void LoadUser() {
		new UserQueryTask(userdbhelper).execute();
	}

	private boolean CheckColumnExist(SQLiteDatabase db, String tableName, String columnName) {
		boolean result = false;
		Cursor cursor = null;
		try {
			// ��ѯһ��
			cursor = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 0", null);
			result = cursor != null && cursor.getColumnIndex(columnName) != -1;
		} catch (Exception e) {
			// Log.e(TAG,"checkColumnExists1..." + e.getMessage()) ;
		} finally {
			if (null != cursor && !cursor.isClosed()) {
				cursor.close();
			}
		}

		return result;
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}
}
