package jp.gr.procon.proconapp.model.twitter;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("id")
    private Long mId;

    @SerializedName("id_str")
    private String mIdStr;

    @SerializedName("name")
    private String mName;

    @SerializedName("screen_name")
    private String mScreenName;

    @SerializedName("location")
    private String mLocation;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("url")
    private String mUrl;

    @SerializedName("entities")
    private Entities mEntities;

    @SerializedName("protected")
    private Boolean mProtected;

    @SerializedName("followers_count")
    private Long mFollowersCount;

    @SerializedName("friends_count")
    private Long mFriendsCount;

    @SerializedName("listed_count")
    private Long mListedCount;

    @SerializedName("created_at")
    private String mCreatedAt;

    @SerializedName("favourites_count")
    private Long mFavouritesCount;

    @SerializedName("utc_offset")
    private String mUtcOffset;

    @SerializedName("time_zone")
    private String mTimeZone;

    @SerializedName("geo_enabled")
    private Boolean mGeoEnabled;

    @SerializedName("verified")
    private Boolean mVerified;

    @SerializedName("statuses_count")
    private Long mStatusesCount;

    @SerializedName("lang")
    private String mLang;

    @SerializedName("contributors_enabled")
    private Boolean mContributorsEnabled;

    @SerializedName("is_translator")
    private Boolean mIsTranslator;

    @SerializedName("is_translation_enabled")
    private Boolean mIsTranslationEnabled;

    @SerializedName("profile_background_color")
    private String mProfileBackgroundColor;

    @SerializedName("profile_background_image_url")
    private String mProfileBackgroundBmageUrl;

    @SerializedName("profile_background_image_url_https")
    private String mProfileBackgroundImageUrlHttps;

    @SerializedName("profile_background_tile")
    private Boolean mProfileBackgroundTile;

    @SerializedName("profile_image_url")
    private String mProfileImageUrl;

    @SerializedName("profile_image_url_https")
    private String mProfileImageUrlHttps;

    @SerializedName("profile_banner_url")
    private String mProfileBannerUrl;

    @SerializedName("profile_link_color")
    private String mProfileLinkColor;

    @SerializedName("profile_sidebar_border_color")
    private String mProfileSidebarBorderColor;

    @SerializedName("profile_sidebar_fill_color")
    private String mProfileSidebarFillColor;

    @SerializedName("profile_text_color")
    private String mProfileTextColor;

    @SerializedName("profile_use_background_image")
    private Boolean mProfileUseBackgroundImage;

    @SerializedName("has_extended_profile")
    private Boolean mHasExtendedProfile;

    @SerializedName("default_profile")
    private Boolean mDefaultProfile;

    @SerializedName("default_profile_image")
    private Boolean mDefaultProfileImage;

    @SerializedName("following")
    private Boolean mFollowing;

    @SerializedName("follow_request_sent")
    private Boolean mFollowRequestSent;

    @SerializedName("notifications")
    private Boolean mNotifications;

    public Long getId() {
        return mId;
    }

    public String getIdStr() {
        return mIdStr;
    }

    public String getName() {
        return mName;
    }

    public String getScreenName() {
        return mScreenName;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getUrl() {
        return mUrl;
    }

    public Entities getEntities() {
        return mEntities;
    }

    public Boolean getProtected() {
        return mProtected;
    }

    public Long getFollowersCount() {
        return mFollowersCount;
    }

    public Long getFriendsCount() {
        return mFriendsCount;
    }

    public Long getListedCount() {
        return mListedCount;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public Long getFavouritesCount() {
        return mFavouritesCount;
    }

    public String getUtcOffset() {
        return mUtcOffset;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public Boolean getGeoEnabled() {
        return mGeoEnabled;
    }

    public Boolean getVerified() {
        return mVerified;
    }

    public Long getStatusesCount() {
        return mStatusesCount;
    }

    public String getLang() {
        return mLang;
    }

    public Boolean getContributorsEnabled() {
        return mContributorsEnabled;
    }

    public Boolean getIsTranslator() {
        return mIsTranslator;
    }

    public Boolean getIsTranslationEnabled() {
        return mIsTranslationEnabled;
    }

    public String getProfileBackgroundColor() {
        return mProfileBackgroundColor;
    }

    public String getProfileBackgroundBmageUrl() {
        return mProfileBackgroundBmageUrl;
    }

    public String getProfileBackgroundImageUrlHttps() {
        return mProfileBackgroundImageUrlHttps;
    }

    public Boolean getProfileBackgroundTile() {
        return mProfileBackgroundTile;
    }

    public String getProfileImageUrl() {
        return mProfileImageUrl;
    }

    public String getProfileImageUrlHttps() {
        return mProfileImageUrlHttps;
    }

    public String getProfileBannerUrl() {
        return mProfileBannerUrl;
    }

    public String getProfileLinkColor() {
        return mProfileLinkColor;
    }

    public String getProfileSidebarBorderColor() {
        return mProfileSidebarBorderColor;
    }

    public String getProfileSidebarFillColor() {
        return mProfileSidebarFillColor;
    }

    public String getProfileTextColor() {
        return mProfileTextColor;
    }

    public Boolean getProfileUseBackgroundImage() {
        return mProfileUseBackgroundImage;
    }

    public Boolean getHasExtendedProfile() {
        return mHasExtendedProfile;
    }

    public Boolean getDefaultProfile() {
        return mDefaultProfile;
    }

    public Boolean getDefaultProfileImage() {
        return mDefaultProfileImage;
    }

    public Boolean getFollowing() {
        return mFollowing;
    }

    public Boolean getFollowRequestSent() {
        return mFollowRequestSent;
    }

    public Boolean getNotifications() {
        return mNotifications;
    }
}
