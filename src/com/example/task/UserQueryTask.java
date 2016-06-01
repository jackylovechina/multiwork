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

import com.example.domain.User;
import com.example.utils.UserDatabaseHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public class UserQueryTask extends AsyncTask<String, String, Long> {
	UserDatabaseHelper dbHelper;

	public UserQueryTask(UserDatabaseHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	@Override
	protected Long doInBackground(String... params) {
		String strResult = null;
		Long l = null;

		String path = "http://172.16.150.118:8080/MultiWork/selectalluser.do";
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

				l = getUserFromJson(dbHelper, jsonObject);

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

	public static Long getUserFromJson(UserDatabaseHelper dbHelper, JSONObject jsonObject) {
		User user = new User();
		try {
			user.id = jsonObject.getLong("id");
			user.userinfo = jsonObject.getString("userinfo");
			user.password = jsonObject.getInt("password");
			ContentValues values = new ContentValues();
			values.put("id", user.id);
			values.put("userinfo", user.userinfo);
			values.put("password", user.password);

			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Long i = db.insert("user", null, values);
			values.clear();
			return i;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;

	}

}
