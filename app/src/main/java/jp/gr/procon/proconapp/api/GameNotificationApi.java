package jp.gr.procon.proconapp.api;

import android.text.TextUtils;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import jp.gr.procon.proconapp.model.GameNotificationList;
import jp.gr.procon.proconapp.model.GamePhotoList;
import jp.gr.procon.proconapp.util.JsonUtil;
import timber.log.Timber;

public class GameNotificationApi {
    private static final String PATH = "user/me";
    private static final String SEGMENT = "game_notification";

    public static class GetRequest extends BaseUserApi<GameNotificationList> {
        public GetRequest(String userToken) {
            super(userToken);
        }

        public GetRequest get() {
            HttpUrl url = getDefaultUrlBuilder()
                    .addEncodedPathSegment(PATH)
                    .addEncodedPathSegment(SEGMENT)
                    .build();

            Request req = getDefaultRequestBuilder()
                    .url(url)
                    .get()
                    .build();
            execute(req);

            if (!TextUtils.isEmpty(mResponseBodyText)) {
                Timber.d("post: code=" + mResponseStatusCode + " body=" + mResponseBodyText);
                mResponseObj = JsonUtil.fromJson(mResponseBodyText, GameNotificationList.class);
            }

            return this;
        }
    }

    public static class PutRequest extends BaseUserApi<Void> {

        public PutRequest(String userToken) {
            super(userToken);
        }

        public PutRequest put(GameNotificationList ids) {
            HttpUrl url = getDefaultUrlBuilder()
                    .addEncodedPathSegment(PATH)
                    .addEncodedPathSegment(SEGMENT)
                    .build();

            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, JsonUtil.toJson(ids));

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
