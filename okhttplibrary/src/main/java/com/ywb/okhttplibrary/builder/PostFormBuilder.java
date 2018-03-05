package com.ywb.okhttplibrary.builder;

import com.ywb.okhttplibrary.bean.FormFileBean;
import com.ywb.okhttplibrary.builder.base.OkRequestBuilder;
import com.ywb.okhttplibrary.builder.base.ParamsInterface;
import com.ywb.okhttplibrary.request.PostFormRequest;
import com.ywb.okhttplibrary.request.base.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ywb on 2018/3/2.
 */

public class PostFormBuilder extends OkRequestBuilder<PostFormBuilder> implements ParamsInterface<PostFormBuilder> {

	private List<FormFileBean> files = new ArrayList<>();

	@Override
	public RequestCall build() {
		return new PostFormRequest(url,tag,params,headers,id,files).build();
	}

	@Override
	public PostFormBuilder params(Map<String, String> params) {
		this.params = params;
		return this;
	}

	@Override
	public PostFormBuilder addParams(String key, String val) {
		if(params == null){
			params = new LinkedHashMap<>();
		}
		params.put(key, val);
		return this;
	}

	public PostFormBuilder files(String key,Map<String,File> files) {
		for (String fileName:files.keySet()
			 ) {
			addFile(key,fileName,files.get(fileName));
		}
		return this;
	}

	public PostFormBuilder addFile(String key, String fileName, File file) {
		files.add(new FormFileBean(key,fileName,file));
		return this;
	}
}
