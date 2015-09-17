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
import jp.gr.procon.proconapp.dummymodel.DummyGameResultList;
import jp.gr.procon.proconapp.model.GameResult;
import jp.gr.procon.proconapp.model.GameResultList;
import jp.gr.procon.proconapp.ui.adapter.GameResultExpandableListAdapter;
import jp.gr.procon.proconapp.util.JsonUtil;

public class GameResultListFragment extends BaseFragment {

    public static GameResultListFragment newInstance() {
        GameResultListFragment fragment = new GameResultListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private ExpandableListView mExpandableListView;
    private GameResultExpandableListAdapter mAdapter;

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
        // TODO sort
        ArrayList<GameResult> gameResults = JsonUtil.fromJson(DummyGameResultList.getDummyGameResultList(), GameResultList.class);
        for (GameResult result : gameResults) {
            Collections.sort(result.getResult());
        }

        mExpandableListView = (ExpandableListView) view.findViewById(R.id.expandable_list_view);
        mAdapter = new GameResultExpandableListAdapter(gameResults);
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
}
