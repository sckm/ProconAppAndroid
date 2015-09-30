package jp.gr.procon.proconapp.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.event.BusHolder;
import jp.gr.procon.proconapp.event.RequestUpdateEvent;

public class HomeFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener
        , NoticeOutlineFragment.OnUpdateNoticeOutlineListener
        , GameResultOutlineFragment.OnUpdateGameResultOutlineListener
        , PhotoOutlineFragment.OnUpdatePhotoOutlineListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean mUpdatedNotice;
    private boolean mUpdatedGameResult;
    private boolean mUpdatedPhoto;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.indicator_red,
                R.color.indicator_green,
                R.color.indicator_blue,
                R.color.indicator_orange);

        if (savedInstanceState == null) {
            getChildFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_notice, NoticeOutlineFragment.newInstance())
                    .add(R.id.container_game_result, GameResultOutlineFragment.newInstance())
                    .add(R.id.container_photo, PhotoOutlineFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onStop() {
        mSwipeRefreshLayout.setRefreshing(false);
        super.onStop();
    }

    @Override
    public void onRefresh() {
        mUpdatedNotice = false;
        mUpdatedGameResult = false;
        mUpdatedPhoto = false;
        BusHolder.getInstance().post(new RequestUpdateEvent());
    }

    @Override
    public void OnCompleteNoticeOutlineUpdate() {
        mUpdatedNotice = true;
        finishRefreshIfFinish();
    }

    @Override
    public void OnCompleteGameResultOutlineUpdate() {
        mUpdatedGameResult = true;
        finishRefreshIfFinish();
    }

    @Override
    public void OnCompletePhotoOutlineUpdate() {
        mUpdatedPhoto = true;
        finishRefreshIfFinish();
    }

    private void finishRefreshIfFinish() {
        if (mUpdatedNotice && mUpdatedGameResult && mUpdatedPhoto) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}