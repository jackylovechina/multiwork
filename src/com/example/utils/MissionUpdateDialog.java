package com.example.utils;

import com.example.multiwork.R;
import com.example.multiwork.UpdateMissionActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MissionUpdateDialog {
	private Context context;
	private int id;
	private String typeid;
	private String type;
	private String content;
	UserDatabaseHelper dbHelper;

	public MissionUpdateDialog(final Context context, UserDatabaseHelper dbHelper, final int id, final String typeid,
			final String content) {
		this.context = context;
		this.dbHelper = dbHelper;
		this.id = id;
		this.typeid = typeid;
		this.content = content;

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from usertype where id = ?", new String[] { typeid });
		if (cursor.moveToFirst()) {
			do {
				type = cursor.getString(cursor.getColumnIndex("type"));

			} while (cursor.moveToNext());
		}
		cursor.close();

		final String items[] = { type, content };
		// dialog参数设置
		AlertDialog.Builder builder = new AlertDialog.Builder(context); // 先得到构造器
		builder.setTitle("查看详情"); // 设置标题
		// builder.setMessage("是否确认退出?"); //设置内容
		builder.setIcon(R.drawable.ic_launcher);// 设置图标，图片id即可
		// 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		});
		builder.setNegativeButton("修改", new DialogInterface.OnClickListener() { // 设置取消按钮
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent update = new Intent();
				update.putExtra("id", id);
				update.putExtra("typeid", typeid);
				update.putExtra("content", content);
				update.setClass(context, UpdateMissionActivity.class);
				context.startActivity(update);
				dialog.dismiss();

			}
		});
		builder.create().show();
	}

}
