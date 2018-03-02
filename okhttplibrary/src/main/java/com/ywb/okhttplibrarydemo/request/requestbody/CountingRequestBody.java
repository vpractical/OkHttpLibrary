package com.ywb.okhttplibrarydemo.request.requestbody;

import com.ywb.okhttplibrarydemo.request.requestbody.listener.OnProgressListener;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by ywb on 2018/3/2.
 */

public class CountingRequestBody extends RequestBody {

	private RequestBody requestBody;
	private OnProgressListener listener;
	private long total = -1;
	private CountingSink countingSink;

	public CountingRequestBody(RequestBody requestBody, OnProgressListener listener){
		this.requestBody = requestBody;
		this.listener = listener;
		try {
			total = requestBody.contentLength();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public MediaType contentType() {
		return requestBody.contentType();
	}

	@Override
	public void writeTo(BufferedSink sink) throws IOException {
		countingSink = new CountingSink(sink);
		BufferedSink bufferedSink = Okio.buffer(countingSink);
		requestBody.writeTo(bufferedSink);
		bufferedSink.flush();
	}

	protected final class CountingSink extends ForwardingSink
	{

		private long bytesWritten = 0;

		public CountingSink(Sink delegate)
		{
			super(delegate);
		}

		@Override
		public void write(Buffer source, long byteCount) throws IOException
		{
			super.write(source, byteCount);

			bytesWritten += byteCount;
			listener.currentProgress(bytesWritten, contentLength());
		}

	}
}
