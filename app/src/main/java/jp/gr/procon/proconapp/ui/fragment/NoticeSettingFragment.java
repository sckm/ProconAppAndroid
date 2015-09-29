package jp.gr.procon.proconapp.ui.fragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.api.GameNotificationApi;
import jp.gr.procon.proconapp.dummymodel.DummyPlayer;
import jp.gr.procon.proconapp.model.GameNotificationList;
import jp.gr.procon.proconapp.model.Player;
import jp.gr.procon.proconapp.model.PlayerCheckedItem;
import jp.gr.procon.proconapp.model.PlayerList;
import jp.gr.procon.proconapp.ui.adapter.NoticeSettingAdapter;
import jp.gr.procon.proconapp.util.JsonUtil;
import timber.log.Timber;

public class NoticeSettingFragment extends BaseFragment implements
        NoticeSettingAdapter.OnChangeCheckListener
        , View.OnClickListener {

    public interface OnCompleteNoticeSettingListener {
        void onCompleteNoticeSetting();

        void onCancelNoticeSetting();
    }

    private RecyclerView mRecyclerView;
    private NoticeSettingAdapter mNoticeSettingAdapter;
    private OnCompleteNoticeSettingListener mOnCompleteNoticeSettingListener;
    private ArrayList<Long> mGameNotificationIds;

    private GetGameNotificationApiAsyncTask mGetGameNotificationApiAsyncTask;
    private PutGameNotificationApiAsyncTask mPutGameNotificationApiAsyncTask;

    public static NoticeSettingFragment newInstance() {
        NoticeSettingFragment fragment = new NoticeSettingFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notice_setting, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO 初回起動時に取得 apiから取得しておく
        ArrayList<Player> players = JsonUtil.fromJson(DummyPlayer.getPlayerList(), PlayerList.class);
        ArrayList<PlayerCheckedItem> items = new ArrayList<>();
        for (Player player : players) {
            items.add(new PlayerCheckedItem(player, false));
        }
        // TODO savedInstanceState

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mNoticeSettingAdapter = new NoticeSettingAdapter(items);
        mNoticeSettingAdapter.setOnChangeCheckListener(this);
        mRecyclerView.setAdapter(mNoticeSettingAdapter);

        view.findViewById(R.id.btn_cancel).setOnClickListener(this);
        view.findViewById(R.id.btn_submit).setOnClickListener(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Fragment fragment = getParentFragment();
        if (fragment != null && fragment instanceof OnCompleteNoticeSettingListener) {
            mOnCompleteNoticeSettingListener = (OnCompleteNoticeSettingListener) fragment;
        } else if (activity instanceof OnCompleteNoticeSettingListener) {
            mOnCompleteNoticeSettingListener = (OnCompleteNoticeSettingListener) activity;
        } else {
            throw new RuntimeException("parent fragment or activity mush implements OnCompleteNoticeSettingListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnCompleteNoticeSettingListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d("onResume: " + mGameNotificationIds);
        if (mGameNotificationIds == null) {
            startGetApiAsyncTask();
        }
    }

    @Override
    public void onPause() {
        stopGetApiAsyncTask();
        stopPutApiAsyncTask();
        super.onPause();
    }

    @Override
    public void onChangeCheck(PlayerCheckedItem item) {
        mNoticeSettingAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                ArrayList<PlayerCheckedItem> checkedItems = mNoticeSettingAdapter.getCheckedItemList();
                ArrayList<Long> ids = new ArrayList<>();
                for (PlayerCheckedItem item : checkedItems) {
                    ids.add(item.getPlayer().getId());
                }
                GameNotificationList list = new GameNotificationList(ids);
                startPutApiAsyncTask(list);
                break;

            case R.id.btn_cancel:
                if (mOnCompleteNoticeSettingListener != null) {
                    mOnCompleteNoticeSettingListener.onCancelNoticeSetting();
                }
                break;

            default:
                break;
        }
    }

    private void startGetApiAsyncTask() {
        if (mGetGameNotificationApiAsyncTask != null) {
            return;
        }
        mGetGameNotificationApiAsyncTask = new GetGameNotificationApiAsyncTask();
        mGetGameNotificationApiAsyncTask.execute();
    }

    private void stopGetApiAsyncTask() {
        if (mGetGameNotificationApiAsyncTask != null) {
            mGetGameNotificationApiAsyncTask.cancel(true);
            mGetGameNotificationApiAsyncTask = null;
        }
    }

    private void startPutApiAsyncTask(GameNotificationList list) {
        if (mPutGameNotificationApiAsyncTask != null) {
            return;
        }

        mPutGameNotificationApiAsyncTask = new PutGameNotificationApiAsyncTask();
        mPutGameNotificationApiAsyncTask.execute(list);
    }

    private void stopPutApiAsyncTask() {
        if (mPutGameNotificationApiAsyncTask != null) {
            mPutGameNotificationApiAsyncTask.cancel(true);
            mPutGameNotificationApiAsyncTask = null;
        }
    }

    // TODO progress
    private class GetGameNotificationApiAsyncTask extends AsyncTask<Void, Void, GameNotificationApi.GetRequest> {

        @Override
        protected GameNotificationApi.GetRequest doInBackground(Void... params) {
            return new GameNotificationApi.GetRequest(getUserToken()).get();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(GameNotificationApi.GetRequest api) {
            super.onPostExecute(api);
            if (isCancelled() || isDetached() || getActivity() == null) {
                return;
            }

            if (api.isSuccessful()) {
                mGameNotificationIds = api.getResponseObj().getIds();
                mNoticeSettingAdapter.setCheckWithIds(mGameNotificationIds, true);
                mNoticeSettingAdapter.setClickable(true);
                mNoticeSettingAdapter.notifyDataSetChanged();
            } else {
                // TODO error
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    // TODO progress
    private class PutGameNotificationApiAsyncTask extends AsyncTask<GameNotificationList, Void, GameNotificationApi.PutRequest> {

        @Override
        protected GameNotificationApi.PutRequest doInBackground(GameNotificationList... params) {
            return new GameNotificationApi.PutRequest(getUserToken()).put(params[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(GameNotificationApi.PutRequest api) {
            super.onPostExecute(api);
            mPutGameNotificationApiAsyncTask = null;
            if (isCancelled() || isDetached() || getActivity() == null) {
                return;
            }

            if (api.isSuccessful()) {
                if (mOnCompleteNoticeSettingListener != null) {
                    mOnCompleteNoticeSettingListener.onCompleteNoticeSetting();
                }
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            mPutGameNotificationApiAsyncTask = null;
        }
    }
}