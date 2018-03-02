package com.ywb.okhttplibrarydemo.request;

import com.ywb.okhttplibrarydemo.callback.Callback;

import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by ywb on 2018/3/1.
 */

public abstract class OkRequest {

	protected String url;
	protected Object tag;
	protected Map<String, String> params;
	protected Map<String, String> headers;
	protected int id;
	protected Request.Builder builder = new Request.Builder();


	protected OkRequest(String url, Object tag,
							Map<String, String> params, Map<String, String> headers,int id)
	{
		this.url = url;
		this.tag = tag;
		this.params = params;
		this.headers = headers;
		this.id = id ;

		if (url == null)
		{
			throw new IllegalArgumentException("request url can not be null");
		}

		initBuilder();
	}

	private void initBuilder(){
		builder.url(url).tag(tag);
		appendHeader();
	}


	protected void appendHeader(){
		Headers.Builder headerBuilder = new Headers.Builder();

		if(headers != null && headers.size() > 0){
			for (String key:headers.keySet()
				 ) {
				headerBuilder.add(key,headers.get(key));
			}
			builder.headers(headerBuilder.build());
		}
	}

	public int getId()
	{
		return id;
	}


	protected abstract RequestBody createRequestBody();
	protected abstract Request createRequest(RequestBody requestBody);
	protected RequestBody wrapRequestBody(RequestBody requestBody,Callback callback){
		return requestBody;
	}

	public Request buildRequest(Callback callback){
		RequestBody requestBody = createRequestBody();
		RequestBody wrappedRequestBody = wrapRequestBody(requestBody,callback);
		Request request = createRequest(wrappedRequestBody);
		return request;
	}

	public RequestCall build()
	{
		return new RequestCall(this);
	}
}
