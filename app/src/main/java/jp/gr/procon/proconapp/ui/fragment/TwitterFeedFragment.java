package jp.gr.procon.proconapp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.dummymodel.DummySocialFeed;
import jp.gr.procon.proconapp.model.FeedTwitterStatus;
import jp.gr.procon.proconapp.model.SocialFeedTwitter;
import jp.gr.procon.proconapp.ui.adapter.TwitterFeedRecyclerAdapter;
import jp.gr.procon.proconapp.util.JsonUtil;

public class TwitterFeedFragment extends BaseFragment {

    public static TwitterFeedFragment newInstance() {
        TwitterFeedFragment fragment = new TwitterFeedFragment();
        return fragment;
    }

    private RecyclerView mRecyclerView;
    private TwitterFeedRecyclerAdapter mAdapter;

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

        // TODO apiから取得
        ArrayList<FeedTwitterStatus> tweetList = JsonUtil.fromJson(DummySocialFeed.getDummyTwitterFeed(), SocialFeedTwitter.class).getStatusList();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new TwitterFeedRecyclerAdapter(tweetList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onDestroyView() {
        mRecyclerView = null;
        super.onDestroyView();
    }
}
