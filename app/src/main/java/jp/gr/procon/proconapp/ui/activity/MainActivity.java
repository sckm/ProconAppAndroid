package jp.gr.procon.proconapp.ui.activity;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import jp.gr.procon.proconapp.GoogleAnalyticsConfig;
import jp.gr.procon.proconapp.ProconApplication;
import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.model.FeedTwitterStatus;
import jp.gr.procon.proconapp.model.Notice;
import jp.gr.procon.proconapp.model.twitter.User;
import jp.gr.procon.proconapp.notification.NotificationConfig;
import jp.gr.procon.proconapp.ui.adapter.HomeViewPagerAdapter;
import jp.gr.procon.proconapp.ui.callback.OnNoticeClickListener;
import jp.gr.procon.proconapp.ui.fragment.GameResultOutlineFragment;
import jp.gr.procon.proconapp.ui.fragment.NoticeOutlineFragment;
import jp.gr.procon.proconapp.ui.fragment.PhotoOutlineFragment;
import jp.gr.procon.proconapp.ui.fragment.RegisterTokenFragment;
import jp.gr.procon.proconapp.ui.fragment.TwitterFeedFragment;
import jp.gr.procon.proconapp.util.AppSharedPreference;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements
        NoticeOutlineFragment.OnShowAllNoticeClickListener
        , GameResultOutlineFragment.OnShowAllGameResultClickListener
        , PhotoOutlineFragment.OnShowAllGamePhotoClickListener
        , OnNoticeClickListener
        , TwitterFeedFragment.OnClickTweetListener
        , ViewPager.OnPageChangeListener {
    private static final String ARG_FROM_NOTIFICATION = "arg_from_notification";

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    public static Intent createIntent(Context context) {
        return createIntent(context, false);
    }

    public static Intent createIntent(Context context, boolean fromNotification) {
        Intent intent = new Intent(context, MainActivity.class);
        Bundle args = new Bundle();
        args.putBoolean(ARG_FROM_NOTIFICATION, fromNotification);
        intent.putExtras(args);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 通知からきていた場合 通知を削除
        Bundle args = getIntent().getExtras();
        boolean isFromNotification = false;
        if (args != null) {
            isFromNotification = args.getBoolean(ARG_FROM_NOTIFICATION, false);
        }
        if (isFromNotification) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
            notificationManager.cancel(NotificationConfig.NOTIFICATION_ID_MAIN);
        }

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.addOnPageChangeListener(this);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if (mViewPager.getAdapter() == null) {
            setupView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        replaceRegisterFragment();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void setupView() {
        mViewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
        // 最初にアイテムが選択される際にonPageSelectedが呼び出されないので呼んでおく
        onPageSelected(0);
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

    @Override
    public void onClickTweet(FeedTwitterStatus tweet) {
        if (tweet == null || tweet.getUser() == null) {
            return;
        }
        // ツイート詳細を表示
        Intent twitterIntent = new Intent(Intent.ACTION_VIEW);
        User user = tweet.getUser();
        String formatTweetUrl = "https://twitter.com/%1$s/status/%2$s";
        Uri httpUri = Uri.parse(String.format(formatTweetUrl, user.getScreenName(), tweet.getIdStr()));
        twitterIntent.setData(httpUri);
        startActivity(twitterIntent);
    }

    @Override
    public void onClickPostTweet() {
        String hashTag = "#" + getString(R.string.twitter_hash_tag);
        Uri uri = Uri.parse("twitter://post?message=" + Uri.encode(hashTag));
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Intent httpTweetIntent = new Intent(Intent.ACTION_VIEW);
            String formatHttpUrl = "http://twitter.com/share?text=%1$s";
            Uri httpUri = Uri.parse(String.format(formatHttpUrl, Uri.encode(hashTag)));
            httpTweetIntent.setData(httpUri);
            startActivity(httpTweetIntent);
        }
    }

    @Override
    public void onClickMoreButton() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("https://twitter.com/hashtag/" + getString(R.string.twitter_hash_tag));
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        sendAnalyticsWithScreenName(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private void sendAnalyticsWithScreenName(int position) {
        Tracker tracker = ((ProconApplication) getApplication()).getTracker();
        String screenName = position == 0
                ? GoogleAnalyticsConfig.SCREEN_NAME_HOME
                : GoogleAnalyticsConfig.SCREEN_NAME_SOCIAL;
        tracker.setScreenName(screenName);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
