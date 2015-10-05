package jp.gr.procon.proconapp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.model.Notice;
import jp.gr.procon.proconapp.ui.callback.OnGetViewListener;
import jp.gr.procon.proconapp.ui.view.NoticeListItemView;
import jp.gr.procon.proconapp.util.DateUtil;

// TODO progress 表示
public class NoticeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_FOOTER = 2;


    public interface OnNoticeItemClickListener {
        void onNoticeItemClick(Notice notice);
    }

    private ArrayList<Notice> mItems;
    private boolean mIsShowLoading;

    private OnGetViewListener mOnGetViewListener;
    private OnNoticeItemClickListener mOnNoticeItemClickListener;

    public NoticeListAdapter(@NonNull ArrayList<Notice> items) {
        mItems = items;
        mIsShowLoading = false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_FOOTER:
                return FooterViewHolder.create(parent);

            default:
                return ItemViewHolder.create(parent);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_FOOTER:
                break;

            case VIEW_TYPE_ITEM:
                ((ItemViewHolder) holder).bindTo(mItems.get(position), mOnNoticeItemClickListener);
                break;
        }

        if (mOnGetViewListener != null) {
            mOnGetViewListener.OnGetView(this, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mItems.size()) {
            return VIEW_TYPE_ITEM;
        } else {
            return VIEW_TYPE_FOOTER;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size() + getFootersCount();
    }

    public int getFootersCount() {
        return mIsShowLoading ? 1 : 0;
    }

    /** フッターにプログレスを表示する場合はtrue, それ以外はfalse */
    public void setIsShowLoading(boolean isShowLoading) {
        mIsShowLoading = isShowLoading;
    }

    public void setOnGetViewListener(OnGetViewListener onGetViewListener) {
        mOnGetViewListener = onGetViewListener;
    }

    public void setOnNoticeItemClickListener(OnNoticeItemClickListener onNoticeItemClickListener) {
        mOnNoticeItemClickListener = onNoticeItemClickListener;
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        public static final int RES_ID = NoticeListItemView.RESOURECE_ID;

        private TextView mTitleText;
        private TextView mPublishedAtText;

        public static ItemViewHolder create(ViewGroup parent) {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(RES_ID, parent, false));
        }

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTitleText = (TextView) itemView.findViewById(R.id.text_title);
            mPublishedAtText = (TextView) itemView.findViewById(R.id.text_published_at);
        }

        public void bindTo(final Notice item, final OnNoticeItemClickListener onNoticeItemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onNoticeItemClickListener != null) {
                        onNoticeItemClickListener.onNoticeItemClick(item);
                    }
                }
            });
            mTitleText.setText(item.getTitle());
            mPublishedAtText.setText(DateUtil.timeToPostDate(item.getPublishedAt()));
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        private static final int RES_ID = R.layout.loading;

        public static FooterViewHolder create(ViewGroup parent) {
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(RES_ID, parent, false));
        }

        public FooterViewHolder(View itemView) {
            super(itemView);
            itemView.findViewById(R.id.progress).setVisibility(View.VISIBLE);
        }
    }
}
