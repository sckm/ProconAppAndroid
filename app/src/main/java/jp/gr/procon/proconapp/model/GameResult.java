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

    public Long getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public Long getmStatus() {
        return mStatus;
    }

    public ArrayList<PlayerResult> getmResult() {
        return mResult;
    }
}
