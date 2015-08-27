package jp.gr.procon.proconapp;

import android.content.Intent;
import android.os.Bundle;

import jp.gr.procon.proconapp.ui.activity.NoticeListActivity;
import jp.gr.procon.proconapp.ui.fragment.NoticeOutlineFragment;

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
        Intent intent = NoticeListActivity.createIntent(this);
        startActivity(intent);
    }
}
