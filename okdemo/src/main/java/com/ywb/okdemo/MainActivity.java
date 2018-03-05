package com.ywb.okdemo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import com.ywb.okhttplibrary.Ok;
import com.ywb.okhttplibrary.callback.StringCallback;
import com.ywb.okhttplibrary.utils.LoggerInterceptor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		OkHttpClient okClient = new OkHttpClient.Builder()
				.addInterceptor(new LoggerInterceptor("demo log ----> "))
				.connectTimeout(6 * 1000L, TimeUnit.MILLISECONDS)
				.build();
		Ok.initClient(okClient);

//		get();
//		post();
//		postFile();
//		postForm();
//		delete();
//		put();
	}

	private void put(){

		String url = "http://192.168.1.76:8087/v10/friend/apply";
		Ok
				.put()
				.url(url)
				.addParams("accId","vvval46ue")
				.addParams("debug","1")
				.build()
				.execute(new ResStringCallBack());
	}

	private void delete(){

		String url = "http://192.168.1.76:8087/v10/friend/apply";
		Ok
				.delete()
				.url(url)
				.addParams("accId","vvvalue")
				.addParams("debug","1")
				.build()
				.execute(new ResStringCallBack());
	}

	private void postForm(){
		String url = "http://192.168.1.76:8087/v10/log";
		File file = new File(Environment.getExternalStorageDirectory(), "ssssss.png");

		Map<String,File> files = new LinkedHashMap<>();
		files.put("ssssss.png",file);

		Ok
				.post()
				.url(url)
				.addParams("name","ywb")
				.files("kkkey",files)
				.addFile("kkey","ssssss.png",file)
				.build()
				.execute(new ResStringCallBack());

		L.e("请求post表单执行");
	}

	private void postFile(){
		String url = "http://192.168.1.76:8087/v10/log";
		File file = new File(Environment.getExternalStorageDirectory(), "ssssss.png");

		Ok
				.postFile()
				.url(url)
				.file(file)
				.build()
				.execute(new ResStringCallBack());

		L.e("请求postFile执行");
	}

	private void post(){
		String url = "http://192.168.1.76:8087/v10/log";
		JSONObject json = new JSONObject();
		try {
			json.put("name","coco");
			json.put("age","22");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Ok
				.postJson()
				.url(url)
				.addHeader("agent","valA")
				.addHeader("agent11","a11")
				.id(2)
				.tag(this)
				.content(json.toString())
				.mediaType(com.ywb.okhttplibrary.utils.MediaType.mediaTypeJson())
				.build()
				.execute(new ResStringCallBack());


		L.e("请求post执行");
	}

	private void get() {
		String url = "http://192.168.1.76:8087/v10/version";

		Ok
				.get()
				.url(url)
				.addParams("version", "135")
				.addParams("debug", "1")
				.build()
				.execute(new ResStringCallBack());
		L.e("请求get执行");
	}

	private class ResStringCallBack extends StringCallback{

		@Override
		public void onResponse(String response, int id) {
			L.e("response = " + response);
		}

		@Override
		public void onError(Call call, Exception e, int id) {
			L.e("response = " + e.getMessage());
		}

		@Override
		public void onAfter(int id) {
			super.onAfter(id);
			L.e("onAfter");
		}

		@Override
		public void onBefore(Request request, int id) {
			super.onBefore(request, id);
			L.e("onBefore");
		}

		@Override
		public void inProgress(float progress, long total, int id) {
			super.inProgress(progress, total, id);
			L.e("progress = " + progress);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Ok.getInstance().cancelRequestByTag(this);
	}
}
