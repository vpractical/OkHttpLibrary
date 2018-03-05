package com.ywb.okhttplibrary;

import com.ywb.okhttplibrary.builder.DeleteBuilder;
import com.ywb.okhttplibrary.builder.GetBuilder;
import com.ywb.okhttplibrary.builder.PostFileBuilder;
import com.ywb.okhttplibrary.builder.PostFormBuilder;
import com.ywb.okhttplibrary.builder.PostJsonBuilder;
import com.ywb.okhttplibrary.builder.PutBuilder;
import com.ywb.okhttplibrary.callback.base.Callback;
import com.ywb.okhttplibrary.request.base.RequestCall;
import com.ywb.okhttplibrary.utils.Tasks;

import java.io.IOException;

import okhttp3.Call;
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

	public void execute(RequestCall requestCall, final Callback callback) {
		final int id = requestCall.getOkRequest().getId();
		requestCall.getCall().enqueue(new okhttp3.Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				if(callback != null){
					returnFailResCallback(call, e, callback, id);
				}
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				try {
					if(callback != null){
						if (call.isCanceled()) {
							onFailure(call, new IOException("request canceled"));
						}
						if (!response.isSuccessful()) {
							onFailure(call, new IOException("request is Failed,response's code is " + response.code()));
						}
						Object o = callback.parseNetworkResponse(response, id);
						returnSuccessResCallback(o, callback, id);
					}
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
