package jp.gr.procon.proconapp.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.api.NoticeListApi;
import jp.gr.procon.proconapp.api.asynctask.NoticeApiAsyncTask;
import jp.gr.procon.proconapp.model.Notice;
import jp.gr.procon.proconapp.model.PageApiState;
import jp.gr.procon.proconapp.ui.adapter.NoticeListAdapter;
import jp.gr.procon.proconapp.ui.callback.OnGetViewListener;
import jp.gr.procon.proconapp.ui.callback.OnNoticeClickListener;

public class NoticeListFragment extends BaseFragment implements
        NoticeApiAsyncTask.NoticeApiListener
        , OnGetViewListener {
    private static final String STATE_PAGE_API_STATE = "state_page_api_state";
    private RecyclerView mRecyclerView;

    private NoticeListAdapter mAdapter;

    private PageApiState<Notice> mNoticePageApiState;
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

        // TODO savedInstanceState
        if (savedInstanceState != null) {
            mNoticePageApiState = (PageApiState<Notice>) savedInstanceState.getSerializable(STATE_PAGE_API_STATE);
        }

        if (mNoticePageApiState == null) {
            mNoticePageApiState = new PageApiState<>();
        }

        // TODO RecyclerViewを使用
        // TODO リストの下の方へいくと自動で次ページを読み込むようにする
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new NoticeListAdapter(mNoticePageApiState.getItems());
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
        if (!mNoticePageApiState.isLoadedAll()) {
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
    public void onPreExecuteNoticeApi() {
        // TODO progress
    }

    @Override
    public void onPostExecuteNoticeApi(NoticeListApi.GetRequest api) {
        mNoticeApiAsyncTask = null;
        if (isDetached() || getActivity() == null) {
            return;
        }

        if (api.isSuccessful()) {
            mNoticePageApiState.addPageList(api.getResponseObj());
            mAdapter.notifyDataSetChanged();
        } else {
            // TODO error
        }
    }

    @Override
    public void onCanceledNoticeApi() {
        mNoticeApiAsyncTask = null;
    }

    @Override
    public void OnGetView(RecyclerView.Adapter adapter, int position) {
        if (adapter.getItemCount() - 5 < position) {
            startApiAsyncTask();
        }
    }

    private void startApiAsyncTask() {
        if (mNoticeApiAsyncTask != null || mNoticePageApiState.isLoadedAll()) {
            return;
        }

        mNoticeApiAsyncTask = new NoticeApiAsyncTask(getUserToken(), this);
        mNoticeApiAsyncTask.execute(mNoticePageApiState.getNextPage(), mNoticePageApiState.getNumPageItem());
    }

    private void stopApiAsyncTask() {
        if (mNoticeApiAsyncTask != null) {
            mNoticeApiAsyncTask.cancel(true);
            mNoticeApiAsyncTask = null;
        }
    }
}
