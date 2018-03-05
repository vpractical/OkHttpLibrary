package com.ywb.okhttplibrary.utils;

import android.net.Uri;

import java.util.Map;

/**
 * Created by ywb on 2018/3/2.
 */

public class ParamsAppendUtil {
	public static String appendParams(String url,Map<String,String> params){

		if(url != null && params != null && params.size() > 0){
			Uri.Builder builder = Uri.parse(url).buildUpon();
			for (String key:params.keySet()
					) {
				builder.appendQueryParameter(key,params.get(key));
			}
			return builder.build().toString();
		}

		return url;
	}
}
