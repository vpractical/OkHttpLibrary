package com.ywb.okhttplibrary.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ywb.okhttplibrary.callback.base.Callback;
import com.ywb.okhttplibrary.utils.Tasks;

import java.io.InputStream;

import okhttp3.Response;

/**
 * Created by ywb on 2018/3/5.
 */

public abstract class BitmapCallback extends Callback<Bitmap> {

	private boolean progressNeed;

	public BitmapCallback(boolean progressNeed) {
		this.progressNeed = progressNeed;
	}

	@Override
	public Bitmap parseNetworkResponse(Response response, final int id) throws Exception {

		if (!progressNeed) {
			return BitmapFactory.decodeStream(response.body().byteStream());
		}

		InputStream is = null;
		final long total = response.body().contentLength();
		try {
			is = response.body().byteStream();
			byte[] buffer = new byte[512];
			long readLen = 0;
			int len;

			do {
				len = is.read(buffer);
				readLen += len;
				final long progress = readLen;
				Tasks.execute(new Runnable() {
					@Override
					public void run() {
						inProgress(progress, total, id);
					}
				});
			} while (len != -1);

			return BitmapFactory.decodeStream(response.body().byteStream());
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
