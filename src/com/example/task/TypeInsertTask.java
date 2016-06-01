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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.multiwork.InsertTypeActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class TypeInsertTask extends AsyncTask<String, String, String> {
	Context context;
	Long userid;

	public TypeInsertTask(Context context, Long userid) {
		this.context = context;
		this.userid = userid;
	}

	@Override
	protected String doInBackground(String... params) {
		Long typeid = Long.valueOf(params[0]);
		String type = params[1];
		try {
			type = URLEncoder.encode(type, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		// 查询当前输入type是否存在
		String select_path = "http://172.16.150.118:8080/MultiWork/selecttype.do";
		select_path += "?type=" + type;
		//Log.d("look", select_path);
		try {
			HttpClient httpClient_select = new DefaultHttpClient();
			HttpGet httpGet_select = new HttpGet(select_path);
			HttpResponse httpResponse_select = httpClient_select.execute(httpGet_select);
			if (httpResponse_select.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse_select.getEntity();
				String strResult = EntityUtils.toString(httpEntity, "UTF-8");
				try {
					JSONArray jsonArray = new JSONArray(strResult);
					JSONObject jsonObject = jsonArray.getJSONObject(0);
					Long existtypeid = jsonObject.getLong("id");
					//Log.d("look", existtypeid + "existtypeid");
					// 查询到已存在的type，与现在的user进行关联
					new UserTypeInsertTask(context).execute(userid, existtypeid);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return "selectok";

			} else if (httpResponse_select.getStatusLine().getStatusCode() == 500) {
				// 查询当前type不存在，在type表中插入新type
				String insert_path = "http://172.16.150.118:8080/MultiWork/inserttype.do";
				insert_path += "?typeid=" + typeid + "&type=" + type;
				//Log.d("look", insert_path);
				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(insert_path);
					HttpResponse httpResponse = httpClient.execute(httpGet);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						new UserTypeInsertTask(context).execute(userid, typeid);
						return "insertok";
					}

				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
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

		if (result.equals("selectok")) {
			//Toast.makeText(context, "select type success!", 0).show();
		} else if (result.equals("selecter")) {
			Toast.makeText(context, "select type fail!", 0).show();
		} else if (result.equals("insertok")) {
			Toast.makeText(context, "insert type ok!", 0).show();
		}
	}

}
