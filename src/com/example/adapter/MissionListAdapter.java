package com.example.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.domain.Mission;
import com.example.multiwork.R;
import com.example.task.TypeQueryFromId;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MissionListAdapter extends BaseAdapter {
	private List<Mission> data;
	LayoutInflater layoutInflater;

	public MissionListAdapter(Context context, List<Mission> data) {
		this.data = data;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tv_type = null;
		TextView tv_content = null;

		TextView tv_date = null;
		if (convertView == null) {

			convertView = layoutInflater.inflate(R.layout.listview_item_mission, null);
			tv_type = (TextView) convertView.findViewById(R.id.textView_type_missionlist);
			tv_content = (TextView) convertView.findViewById(R.id.textView_content_missionlist);
			tv_date = (TextView) convertView.findViewById(R.id.textView_time_missionlist);
			convertView.setTag(new DataWrapper(tv_type, tv_content, tv_date));
		} else {
			DataWrapper dataWrapper = (DataWrapper) convertView.getTag();
			tv_type = dataWrapper.tv_type;
			tv_content = dataWrapper.tv_content;

			tv_date = dataWrapper.tv_date;

		}
		Mission mission = data.get(position);
		tv_content.setText(mission.getContent().toString());
		// 解析type类型
		Long typeid = mission.getTypeid();
		new TypeQueryFromId(tv_type).execute(typeid);
		// tv_type.setText(mission.getTypeid().toString());

		// 解析时间格式
		Date mTime = new Date(mission.getTime());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = dateFormat.format(mTime);
		tv_date.setText(dateStr);

		return convertView;
	}

	private final class DataWrapper {

		public TextView tv_type;
		public TextView tv_content;
		public TextView tv_date;

		public DataWrapper(TextView tv_type, TextView tv_content, TextView tv_date) {
			this.tv_type = tv_type;
			this.tv_content = tv_content;
			this.tv_date = tv_date;
		}
	}

}
