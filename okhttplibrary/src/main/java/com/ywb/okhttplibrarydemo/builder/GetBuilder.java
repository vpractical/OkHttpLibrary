package com.ywb.okhttplibrarydemo.builder;

import com.ywb.okhttplibrarydemo.request.GetRequest;
import com.ywb.okhttplibrarydemo.request.RequestCall;
import com.ywb.okhttplibrarydemo.utils.ParamsAppendUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ywb on 2018/3/1.
 */

public class GetBuilder extends OkRequestBuilder<GetBuilder> implements ParamsInterface<GetBuilder> {


	@Override
	public GetBuilder params(Map<String, String> params) {
		this.params = params;
		return this;
	}

	@Override
	public GetBuilder addParams(String key, String val) {
		if(params == null){
			params = new LinkedHashMap<>();
		}
		params.put(key,val);
		return this;
	}

	@Override
	public RequestCall build() {
		if(params != null){
			url = ParamsAppendUtil.appendParams(url,params);
		}
		return new GetRequest(url,tag,params,headers,id).build();
	}
}
