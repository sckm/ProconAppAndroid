package jp.gr.procon.proconapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.util.AppSharedPreference;

public class SplashActivity extends AppCompatActivity {
    private static final int DELAY_TIME = 2000;

    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 前回登録したGCMのregister idの情報を削除
        // 本来なら起動時に前回との差分を見て異なった場合だけAPIサーバーへ登録する方が良い
        // 今回はとりあえず起動ごとにAPIサーバーへ投げるようにするためにリセット
        AppSharedPreference.putString(this, AppSharedPreference.PREFERENCE_GCM_REGISTER_ID, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mHandler == null) {
            mHandler = new Handler();
        }
        boolean isFirstLaunch = TextUtils.isEmpty(AppSharedPreference.getString(this, AppSharedPreference.PREFERENCE_IS_FIRST_LAUNCH));
        mRunnable = isFirstLaunch ? new FirstLaunchRunnable() : new HomeLaunchRunnable();
        mHandler.postDelayed(mRunnable, DELAY_TIME);
    }

    @Override
    protected void onPause() {
        mHandler.removeCallbacks(mRunnable);
        super.onPause();
    }

    private class HomeLaunchRunnable implements Runnable {
        @Override
        public void run() {
            Intent intent = MainActivity.createIntent(getBaseContext());
            startActivity(intent);
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
            finish();
        }
    }

    private class FirstLaunchRunnable implements Runnable {

        @Override
        public void run() {
            Intent intent = EnterActivity.createIntent(getBaseContext());
            startActivity(intent);
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
            finish();
        }
    }

}
