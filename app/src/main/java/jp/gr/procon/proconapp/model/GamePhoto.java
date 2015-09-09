package jp.gr.procon.proconapp.model;

import com.google.gson.annotations.SerializedName;

public class GamePhoto {

    @SerializedName("id")
    private Long mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("original_url")
    private String mOriginalUrl;

    @SerializedName("thumbnail_url")
    private String mThumbnailUrl;

    @SerializedName("created_at")
    private Long mCreatedAt;

    public Long getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmOriginalUrl() {
        return mOriginalUrl;
    }

    public String getmThumbnailUrl() {
        return mThumbnailUrl;
    }

    public Long getmCreatedAt() {
        return mCreatedAt;
    }

    @Override
    public String toString() {
        return "GamePhoto{" +
                "mId=" + mId +
                ", mTitle='" + mTitle + '\'' +
                ", mOriginalUrl='" + mOriginalUrl + '\'' +
                ", mThumbnailUrl='" + mThumbnailUrl + '\'' +
                ", mCreatedAt=" + mCreatedAt +
                '}';
    }
}
