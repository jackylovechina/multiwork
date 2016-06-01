package com.example.task;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.widget.TextView;

public class TypeQueryFromId extends AsyncTask<Long, Integer, String> {
	TextView textView;

	public TypeQueryFromId(TextView textView) {
		this.textView = textView;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String doInBackground(Long... params) {
		Long typeid = params[0];
		String strResult = null;

		String path = "http://172.16.150.118:8080/MultiWork/gettype.do";
		path += "?typeid=" + typeid;
		HttpGet httpRequest = new HttpGet(path);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {

				strResult = EntityUtils.toString(httpResponse.getEntity());
				JSONArray jsonA = new JSONArray(strResult);
				JSONObject jsonO = jsonA.getJSONObject(0);
				String type = jsonO.getString("type");
				return type;

			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		textView.setText(result);
		super.onPostExecute(result);
	}

}
