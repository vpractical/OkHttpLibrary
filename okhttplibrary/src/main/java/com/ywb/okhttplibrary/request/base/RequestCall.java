package com.ywb.okhttplibrary.request.base;

import com.ywb.okhttplibrary.Ok;
import com.ywb.okhttplibrary.callback.base.Callback;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by ywb on 2018/3/1.
 */

public class RequestCall {

	private OkRequest okRequest;
	private Request request;
	private Call call;
	private OkHttpClient client;

	private long readTimeout,writeTimeout,connectTimeout;

	public RequestCall(OkRequest okRequest){
		this.okRequest = okRequest;
	}

	public Call buildCall(Callback callback){
		request = buildRequest(callback);
		if(readTimeout > 0 || writeTimeout > 0 || connectTimeout > 0){
			readTimeout = readTimeout > 0 ? readTimeout : Ok.DEFAULT_TIMEOUT;
			writeTimeout = writeTimeout > 0 ? writeTimeout : Ok.DEFAULT_TIMEOUT;
			connectTimeout = connectTimeout > 0 ? connectTimeout : Ok.DEFAULT_TIMEOUT;

			client = Ok.getInstance().getOkHttpClient().newBuilder()
					.readTimeout(readTimeout, TimeUnit.MILLISECONDS)
					.writeTimeout(writeTimeout, TimeUnit.MILLISECONDS)
					.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
					.build();
			call = client.newCall(request);
		}else{
			call = Ok.getInstance().getOkHttpClient().newCall(request);
		}
		return call;
	}

	private Request buildRequest(Callback callback){
		return okRequest.buildRequest(callback);
	}

	public void execute(Callback callback){
		buildCall(callback);
		if(callback == null){
			callback.onBefore(request,okRequest.getId());
		}
		Ok.getInstance().execute(this,callback);
	}

	public Call getCall() {
		return call;
	}

	public OkRequest getOkRequest() {
		return okRequest;
	}

	public RequestCall readTimeOut(long readTimeout)
	{
		this.readTimeout = readTimeout;
		return this;
	}

	public RequestCall writeTimeout(long writeTimeout)
	{
		this.writeTimeout = writeTimeout;
		return this;
	}

	public RequestCall connectTimeout(long connectTimeout)
	{
		this.connectTimeout = connectTimeout;
		return this;
	}

}
