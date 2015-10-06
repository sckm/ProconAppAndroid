package jp.gr.procon.proconapp.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.api.GameResultListApi;
import jp.gr.procon.proconapp.api.asynctask.GameResultApiAsyncTask;
import jp.gr.procon.proconapp.dummymodel.DummyGameResultList;
import jp.gr.procon.proconapp.model.GameResult;
import jp.gr.procon.proconapp.model.GameResultList;
import jp.gr.procon.proconapp.model.PageApiState;
import jp.gr.procon.proconapp.ui.adapter.GameResultExpandableListAdapter;
import jp.gr.procon.proconapp.util.JsonUtil;
import jp.gr.procon.proconapp.util.ToastUtil;

public class GameResultListFragment extends BaseFragment
        implements GameResultApiAsyncTask.GameResultApiListener {
    private static final String ARG_SHOULD_FILTER = "arg_should_filter";

    public static GameResultListFragment newInstance(boolean shouldFilter) {
        GameResultListFragment fragment = new GameResultListFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_SHOULD_FILTER, shouldFilter);
        fragment.setArguments(args);
        return fragment;
    }

    private boolean mShouldFilter;
    private View mLoadingView;
    private ExpandableListView mExpandableListView;
    private GameResultExpandableListAdapter mAdapter;

    private View mDescriptionLayoutView;
    private TextView mDescriptionText;

    private GameResultApiAsyncTask mGameResultApiAsyncTask;
    private PageApiState<GameResult> mPageApiState;

    public GameResultListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mShouldFilter = getArguments().getBoolean(ARG_SHOULD_FILTER, false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDescriptionLayoutView = inflater.inflate(R.layout.text_description, null, false);
        return inflater.inflate(R.layout.fragment_game_result_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO savedInstanceState

        if (mPageApiState == null) {
            mPageApiState = new PageApiState<>();
        }

        mDescriptionText = (TextView) mDescriptionLayoutView.findViewById(R.id.text_body);
        mDescriptionText.setText(R.string.desc_game_result);

        mLoadingView = view.findViewById(R.id.progress);
        mExpandableListView = (ExpandableListView) view.findViewById(R.id.expandable_list_view);
        mExpandableListView.addHeaderView(mDescriptionLayoutView, null, false);
        mAdapter = new GameResultExpandableListAdapter(mPageApiState.getItems());
        mExpandableListView.setAdapter(mAdapter);
        int groupCount = mAdapter.getGroupCount();
        for (int i = 0; i < groupCount; i++) {
            mExpandableListView.expandGroup(i);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPageApiState.getNextPage() == 0) {
            startApiAsyncTask();
        }
    }

    @Override
    public void onPause() {
        stopApiAsyncTask();
        super.onPause();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
//            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    @Override
    public void onPreExecuteGameResultApi() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPostExecuteGameResultApi(GameResultListApi.GetRequest api) {
        if (isDetached() || getActivity() == null) {
            return;
        }

        if (api.isSuccessful()) {
            ArrayList<GameResult> results = api.getResponseObj();
            for (GameResult result : results) {
                Collections.sort(result.getResult());
            }
            mPageApiState.addPageList(results);
            int groupCount = mAdapter.getGroupCount();
            for (int i = groupCount - results.size(); i < groupCount; i++) {
                mExpandableListView.expandGroup(i);
            }
        } else {
            ToastUtil.show(getActivity(), R.string.error_network);
        }

        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCanceledGameResultApi() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }
    }

    private void startApiAsyncTask() {
        if (mGameResultApiAsyncTask != null) {
            return;
        }

        mGameResultApiAsyncTask = new GameResultApiAsyncTask(getUserToken(), this);
        // TODO 取得数変更
        String filter = mShouldFilter ? GameResultListApi.GetRequest.FILTER_ONLY_FOR_NOTIFICATION : GameResultListApi.GetRequest.FILTER_ALL;
        mGameResultApiAsyncTask.execute(50, filter);
    }

    private void stopApiAsyncTask() {
        if (mGameResultApiAsyncTask != null) {
            mGameResultApiAsyncTask.cancel(true);
            mGameResultApiAsyncTask = null;
        }
    }
}
