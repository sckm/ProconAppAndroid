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

    public Long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getShortName() {
        return mShortName;
    }
}
