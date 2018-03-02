package com.ywb.okhttplibrarydemo.builder;

import com.ywb.okhttplibrarydemo.request.DeleteRequest;
import com.ywb.okhttplibrarydemo.request.RequestCall;
import com.ywb.okhttplibrarydemo.utils.ParamsAppendUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ywb on 2018/3/2.
 */

public class DeleteBuilder extends OkRequestBuilder<DeleteBuilder> implements ParamsInterface<DeleteBuilder>{
	@Override
	public RequestCall build() {
		if(params != null){
			url = ParamsAppendUtil.appendParams(url,params);
		}
		return new DeleteRequest(url,tag,params,headers,id).build();
	}

	@Override
	public DeleteBuilder params(Map<String, String> params) {
		this.params = params;
		return this;
	}

	@Override
	public DeleteBuilder addParams(String key, String val) {
		if(params == null){
			params = new LinkedHashMap<>();
		}
		params.put(key, val);
		return this;
	}
}
