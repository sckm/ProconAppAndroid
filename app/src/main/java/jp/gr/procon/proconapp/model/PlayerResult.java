package jp.gr.procon.proconapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerResult implements Serializable, Comparable<PlayerResult> {
    @SerializedName("player")
    private GamePlayer mPlayer;

    @SerializedName("score")
    private Long mScore;

    @SerializedName("scores")
    private ArrayList<Long> mScores;

    @SerializedName("advance")
    private boolean mAdvance;

    @SerializedName("rank")
    private Integer mRank;

    public GamePlayer getPlayer() {
        return mPlayer;
    }

    public Long getScore() {
        return mScore;
    }

    public ArrayList<Long> getScores() {
        return mScores;
    }

    public boolean isAdvance() {
        return mAdvance;
    }

    public Integer getRank() {
        return mRank;
    }

    @Override
    public int compareTo(PlayerResult another) {
        return this.getRank() - another.getRank();
    }
}
