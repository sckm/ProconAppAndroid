package jp.gr.procon.proconapp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import jp.gr.procon.proconapp.BaseFragment;
import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.dummymodel.DummyNotice;
import jp.gr.procon.proconapp.model.NoticeList;
import jp.gr.procon.proconapp.ui.adapter.NoticeListAdapter;
import jp.gr.procon.proconapp.util.JsonUtil;
import timber.log.Timber;

public class NoticeListFragment extends BaseFragment {

    public static NoticeListFragment newInstance() {
        NoticeListFragment fragment = new NoticeListFragment();
        return fragment;
    }

    private ListView mListView;

    public NoticeListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO apiから取得
        NoticeList mNoticeList = JsonUtil.fromJson(DummyNotice.getDummyNoticeList(), NoticeList.class);
        Timber.d(mNoticeList.toString());

        mListView = (ListView) view.findViewById(R.id.list_view);
        NoticeListAdapter adapter = new NoticeListAdapter(getActivity(), mNoticeList);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        mListView.setAdapter(null);
        mListView = null;
        super.onDestroyView();
    }
}
