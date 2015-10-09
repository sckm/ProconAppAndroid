package jp.gr.procon.proconapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Notice implements Serializable, Comparable<Notice> {

    @SerializedName("id")
    private Long mId;

    @SerializedName("title")
    private String mTitle;

    /** unix time(ms) */
    @SerializedName("published_at")
    private Long mPublishedAt;

    @SerializedName("body_size")
    private Long mBodySize;

    @SerializedName("priority")
    private Long mPriority;

    public Long getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public Long getPublishedAt() {
        return mPublishedAt * 1000;
    }

    public Long getBodySize() {
        return mBodySize;
    }

    public Long getPriority() {
        return mPriority;
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

    @Override
    public int compareTo(Notice another) {
        // 優先度が高い方をリストの先頭に
        if (another.getPriority().compareTo(this.getPriority()) == 0) {
            // 優先度が同じ場合は配信時間の遅い方をリストの先頭に
            if (another.getPublishedAt().compareTo(this.getPublishedAt()) == 0) {
                return another.getId().compareTo(this.getId());
            } else {
                // 優先度、配信時間が同じ時はIDが大きい方をリストの先頭に
                return another.getPublishedAt().compareTo(this.getPublishedAt());
            }
        } else {
            return another.getPriority().compareTo(this.getPriority());
        }
    }
}
