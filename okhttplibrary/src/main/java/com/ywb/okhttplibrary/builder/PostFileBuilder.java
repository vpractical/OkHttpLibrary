package com.ywb.okhttplibrary.builder;

import com.ywb.okhttplibrary.Ok;
import com.ywb.okhttplibrary.builder.base.OkRequestBuilder;
import com.ywb.okhttplibrary.request.PostFileRequest;
import com.ywb.okhttplibrary.request.base.RequestCall;

import java.io.File;

import okhttp3.MediaType;

/**
 * Created by ywb on 2018/3/2.
 */

public class PostFileBuilder extends OkRequestBuilder<PostFileBuilder> {

	private File file;
	private MediaType mediaType;

	public PostFileBuilder file(File file) {
		if (file == null) {
			throw new IllegalArgumentException("file is null");
		}
		this.file = file;
		return this;
	}

	public PostFileBuilder mediaType(MediaType mediaType) {
		if (mediaType == null) {
			mediaType = Ok.getInstance().mediaTypeStream();
		}
		this.mediaType = mediaType;
		return this;
	}

	@Override
	public RequestCall build() {
		return new PostFileRequest(url, tag, params, headers, id, file, mediaType).build();
	}
}
