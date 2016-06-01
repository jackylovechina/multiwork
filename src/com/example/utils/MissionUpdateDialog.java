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
		// dialog��������
		AlertDialog.Builder builder = new AlertDialog.Builder(context); // �ȵõ�������
		builder.setTitle("�鿴����"); // ���ñ���
		// builder.setMessage("�Ƿ�ȷ���˳�?"); //��������
		builder.setIcon(R.drawable.ic_launcher);// ����ͼ�꣬ͼƬid����
		// �����б���ʾ��ע���������б���ʾ�Ͳ�Ҫ����builder.setMessage()�ˣ������б������á�
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		});
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		});
		builder.setNegativeButton("�޸�", new DialogInterface.OnClickListener() { // ����ȡ����ť
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
