package com.example.multiwork;

import java.util.List;

import com.example.adapter.MissionListAdapter;
import com.example.domain.Mission;
import com.example.task.MissionQueryTask;
import com.example.task.UserTypeQueryTask;
import com.example.utils.CheckTableExist;
import com.example.utils.MissionUpdateDialog;
import com.example.utils.UserDatabaseHelper;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class QueryMissionActivity extends Activity implements OnRefreshListener {
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			mListView.setAdapter(new MissionListAdapter(QueryMissionActivity.this, (List<Mission>) msg.obj));

			switch (msg.what) {

			case REFRESH_COMPLETE:
				mSwipeLayout.setRefreshing(false);
				Toast.makeText(QueryMissionActivity.this, "º”‘ÿÕÍ≥…", 0).show();
				break;

			}
		};
	};
	private static final int REFRESH_COMPLETE = 0X110;
	private SwipeRefreshLayout mSwipeLayout;
	private ListView mListView;
	Long userid;
	List<Mission> mData;
	UserDatabaseHelper userdbhelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_querymission);
		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swiperRefresh_querymission);
		mListView = (ListView) findViewById(R.id.listView_querymission);
		Log.d("look", "QueryMissionActivity");
		Intent intent = getIntent();
		userid = intent.getLongExtra("userid", 0);

		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Mission str = mData.get(position);
				MissionUpdateDialog dialog = new MissionUpdateDialog(QueryMissionActivity.this, userdbhelper, str.getId(),
						str.getTypeid().toString(), str.getContent());

			}

		});

		userdbhelper = new UserDatabaseHelper(this, "UserData.db", null, 1);
		SQLiteDatabase db = userdbhelper.getReadableDatabase();
		if (CheckTableExist.exec(db, "usertype", "id")) {
			db.execSQL("delete from usertype");
			new UserTypeQueryTask(userdbhelper).execute(userid);
		} else {
			new UserTypeQueryTask(userdbhelper).execute(userid);
		}

	}

	@Override
	protected void onRestart() {
		SQLiteDatabase db = userdbhelper.getReadableDatabase();
		if (CheckTableExist.exec(db, "usertype", "id")) {
			db.execSQL("delete from usertype");
			new UserTypeQueryTask(userdbhelper).execute(userid);
		} else {
			new UserTypeQueryTask(userdbhelper).execute(userid);
		}
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {

					mData = MissionQueryTask.getMission(userid);
					mHandler.sendMessage(mHandler.obtainMessage(REFRESH_COMPLETE, mData));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
