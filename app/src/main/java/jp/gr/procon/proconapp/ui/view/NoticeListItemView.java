package jp.gr.procon.proconapp.ui.view;

import android.view.View;
import android.widget.TextView;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.model.Notice;

public class NoticeListItemView {
    public static final int RESOURECE_ID = R.layout.item_notice_list;

    private View mRoot;
    private TextView mTitleText;
    private TextView mPublishedAtText;

    public NoticeListItemView(View v) {
        mRoot = v;
        mTitleText = (TextView) v.findViewById(R.id.text_title);
        mPublishedAtText = (TextView) v.findViewById(R.id.text_published_at);
    }

    public void bindTo(Notice notice) {
        // TODO テキスト変更
        mTitleText.setText(notice.getTitle());
        mPublishedAtText.setText(notice.getPublishedAt() + "");
    }

}
