package com.ywb.okhttplibrary.builder;

import com.ywb.okhttplibrary.builder.base.OkRequestBuilder;
import com.ywb.okhttplibrary.request.PostJsonRequest;
import com.ywb.okhttplibrary.request.base.RequestCall;

import okhttp3.MediaType;

/**
 * Created by ywb on 2018/3/2.
 */

public class PostJsonBuilder extends OkRequestBuilder<PostJsonBuilder> {
	private String content;
	private MediaType mediaType;


	public PostJsonBuilder content(String content){
		this.content = content;
		return this;
	}

	public PostJsonBuilder mediaType(MediaType mediaType){
		this.mediaType = mediaType;
		return this;
	}

	@Override
	public RequestCall build() {
		return new PostJsonRequest(url,tag,params,headers,id,content,mediaType).build();
	}
}
