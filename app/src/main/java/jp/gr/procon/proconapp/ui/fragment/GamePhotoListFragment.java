package jp.gr.procon.proconapp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.dummymodel.DummyGamePhoto;
import jp.gr.procon.proconapp.model.GamePhoto;
import jp.gr.procon.proconapp.model.GamePhotoList;
import jp.gr.procon.proconapp.ui.adapter.GamePhotoRecyclerAdapter;
import jp.gr.procon.proconapp.util.JsonUtil;
import timber.log.Timber;

public class GamePhotoListFragment extends BaseFragment {

    public static GamePhotoListFragment newInstance() {
        GamePhotoListFragment fragment = new GamePhotoListFragment();
        return fragment;
    }

    private RecyclerView mRecyclerView;
    private GamePhotoRecyclerAdapter mAdapter;

    public GamePhotoListFragment() {
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
        ArrayList<GamePhoto> photoList = JsonUtil.fromJson(DummyGamePhoto.getDummyGamePhoto(), GamePhotoList.class);
        Timber.d(photoList.toString());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new GamePhotoRecyclerAdapter(photoList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onDestroyView() {
        mRecyclerView = null;
        super.onDestroyView();
    }
}
