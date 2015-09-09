package jp.gr.procon.proconapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SocialFeedTwitter implements Serializable {

    @SerializedName("statuses")
    private ArrayList<FeedTwitterStatus> mStatusList;

    public ArrayList<FeedTwitterStatus> getStatusList() {
        return mStatusList;
    }
}
