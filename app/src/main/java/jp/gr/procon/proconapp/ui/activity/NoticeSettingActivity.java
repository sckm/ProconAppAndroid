package jp.gr.procon.proconapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.ui.fragment.NoticeSettingFragment;
import jp.gr.procon.proconapp.ui.fragment.NoticeSettingFragment.OnCompleteNoticeSettingListener;

public class NoticeSettingActivity extends BaseActivity implements OnCompleteNoticeSettingListener {

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, NoticeSettingActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, NoticeSettingFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onCompleteNoticeSetting() {
        finish();
    }

    @Override
    public void onCancelNoticeSetting() {
        finish();
    }
}
