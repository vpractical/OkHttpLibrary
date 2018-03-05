package com.ywb.okhttplibrary.bean;

import java.io.File;

/**
 * 上传表单中的文件
 * Created by ywb on 2018/3/2.
 */

public class FormFileBean {

	public FormFileBean(String key,String fileName,File file){
		this.key = key;
		this.fileName = fileName;
		this.file = file;
	}

	public String key,fileName;
	public File file;

}
