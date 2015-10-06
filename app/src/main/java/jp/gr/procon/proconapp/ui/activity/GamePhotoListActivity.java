package jp.gr.procon.proconapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.model.GamePhoto;
import jp.gr.procon.proconapp.ui.callback.OnClickPhotoListener;
import jp.gr.procon.proconapp.ui.fragment.GamePhotoListFragment;
import jp.gr.procon.proconapp.ui.fragment.NoticeListFragment;

public class GamePhotoListActivity extends AppCompatActivity implements OnClickPhotoListener {

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

    @Override
    public void onClickPhoto(GamePhoto photo) {
        Intent intent = PhotoDetailActivity.createIntent(this, photo);
        startActivity(intent);
    }
}
