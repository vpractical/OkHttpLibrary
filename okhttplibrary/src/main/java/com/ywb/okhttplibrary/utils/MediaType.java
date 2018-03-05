package com.ywb.okhttplibrary.utils;

import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by ywb on 2018/3/5.
 */

public class MediaType {

	/**------------------------请求参数类型--------------------------*/

	public static okhttp3.MediaType mediaTypeJson() {
		return okhttp3.MediaType.parse("application/json; charset=utf-8");
	}

	public static okhttp3.MediaType mediaTypePlain() {
		return okhttp3.MediaType.parse("text/plain;charset=utf-8");
	}

	public static okhttp3.MediaType mediaTypeStream() {
		return okhttp3.MediaType.parse("application/octet-stream");
	}

	public static okhttp3.MediaType mediaTypeFile(String path) {
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		String contentTypeFor = null;
		try {
			contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (contentTypeFor == null) {
			return mediaTypeStream();
		} else {
			return okhttp3.MediaType.parse(contentTypeFor);
		}

	}

}
