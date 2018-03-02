package com.ywb.okhttplibrarydemo.request;

import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by ywb on 2018/3/2.
 */

public class DeleteRequest extends OkRequest {
	public DeleteRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, int id) {
		super(url, tag, params, headers, id);
	}

	@Override
	protected RequestBody createRequestBody() {
		return null;
	}

	@Override
	protected Request createRequest(RequestBody requestBody) {
		if (requestBody == null) {
			return builder.delete().build();
		} else {
			return builder.delete(requestBody).build();
		}
	}
}
