package com.example.task;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class MissionInsertTask extends AsyncTask<Object, Integer, String> {
	Context context;

	public MissionInsertTask(Context context) {
		this.context = context;
	}

	@Override
	protected String doInBackground(Object... params) {
		Long userid = (Long) params[0];
		Long typeid = (Long) params[1];
		Long time = (Long) params[2];
		String content = (String) params[3];
		try {
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String strResult = null;

		String path = "http://172.16.150.118:8080/MultiWork/insertmission.do";
		path += "?userid=" + userid + "&typeid=" + typeid + "&time=" + time + "&content=" + content;

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(path);
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				strResult = EntityUtils.toString(httpEntity, "UTF-8");
			}
			return strResult;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		if (result.contains("1")) {
			Toast.makeText(context, "insert success!!", 0).show();
		}
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

}
