package jp.gr.procon.proconapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.microsoft.windowsazure.messaging.NotificationHub;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.ui.fragment.NoticeSettingFragment;
import jp.gr.procon.proconapp.ui.fragment.RegisterUserFragment;
import jp.gr.procon.proconapp.ui.fragment.WelcomeFragment;
import jp.gr.procon.proconapp.util.AppSharedPreference;
import timber.log.Timber;

public class EnterActivity extends BaseActivity implements
        View.OnClickListener
        , WelcomeFragment.OnClickEnterButtonListener
        , NoticeSettingFragment.OnCompleteNoticeSettingListener
        , RegisterUserFragment.OnCompleteRegisterUserListener {

    private boolean mClickedEnterButton;
    private String mUserToken;


    public static Intent createIntent(Context context) {
        return new Intent(context, EnterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mUserToken = getUserToken();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, WelcomeFragment.newInstance())
                    .add(R.id.container, RegisterUserFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = MainActivity.createIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClickEnterButton() {
        mClickedEnterButton = true;
        if (mClickedEnterButton && !TextUtils.isEmpty(mUserToken)) {
            replaceWithNotificationSetting();
        }
    }

    @Override
    public void onCompleteNoticeSetting() {
        // TODO booleanで保存できるようにする
        // 初回起動か判定するために文字列保存
        AppSharedPreference.putString(this, AppSharedPreference.PREFERENCE_IS_FIRST_LAUNCH, "something");
        Intent intent = MainActivity.createIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void onCancelNoticeSetting() {

    }

    @Override
    public void onCompleteRegisterUser(String token) {
        Timber.d("onCompleteRegisterUser: " + token);
        mUserToken = token;
        if (mClickedEnterButton && !TextUtils.isEmpty(mUserToken)) {
            replaceWithNotificationSetting();
        }
    }

    private void replaceWithNotificationSetting() {
        setTitle(R.string.title_setting_notice);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, NoticeSettingFragment.newInstance(false, true))
                .commit();
    }
}
