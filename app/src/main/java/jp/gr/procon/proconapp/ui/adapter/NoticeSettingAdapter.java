package jp.gr.procon.proconapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.model.PlayerCheckedItem;
import timber.log.Timber;

public class NoticeSettingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 1;
    private static final int VIEW_TYPE_ITEM = 2;


    public interface OnChangeCheckListener {
        void onChangeCheck(PlayerCheckedItem item);
    }

    private ArrayList<PlayerCheckedItem> mItems;
    private boolean mIsClickable;
    private OnChangeCheckListener mOnChangeCheckListener;

    public NoticeSettingAdapter(ArrayList<PlayerCheckedItem> items) {
        mItems = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                return HeaderViewHolder.create(parent);

            default:
                return ItemViewHolder.create(parent);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_HEADER:
                ((HeaderViewHolder) holder).bindTo();
                break;

            case VIEW_TYPE_ITEM:
                ((ItemViewHolder) holder).bindTo(mItems.get(position), mIsClickable, mOnChangeCheckListener);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size() + 1;
    }

    public void setCheckWithIds(ArrayList<Long> ids, boolean isCheck) {
        ArrayList<PlayerCheckedItem> checkedItems = new ArrayList<>(mItems);
        Collections.sort(checkedItems, new Comparator<PlayerCheckedItem>() {
            @Override
            public int compare(PlayerCheckedItem lhs, PlayerCheckedItem rhs) {
                return lhs.getPlayer().getId().compareTo(rhs.getPlayer().getId());
            }
        });

        for (long id : ids) {
            int index = Collections.binarySearch(checkedItems, id);
            if (index >= 0) {
                PlayerCheckedItem item = checkedItems.get(index);
                item.setIsCheck(isCheck);
                Timber.d("setCheckWithIds: " + item.getPlayer().getId());
            }
        }
    }

    /**
     * @return 選択状態がONになっているアイテムのリスト
     */
    public ArrayList<PlayerCheckedItem> getCheckedItemList() {
        ArrayList<PlayerCheckedItem> checkedItems = new ArrayList<>();
        for (PlayerCheckedItem item : mItems) {
            if (item.isCheck()) {
                checkedItems.add(item);
            }
        }
        return checkedItems;
    }

    public void setClickable(boolean isClickable) {
        mIsClickable = isClickable;
    }

    public void setOnChangeCheckListener(OnChangeCheckListener onChangeCheckListener) {
        mOnChangeCheckListener = onChangeCheckListener;
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private static final int RES_ID = R.layout.text_description;

        private TextView mTextView;

        public static HeaderViewHolder create(ViewGroup parent) {
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(RES_ID, parent, false));
        }

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.text_body);
        }

        public void bindTo() {
            mTextView.setText(R.string.desc_notification_setting);
        }
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        private static final int RES_ID = R.layout.item_checked_player;

        private TextView mNameText;
        private CheckBox mCheckBox;

        private static ItemViewHolder create(ViewGroup parent) {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(RES_ID, parent, false));
        }

        public ItemViewHolder(View itemView) {
            super(itemView);
            mNameText = (TextView) itemView.findViewById(R.id.name_text);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.check_box);
        }

        public void bindTo(final PlayerCheckedItem item, boolean isClickable, final OnChangeCheckListener listener) {
            mNameText.setText(item.getPlayer().getName());
            mCheckBox.setChecked(item.isCheck());
            if (isClickable) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.toggleCheck();
                        if (listener != null) {
                            listener.onChangeCheck(item);
                        }
                    }
                });
            } else {
                itemView.setOnClickListener(null);
            }
        }
    }
}
