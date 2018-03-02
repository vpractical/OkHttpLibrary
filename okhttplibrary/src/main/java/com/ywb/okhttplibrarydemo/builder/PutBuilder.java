package com.ywb.okhttplibrarydemo.builder;

import com.ywb.okhttplibrarydemo.request.PutRequest;
import com.ywb.okhttplibrarydemo.request.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ywb on 2018/3/2.
 */

public class PutBuilder extends OkRequestBuilder<PutBuilder> implements ParamsInterface<PutBuilder> {
	@Override
	public RequestCall build() {
		return new PutRequest(url,tag,params,headers,id).build();
	}

	@Override
	public PutBuilder params(Map<String, String> params) {
		this.params = params;
		return this;
	}

	@Override
	public PutBuilder addParams(String key, String val) {
		if(params == null){
			params = new LinkedHashMap<>();
		}
		params.put(key, val);
		return this;
	}
}
