package jp.gr.procon.proconapp.api;

import android.text.TextUtils;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import jp.gr.procon.proconapp.model.Notice;
import jp.gr.procon.proconapp.model.NoticeList;
import jp.gr.procon.proconapp.model.User;
import jp.gr.procon.proconapp.util.JsonUtil;
import timber.log.Timber;

public class NoticeListApi {
    public static class GetRequest extends BaseUserApi<NoticeList> {
        private static final String PATH = "notices";
        private static final String SEGMENT = "list";
        private static final String QUERY_PAGE = "page";
        private static final String QUERY_COUNT = "count";

        public GetRequest(String userToken) {
            super(userToken);
        }

        public GetRequest get(String page, String count) {
            HttpUrl url = getDefaultUrlBuilder()
                    .addEncodedPathSegment(PATH)
                    .addEncodedPathSegment(SEGMENT)
                    .addEncodedQueryParameter(QUERY_PAGE, page)
                    .addEncodedQueryParameter(QUERY_COUNT, count)
                    .build();

            Request req = getDefaultRequestBuilder()
                    .url(url)
                    .get()
                    .build();
            execute(req);

            if (!TextUtils.isEmpty(mResponseBodyText)) {
                Timber.d("post: code=" + mResponseStatusCode + " body=" + mResponseBodyText);
                mResponseObj = JsonUtil.fromJson(mResponseBodyText, NoticeList.class);
            }

            return this;
        }
    }
}
