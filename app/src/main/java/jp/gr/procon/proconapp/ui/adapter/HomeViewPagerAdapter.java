package jp.gr.procon.proconapp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import jp.gr.procon.proconapp.ui.fragment.HomeFragment;
import jp.gr.procon.proconapp.ui.fragment.TwitterFeedFragment;

public class HomeViewPagerAdapter extends FragmentPagerAdapter {

    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return HomeFragment.newInstance();

            case 1:
                return TwitterFeedFragment.newInstance();

            default:
                throw new RuntimeException("unknown pager position");
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // TODO title
        switch (position) {
            case 0:
                return "ホーム";
            case 1:
                return "ソーシャル";
            default:
                return super.getPageTitle(position);
        }
    }
}
