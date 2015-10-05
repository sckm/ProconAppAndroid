package jp.gr.procon.proconapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.ui.fragment.WebFragment;
import timber.log.Timber;

public class AccessActivity extends BaseActivity implements WebFragment.OnLoadUrlListener {
    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, AccessActivity.class);
        return intent;
    }

    private static final String MAP_URL_PREFIX = "https://www.google.co.jp/maps/place";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout);

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

    @Override
    public boolean onLoadUrl(String url) {
        if (url.startsWith(MAP_URL_PREFIX)) {
            Uri gmmIntentUri = Uri.parse("geo:36.6356678,138.1867552?q=" +
                    Uri.encode("〒380-0928 長野県長野市若里１丁目１−３"));
            Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            intent.setPackage("com.google.android.apps.maps");
            if (intent.resolveActivity(getPackageManager()) == null) {
                // TODO error google mapアプリが無い
                return false;
            }
            startActivity(intent);
            return true;
        }
        return false;
    }
}
