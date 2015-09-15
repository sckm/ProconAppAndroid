package jp.gr.procon.proconapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PlayerResult implements Serializable, Comparable<PlayerResult> {
    @SerializedName("player")
    private GamePlayer mPlayer;

    @SerializedName("score")
    private Long mScore;

    @SerializedName("rank")
    private Integer mRank;

    public GamePlayer getPlayer() {
        return mPlayer;
    }

    public Long getScore() {
        return mScore;
    }

    public Integer getRank() {
        return mRank;
    }

    @Override
    public int compareTo(PlayerResult another) {
        return this.getRank() - another.getRank();
    }
}
