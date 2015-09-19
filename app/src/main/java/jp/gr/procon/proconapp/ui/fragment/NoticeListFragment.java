package jp.gr.procon.proconapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.api.NoticeListApi;
import jp.gr.procon.proconapp.api.asynctask.NoticeApiAsyncTask;
import jp.gr.procon.proconapp.dummymodel.DummyNotice;
import jp.gr.procon.proconapp.model.Notice;
import jp.gr.procon.proconapp.model.NoticeList;
import jp.gr.procon.proconapp.model.PageApiState;
import jp.gr.procon.proconapp.ui.adapter.NoticeListAdapter;
import jp.gr.procon.proconapp.util.JsonUtil;
import timber.log.Timber;

public class NoticeListFragment extends BaseFragment implements NoticeApiAsyncTask.NoticeApiListener {

    public static NoticeListFragment newInstance() {
        NoticeListFragment fragment = new NoticeListFragment();
        return fragment;
    }

    private ListView mListView;
    private NoticeListAdapter mAdapter;
    private PageApiState<Notice> mNoticePageApiState;
    private NoticeApiAsyncTask mNoticeApiAsyncTask;

    public NoticeListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO savedInstanceState
        if (savedInstanceState != null) {

        }

        if (mNoticePageApiState == null) {
            mNoticePageApiState = new PageApiState<>();
        }

        // TODO apiから取得
//        NoticeList mNoticeList = JsonUtil.fromJson(DummyNotice.getDummyNoticeList(), NoticeList.class);
//        Timber.d(mNoticeList.toString());

        // TODO RecyclerViewを使用
        // TODO リストの下の方へいくと自動で次ページを読み込むようにする
        mListView = (ListView) view.findViewById(R.id.list_view);
        mAdapter = new NoticeListAdapter(getActivity(), mNoticePageApiState.getItems());
        mListView.setAdapter(mAdapter);
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
    public void onDestroyView() {
        mListView.setAdapter(null);
        mListView = null;
        super.onDestroyView();
    }


    @Override
    public void onPreExecuteNoticeApi() {
        // TODO progress
    }

    @Override
    public void onPostExecuteNoticeApi(NoticeListApi.GetRequest api) {
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
    }

    private void startApiAsyncTask() {
        if (mNoticeApiAsyncTask != null) {
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
