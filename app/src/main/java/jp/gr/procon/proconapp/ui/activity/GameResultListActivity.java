package jp.gr.procon.proconapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import jp.gr.procon.proconapp.BaseActivity;
import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.ui.fragment.GameResultListFragment;

public class GameResultListActivity extends BaseActivity {

    public static Intent createIntent(Context context) {
        return new Intent(context, GameResultListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result_list);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, GameResultListFragment.newInstance())
                    .commit();
        }
    }
}
