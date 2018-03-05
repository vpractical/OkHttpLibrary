package com.ywb.okhttplibrary.request;

import com.ywb.okhttplibrary.request.base.OkRequest;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by ywb on 2018/3/2.
 */

public class PutRequest extends OkRequest {
	public PutRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, int id) {
		super(url, tag, params, headers, id);
	}

	@Override
	protected RequestBody createRequestBody() {
		FormBody.Builder builder = new FormBody.Builder();
		if(params != null){
			for (String key:params.keySet()
					) {
				builder.add(key,params.get(key));
			}
		}
		return builder.build();
	}

	@Override
	protected Request createRequest(RequestBody requestBody) {
		return builder.put(requestBody).build();
	}
}
