package jp.gr.procon.proconapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.ui.fragment.WebFragment;

public class AccessActivity extends BaseActivity {
    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, AccessActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout);

        // TODO 時間があればnativeのview作成
        String url = getString(R.string.url_access);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, WebFragment.newInstance(url))
                    .commit();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean ret = super.onPrepareOptionsMenu(menu);
        MenuItem access = menu.findItem(R.id.action_access);
        if (access != null) {
            access.setVisible(false);
            ret = true;
        }
        return ret;
    }
}
