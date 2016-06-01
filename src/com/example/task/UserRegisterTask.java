package com.example.task;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class UserRegisterTask extends AsyncTask<String, String, String> {
	Context context;

	public UserRegisterTask(Context context) {
		this.context = context;
	}

	@Override
	protected String doInBackground(String... params) {
		Long userid = Long.valueOf(params[0]);
		String userinfo = params[1];
		try {
			userinfo = URLEncoder.encode(userinfo, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		int password = Integer.valueOf(params[2]);
		String path = "http://172.16.150.118:8080/MultiWork/insertuser.do";
		path += "?userid=" + userid + "&userinfo=" + userinfo + "&password=" + password;
		//Log.d("look", path);
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(path);
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {

				return "success";
			} else {
				return "fail";
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
		if (result.equals("success")) {
			Toast.makeText(context, "×¢²á³É¹¦", 0).show();
		} else if (result.equals("fail")) {
			Toast.makeText(context, "×¢²áÊ§°Ü", 0).show();
		}

	}
}
