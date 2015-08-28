package jp.gr.procon.proconapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GamePlayer implements Serializable {
    @SerializedName("id")
    private Long mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("short_name")
    private String mShortName;

    public Long getmId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }

    public String getmShortName() {
        return mShortName;
    }
}
