package jp.gr.procon.proconapp.model;

import com.google.gson.annotations.SerializedName;

public class DeviceToken {
    private static final String DEVICE_TYPE_ANDROID = "android";

    @SerializedName("device_type")
    private String mDeviceType;

    @SerializedName("device_token")
    private String mDeviceToken;

    public DeviceToken(String deviceToken) {
        mDeviceType = DEVICE_TYPE_ANDROID;
        mDeviceToken = deviceToken;
    }
}
