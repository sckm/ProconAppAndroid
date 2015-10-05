package jp.gr.procon.proconapp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.api.GamePhotoListApi;
import jp.gr.procon.proconapp.api.asynctask.GamePhotoApiAsyncTask;
import jp.gr.procon.proconapp.model.GamePhoto;
import jp.gr.procon.proconapp.model.PageApiState;
import jp.gr.procon.proconapp.ui.adapter.GamePhotoRecyclerAdapter;
import jp.gr.procon.proconapp.ui.view.GridDividerItemDecoration;
import jp.gr.procon.proconapp.util.ToastUtil;

public class GamePhotoListFragment extends BaseFragment implements
        GamePhotoApiAsyncTask.GamePhotoApiListener {

    public static GamePhotoListFragment newInstance() {
        GamePhotoListFragment fragment = new GamePhotoListFragment();
        return fragment;
    }

    private View mLoadingView;
    private RecyclerView mRecyclerView;
    private GamePhotoRecyclerAdapter mAdapter;
    private PageApiState<GamePhoto> mApiState;

    private GamePhotoApiAsyncTask mGamePhotoApiAsyncTask;

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

        if (mApiState == null) {
            mApiState = new PageApiState<>(50);
        }

        mLoadingView = view.findViewById(R.id.progress);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new GamePhotoRecyclerAdapter(mApiState.getItems());
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.addItemDecoration(new GridDividerItemDecoration(getActivity(), 2));
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onPreExecuteGamePhotoApi() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPostExecuteGamePhotoApi(GamePhotoListApi.GetRequest api) {
        if (isDetached() || getActivity() == null) {
            return;
        }

        if (api.isSuccessful()) {
            ArrayList<GamePhoto> photoList = api.getResponseObj();
            mApiState.addPageList(photoList);
            mAdapter.notifyDataSetChanged();
        } else {
            ToastUtil.show(getActivity(), R.string.error_network);
        }

        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCanceledGamePhotoApi() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }
    }

    private void startApiAsyncTask() {
        if (mGamePhotoApiAsyncTask != null) {
            return;
        }

        // TODO 取得する数変更
        mGamePhotoApiAsyncTask = new GamePhotoApiAsyncTask(getUserToken(), this);
        mGamePhotoApiAsyncTask.execute(mApiState.getNumPageItem());
    }

    private void stopApiAsyncTask() {
        if (mGamePhotoApiAsyncTask != null) {
            mGamePhotoApiAsyncTask.cancel(true);
            mGamePhotoApiAsyncTask = null;
        }
    }
}
