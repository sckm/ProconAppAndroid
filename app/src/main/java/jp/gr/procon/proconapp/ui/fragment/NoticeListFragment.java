package jp.gr.procon.proconapp.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.api.NoticeListApi;
import jp.gr.procon.proconapp.api.asynctask.NoticeApiAsyncTask;
import jp.gr.procon.proconapp.model.Notice;
import jp.gr.procon.proconapp.ui.adapter.NoticeListAdapter;
import jp.gr.procon.proconapp.ui.callback.OnGetViewListener;
import jp.gr.procon.proconapp.ui.callback.OnNoticeClickListener;
import jp.gr.procon.proconapp.ui.view.DividerItemDecoration;
import jp.gr.procon.proconapp.util.ToastUtil;
import timber.log.Timber;

public class NoticeListFragment extends BaseFragment implements
        NoticeApiAsyncTask.NoticeApiListener
        , OnGetViewListener {
    private static final String STATE_FAIL_LOADING = "state_fail_loading";
    private static final String STATE_PAGE_API_STATE = "state_page_api_state";
    private static final int PAGE_COUNT = 10;

    private View mLoadingView;
    private RecyclerView mRecyclerView;
    /** ロードに失敗したらtrue, それ以外はfalse */
    private boolean mFailLoading;

    private NoticeListAdapter mAdapter;

    //    private PageApiState<Notice> mNoticePageApiState;
    private ArrayList<Notice> mNoticeList;
    private boolean mIsLoadedAll;

    /** APIで次に取得すべきページ */
    private int mNextPage;

    private NoticeApiAsyncTask mNoticeApiAsyncTask;
    private OnNoticeClickListener mOnNoticeClickListener;

    public static NoticeListFragment newInstance() {
        NoticeListFragment fragment = new NoticeListFragment();
        return fragment;
    }

    public NoticeListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            // 前のデータを保存しておく場合は新規データ読み込み時に差分のみ追加するようにする
//            mFailLoading = savedInstanceState.getBoolean(STATE_FAIL_LOADING);
//            mNoticePageApiState = (PageApiState<Notice>) savedInstanceState.getSerializable(STATE_PAGE_API_STATE);
        }

        // 前回表示時にロードに失敗していたらいったんデータをリセットしておく
        if (mFailLoading || mNoticeList == null) {
            mNoticeList = new ArrayList<>();
        }

        mLoadingView = view.findViewById(R.id.progress);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        mAdapter = new NoticeListAdapter(mNoticeList);
        mAdapter.setOnGetViewListener(this);
        mAdapter.setOnNoticeItemClickListener(new NoticeListAdapter.OnNoticeItemClickListener() {
            @Override
            public void onNoticeItemClick(Notice notice) {
                if (mOnNoticeClickListener != null) {
                    mOnNoticeClickListener.onNoticeClick(notice);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mFailLoading = false;
        if (!mIsLoadedAll) {
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
        Fragment parent = getParentFragment();
        if (parent != null && parent instanceof OnNoticeClickListener) {
            mOnNoticeClickListener = (OnNoticeClickListener) parent;
        } else if (activity instanceof OnNoticeClickListener) {
            mOnNoticeClickListener = (OnNoticeClickListener) activity;
        } else {
            throw new RuntimeException("parent or activity must implement listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnNoticeClickListener = null;
    }

    @Override
    public void onDestroyView() {
        mRecyclerView.setAdapter(null);
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_FAIL_LOADING, mFailLoading);
        outState.putSerializable(STATE_PAGE_API_STATE, mNoticeList);
    }

    @Override
    public void onPreExecuteNoticeApi() {
        // リストにアイテムが無い時だけmLoadingViewでプログレス表示
        // リストにアイテムがある場合はリストフッターでプログレス表示
        if (mNoticeList.size() == 0 && mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPostExecuteNoticeApi(NoticeListApi.GetRequest api) {
        mNoticeApiAsyncTask = null;
        if (isDetached() || getActivity() == null) {
            return;
        }

        if (api.isSuccessful()) {
            ArrayList<Notice> list = api.getResponseObj();

            ArrayList<Notice> newList = new ArrayList<>();
            if (mNoticeList.size() == 0) {
                newList.addAll(list);
            } else {
                // リストに追加されていないお知らせをnewListに追加
                for (Notice notice : list) {
                    int index = Collections.binarySearch(mNoticeList, notice);
                    if (index < 0) {
                        newList.add(notice);
                    } else {
                        Timber.d("onPostExecuteNoticeApi: already added id=" + notice.getId() + " pub_at=" + notice.getPublishedAt() + " index=" + index);
                    }
                }
            }
            Timber.d("onPostExecuteNoticeApi: page=" + mNextPage + " size=" + newList.size());
            mNoticeList.addAll(newList);
            Collections.sort(mNoticeList);
            mIsLoadedAll = newList.size() == 0;

            mNextPage++;
            mAdapter.notifyDataSetChanged();
            setShowListFooter(!mIsLoadedAll);
        } else {
            if (mNoticeList.size() == 0) {
                ToastUtil.show(getActivity(), R.string.error_network);
            }
            mFailLoading = true;
            setShowListFooter(false);
        }

        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCanceledNoticeApi() {
        mNoticeApiAsyncTask = null;
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }
        setShowListFooter(false);
    }

    @Override
    public void OnGetView(RecyclerView.Adapter adapter, int position) {
        if (!mFailLoading && adapter.getItemCount() - 5 < position && !mIsLoadedAll) {
            startApiAsyncTask();
        }
    }

    private void setShowListFooter(boolean isShow) {
        mAdapter.setIsShowLoading(isShow);
        mAdapter.notifyDataSetChanged();
    }

    private void startApiAsyncTask() {
        if (mNoticeApiAsyncTask != null || mIsLoadedAll) {
            return;
        }

        mNoticeApiAsyncTask = new NoticeApiAsyncTask(getUserToken(), this);
        mNoticeApiAsyncTask.execute(mNextPage, PAGE_COUNT);
    }

    private void stopApiAsyncTask() {
        if (mNoticeApiAsyncTask != null) {
            mNoticeApiAsyncTask.cancel(true);
            mNoticeApiAsyncTask = null;
        }
    }
}
