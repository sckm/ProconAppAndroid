package jp.gr.procon.proconapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class GameNotificationList implements Serializable {
    @SerializedName("ids")
    private ArrayList<Long> mIds;

    public GameNotificationList(ArrayList<Long> ids) {
        mIds = ids;
    }

    public ArrayList<Long> getIds() {
        return mIds;
    }
}
