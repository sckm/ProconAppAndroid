package jp.gr.procon.proconapp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.model.FeedTwitterStatus;
import jp.gr.procon.proconapp.ui.fragment.TwitterFeedFragment;
import jp.gr.procon.proconapp.util.DateUtil;
import timber.log.Timber;

public class TwitterFeedRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FeedTwitterStatus> mItems;
    private TwitterFeedFragment.OnClickTweetListener mOnClickTweetListener;

    public TwitterFeedRecyclerAdapter(@NonNull List<FeedTwitterStatus> items) {
        mItems = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).bindTo(mItems.get(position), mOnClickTweetListener);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setOnClickTweetListener(TwitterFeedFragment.OnClickTweetListener onClickTweetListener) {
        mOnClickTweetListener = onClickTweetListener;
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

        public void bindTo(final FeedTwitterStatus tweet, final TwitterFeedFragment.OnClickTweetListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClickTweet(tweet);
                    }
                }
            });
            Glide.with(itemView.getContext())
                    .load(convertProfileIconUrlToLargeUrl(tweet.getUser().getProfileImageUrl()))
                    .fitCenter()
                    .into(mThumbnailView);

            String twitterDateFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
            SimpleDateFormat format = new SimpleDateFormat(twitterDateFormat, Locale.ENGLISH);
            format.setLenient(true);
            Timber.d("bindTo: " + format.toPattern());
            long targetDateTime = 0;
            try {
                targetDateTime = format.parse(tweet.getCreatedAt()).getTime();
            } catch (ParseException e) {
                targetDateTime = -1;
                e.printStackTrace();
            }
            Timber.d("bindTo: " + System.currentTimeMillis() + " " + tweet.getCreatedAt() + " ");
            mNameText.setText(tweet.getUser().getName());
            mScreenNameText.setText("@" + tweet.getUser().getScreenName());
            mCreatedAtText.setText(DateUtil.timeToPostDate(targetDateTime));
            mTweetBody.setText(tweet.getText());

        }

        private String convertProfileIconUrlToLargeUrl(String url) {
            if (TextUtils.isDigitsOnly(url)) {
                return url;
            }
            String normalUrlString = "_normal";
            return url.replace(normalUrlString, "");
        }
    }


}
