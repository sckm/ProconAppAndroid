package jp.gr.procon.proconapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.ui.fragment.WebFragment;

public class ProgramActivity extends BaseActivity implements WebFragment.OnLoadUrlListener {
    private static final String ARG_URL = "arg_url";

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, ProgramActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout);

        // TODO 時間があればnativeのview作成
        String url = getString(R.string.url_program);
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
        MenuItem program = menu.findItem(R.id.action_program);
        if (program != null) {
            program.setVisible(false);
            ret = true;
        }
        return ret;
    }

    @Override
    public boolean onLoadUrl(String url) {
        if (url.endsWith(".pdf")) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse(url);
            intent.setData(uri);
            startActivity(intent);
            return true;
        }
        return false;
    }
}
