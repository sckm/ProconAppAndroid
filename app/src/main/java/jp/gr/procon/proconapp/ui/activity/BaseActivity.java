package jp.gr.procon.proconapp.ui.activity;

import android.support.v7.app.AppCompatActivity;

import jp.gr.procon.proconapp.util.AppSharedPreference;

public class BaseActivity extends AppCompatActivity {

    /**
     * @return ユーザートークンが登録済みなら登録されているトークン、登録されていないならnull
     */
    protected String getUserToken() {
        return AppSharedPreference.getString(this, AppSharedPreference.PREFERENCE_USER_TOKEN);
    }
}
