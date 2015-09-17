package jp.gr.procon.proconapp.model;

import com.google.gson.annotations.SerializedName;

public class User {

    /** user id */
    @SerializedName("user_id")
    private Long mUserId;

    /** user token */
    @SerializedName("user_token")
    private String mUserToken;

    /** twitter id */
    @SerializedName("twitter_id")
    private Long mTwitterId;

    /** facebook id */
    @SerializedName("facebook_id")
    private Long mFacebookId;

    public Long getUserId() {
        return mUserId;
    }

    public String getUserToken() {
        return mUserToken;
    }

    public Long getTwitterId() {
        return mTwitterId;
    }

    public Long getFacebookId() {
        return mFacebookId;
    }
}
