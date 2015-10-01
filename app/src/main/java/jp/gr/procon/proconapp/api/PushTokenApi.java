package jp.gr.procon.proconapp.api;

import android.text.TextUtils;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import jp.gr.procon.proconapp.model.DeviceToken;
import jp.gr.procon.proconapp.util.JsonUtil;
import timber.log.Timber;

public class PushTokenApi {
    private static final String PATH = "user/me";
    private static final String SEGMENT = "push_token";

    public static class PutRequest extends BaseUserApi<Void> {

        public PutRequest(String userToken) {
            super(userToken);
        }

        public PutRequest put(String token) {
            HttpUrl url = getDefaultUrlBuilder()
                    .addEncodedPathSegment(PATH)
                    .addEncodedPathSegment(SEGMENT)
                    .build();

            DeviceToken deviceToken = new DeviceToken(token);
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, JsonUtil.toJson(deviceToken));

            Request req = getDefaultRequestBuilder()
                    .url(url)
                    .put(body)
                    .build();
            execute(req);

            if (!TextUtils.isEmpty(mResponseBodyText)) {
                Timber.d("post: code=" + mResponseStatusCode + " body=" + mResponseBodyText);
                mResponseObj = JsonUtil.fromJson(mResponseBodyText, Void.class);
            }

            return this;
        }
    }
}
