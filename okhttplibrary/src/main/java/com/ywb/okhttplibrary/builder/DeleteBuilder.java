package com.ywb.okhttplibrary.builder;

import com.ywb.okhttplibrary.builder.base.OkRequestBuilder;
import com.ywb.okhttplibrary.builder.base.ParamsInterface;
import com.ywb.okhttplibrary.request.DeleteRequest;
import com.ywb.okhttplibrary.request.base.RequestCall;
import com.ywb.okhttplibrary.utils.ParamsAppendUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ywb on 2018/3/2.
 */

public class DeleteBuilder extends OkRequestBuilder<DeleteBuilder> implements ParamsInterface<DeleteBuilder> {
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
