package jp.gr.procon.proconapp.api;

import android.text.TextUtils;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Request;

import jp.gr.procon.proconapp.model.GamePhotoList;
import jp.gr.procon.proconapp.model.GameResultList;
import jp.gr.procon.proconapp.util.JsonUtil;
import timber.log.Timber;

public class GamePhotoListApi {
    public static class GetRequest extends BaseUserApi<GamePhotoList> {
        private static final String PATH = "game";
        private static final String SEGMENT = "photos";
        private static final String QUERY_COUNT = "count";

        public GetRequest(String userToken) {
            super(userToken);
        }

        public GetRequest get(String count) {
            HttpUrl url = getDefaultUrlBuilder()
                    .addEncodedPathSegment(PATH)
                    .addEncodedPathSegment(SEGMENT)
                    .addEncodedQueryParameter(QUERY_COUNT, count)
                    .build();

            Request req = getDefaultRequestBuilder()
                    .url(url)
                    .get()
                    .build();
            execute(req);

            if (!TextUtils.isEmpty(mResponseBodyText)) {
                Timber.d("post: code=" + mResponseStatusCode + " body=" + mResponseBodyText);
                mResponseObj = JsonUtil.fromJson(mResponseBodyText, GamePhotoList.class);
            }

            return this;
        }
    }
}
