package jp.gr.procon.proconapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Notice implements Serializable {

    @SerializedName("id")
    private Long mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("published_at")
    private Long mPublishedAt;

    @SerializedName("body_size")
    private Long mBodySize;

    public Long getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public Long getmPublishedAt() {
        return mPublishedAt;
    }

    public Long getmBodySize() {
        return mBodySize;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "mId=" + mId +
                ", mTitle='" + mTitle + '\'' +
                ", mPublishedAt=" + mPublishedAt +
                ", mBodySize=" + mBodySize +
                '}';
    }
}
