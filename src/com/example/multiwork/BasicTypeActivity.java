package com.example.multiwork;

import java.util.List;

import com.example.adapter.TypeListAdapter;
import com.example.domain.WorkType;
import com.example.utils.TypeDeleteDialog;
import com.example.task.ExistTypeQueryTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class BasicTypeActivity extends Activity {
	private ListView mlistView;

	private List<WorkType> worktypes;
	private Long userid;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {

			mlistView.setAdapter(
					new TypeListAdapter(BasicTypeActivity.this, (List<WorkType>) msg.obj, R.layout.listview_item_type));
		};

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basictype);
		mlistView = (ListView) findViewById(R.id.listView_type_basictype);

		mlistView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				WorkType worktype = worktypes.get(position);

				new TypeDeleteDialog(BasicTypeActivity.this, userid, worktype.id, worktype.type);
				// TODO Auto-generated method stub

			}
		});
		Intent intent = getIntent();
		userid = intent.getLongExtra("userid", 0);
		// RefreshList();
	}

	@Override
	protected void onResume() {
		RefreshList();
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		// RefreshList();
	}

	public void onClickAdd(View v) {

		Intent intent = new Intent();
		intent.putExtra("userid", userid);
		intent.setClass(BasicTypeActivity.this, InsertTypeActivity.class);
		startActivity(intent);
	}

	public void RefreshList() {
		new Thread(new Runnable() {
			public void run() {

				try {
					worktypes = ExistTypeQueryTask.getWorkType(userid);
					mHandler.sendMessage(mHandler.obtainMessage(1, worktypes));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
