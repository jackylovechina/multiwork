package com.example.adapter;

import java.util.List;

import com.example.domain.WorkType;
import com.example.multiwork.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TypeListAdapter extends BaseAdapter {
	private List<WorkType> worktypes;
	private int listviewItem;
	LayoutInflater layoutInflater;

	public TypeListAdapter(Context context, List<WorkType> worktypes, int listviewItem) {
		this.worktypes = worktypes;
		this.listviewItem = listviewItem;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return worktypes.size();
	}

	@Override
	public Object getItem(int position) {
		return worktypes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tv_type = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(listviewItem, null);
			tv_type = (TextView) convertView.findViewById(R.id.textView_type_typelist);
			convertView.setTag(new DataWrapper(tv_type));
		} else {
			DataWrapper datawrapper = (DataWrapper) convertView.getTag();
			tv_type = datawrapper.tv_type;
		}
		WorkType worktype = worktypes.get(position);
		tv_type.setText(worktype.getType());

		return convertView;
	}

	private final class DataWrapper {
		public TextView tv_type;

		public DataWrapper(TextView tv_type) {
			this.tv_type = tv_type;
		}
	}
}
