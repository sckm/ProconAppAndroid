package jp.gr.procon.proconapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.model.Notice;
import jp.gr.procon.proconapp.ui.callback.OnNoticeClickListener;
import jp.gr.procon.proconapp.ui.fragment.NoticeListFragment;

public class NoticeListActivity extends BaseActivity implements OnNoticeClickListener {

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, NoticeListActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, NoticeListFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onNoticeClick(Notice notice) {
        Intent intent = NoticeDetailActivity.createIntent(this, notice.getId());
        startActivity(intent);
    }
}
