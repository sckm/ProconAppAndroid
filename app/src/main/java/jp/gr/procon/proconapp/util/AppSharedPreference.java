package jp.gr.procon.proconapp.util;

import android.content.Context;

public class AppSharedPreference {
    private static final String FILE_NAME = "procon_pref";

    public static final String PREFERENCE_USER_TOKEN = "preference_user_token";
    public static final String PREFERENCE_GCM_REGISTER_ID = "preference_gcm_register_id";

    public static void putString(Context context, String key, String value) {
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(key, value)
                .apply();
    }

    public static String getString(Context context, String key) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                .getString(key, null);
    }
}
