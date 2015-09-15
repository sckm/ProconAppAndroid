package jp.gr.procon.proconapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class GameResult implements Serializable {
    public static final int STATUS_GAME_ENDED = 0;
    public static final int STATUS_GAME_PROGRESS = 1;

    @SerializedName("id")
    private Long mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("status")
    private Integer mStatus;

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

    public Integer getStatus() {
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
