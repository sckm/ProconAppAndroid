package jp.gr.procon.proconapp.api;

import com.squareup.okhttp.Request;

public class BaseUserApi<T> extends BaseApi<T> {
    protected static final String HEADER_USER_TOKEN = "X-USER-TOKEN";

    private String mUserToken;

    public BaseUserApi(String userToken) {
        mUserToken = userToken;
    }

    @Override
    protected Request.Builder getDefaultRequestBuilder() {
        return super.getDefaultRequestBuilder()
                .addHeader(HEADER_USER_TOKEN, mUserToken);
    }
}
