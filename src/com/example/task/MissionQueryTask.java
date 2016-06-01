package com.example.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.domain.Mission;

public class MissionQueryTask {
	public static List<Mission> getMission(Long userid) throws Exception {
		List<Mission> lists = new ArrayList<Mission>();
		String strResult = null;

		String path = "http://172.16.150.118:8080/MultiWork/selectmission.do?dtype=json";
		HttpGet httpRequest = new HttpGet(path);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = httpClient.execute(httpRequest);

		if (httpResponse.getStatusLine().getStatusCode() == 200) {

			strResult = EntityUtils.toString(httpResponse.getEntity());

		}
		JSONArray jsonArray = new JSONArray(strResult);
		// Log.d("look", jsonArray + "1");
		JSONObject jsonObject;
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = (JSONObject) jsonArray.get(i);

			if (jsonObject.getLong("user_id") == userid) {

				lists.add(getMapForJson(jsonObject));
			}

		}

		return lists;
	}

	public static Mission getMapForJson(JSONObject jsonObject) {

		Mission mission = new Mission();

		try {
			mission.id = jsonObject.getInt("id");
			mission.typeid = jsonObject.getLong("type_id");
			mission.time = jsonObject.getLong("time");
			mission.content = jsonObject.getString("content");
			// 获取时间的json
			// jsonTime = jsonObject.getJSONObject("time");

			return mission;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

}
