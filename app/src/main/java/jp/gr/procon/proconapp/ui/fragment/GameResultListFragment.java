package jp.gr.procon.proconapp.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

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

public class GameResultListFragment extends BaseFragment
        implements GameResultApiAsyncTask.GameResultApiListener {

    public static GameResultListFragment newInstance() {
        GameResultListFragment fragment = new GameResultListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private ExpandableListView mExpandableListView;
    private GameResultExpandableListAdapter mAdapter;

    private GameResultApiAsyncTask mGameResultApiAsyncTask;
    private PageApiState<GameResult> mPageApiState;

    public GameResultListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_result_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO savedInstanceState

        if (mPageApiState == null) {
            mPageApiState = new PageApiState<>();
        }

        mExpandableListView = (ExpandableListView) view.findViewById(R.id.expandable_list_view);
        mAdapter = new GameResultExpandableListAdapter(mPageApiState.getItems());
        mExpandableListView.setAdapter(mAdapter);
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
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
        // TODO progress
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
            // TODO error
        }
    }

    @Override
    public void onCanceledGameResultApi() {

    }

    private void startApiAsyncTask() {
        if (mGameResultApiAsyncTask != null) {
            return;
        }

        mGameResultApiAsyncTask = new GameResultApiAsyncTask(getUserToken(), this);
        // TODO 取得数変更
        mGameResultApiAsyncTask.execute(10);
    }

    private void stopApiAsyncTask() {
        if (mGameResultApiAsyncTask != null) {
            mGameResultApiAsyncTask.cancel(true);
            mGameResultApiAsyncTask = null;
        }
    }
}
