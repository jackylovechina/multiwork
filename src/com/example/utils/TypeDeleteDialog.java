package com.example.utils;

import com.example.multiwork.R;
import com.example.task.MissionDeleteTask;
import com.example.task.UserTypeDeleteTask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class TypeDeleteDialog {
	Context context;
	Long userid;
	Long typeid;
	String type;

	public TypeDeleteDialog(final Context context, final Long userid, final Long typeid, String type) {
		super();
		this.context = context;
		this.typeid = typeid;
		this.userid = userid;
		this.type = type;

		final String items[] = { "�������ƣ�" + type, "ɾ���ù���������Ϣ,�Ὣ���ڸ����͵����й�����¼ɾ��,�����ѡ�񣡣�" };
		// dialog��������
		AlertDialog.Builder builder = new AlertDialog.Builder(context); // �ȵõ�������
		builder.setTitle("���ѣ���"); // ���ñ���
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
				new MissionDeleteTask(context).execute(userid, typeid);
				new UserTypeDeleteTask(context).execute(userid, typeid);
				dialog.dismiss();

			}
		});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() { // ����ȡ����ť
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		});
		builder.create().show();
	}

}
