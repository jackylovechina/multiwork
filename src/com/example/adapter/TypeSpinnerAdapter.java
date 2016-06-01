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

public class TypeSpinnerAdapter extends BaseAdapter {
	private Context mContext;
	private List<WorkType> mWorkType;

	public TypeSpinnerAdapter(Context context, List<WorkType> workType) {
		this.mContext = context;
		this.mWorkType = workType;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mWorkType.size();
	}

	@Override
	public Object getItem(int position) {
		return mWorkType.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView mTextView = null;
		LayoutInflater mlayoutInflater = LayoutInflater.from(mContext);
		convertView = mlayoutInflater.inflate(R.layout.listview_spinner_type, null);

		if (convertView != null) {
			mTextView = (TextView) convertView.findViewById(R.id.spinner_textview_typespinner);
			mTextView.setText(mWorkType.get(position).getType());
		}
		return convertView;
	}

}
