package com.ywb.okhttplibrary.callback;

import com.ywb.okhttplibrary.callback.base.Callback;
import com.ywb.okhttplibrary.utils.Tasks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.Response;

/**
 * Created by ywb on 2018/3/5.
 */

public abstract class FileCallback extends Callback<File> {

	private String fileDir, fileName;
	private boolean progressNeed;

	public FileCallback(String fileDir, String fileName, boolean progressNeed) {
		this.fileDir = fileDir;
		this.fileName = fileName;
		this.progressNeed = progressNeed;
	}

	@Override
	public File parseNetworkResponse(Response response, final int id) throws Exception {
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			File dir = new File(fileDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			final long total = response.body().contentLength();
			long readLen = 0;
			is = response.body().byteStream();
			byte[] buffer = new byte[1024];
			int len;

			do {
				is.read(buffer);
				fos.write(buffer);
				len = buffer.length;
				readLen += len;
				if (progressNeed) {
					final long progress = readLen;
					Tasks.execute(new Runnable() {
						@Override
						public void run() {
							inProgress(progress, total, id);
						}
					});
				}

			} while (len != -1);

			fos.flush();
			return new File(fileDir, fileName);
		} finally {

			try {
				response.body().close();
				if(is != null)
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if(fos != null)
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
