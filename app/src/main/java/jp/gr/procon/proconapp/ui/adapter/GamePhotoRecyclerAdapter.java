package jp.gr.procon.proconapp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.model.GamePhoto;
import jp.gr.procon.proconapp.ui.callback.OnClickPhotoListener;

public class GamePhotoRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<GamePhoto> mItems;
    private OnClickPhotoListener mOnClickPhotoListener;

    public GamePhotoRecyclerAdapter(@NonNull List<GamePhoto> items) {
        mItems = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).bindTo(mItems.get(position), mOnClickPhotoListener);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setOnClickPhotoListener(OnClickPhotoListener onClickPhotoListener) {
        mOnClickPhotoListener = onClickPhotoListener;
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        public static final int RES_ID = R.layout.item_game_photo;
        private ImageView mThumbnailView;

        public static ItemViewHolder create(ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new ItemViewHolder(inflater.inflate(RES_ID, parent, false));
        }

        public ItemViewHolder(View itemView) {
            super(itemView);
            mThumbnailView = (ImageView) itemView.findViewById(R.id.thumbnail_view);
        }

        public void bindTo(final GamePhoto photo, final OnClickPhotoListener listener) {
            Glide.with(itemView.getContext())
                    .load(photo.getmThumbnailUrl())
                    .centerCrop()
                    .into(mThumbnailView);

            mThumbnailView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClickPhoto(photo);
                    }
                }
            });
        }

    }
}
