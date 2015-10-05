package jp.gr.procon.proconapp.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Collections;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.api.GameResultListApi;
import jp.gr.procon.proconapp.api.asynctask.GameResultApiAsyncTask;
import jp.gr.procon.proconapp.event.BusHolder;
import jp.gr.procon.proconapp.event.RequestUpdateEvent;
import jp.gr.procon.proconapp.model.GameResult;
import jp.gr.procon.proconapp.model.GameResultList;
import jp.gr.procon.proconapp.model.PlayerResult;
import jp.gr.procon.proconapp.ui.view.GameResultTitleRow;

public class GameResultOutlineFragment extends BaseFragment implements
        View.OnClickListener
        , GameResultApiAsyncTask.GameResultApiListener {
    private static final int MAX_NUM_ROW = 3;

    // 失敗/キャンセルを知りたい場合は追加
    public interface OnUpdateGameResultOutlineListener {
        void OnCompleteGameResultOutlineUpdate();
    }

    public interface OnShowAllGameResultClickListener {
        void onShowAllGameResultClick();
    }

    public static GameResultOutlineFragment newInstance() {
        GameResultOutlineFragment fragment = new GameResultOutlineFragment();
        return fragment;
    }

    private TableLayout mTableLayout;
    private GameResultList mGameResultList;
    private ArrayList<ViewHolder> mHolders;
    private OnShowAllGameResultClickListener mOnShowAllGameResultClickListener;
    private OnUpdateGameResultOutlineListener mOnUpdateGameResultOutlineListener;
    private GameResultApiAsyncTask mGameResultApiAsyncTask;

    public GameResultOutlineFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_outline_game_result, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO save

        ImageView iconImageView = (ImageView) view.findViewById(R.id.icon);
        iconImageView.setImageResource(R.drawable.news);

        TextView titleTextView = (TextView) view.findViewById(R.id.outline_title);
        titleTextView.setText(R.string.title_outline_game_result);

        TextView showAllTextView = (TextView) view.findViewById(R.id.outline_show_all);
        showAllTextView.setOnClickListener(this);

        mTableLayout = (TableLayout) view.findViewById(R.id.outline_table);
        setupView();
        if (mGameResultList != null) {
            setDataToView();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        BusHolder.getInstance().register(this);
    }

    @Override
    public void onStop() {
        BusHolder.getInstance().unregister(this);
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGameResultList == null) {
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

        if (parent != null && parent instanceof OnUpdateGameResultOutlineListener) {
            mOnUpdateGameResultOutlineListener = (OnUpdateGameResultOutlineListener) parent;
        } else if (activity instanceof OnUpdateGameResultOutlineListener) {
            mOnUpdateGameResultOutlineListener = (OnUpdateGameResultOutlineListener) activity;
        } else {
            throw new RuntimeException("parent or activity must implement listener");
        }
    }

    @Override
    public void onDetach() {
        mOnShowAllGameResultClickListener = null;
        super.onDetach();
    }

    private void setupView() {
        mHolders = new ArrayList<>();

        LayoutInflater inflater = LayoutInflater.from(mTableLayout.getContext());
        View divider = inflater.inflate(R.layout.item_divider, mTableLayout, false);
        divider.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        mTableLayout.addView(divider);
        for (int i = 0; i < MAX_NUM_ROW; i++) {
            GameResultTitleRow titleRow = new GameResultTitleRow(mTableLayout.getContext());
            mTableLayout.addView(titleRow);
            TableRow row = (TableRow) inflater.inflate(R.layout.row_game_result, mTableLayout, false);
            mTableLayout.addView(row);

            // 区切り線
            divider = inflater.inflate(R.layout.item_divider, mTableLayout, false);
            divider.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            mTableLayout.addView(divider);

            titleRow.setVisibility(View.GONE);
            row.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);

            ViewHolder holder = new ViewHolder(titleRow, row, divider);
            mHolders.add(holder);
        }
    }

    private void setDataToView() {
        if (mTableLayout == null) {
            return;
        }

        int i = 0;
        for (GameResult result : mGameResultList.subList(0, Math.min(mGameResultList.size(), MAX_NUM_ROW))) {
            ViewHolder holder = mHolders.get(i);
            Collections.sort(result.getResult());

            GameResultTitleRow titleRow = holder.titleRow;
            titleRow.setGameResult(result);

            TableRow row = holder.bodyRow;
            for (int j = 0; j < Math.min(3, result.getResult().size()); j++) {
                PlayerResult playerResult = result.getResult().get(j);
                TextView rankText = (TextView) row.getChildAt(j * 2);
                TextView titleText = (TextView) row.getChildAt(j * 2 + 1);

                // TODO resource
                switch (result.getStatus()) {
                    case GameResult.STATUS_GAME_ENDED:
                        if (playerResult.isAdvance()) {
                            rankText.setBackgroundResource(R.drawable.background_circle_red);
                        } else {
                            rankText.setBackgroundResource(R.drawable.background_circle_blue);
                        }
                        rankText.setText(playerResult.getRank() + "");
                        break;

                    case GameResult.STATUS_GAME_PROGRESS:
                        rankText.setText(playerResult.getScore() + "zk");
                        break;
                }
                titleText.setText(playerResult.getPlayer().getShortName());
            }


            // 区切り線
            View divider = holder.divider;

            titleRow.setVisibility(View.VISIBLE);
            row.setVisibility(View.VISIBLE);
            divider.setVisibility(View.VISIBLE);
            i++;
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


    @Override
    public void onPreExecuteGameResultApi() {

    }

    @Override
    public void onPostExecuteGameResultApi(GameResultListApi.GetRequest api) {
        if (isDetached() || getActivity() == null) {
            return;
        }

        if (api.isSuccessful()) {
            mGameResultList = api.getResponseObj();
            setDataToView();
        } else {
            // TODO error
        }

        mOnUpdateGameResultOutlineListener.OnCompleteGameResultOutlineUpdate();
    }

    @Override
    public void onCanceledGameResultApi() {
        mOnUpdateGameResultOutlineListener.OnCompleteGameResultOutlineUpdate();
    }

    private void startApiAsyncTask() {
        if (mGameResultApiAsyncTask != null) {
            return;
        }

        mGameResultApiAsyncTask = new GameResultApiAsyncTask(getUserToken(), this);
        mGameResultApiAsyncTask.execute(3);
    }

    private void stopApiAsyncTask() {
        if (mGameResultApiAsyncTask != null) {
            mGameResultApiAsyncTask.cancel(true);
            mGameResultApiAsyncTask = null;
        }
    }

    @Subscribe
    public void requestUpdate(RequestUpdateEvent event) {
        stopApiAsyncTask();
        startApiAsyncTask();
    }

    private static class ViewHolder {
        private GameResultTitleRow titleRow;
        private TableRow bodyRow;
        private View divider;

        public ViewHolder(GameResultTitleRow titleRow, TableRow bodyRow, View divider) {
            this.titleRow = titleRow;
            this.bodyRow = bodyRow;
            this.divider = divider;
        }
    }
}

