package jp.gr.procon.proconapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import jp.gr.procon.proconapp.model.GamePhoto;
import jp.gr.procon.proconapp.model.GamePhotoList;
import jp.gr.procon.proconapp.ui.activity.GamePhotoListActivity;
import jp.gr.procon.proconapp.ui.activity.GameResultListActivity;
import jp.gr.procon.proconapp.ui.activity.NoticeListActivity;
import jp.gr.procon.proconapp.ui.adapter.HomeViewPagerAdapter;
import jp.gr.procon.proconapp.ui.fragment.GameResultOutlineFragment;
import jp.gr.procon.proconapp.ui.fragment.HomeFragment;
import jp.gr.procon.proconapp.ui.fragment.NoticeOutlineFragment;
import jp.gr.procon.proconapp.ui.fragment.PhotoOutlineFragment;

public class MainActivity extends BaseActivity implements
        NoticeOutlineFragment.OnShowAllNoticeClickListener
        , GameResultOutlineFragment.OnShowAllGameResultClickListener
        , PhotoOutlineFragment.OnShowAllGamePhotoClickListener {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager()));

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
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
}
