package jp.gr.procon.proconapp.ui.adapter;

import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.model.GameResult;
import jp.gr.procon.proconapp.model.PlayerResult;
import jp.gr.procon.proconapp.util.DateUtil;

public class GameResultExpandableListAdapter extends BaseExpandableListAdapter {
    private ArrayList<GameResult> mItems;

    public GameResultExpandableListAdapter(ArrayList<GameResult> items) {
        this.mItems = items;
    }

    @Override
    public int getGroupCount() {
        return mItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mItems.get(groupPosition).getResult().size();
    }

    @Override
    public GameResult getGroup(int groupPosition) {
        return mItems.get(groupPosition);
    }

    @Override
    public PlayerResult getChild(int groupPosition, int childPosition) {
        return mItems.get(groupPosition).getResult().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(GroupViewHolder.RES_ID, parent, false);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }

        GameResult groupItem = getGroup(groupPosition);

        holder.mTitleText.setText(groupItem.getTitle());

        Calendar startedCal = Calendar.getInstance();
        startedCal.setTimeInMillis(groupItem.getStartedAt());
        Calendar finishedCal = Calendar.getInstance();
        finishedCal.setTimeInMillis(groupItem.getFinishedAt());

        String dateFormat = "hh:mm";

        String startedAt = DateFormat.format(dateFormat, startedCal).toString();
        String finishedAt = DateFormat.format(dateFormat, finishedCal).toString();
        String gameResultDateFormat = "開始: %1$s - 終了: %2$s";

        holder.mDateText.setText(String.format(gameResultDateFormat, startedAt, finishedAt));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(ChildViewHolder.RES_ID, parent, false);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }

        PlayerResult item = getChild(groupPosition, childPosition);

        // TODO format
        holder.mRankText.setText(item.getRank() + "");
        holder.mRankText.setBackgroundResource(item.isAdvance() ? R.drawable.background_circle_red : R.drawable.background_circle_blue);

        holder.mTitleText.setText(item.getPlayer().getName());
        String score = item.getScore() < 0 ? "未回答" : item.getScore() + "zk";
        holder.mScoreText.setText(score);

        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    private static class GroupViewHolder {
        public static final int RES_ID = R.layout.item_game_result_exlist_group;

        private TextView mTitleText;
        private TextView mDateText;

        public GroupViewHolder(View v) {
            mTitleText = (TextView) v.findViewById(R.id.text_title);
            mDateText = (TextView) v.findViewById(R.id.text_game_date);
        }
    }

    private static class ChildViewHolder {
        public static final int RES_ID = R.layout.item_game_result_exlist_child;
        private TextView mRankText;
        private TextView mTitleText;

        private TextView mScoreText;

        public ChildViewHolder(View v) {
            mRankText = (TextView) v.findViewById(R.id.text_rank);
            mTitleText = (TextView) v.findViewById(R.id.text_title);
            mScoreText = (TextView) v.findViewById(R.id.text_score);
        }

    }
}
