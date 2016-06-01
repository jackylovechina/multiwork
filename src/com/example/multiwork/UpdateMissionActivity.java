package com.example.multiwork;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.TypeUpdateAdapter;
import com.example.domain.WorkType;
import com.example.task.MissionUpdateTask;
import com.example.utils.UserDatabaseHelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;

public class UpdateMissionActivity extends Activity {
	int position_spinner;
	int id;
	Long t_id, typeid;
	String t_type, content;
	private EditText et_content;
	private Spinner sp_update;
	UserDatabaseHelper userdbhelper;
	List<WorkType> lists = new ArrayList<WorkType>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);

		et_content = (EditText) findViewById(R.id.editText_content_update);
		sp_update = (Spinner) findViewById(R.id.spinner_type_update);
		Intent intent = getIntent();
		id = intent.getIntExtra("id", 0);
		typeid = Long.valueOf(intent.getStringExtra("typeid"));
		content = intent.getStringExtra("content");

		userdbhelper = new UserDatabaseHelper(this, "UserData.db", null, 1);
		SQLiteDatabase db = userdbhelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from usertype ", null);
		if (cursor.moveToFirst()) {
			do {
				t_id = cursor.getLong(cursor.getColumnIndex("id"));
				t_type = cursor.getString(cursor.getColumnIndex("type"));
				WorkType worktype = new WorkType(t_id, t_type);
				lists.add(worktype);
			} while (cursor.moveToNext());
		}
		cursor.close();

		for (int i = 0; i < lists.size(); i++) {

			// Log.d("look", "lists.get(i).getId():" + lists.get(i).getId());
			// Log.d("look", "typeid:" + typeid);
			if (lists.get(i).getId().equals(typeid)) {
				position_spinner = i;
				// Log.d("look", "i=" + i);
				break;
			}

		}
		sp_update.setAdapter(new TypeUpdateAdapter(UpdateMissionActivity.this, lists));
		sp_update.setSelection(position_spinner, true);
		sp_update.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				WorkType type = lists.get(position);
				typeid = type.getId();

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		et_content.setText(content);

	}

	public void onClickUpdate(View v) {
		content = et_content.getText().toString();
		new MissionUpdateTask(UpdateMissionActivity.this).execute(id, typeid, content);
	}
}
