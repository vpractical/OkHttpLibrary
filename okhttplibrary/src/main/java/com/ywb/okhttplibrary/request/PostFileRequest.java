package com.ywb.okhttplibrary.request;

import com.ywb.okhttplibrary.callback.base.Callback;
import com.ywb.okhttplibrary.request.base.OkRequest;
import com.ywb.okhttplibrary.request.requestbody.CountingRequestBody;
import com.ywb.okhttplibrary.request.requestbody.OnProgressListener;
import com.ywb.okhttplibrary.utils.Tasks;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by ywb on 2018/3/2.
 */

public class PostFileRequest extends OkRequest {

	private File file;
	private MediaType mediaType;

	public PostFileRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, int id,File file, MediaType mediaType) {
		super(url, tag, params, headers, id);
		this.file = file;
		this.mediaType = mediaType;
	}

	@Override
	protected RequestBody createRequestBody() {
		return RequestBody.create(mediaType,file);
	}

	@Override
	protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
		if(callback != null ){
			CountingRequestBody countingRequestBody = new CountingRequestBody(requestBody, new OnProgressListener() {
				@Override
				public void currentProgress(final float progress, final long total) {
					Tasks.execute(new Runnable() {
						@Override
						public void run() {
							callback.inProgress(progress,total,id);
						}
					});
				}
			});
			return countingRequestBody;
		}
		return requestBody;
	}

	@Override
	protected Request createRequest(RequestBody requestBody) {
		return builder.post(requestBody).build();
	}
}
