package jp.gr.procon.proconapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PlayerResult implements Serializable {
    @SerializedName("player")
    private GamePlayer mPlayer;

    @SerializedName("score")
    private Long mScore;

    @SerializedName("rank")
    private Integer mRank;

    public GamePlayer getmPlayer() {
        return mPlayer;
    }

    public Long getmScore() {
        return mScore;
    }

    public Integer getmRank() {
        return mRank;
    }
}
