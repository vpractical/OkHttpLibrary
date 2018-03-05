package com.ywb.okhttplibrary.utils;

import java.util.concurrent.Executors;

/**
 * Created by ywb on 2018/3/1.
 */

public class Tasks {

	public static void execute(Runnable runnable){
		Executors.newCachedThreadPool().execute(runnable);
	}
}
