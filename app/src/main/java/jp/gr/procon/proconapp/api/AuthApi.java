package jp.gr.procon.proconapp.api;

import android.text.TextUtils;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import jp.gr.procon.proconapp.model.User;
import jp.gr.procon.proconapp.util.JsonUtil;
import timber.log.Timber;

public class AuthApi {
    public static class PostNewUser extends BaseApi<User> {
        private static final String PATH = "auth";
        private static final String SEGMENT = "new_user";

        public PostNewUser post() {
            HttpUrl url = getDefaultUrlBuilder()
                    .addEncodedPathSegment(PATH)
                    .addEncodedPathSegment(SEGMENT)
                    .build();

            RequestBody body = RequestBody.create(MEDIA_TYPE_TEXT_PLAIN, "");
            Request req = getDefaultRequestBuilder()
                    .url(url)
                    .post(body)
                    .build();
            Timber.d("post: " + req.urlString());
            execute(req);

            if (!TextUtils.isEmpty(mResponseBodyText)) {
                Timber.d("post: code=" + mResponseStatusCode + " body=" + mResponseBodyText);
                mResponseObj = JsonUtil.fromJson(mResponseBodyText, User.class);
            }

            return this;
        }
    }
}
