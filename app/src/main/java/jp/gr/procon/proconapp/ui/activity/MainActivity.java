package jp.gr.procon.proconapp.ui.activity;

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
import jp.gr.procon.proconapp.model.User;
import jp.gr.procon.proconapp.ui.adapter.HomeViewPagerAdapter;
import jp.gr.procon.proconapp.ui.fragment.GameResultOutlineFragment;
import jp.gr.procon.proconapp.ui.fragment.NoticeOutlineFragment;
import jp.gr.procon.proconapp.ui.fragment.PhotoOutlineFragment;
import jp.gr.procon.proconapp.util.AppSharedPreference;

public class MainActivity extends BaseActivity implements
        NoticeOutlineFragment.OnShowAllNoticeClickListener
        , GameResultOutlineFragment.OnShowAllGameResultClickListener
        , PhotoOutlineFragment.OnShowAllGamePhotoClickListener {

    private final WeakReference<MainActivity> wkRef = new WeakReference<>(this);

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private AuthApiAsyncTask mAuthApiAsyncTask;

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

        String userToken = getUserToken();
        if (TextUtils.isEmpty(userToken)) {
            mAuthApiAsyncTask = new AuthApiAsyncTask();
            mAuthApiAsyncTask.execute();
        } else if (mViewPager.getAdapter() == null) {
            setupView();
        }
    }

    @Override
    protected void onPause() {
        if (mAuthApiAsyncTask != null) {
            mAuthApiAsyncTask.cancel(true);
            mAuthApiAsyncTask = null;
        }
        super.onPause();
    }

    private void setupView() {
        mViewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
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

    // TODO fragment内で行う
    private class AuthApiAsyncTask extends AsyncTask<Void, Void, BaseApi<User>> {

        @Override
        protected BaseApi<User> doInBackground(Void... params) {
            return new AuthApi.PostNewUser().post();
        }

        @Override
        protected void onPostExecute(BaseApi<User> api) {
            super.onPostExecute(api);
            MainActivity activity = wkRef.get();
            if (isCancelled() || activity == null) {
                return;
            }

            if (api.isSuccessful()) {
                AppSharedPreference.putString(activity,
                        AppSharedPreference.PREFERENCE_USER_TOKEN, api.getResponseObj().getUserToken());
                setupView();
            } else {
                // TODO error
            }


        }
    }
}
