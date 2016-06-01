package com.example.task;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.domain.WorkType;
import com.example.utils.UserDatabaseHelper;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class UserTypeQueryTask extends AsyncTask<Long, Integer, Long> {
	UserDatabaseHelper dbHelper;

	public UserTypeQueryTask(UserDatabaseHelper dbHelper) {
		super();
		this.dbHelper = dbHelper;
	}

	@Override
	protected Long doInBackground(Long... params) {
		Long userid = params[0];
		String strResult = null;
		Long l = null;

		String path = "http://172.16.150.118:8080/MultiWork/selectusertype.do";
		path += "?userid=" + userid;
		//Log.d("look", path);
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(path);
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				strResult = EntityUtils.toString(httpEntity, "UTF-8");
			}

			JSONArray jsonArray = new JSONArray(strResult);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);

				l = getUserTypeFromJson(dbHelper, jsonObject);

			}
			return l;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static Long getUserTypeFromJson(UserDatabaseHelper dbHelper, JSONObject jsonObject) {
		WorkType usertype = new WorkType();
		try {
			JSONArray jsonArray_worktype = jsonObject.getJSONArray("worktype");
			JSONObject jsonObject_worktype = (JSONObject) jsonArray_worktype.get(0);
			usertype.id = jsonObject_worktype.getLong("id");
			usertype.type = jsonObject_worktype.getString("type");

			ContentValues values = new ContentValues();
			values.put("id", usertype.id);
			values.put("type", usertype.type);

			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Long i = db.insert("usertype", null, values);
			values.clear();
			return i;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;

	}
}
