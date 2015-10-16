package jp.gr.procon.proconapp.api;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import jp.gr.procon.proconapp.ApiConfig;
import timber.log.Timber;

public abstract class BaseApi<T> {
    protected static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    protected static final MediaType MEDIA_TYPE_TEXT_PLAIN = MediaType.parse("text/plain; charset=utf-8");

    protected T mResponseObj;
    protected String mResponseBodyText;
    protected int mResponseStatusCode;

    public T getResponseObj() {
        return mResponseObj;
    }

    public int getResponseStatusCode() {
        return mResponseStatusCode;
    }

    protected HttpUrl.Builder getDefaultUrlBuilder() {
        return new HttpUrl.Builder()
                .scheme(ApiConfig.getDefaultScheme())
                .host(ApiConfig.getDefaultHost())
                .addEncodedPathSegment(ApiConfig.getDefaultPrefixPathSegment());
    }

    protected Request.Builder getDefaultRequestBuilder() {
        return new Request.Builder();
    }

    /** 実際に通信処理を行う */
    protected void execute(Request req) {
        mResponseObj = null;
        mResponseBodyText = null;
        mResponseStatusCode = -1;

        OkHttpClient client = new OkHttpClient();

        ResponseBody body = null;
        try {
            Response res = client.newCall(req).execute();
            mResponseStatusCode = res.code();

            body = res.body();
            if (isResponseOk(res)) {
                mResponseBodyText = body.string();
            } else {
                Timber.e("execute: failed request code=" + res.code());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (body != null) {
                try {
                    body.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected boolean isResponseOk(Response res) {
        return res != null && res.isSuccessful() && res.body() != null;
    }

    public boolean isSuccessful() {
        return mResponseObj != null && 200 <= mResponseStatusCode && mResponseStatusCode < 300;
    }

}
