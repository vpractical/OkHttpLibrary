package com.ywb.okhttplibrarydemo.callback;

import okhttp3.Response;

/**
 * Created by ywb on 2018/3/1.
 */

public abstract class StringCallback extends Callback<String> {
	@Override
	public String parseNetworkResponse(Response response, int id) throws Exception {
		return response.body().string();
	}
}
