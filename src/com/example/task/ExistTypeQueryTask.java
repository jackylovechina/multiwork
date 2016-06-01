package com.example.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.domain.WorkType;

public class ExistTypeQueryTask {
	public static List<WorkType> getWorkType(Long userid) throws Exception {
		List<WorkType> lists = new ArrayList<WorkType>();
		String strResult = null;

		String path = "http://172.16.150.118:8080/MultiWork/selectusertype.do";
		path += "?userid=" + userid;
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(path);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			HttpEntity httpEntity = httpResponse.getEntity();
			strResult = EntityUtils.toString(httpEntity, "UTF-8");
		}

		JSONArray jsonArray = new JSONArray(strResult);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			JSONArray jsonArray_type = jsonObject.getJSONArray("worktype");
			JSONObject jsonObject_type = jsonArray_type.getJSONObject(0);
			lists.add(getTypeFromJson(jsonObject_type));

		}

		return lists;

	}

	public static WorkType getTypeFromJson(JSONObject jsonObject) {
		WorkType worktype = new WorkType();
		try {
			worktype.id = jsonObject.getLong("id");
			worktype.type = jsonObject.getString("type");
			return worktype;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;

	}
}
