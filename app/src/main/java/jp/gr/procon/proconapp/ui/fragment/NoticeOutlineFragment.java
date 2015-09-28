package jp.gr.procon.proconapp.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.api.NoticeListApi;
import jp.gr.procon.proconapp.api.asynctask.NoticeApiAsyncTask;
import jp.gr.procon.proconapp.dummymodel.DummyNotice;
import jp.gr.procon.proconapp.model.Notice;
import jp.gr.procon.proconapp.model.NoticeList;
import jp.gr.procon.proconapp.ui.callback.OnNoticeClickListener;
import jp.gr.procon.proconapp.ui.view.NoticeListItemView;
import jp.gr.procon.proconapp.util.JsonUtil;
import timber.log.Timber;

public class NoticeOutlineFragment extends BaseFragment implements View.OnClickListener, NoticeApiAsyncTask.NoticeApiListener {
    private static final int MAX_NUM_ROW = 3;

    public interface OnShowAllNoticeClickListener {
        void onShowAllNoticeClick();
    }

    public static NoticeOutlineFragment newInstance() {
        NoticeOutlineFragment fragment = new NoticeOutlineFragment();
        return fragment;
    }

    private ViewGroup mBodyLayout;
    private NoticeList mNoticeList;
    private OnShowAllNoticeClickListener mOnShowAllNoticeClickListener;
    private OnNoticeClickListener mOnNoticeClickListener;

    private NoticeApiAsyncTask mNoticeApiAsyncTask;

    public NoticeOutlineFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_outline, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO apiから取得
//        mNoticeList = JsonUtil.fromJson(DummyNotice.getDummyNoticeList(), NoticeList.class);
//        Timber.d(mNoticeList.toString());

        // TODO icon
        ImageView iconImageView = (ImageView) view.findViewById(R.id.icon);

        TextView titleTextView = (TextView) view.findViewById(R.id.outline_title);
        titleTextView.setText(R.string.title_outline_notice);

        TextView showAllTextView = (TextView) view.findViewById(R.id.outline_show_all);
        showAllTextView.setOnClickListener(this);

        mBodyLayout = (ViewGroup) view.findViewById(R.id.outline_body);

        if (mNoticeList != null) {
            setDataToView();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mNoticeList == null) {
            mNoticeApiAsyncTask = new NoticeApiAsyncTask(getUserToken(), this);
            mNoticeApiAsyncTask.execute(0);
        }
    }

    @Override
    public void onPause() {
        if (mNoticeApiAsyncTask != null) {
            mNoticeApiAsyncTask.cancel(true);
            mNoticeApiAsyncTask = null;
        }
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        mBodyLayout = null;
        super.onDestroyView();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Fragment parent = getParentFragment();
        if (parent != null && parent instanceof OnShowAllNoticeClickListener) {
            mOnShowAllNoticeClickListener = (OnShowAllNoticeClickListener) parent;
        } else if (activity instanceof OnShowAllNoticeClickListener) {
            mOnShowAllNoticeClickListener = (OnShowAllNoticeClickListener) activity;
        } else {
            throw new RuntimeException("parent or activity must implement listener");
        }

        if (parent != null && parent instanceof OnNoticeClickListener) {
            mOnNoticeClickListener = (OnNoticeClickListener) parent;
        } else if (activity instanceof OnShowAllNoticeClickListener) {
            mOnNoticeClickListener = (OnNoticeClickListener) activity;
        } else {
            throw new RuntimeException("parent or activity must implement listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnNoticeClickListener = null;
        mOnShowAllNoticeClickListener = null;
    }

    private void setDataToView() {
        if (mBodyLayout == null) {
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(mBodyLayout.getContext());
        View divider = inflater.inflate(R.layout.item_divider, mBodyLayout, false);
        mBodyLayout.addView(divider);
        for (final Notice notice : mNoticeList.subList(0, Math.min(mNoticeList.size(), MAX_NUM_ROW))) {
            View v = inflater.inflate(NoticeListItemView.RESOURECE_ID, mBodyLayout, false);
            NoticeListItemView itemView = new NoticeListItemView(v);
            itemView.bindTo(notice);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnNoticeClickListener != null) {
                        mOnNoticeClickListener.onNoticeClick(notice);
                    }
                }
            });
            mBodyLayout.addView(v);

            divider = inflater.inflate(R.layout.item_divider, mBodyLayout, false);
            mBodyLayout.addView(divider);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.outline_show_all:
                if (mOnShowAllNoticeClickListener != null) {
                    mOnShowAllNoticeClickListener.onShowAllNoticeClick();
                }
                break;
        }
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
            mNoticeList = api.getResponseObj();
            setDataToView();
        }
    }

    @Override
    public void onCanceledNoticeApi() {

    }
}
