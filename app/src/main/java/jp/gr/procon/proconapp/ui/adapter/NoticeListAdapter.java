package jp.gr.procon.proconapp.ui.adapter;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.model.Notice;
import jp.gr.procon.proconapp.ui.callback.OnGetViewListener;
import jp.gr.procon.proconapp.ui.view.NoticeListItemView;

// TODO progress 表示
public class NoticeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Notice> mItems;
    private OnGetViewListener mOnGetViewListener;

    public NoticeListAdapter(ArrayList<Notice> items) {
        mItems = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).bindTo(mItems.get(position));
        if (mOnGetViewListener != null) {
            mOnGetViewListener.OnGetView(this, position);
        }
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public void setOnGetViewListener(OnGetViewListener onGetViewListener) {
        mOnGetViewListener = onGetViewListener;
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

        public void bindTo(Notice item) {
            // TODO テキスト変更
            mTitleText.setText(item.getmTitle());
            mPublishedAtText.setText(item.getmPublishedAt() + "");
        }
    }
}
