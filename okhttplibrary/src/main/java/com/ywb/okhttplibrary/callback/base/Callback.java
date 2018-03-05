package com.ywb.okhttplibrary.callback.base;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ywb on 2018/3/1.
 */

public abstract class Callback<T> {
	public void onBefore(Request request,int id){}
	public void onAfter(int id){}
	public abstract T parseNetworkResponse(Response response, int id) throws Exception;
	public abstract void onResponse(T response,int id);
	public abstract void onError(Call call, Exception e, int id);
	public void inProgress(float progress,long total,int id){}

}
