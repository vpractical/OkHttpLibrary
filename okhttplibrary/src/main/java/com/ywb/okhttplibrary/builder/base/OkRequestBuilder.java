package com.ywb.okhttplibrary.builder.base;

import com.ywb.okhttplibrary.request.base.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ywb on 2018/3/1.
 */

public abstract class OkRequestBuilder<T extends OkRequestBuilder> {

	protected String url;
	protected Object tag;
	protected Map<String,String> headers;
	protected Map<String,String> params;
	protected int id;

	public T url(String url){
		this.url = url;
		return (T)this;
	}

	public T tag(Object tag){
		this.tag = tag;
		return (T)this;
	}

	public T id(int id){
		this.id = id;
		return (T)this;
	}

	public T header(Map<String,String> headers){
		this.headers = headers;
		return (T)this;
	}

	public T addHeader(String key,String val){
		if(headers == null){
			headers = new LinkedHashMap<>();
		}
		headers.put(key,val);
		return (T)this;

	}

	public abstract RequestCall build();

}
