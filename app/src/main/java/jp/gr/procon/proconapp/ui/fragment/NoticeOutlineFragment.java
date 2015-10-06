package jp.gr.procon.proconapp.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.api.NoticeListApi;
import jp.gr.procon.proconapp.api.asynctask.NoticeApiAsyncTask;
import jp.gr.procon.proconapp.event.BusHolder;
import jp.gr.procon.proconapp.event.RequestUpdateEvent;
import jp.gr.procon.proconapp.model.Notice;
import jp.gr.procon.proconapp.model.NoticeList;
import jp.gr.procon.proconapp.ui.callback.OnNoticeClickListener;
import jp.gr.procon.proconapp.ui.view.NoticeListItemView;

public class NoticeOutlineFragment extends BaseFragment implements View.OnClickListener, NoticeApiAsyncTask.NoticeApiListener {
    private static final int MAX_NUM_ROW = 3;

    // 失敗/キャンセルを知りたい場合は追加
    public interface OnUpdateNoticeOutlineListener {
        void OnCompleteNoticeOutlineUpdate();
    }

    public interface OnShowAllNoticeClickListener {
        void onShowAllNoticeClick();
    }

    public static NoticeOutlineFragment newInstance() {
        NoticeOutlineFragment fragment = new NoticeOutlineFragment();
        return fragment;
    }

    private ViewGroup mBodyLayout;
    private NoticeList mNoticeList;
    private ArrayList<ViewHolder> mHolders;
    private OnShowAllNoticeClickListener mOnShowAllNoticeClickListener;
    private OnNoticeClickListener mOnNoticeClickListener;
    private OnUpdateNoticeOutlineListener mOnUpdateNoticeOutlineListener;

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

// TODO save
        View header = view.findViewById(R.id.header);
        header.setOnClickListener(this);
        ImageView iconImageView = (ImageView) view.findViewById(R.id.icon);
        iconImageView.setImageResource(R.drawable.notice);

        TextView titleTextView = (TextView) view.findViewById(R.id.outline_title);
        titleTextView.setText(R.string.title_outline_notice);

        TextView showAllTextView = (TextView) view.findViewById(R.id.outline_show_all);
//        showAllTextView.setOnClickListener(this);

        mBodyLayout = (ViewGroup) view.findViewById(R.id.outline_body);
        setupView();
        if (mNoticeList != null) {
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
        if (mNoticeList == null) {
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

        if (parent != null && parent instanceof OnUpdateNoticeOutlineListener) {
            mOnUpdateNoticeOutlineListener = (OnUpdateNoticeOutlineListener) parent;
        } else if (activity instanceof OnUpdateNoticeOutlineListener) {
            mOnUpdateNoticeOutlineListener = (OnUpdateNoticeOutlineListener) activity;
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

    private void setupView() {
        mHolders = new ArrayList<>();

        LayoutInflater inflater = LayoutInflater.from(mBodyLayout.getContext());
        View divider = inflater.inflate(R.layout.item_divider, mBodyLayout, false);
        mBodyLayout.addView(divider);
        for (int i = 0; i < MAX_NUM_ROW; i++) {
            View v = inflater.inflate(NoticeListItemView.RESOURECE_ID, mBodyLayout, false);
            divider = inflater.inflate(R.layout.item_divider, mBodyLayout, false);
            v.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
            mBodyLayout.addView(v);
            mBodyLayout.addView(divider);

            NoticeListItemView itemView = new NoticeListItemView(v);
            ViewHolder holder = new ViewHolder(itemView, divider);
            mHolders.add(holder);
        }
    }

    private void setDataToView() {
        int i = 0;
        for (final Notice notice : mNoticeList.subList(0, Math.min(mNoticeList.size(), MAX_NUM_ROW))) {
            ViewHolder holder = mHolders.get(i);
            NoticeListItemView itemView = holder.itemView;
            itemView.bindTo(notice, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnNoticeClickListener != null) {
                        mOnNoticeClickListener.onNoticeClick(notice);
                    }
                }
            });

            itemView.setVisibility(View.VISIBLE);
            holder.divider.setVisibility(View.VISIBLE);
            i++;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.header:
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
        mNoticeApiAsyncTask = null;
        if (isDetached() || getActivity() == null) {
            return;
        }

        if (api.isSuccessful()) {
            mNoticeList = api.getResponseObj();
            setDataToView();
        }
        mOnUpdateNoticeOutlineListener.OnCompleteNoticeOutlineUpdate();

    }

    @Override
    public void onCanceledNoticeApi() {
        mOnUpdateNoticeOutlineListener.OnCompleteNoticeOutlineUpdate();
    }

    private void startApiAsyncTask() {
        if (mNoticeApiAsyncTask != null) {
            return;
        }
        mNoticeApiAsyncTask = new NoticeApiAsyncTask(getUserToken(), this);
        mNoticeApiAsyncTask.execute(0);
    }

    private void stopApiAsyncTask() {
        if (mNoticeApiAsyncTask != null) {
            mNoticeApiAsyncTask.cancel(true);
            mNoticeApiAsyncTask = null;
        }
    }

    @Subscribe
    public void requestUpdate(RequestUpdateEvent event) {
        stopApiAsyncTask();
        startApiAsyncTask();
    }

    private static class ViewHolder {
        private NoticeListItemView itemView;
        private View divider;

        public ViewHolder(NoticeListItemView itemView, View divider) {
            this.itemView = itemView;
            this.divider = divider;
        }
    }
}
