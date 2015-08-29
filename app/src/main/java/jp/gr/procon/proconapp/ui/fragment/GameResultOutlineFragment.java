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
import jp.gr.procon.proconapp.dummymodel.DummyGameResultList;
import jp.gr.procon.proconapp.model.GameResult;
import jp.gr.procon.proconapp.model.GameResultList;
import jp.gr.procon.proconapp.ui.view.NoticeListItemView;
import jp.gr.procon.proconapp.util.JsonUtil;
import timber.log.Timber;

public class GameResultOutlineFragment extends BaseFragment implements View.OnClickListener {
    private static final int MAX_NUM_ROW = 3;

    public interface OnShowAllGameResultClickListener {
        void onShowAllGameResultClick();
    }

    public static GameResultOutlineFragment newInstance() {
        GameResultOutlineFragment fragment = new GameResultOutlineFragment();
        return fragment;
    }

    private ViewGroup mBodyLayout;
    private GameResultList mGameResultList;
    private OnShowAllGameResultClickListener mOnShowAllGameResultClickListener;

    public GameResultOutlineFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_outline, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO apiから取得
        mGameResultList = JsonUtil.fromJson(DummyGameResultList.getDummyGameResultList(), GameResultList.class);
        Timber.d(mGameResultList.toString());

        // TODO icon
        ImageView iconImageView = (ImageView) view.findViewById(R.id.icon);

        TextView titleTextView = (TextView) view.findViewById(R.id.outline_title);
        titleTextView.setText(R.string.title_outline_notice);

        TextView showAllTextView = (TextView) view.findViewById(R.id.outline_show_all);
        showAllTextView.setOnClickListener(this);

        mBodyLayout = (ViewGroup) view.findViewById(R.id.outline_body);

        if (mGameResultList != null) {
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
        if (parent != null && parent instanceof OnShowAllGameResultClickListener) {
            mOnShowAllGameResultClickListener = (OnShowAllGameResultClickListener) parent;
        } else if (activity instanceof OnShowAllGameResultClickListener) {
            mOnShowAllGameResultClickListener = (OnShowAllGameResultClickListener) activity;
        } else {
            throw new RuntimeException("parent or activity must implement listener");
        }
    }

    @Override
    public void onDetach() {
        mOnShowAllGameResultClickListener = null;
        super.onDetach();
    }

    private void setDataToView() {
        if (mBodyLayout == null) {
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(mBodyLayout.getContext());
        for (GameResult result : mGameResultList.subList(0, Math.min(mGameResultList.size(), MAX_NUM_ROW))) {
            // TODO View変更
            TextView v = new TextView(mBodyLayout.getContext());
            v.setText(result.getmTitle());
            mBodyLayout.addView(v);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.outline_show_all:
                if (mOnShowAllGameResultClickListener != null) {
                    mOnShowAllGameResultClickListener.onShowAllGameResultClick();
                }
                break;
        }

    }
}
