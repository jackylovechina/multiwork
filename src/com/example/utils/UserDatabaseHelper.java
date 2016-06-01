package com.example.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class UserDatabaseHelper extends SQLiteOpenHelper {
	public static final String CREATE_USER = "create table user(" + "id Long," + "userinfo text," + "password int)";
	public static final String CREATE_TYPE = "create table usertype(" + "id Long," + "type text)";
	private Context context;

	public UserDatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_USER);
		db.execSQL(CREATE_TYPE);
		Toast.makeText(context, "初次登陆，初始化成功！", Toast.LENGTH_LONG).show();

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
