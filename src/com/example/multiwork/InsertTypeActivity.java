package com.example.multiwork;

import com.example.task.TypeInsertTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class InsertTypeActivity extends Activity {
	private EditText et_type_insert;
	private Long userid;
	private Long typeid;
	private String type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inserttype);
		et_type_insert = (EditText) findViewById(R.id.edittext_type_inserttype);
		Intent intent = getIntent();
		userid = intent.getLongExtra("userid", 0);

	}

	public void onClickInsert(View v) {
		typeid = System.currentTimeMillis();
		type = et_type_insert.getText().toString();

		new TypeInsertTask(InsertTypeActivity.this, userid).execute(typeid.toString(), type);
	}
}
