package com.ywb.okhttplibrary.request;

import com.ywb.okhttplibrary.Ok;
import com.ywb.okhttplibrary.bean.FormFileBean;
import com.ywb.okhttplibrary.callback.base.Callback;
import com.ywb.okhttplibrary.request.base.OkRequest;
import com.ywb.okhttplibrary.request.requestbody.CountingRequestBody;
import com.ywb.okhttplibrary.request.requestbody.OnProgressListener;
import com.ywb.okhttplibrary.utils.Tasks;

import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by ywb on 2018/3/2.
 */

public class PostFormRequest extends OkRequest {

	private List<FormFileBean> files;

	public PostFormRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, int id, List<FormFileBean> files) {
		super(url, tag, params, headers, id);
		this.files = files;
	}

	@Override
	protected RequestBody createRequestBody() {
		if(files == null || files.isEmpty()){
			FormBody.Builder builder = new FormBody.Builder();
			if(params != null){
				for (String key:params.keySet()
						) {
					builder.add(key,params.get(key));
				}
			}
			return builder.build();
		}else{
			MultipartBody.Builder builder = new MultipartBody.Builder();
			if(params != null){
				for (String key:params.keySet()
						) {
					builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
							RequestBody.create(null, params.get(key)));
				}
			}

			for (FormFileBean bean:files
				 ) {
				RequestBody requestBody = RequestBody.create(Ok.getInstance().mediaTypeFile(bean.fileName),bean.file);
				builder.addFormDataPart(bean.key,bean.fileName,requestBody);
			}
			return builder.build();
		}

	}

	@Override
	protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
		if(callback == null){
			return requestBody;
		}
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

	@Override
	protected Request createRequest(RequestBody requestBody) {
		return builder.post(requestBody).build();
	}

}
