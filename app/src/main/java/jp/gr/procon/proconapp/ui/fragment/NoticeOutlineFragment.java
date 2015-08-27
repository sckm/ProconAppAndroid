package jp.gr.procon.proconapp.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import jp.gr.procon.proconapp.BaseFragment;
import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.dummymodel.DummyNotice;
import jp.gr.procon.proconapp.model.Notice;
import jp.gr.procon.proconapp.model.NoticeList;
import jp.gr.procon.proconapp.ui.view.NoticeListItemView;
import jp.gr.procon.proconapp.util.JsonUtil;
import timber.log.Timber;

public class NoticeOutlineFragment extends BaseFragment implements View.OnClickListener {
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
        mNoticeList = JsonUtil.fromJson(DummyNotice.getDummyNoticeList(), NoticeList.class);
        Timber.d(mNoticeList.toString());

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
    }

    @Override
    public void onDetach() {
        mOnShowAllNoticeClickListener = null;
        super.onDetach();
    }

    private void setDataToView() {
        if (mBodyLayout == null) {
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(mBodyLayout.getContext());
        for (Notice notice : mNoticeList.subList(0, Math.min(mNoticeList.size(), MAX_NUM_ROW))) {
            View v = inflater.inflate(NoticeListItemView.RESOURECE_ID, mBodyLayout, false);
            NoticeListItemView itemView = new NoticeListItemView(v);
            itemView.bindTo(notice);
            mBodyLayout.addView(v);
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
}
