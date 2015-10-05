package jp.gr.procon.proconapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.ui.fragment.SettingFragment;

public class SettingActivity extends BaseActivity implements SettingFragment.OnClickSettingItemListener {
    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, SettingFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_settings);
        if (item != null) {
            item.setVisible(false);
            return true;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onClickNoticeSetting() {
        Intent intent = NoticeSettingActivity.createIntent(this);
        startActivity(intent);
    }

    @Override
    public void onClickLicense() {
        Intent intent = LicenseActivity.createIntent(this);
        startActivity(intent);
    }
}
