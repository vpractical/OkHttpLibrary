package com.ywb.okhttplibrary.request;

import com.ywb.okhttplibrary.request.base.OkRequest;

import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by ywb on 2018/3/1.
 */

public class GetRequest extends OkRequest {
	public GetRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, int id) {
		super(url, tag, params, headers, id);
	}

	@Override
	protected RequestBody createRequestBody() {
		return null;
	}

	@Override
	protected Request createRequest(RequestBody requestBody) {
		return builder.get().build();
	}
}
