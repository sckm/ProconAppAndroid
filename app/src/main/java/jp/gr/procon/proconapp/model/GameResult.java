package jp.gr.procon.proconapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class GameResult implements Serializable {
    @SerializedName("id")
    private Long mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("status")
    private Long mStatus;

    @SerializedName("result")
    private ArrayList<PlayerResult> mResult;

    @SerializedName("started_at")
    private Long mStartedAt;

    @SerializedName("finished_at")
    private Long mFinishedAt;

    public Long getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public Long getStatus() {
        return mStatus;
    }

    public ArrayList<PlayerResult> getResult() {
        return mResult;
    }

    public Long getStartedAt() {
        return mStartedAt;
    }

    public Long getFinishedAt() {
        return mFinishedAt;
    }
}
