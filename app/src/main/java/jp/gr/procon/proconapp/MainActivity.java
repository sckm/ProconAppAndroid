package jp.gr.procon.proconapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.dummymodel.DummyNotice;
import jp.gr.procon.proconapp.model.NoticeList;
import jp.gr.procon.proconapp.ui.NoticeOutlineFragment;
import jp.gr.procon.proconapp.util.JsonUtil;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements NoticeOutlineFragment.OnShowAllNoticeClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_notice, NoticeOutlineFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onShowAllNoticeClick() {

    }
}
