package jp.gr.procon.proconapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.ui.fragment.GamePhotoListFragment;
import jp.gr.procon.proconapp.ui.fragment.NoticeListFragment;

public class GamePhotoListActivity extends AppCompatActivity {

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, GamePhotoListActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, GamePhotoListFragment.newInstance())
                    .commit();
        }
    }
}
