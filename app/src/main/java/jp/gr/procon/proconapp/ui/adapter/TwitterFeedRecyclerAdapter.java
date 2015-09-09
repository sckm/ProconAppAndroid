package jp.gr.procon.proconapp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.model.FeedTwitterStatus;

public class TwitterFeedRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FeedTwitterStatus> mItems;

    public TwitterFeedRecyclerAdapter(@NonNull List<FeedTwitterStatus> items) {
        mItems = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).bindTo(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        public static final int RES_ID = R.layout.item_twitter_feed;

        private ImageView mThumbnailView;
        private TextView mNameText;
        private TextView mScreenNameText;
        private TextView mCreatedAtText;
        private TextView mTweetBody;

        public static ItemViewHolder create(ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new ItemViewHolder(inflater.inflate(RES_ID, parent, false));
        }

        public ItemViewHolder(View itemView) {
            super(itemView);
            mThumbnailView = (ImageView) itemView.findViewById(R.id.thumbnail_view);
            mNameText = (TextView) itemView.findViewById(R.id.user_name);
            mScreenNameText = (TextView) itemView.findViewById(R.id.screen_name);
            mCreatedAtText = (TextView) itemView.findViewById(R.id.created_at);
            mTweetBody = (TextView) itemView.findViewById(R.id.tweet_body);
        }

        public void bindTo(FeedTwitterStatus tweet) {
            Glide.with(itemView.getContext())
                    .load(tweet.getUser().getProfileImageUrl())
                    .fitCenter()
                    .into(mThumbnailView);

            mNameText.setText(tweet.getUser().getName());
            mScreenNameText.setText(tweet.getUser().getScreenName());
            mCreatedAtText.setText(tweet.getCreatedAt());
            mTweetBody.setText(tweet.getText());

        }

    }
}
