package com.example.utils;

import com.example.multiwork.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class TypeDeleteDialog {
	Context context;
	Long userid;
	Long typeid;
	String type;

	public TypeDeleteDialog(Context context, Long userid, Long typeid, String type) {
		super();
		this.context = context;
		this.typeid = typeid;
		this.userid = userid;
		this.type = type;

		final String items[] = { "类型名称：" + type, "删除该工作类型信息,会将属于该类型的所有工作记录删除,请谨慎选择！！" };
		// dialog参数设置
		AlertDialog.Builder builder = new AlertDialog.Builder(context); // 先得到构造器
		builder.setTitle("提醒！！"); // 设置标题
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
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { // 设置取消按钮
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		});
		builder.create().show();
	}

}
