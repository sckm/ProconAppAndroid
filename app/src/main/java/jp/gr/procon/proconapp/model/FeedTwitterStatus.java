package jp.gr.procon.proconapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import jp.gr.procon.proconapp.model.twitter.Entities;
import jp.gr.procon.proconapp.model.twitter.Metadata;
import jp.gr.procon.proconapp.model.twitter.User;

// TODO 不必要な要素を除いたクラス作成
public class FeedTwitterStatus implements Serializable {
    @SerializedName("metadata")
    private Metadata mMetadata;

    @SerializedName("created_at")
    private String mCreatedAt;

    @SerializedName("id")
    private Long mId;

    @SerializedName("id_str")
    private String mIdStr;

    @SerializedName("text")
    private String mText;

    @SerializedName("source")
    private String mSource;

    @SerializedName("truncated")
    private Boolean mTruncated;

    @SerializedName("in_reply_to_status_id")
    private Long mInReplyToStatusId;

    @SerializedName("in_reply_to_status_id_str")
    private String mInReplyToStatusIdStr;

    @SerializedName("in_reply_to_user_id")
    private Long mInReplyToUserId;

    @SerializedName("in_reply_to_user_id_str")
    private String mInReplyToUserIdStr;

    @SerializedName("in_reply_to_screen_name")
    private String mInReplyToScreenName;

    @SerializedName("user")
    private User mUser;

    @SerializedName("geo")
    private String mGeo;

    @SerializedName("coordinates")
    private String mCoordinates;

    @SerializedName("place")
    private String mPlace;

    @SerializedName("contributors")
    private String mContributors;

    @SerializedName("is_quote_status")
    private Boolean mIsQuoteStatus;

    @SerializedName("retweet_count")
    private Integer mRetweetCount;

    @SerializedName("favorite_count")
    private Integer mFavoriteCount;

    @SerializedName("entities")
    private Entities mEntities;

    @SerializedName("favorited")
    private Boolean mFavorited;

    @SerializedName("retweeted")
    private Boolean mRetweeted;

    @SerializedName("lang")
    private String mLang;


    public Metadata getMetadata() {
        return mMetadata;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public Long getId() {
        return mId;
    }

    public String getIdStr() {
        return mIdStr;
    }

    public String getText() {
        return mText;
    }

    public String getSource() {
        return mSource;
    }

    public Boolean getTruncated() {
        return mTruncated;
    }

    public Long getInReplyToStatusId() {
        return mInReplyToStatusId;
    }

    public String getInReplyToStatusIdStr() {
        return mInReplyToStatusIdStr;
    }

    public Long getInReplyToUserId() {
        return mInReplyToUserId;
    }

    public String getInReplyToUserIdStr() {
        return mInReplyToUserIdStr;
    }

    public String getInReplyToScreenName() {
        return mInReplyToScreenName;
    }

    public User getUser() {
        return mUser;
    }

    public String getGeo() {
        return mGeo;
    }

    public String getCoordinates() {
        return mCoordinates;
    }

    public String getPlace() {
        return mPlace;
    }

    public String getContributors() {
        return mContributors;
    }

    public Boolean getIsQuoteStatus() {
        return mIsQuoteStatus;
    }

    public Integer getRetweetCount() {
        return mRetweetCount;
    }

    public Integer getFavoriteCount() {
        return mFavoriteCount;
    }

    public Entities getEntities() {
        return mEntities;
    }

    public Boolean getFavorited() {
        return mFavorited;
    }

    public Boolean getRetweeted() {
        return mRetweeted;
    }

    public String getLang() {
        return mLang;
    }
}
