package jp.gr.procon.proconapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NoticeDetail implements Serializable {
    @SerializedName("id")
    private Long mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("published_at")
    private Long mPublishedAt;

    @SerializedName("body")
    private String mBody;

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

    public String getBody() {
        return mBody;
    }

    public Long getmBodySize() {
        return mBodySize;
    }
}
