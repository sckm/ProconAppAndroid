package jp.gr.procon.proconapp.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.api.SocialTwitterApi;
import jp.gr.procon.proconapp.model.FeedTwitterStatus;
import jp.gr.procon.proconapp.model.PageApiState;
import jp.gr.procon.proconapp.ui.adapter.TwitterFeedRecyclerAdapter;

public class TwitterFeedFragment extends BaseFragment {

    public static TwitterFeedFragment newInstance() {
        TwitterFeedFragment fragment = new TwitterFeedFragment();
        return fragment;
    }

    private RecyclerView mRecyclerView;
    private TwitterFeedRecyclerAdapter mAdapter;
    private PageApiState<FeedTwitterStatus> mApiState;

    private SocialTwitterApiAsyncTask mSocialTwitterApiAsyncTask;

    public TwitterFeedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            // TODO save
        }

        if (mApiState == null) {
            mApiState = new PageApiState<>();
        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new TwitterFeedRecyclerAdapter(mApiState.getItems());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mApiState.getNextPage() == 0) {
            startApiAsyncTask();
        }
    }

    @Override
    public void onPause() {
        stopApiAsyncTask();
        super.onPause();
    }

    private void startApiAsyncTask() {
        if (mSocialTwitterApiAsyncTask != null || mApiState.isLoadedAll()) {
            return;
        }

        // TODO 取得数変更
        mSocialTwitterApiAsyncTask = new SocialTwitterApiAsyncTask(getUserToken());
        mSocialTwitterApiAsyncTask.execute(mApiState.getNumPageItem());
    }

    private void stopApiAsyncTask() {
        if (mSocialTwitterApiAsyncTask != null) {
            mSocialTwitterApiAsyncTask.cancel(true);
        }
        mSocialTwitterApiAsyncTask = null;
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
                mApiState.addPageList(feed);
                mAdapter.notifyDataSetChanged();
            }

        }
    }
}
