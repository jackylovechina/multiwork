package com.example.multiwork;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.adapter.TypeSpinnerAdapter;
import com.example.domain.WorkType;
import com.example.task.MissionInsertTask;
import com.example.task.ExistTypeQueryTask;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class InsertMissionActivity extends Activity {
	private TextView tv_user, tv_date, tv_time, tv_content;
	private Spinner sp_usertype;

	Long userid;
	Long typeid;
	String userinfo;

	List<WorkType> usertype;
	Calendar calendar = Calendar.getInstance(Locale.CHINA);
	SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sTimeFormat = new SimpleDateFormat("HH:mm:ss");

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {

			sp_usertype.setAdapter(new TypeSpinnerAdapter(InsertMissionActivity.this, (List<WorkType>) msg.obj));
		};

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insertmission);
		Log.d("look", "InsertMissionActivity");
		// ����mission�����user��Ϣ
		tv_user = (TextView) findViewById(R.id.textView_user_insertmission);
		Intent intent = getIntent();
		userid = intent.getLongExtra("userid", 0);
		userinfo = intent.getStringExtra("userinfo");

		tv_user.setText(userinfo + ",���ã���");
		//
		tv_content = (TextView) findViewById(R.id.textView_content_insertmission);
		// ����mission�����usertype��Ϣ
		sp_usertype = (Spinner) findViewById(R.id.spinner_usertype_insertmission);
		sp_usertype.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				WorkType type = usertype.get(position);
				typeid = type.getId();

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});
		// ����mission�����ʱ����Ϣ
		tv_date = (TextView) findViewById(R.id.textView_date_insertmission);
		tv_time = (TextView) findViewById(R.id.textView_time_insertmission);
		String date = sDateFormat.format(new Date());
		tv_date.setText(date);
		String time = sTimeFormat.format(new Date());
		tv_time.setText(time);
		RefreshSpinner();

	}

	@Override
	protected void onResume() {
		RefreshSpinner();
		// TODO Auto-generated method stub
		super.onResume();
	}

	public void RefreshSpinner() {
		new Thread(new Runnable() {
			public void run() {

				try {
					usertype = ExistTypeQueryTask.getWorkType(userid);
					mHandler.sendMessage(mHandler.obtainMessage(1, usertype));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	// ѡ�����ڵ�listener������������
	DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			// �޸������ؼ����꣬�£���
			// �����year,monthOfYear,dayOfMonth��ֵ��DatePickerDialog�ؼ����õ�����ֵһ��
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, monthOfYear);
			calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			// ��ҳ��TextView����ʾ����Ϊ����ʱ��
			upDateDate();
		}
	};

	public void onClickDate(View v) {
		DatePickerDialog dateDlg = new DatePickerDialog(InsertMissionActivity.this, dateListener,
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

		dateDlg.show();
		// upDateDate();

	}

	private void upDateDate() {
		tv_date.setText(sDateFormat.format(calendar.getTime()));
	}

	// ѡ��ʱ���listener������������
	TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {

		// ͬDatePickerDialog�ؼ�
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
			calendar.set(Calendar.MINUTE, minute);
			upDateTime();

		}
	};

	public void onClickTime(View v) {
		TimePickerDialog timeDlg = new TimePickerDialog(InsertMissionActivity.this, timeListener,
				calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
		timeDlg.show();
	}

	private void upDateTime() {
		tv_time.setText(sTimeFormat.format(calendar.getTime()));
	}

	public void onClickInsert(View v) {
		Long userid = this.userid;
		Long typeid = this.typeid;
		Long dateANDtime = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = tv_date.getText().toString() + " " + tv_time.getText().toString();
		try {
			Date dDate = dateFormat.parse(strDate);
			dateANDtime = dDate.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String content = tv_content.getText().toString();
		if (content.length() == 0) {
			Toast.makeText(InsertMissionActivity.this, "��������Ϊ�գ����������ݣ�", 0).show();
		} else {
			new MissionInsertTask(InsertMissionActivity.this).execute(userid, typeid, dateANDtime, content);
			tv_content.setText("");
		}
	}
}
