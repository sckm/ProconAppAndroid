package jp.gr.procon.proconapp.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import jp.gr.procon.proconapp.BaseFragment;
import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.dummymodel.DummyGameResultList;
import jp.gr.procon.proconapp.model.GameResult;
import jp.gr.procon.proconapp.model.GameResultList;
import jp.gr.procon.proconapp.model.PlayerResult;
import jp.gr.procon.proconapp.ui.view.GameResultTitleRow;
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

    private TableLayout mTableLayout;
    private GameResultList mGameResultList;
    private OnShowAllGameResultClickListener mOnShowAllGameResultClickListener;

    public GameResultOutlineFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_outline_game_result, container, false);
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
        titleTextView.setText(R.string.title_outline_game_result);

        TextView showAllTextView = (TextView) view.findViewById(R.id.outline_show_all);
        showAllTextView.setOnClickListener(this);

        mTableLayout = (TableLayout) view.findViewById(R.id.outline_table);

        if (mGameResultList != null) {
            setDataToView();
        }
    }

    @Override
    public void onDestroyView() {
        mTableLayout = null;
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
        if (mTableLayout == null) {
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(mTableLayout.getContext());
        for (GameResult result : mGameResultList.subList(0, Math.min(mGameResultList.size(), MAX_NUM_ROW))) {
            // TODO View変更
            GameResultTitleRow titleRow = new GameResultTitleRow(mTableLayout.getContext());
            titleRow.setGameResult(result);
            mTableLayout.addView(titleRow);

            TableRow row = (TableRow) inflater.inflate(R.layout.row_game_result, mTableLayout, false);
            for (int i = 0; i < Math.min(3, result.getResult().size()); i++) {
                PlayerResult playerResult = result.getResult().get(i);
                TextView infoText = (TextView) row.getChildAt(i * 2);
                TextView titleText = (TextView) row.getChildAt(i * 2 + 1);
                infoText.setText(playerResult.getRank() + "");
                titleText.setText(playerResult.getPlayer().getmShortName());
            }

            mTableLayout.addView(row);
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
