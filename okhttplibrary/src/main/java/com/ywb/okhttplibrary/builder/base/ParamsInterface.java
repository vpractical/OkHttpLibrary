package com.ywb.okhttplibrary.builder.base;

import java.util.Map;

/**
 * Created by ywb on 2018/3/1.
 */

public interface ParamsInterface<T> {
	T params(Map<String,String> params);
	T addParams(String key,String val);
}
