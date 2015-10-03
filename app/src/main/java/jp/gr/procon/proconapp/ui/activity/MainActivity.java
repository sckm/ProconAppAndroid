package jp.gr.procon.proconapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import java.lang.ref.WeakReference;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.api.AuthApi;
import jp.gr.procon.proconapp.api.BaseApi;
import jp.gr.procon.proconapp.model.Notice;
import jp.gr.procon.proconapp.model.User;
import jp.gr.procon.proconapp.ui.adapter.HomeViewPagerAdapter;
import jp.gr.procon.proconapp.ui.callback.OnNoticeClickListener;
import jp.gr.procon.proconapp.ui.fragment.GameResultOutlineFragment;
import jp.gr.procon.proconapp.ui.fragment.NoticeOutlineFragment;
import jp.gr.procon.proconapp.ui.fragment.PhotoOutlineFragment;
import jp.gr.procon.proconapp.ui.fragment.RegisterTokenFragment;
import jp.gr.procon.proconapp.util.AppSharedPreference;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements
        NoticeOutlineFragment.OnShowAllNoticeClickListener
        , GameResultOutlineFragment.OnShowAllGameResultClickListener
        , PhotoOutlineFragment.OnShowAllGamePhotoClickListener
        , OnNoticeClickListener {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();

        replaceRegisterFragment();
        if (mViewPager.getAdapter() == null) {
            setupView();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void setupView() {
        mViewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void replaceRegisterFragment() {
        if (TextUtils.isEmpty(AppSharedPreference.getString(this, AppSharedPreference.PREFERENCE_USER_TOKEN))) {
            return;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, RegisterTokenFragment.newInstance())
                .commit();
    }

    @Override
    public void onShowAllGameResultClick() {
        Intent intent = GameResultListActivity.createIntent(this);
        startActivity(intent);
    }

    @Override
    public void onShowAllNoticeClick() {
        Intent intent = NoticeListActivity.createIntent(this);
        startActivity(intent);
    }

    public void onShowAllGamePhotoClick() {
        Intent intent = GamePhotoListActivity.createIntent(this);
        startActivity(intent);
    }

    @Override
    public void onNoticeClick(Notice notice) {
        Timber.d("onNoticeClick: ");
        Intent intent = NoticeDetailActivity.createIntent(this, notice.getId());
        startActivity(intent);
    }
}
