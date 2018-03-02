package com.ywb.okhttplibrarydemo;

import com.ywb.okhttplibrarydemo.builder.DeleteBuilder;
import com.ywb.okhttplibrarydemo.builder.GetBuilder;
import com.ywb.okhttplibrarydemo.builder.PostFileBuilder;
import com.ywb.okhttplibrarydemo.builder.PostFormBuilder;
import com.ywb.okhttplibrarydemo.builder.PostJsonBuilder;
import com.ywb.okhttplibrarydemo.builder.PutBuilder;
import com.ywb.okhttplibrarydemo.callback.Callback;
import com.ywb.okhttplibrarydemo.request.RequestCall;
import com.ywb.okhttplibrarydemo.utils.Tasks;

import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by ywb on 2018/3/1.
 */

public class Ok {

	public static final long DEFAULT_TIMEOUT = 10 * 1000L;


	private static volatile Ok instance;
	private OkHttpClient client;

	public Ok(OkHttpClient client) {
		this.client = client == null ? new OkHttpClient() : client;
	}

	public static Ok initClient(OkHttpClient client) {
		if (instance == null) {
			synchronized (Ok.class) {
				if (instance == null) {
					instance = new Ok(client);
				}
			}
		}
		return instance;
	}

	public static Ok getInstance() {
		return initClient(null);
	}

	public OkHttpClient getOkHttpClient() {
		return client;
	}

	/**------------------------创建请求------------------------------*/

	public static GetBuilder get() {
		return new GetBuilder();
	}

	public static PostFormBuilder post() {
		return new PostFormBuilder();
	}

	public static PostFileBuilder postFile(){
		return new PostFileBuilder();
	}

	public static PostJsonBuilder postJson() {
		return new PostJsonBuilder();
	}

	public static DeleteBuilder delete(){
		return new DeleteBuilder();
	}

	public static PutBuilder put(){
		return new PutBuilder();
	}

	/**------------------------请求参数类型--------------------------*/

	public MediaType mediaTypeJson() {
		return MediaType.parse("application/json; charset=utf-8");
	}

	public MediaType mediaTypePlain() {
		return MediaType.parse("text/plain;charset=utf-8");
	}

	public MediaType mediaTypeStream() {
		return MediaType.parse("application/octet-stream");
	}

	public MediaType mediaTypeFile(String path) {
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		String contentTypeFor = null;
		try {
			contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (contentTypeFor == null) {
			return mediaTypeStream();
		} else {
			return MediaType.parse(contentTypeFor);
		}

	}

	/**------------------------取消请求----------------------------*/

	public void cancelRequestByTag(Object tag) {
		if (tag == null) return;
		for (Call call : client.dispatcher().queuedCalls()
				) {
			if (tag.equals(call.request().tag())) {
				call.cancel();
			}
		}
		for (Call call : client.dispatcher().runningCalls()
				) {
			if (tag.equals(call.request().tag())) {
				call.cancel();
			}
		}
	}

	/**------------------------执行请求--------------------------*/

	public void execute(RequestCall requestCall, Callback callback) {

		if (callback == null) callback = Callback.CALLBACK_DEFAULT;
		final Callback finalCallback = callback;
		final int id = requestCall.getOkRequest().getId();
		requestCall.getCall().enqueue(new okhttp3.Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				returnFailResCallback(call, e, finalCallback, id);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				try {
					if (call.isCanceled()) {
						onFailure(call, new IOException("request canceled"));
					}

					if (!response.isSuccessful()) {
						onFailure(call, new IOException("request is Failed,response's code is " + response.code()));
					}

					Object o = finalCallback.parseNetworkResponse(response, id);
					returnSuccessResCallback(o, finalCallback, id);
				} catch (Exception e) {
					e.printStackTrace();
					onFailure(call, new IOException(e.getMessage()));
				} finally {
					if (response.body() != null) {
						response.body().close();
					}
				}
			}
		});
	}

	private void returnFailResCallback(final Call call, final Exception e, final Callback callback, final int id) {
		if (callback == null) return;
		Tasks.execute(new Runnable() {
			@Override
			public void run() {
				callback.onError(call, e, id);
				callback.onAfter(id);
			}
		});
	}

	private void returnSuccessResCallback(final Object obj, final Callback callback, final int id) {
		if (callback == null) return;
		Tasks.execute(new Runnable() {
			@Override
			public void run() {
				callback.onResponse(obj, id);
				callback.onAfter(id);
			}
		});

	}

}
