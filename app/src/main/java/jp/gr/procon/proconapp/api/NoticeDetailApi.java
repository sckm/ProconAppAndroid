package jp.gr.procon.proconapp.api;

import android.text.TextUtils;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Request;

import jp.gr.procon.proconapp.model.NoticeDetail;
import jp.gr.procon.proconapp.model.NoticeList;
import jp.gr.procon.proconapp.util.JsonUtil;
import timber.log.Timber;

public class NoticeDetailApi {
    public static class GetRequest extends BaseUserApi<NoticeDetail> {
        private static final String PATH = "notices";
        private static final String SEGMENT = "info";
        private static final String QUERY_ID = "id";

        public GetRequest(String userToken) {
            super(userToken);
        }

        public GetRequest get(String newsId) {
            HttpUrl url = getDefaultUrlBuilder()
                    .addEncodedPathSegment(PATH)
                    .addEncodedPathSegment(SEGMENT)
                    .addEncodedQueryParameter(QUERY_ID, newsId)
                    .build();

            Request req = getDefaultRequestBuilder()
                    .url(url)
                    .get()
                    .build();
            execute(req);

            if (!TextUtils.isEmpty(mResponseBodyText)) {
                Timber.d("post: code=" + mResponseStatusCode + " body=" + mResponseBodyText);
                mResponseObj = JsonUtil.fromJson(mResponseBodyText, NoticeDetail.class);
            }

            return this;
        }
    }
}
