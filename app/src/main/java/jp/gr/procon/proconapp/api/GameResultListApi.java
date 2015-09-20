package jp.gr.procon.proconapp.api;

import android.text.TextUtils;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Request;

import jp.gr.procon.proconapp.model.GameResultList;
import jp.gr.procon.proconapp.util.JsonUtil;
import timber.log.Timber;

public class GameResultListApi {
    public static class GetRequest extends BaseUserApi<GameResultList> {
        private static final String PATH = "game";
        private static final String SEGMENT = "game_results";
        private static final String QUERY_COUNT = "count";
        private static final String QUERY_FILTER = "filter";

        public static final String FILTER_ALL = "all";
        public static final String FILTER_ONLY_FOR_NOTIFICATION = "only_for_notification";

        public GetRequest(String userToken) {
            super(userToken);
        }

        public GetRequest get(String count) {
            return get(count, FILTER_ALL);
        }

        /**
         * @param count       取得件数
         * @param filterQuery フィルター <code>FILTER_ALL</code> or <code>FILTER_ONLY_FOR_NOTIFICATION</code>
         * @return GetRequest
         */
        public GetRequest get(String count, String filterQuery) {
            HttpUrl url = getDefaultUrlBuilder()
                    .addEncodedPathSegment(PATH)
                    .addEncodedPathSegment(SEGMENT)
                    .addEncodedQueryParameter(QUERY_COUNT, count)
                    .addEncodedQueryParameter(QUERY_FILTER, filterQuery)
                    .build();

            Request req = getDefaultRequestBuilder()
                    .url(url)
                    .get()
                    .build();
            execute(req);

            if (!TextUtils.isEmpty(mResponseBodyText)) {
                Timber.d("post: code=" + mResponseStatusCode + " body=" + mResponseBodyText);
                mResponseObj = JsonUtil.fromJson(mResponseBodyText, GameResultList.class);
            }

            return this;
        }
    }
}
