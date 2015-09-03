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

}
