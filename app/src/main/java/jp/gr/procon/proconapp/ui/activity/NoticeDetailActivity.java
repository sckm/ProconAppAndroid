package jp.gr.procon.proconapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.model.NoticeDetail;
import jp.gr.procon.proconapp.ui.fragment.ObtainNoticeDetailFragment;
import jp.gr.procon.proconapp.ui.fragment.WebFragment;
import jp.gr.procon.proconapp.util.ToastUtil;

public class NoticeDetailActivity extends BaseActivity implements
        ObtainNoticeDetailFragment.OnObtainNewsDetailListener {
    private static final String TAG_GET_NEWS_DETAIL = "tag_get_news_detail";
    private static final String TAG_SHOW_NEWS_DETAIL = "tag_show_news_detail";
    private static final String ARG_NEWS_ID = "arg_news_id";

    private long mNewsId;
    private View mLoadingView;

    public static Intent createIntent(Context context, long newsId) {
        Intent intent = new Intent(context, NoticeDetailActivity.class);
        Bundle args = new Bundle();
        args.putLong(ARG_NEWS_ID, newsId);
        intent.putExtras(args);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout);

        if (getIntent() != null) {
            mNewsId = getIntent().getExtras().getLong(ARG_NEWS_ID);
        }

        mLoadingView = findViewById(R.id.progress);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, ObtainNoticeDetailFragment.newInstance(mNewsId), TAG_GET_NEWS_DETAIL)
                    .commit();
        }

        if (getSupportFragmentManager().findFragmentByTag(TAG_SHOW_NEWS_DETAIL) == null) {
            mLoadingView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onObtainNewsDetail(NoticeDetail noticeDetail) {
        mLoadingView.setVisibility(View.GONE);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, WebFragment.newInstanceWithData(noticeDetail.getBody()), TAG_SHOW_NEWS_DETAIL)
                .commit();
    }

    @Override
    public void onFailedObtainNewsDetail() {
        ToastUtil.show(this, R.string.error_network);
        mLoadingView.setVisibility(View.GONE);
    }
}
