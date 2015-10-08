package jp.gr.procon.proconapp.ui.fragment;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.api.SocialTwitterApi;
import jp.gr.procon.proconapp.model.FeedTwitterStatus;
import jp.gr.procon.proconapp.model.PageApiState;
import jp.gr.procon.proconapp.ui.adapter.TwitterFeedRecyclerAdapter;
import jp.gr.procon.proconapp.ui.view.DividerItemDecoration;
import timber.log.Timber;

public class TwitterFeedFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final int API_MAX_COUNT = 50;

    public interface OnClickTweetListener {
        void onClickTweet(FeedTwitterStatus tweet);

        void onClickPostTweet();

        void onClickMoreButton();
    }

    public static TwitterFeedFragment newInstance() {
        TwitterFeedFragment fragment = new TwitterFeedFragment();
        return fragment;
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private TwitterFeedRecyclerAdapter mAdapter;
    private ArrayList<FeedTwitterStatus> mTweetList;

    private SocialTwitterApiAsyncTask mSocialTwitterApiAsyncTask;
    private OnClickTweetListener mOnClickTweetListener;

    public TwitterFeedFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTweetList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_refreshable_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            // TODO save
        }

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.indicator_red,
                R.color.indicator_green,
                R.color.indicator_blue,
                R.color.indicator_orange);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new TwitterFeedRecyclerAdapter(mTweetList);
        mAdapter.setOnClickTweetListener(mOnClickTweetListener);
        mAdapter.setOnClickMoreButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickTweetListener != null) {
                    mOnClickTweetListener.onClickMoreButton();
                }
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floating_action_button);
        fab.setVisibility(View.VISIBLE);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.background_fab_gray_blue)));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickTweetListener != null) {
                    mOnClickTweetListener.onClickPostTweet();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mTweetList.size() == 0) {
            startApiAsyncTask();
        }
    }

    @Override
    public void onPause() {
        stopApiAsyncTask();
        mSwipeRefreshLayout.setRefreshing(false);
        super.onPause();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Fragment parent = getParentFragment();
        if (parent != null && parent instanceof OnClickTweetListener) {
            mOnClickTweetListener = (OnClickTweetListener) parent;
        } else if (activity instanceof OnClickTweetListener) {
            mOnClickTweetListener = (OnClickTweetListener) activity;
        } else {
            throw new RuntimeException("activity or parent fragment implement OnClickTweetListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnClickTweetListener = null;
    }

    private void startApiAsyncTask() {
        if (mSocialTwitterApiAsyncTask != null) {
            return;
        }

        mSocialTwitterApiAsyncTask = new SocialTwitterApiAsyncTask(getUserToken());
        mSocialTwitterApiAsyncTask.execute(API_MAX_COUNT);
    }

    private void stopApiAsyncTask() {
        if (mSocialTwitterApiAsyncTask != null) {
            mSocialTwitterApiAsyncTask.cancel(true);
        }
        mSocialTwitterApiAsyncTask = null;
    }

    @Override
    public void onRefresh() {
        stopApiAsyncTask();
        startApiAsyncTask();
    }

    public class SocialTwitterApiAsyncTask extends AsyncTask<String, Void, SocialTwitterApi.GetRequest> {
        private String mUserToken;

        public SocialTwitterApiAsyncTask(String userToken) {
            mUserToken = userToken;
        }

        public void execute(int count) {
            execute(Integer.toString(count));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected SocialTwitterApi.GetRequest doInBackground(String... params) {
            return new SocialTwitterApi.GetRequest(mUserToken).get(params[0]);
        }

        @Override
        protected void onPostExecute(SocialTwitterApi.GetRequest api) {
            super.onPostExecute(api);
            if (isDetached() || getActivity() == null) {
                return;
            }

            if (api.isSuccessful()) {
                ArrayList<FeedTwitterStatus> feed = api.getResponseObj().getStatusList();
                Collections.sort(feed);
                if (mTweetList.size() == 0) {
                    // 新規取得
                    mTweetList.addAll(feed);
                } else {
                    FeedTwitterStatus latestTweet = mTweetList.get(0);
                    // 取得済みの最新ツイートより新しいものを追加
                    int index = Collections.binarySearch(feed, latestTweet);
                    if (index > 0) {
                        mTweetList.addAll(0, feed.subList(0, index));
                    } else if (index < 0) {
                        // indexが-1の時全部最新ツイート
                        // -2以下の場合がないと想定
                        Timber.d("onPostExecute: latestTweetIndex in new feed" + index);
                        mTweetList.addAll(0, feed);
                    } else {
                        Timber.d("onPostExecute: already added");
                    }

                }
                mAdapter.notifyDataSetChanged();
            }

            mSwipeRefreshLayout.setRefreshing(false);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
