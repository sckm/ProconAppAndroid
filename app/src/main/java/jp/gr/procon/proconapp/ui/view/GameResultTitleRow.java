package jp.gr.procon.proconapp.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.model.GameResult;

public class GameResultTitleRow extends TableRow {
    private TextView mTitleText;
    private TextView mStartedAtText;
    private GameResult mGameResult;

    public GameResultTitleRow(Context context) {
        super(context);
        init(context);
    }

    public GameResultTitleRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.game_result_title_row, this, true);
        mTitleText = (TextView) v.findViewById(R.id.title_text);
        mStartedAtText = (TextView) v.findViewById(R.id.started_at_text);
    }

    public void setGameResult(GameResult gameResult) {
        // TODO テキスト変更
        mGameResult = gameResult;
        mTitleText.setText(mGameResult.getTitle());

        switch (gameResult.getStatus()) {
            case GameResult.STATUS_GAME_ENDED:
                mStartedAtText.setText(mGameResult.getStartedAt() + "");
                break;

            case GameResult.STATUS_GAME_PROGRESS:
                mStartedAtText.setText("試合中");
                break;
        }

    }
}
