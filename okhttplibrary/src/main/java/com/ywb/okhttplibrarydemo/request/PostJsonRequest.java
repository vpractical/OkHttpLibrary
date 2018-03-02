package com.ywb.okhttplibrarydemo.request;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by ywb on 2018/3/2.
 */

public class PostJsonRequest extends OkRequest {
	private String content;
	private MediaType mediaType;
	public PostJsonRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, int id, String content, MediaType mediaType) {
		super(url, tag, params, headers, id);
		this.content = content;
		this.mediaType = mediaType;
	}

	@Override
	protected RequestBody createRequestBody() {
		return RequestBody.create(mediaType,content);
	}

	@Override
	protected Request createRequest(RequestBody requestBody) {
		return builder.post(requestBody).build();
	}
}
