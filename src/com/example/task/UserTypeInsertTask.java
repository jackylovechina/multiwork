package com.example.task;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class UserTypeInsertTask extends AsyncTask<Long, Integer, String> {
	Context context;

	public UserTypeInsertTask(Context context) {
		this.context = context;

	}

	@Override
	protected String doInBackground(Long... params) {
		Long userid = params[0];
		Long typeid = params[1];
		String path = "http://172.16.150.118:8080/MultiWork/insertusertype.do";
		path += "?userid=" + userid + "&typeid=" + typeid;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(path);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String strResult = EntityUtils.toString(httpEntity, "UTF-8");
				//Log.d("look", "strResult" + strResult);

				return strResult.contains("0") ? "该类型已存在" : "insertok";
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {

		Toast.makeText(context, result, 0).show();

	}
}
