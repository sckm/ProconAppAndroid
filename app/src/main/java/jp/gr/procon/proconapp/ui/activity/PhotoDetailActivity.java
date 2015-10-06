package jp.gr.procon.proconapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.model.GamePhoto;
import jp.gr.procon.proconapp.ui.fragment.PhotoDetailFragment;

public class PhotoDetailActivity extends BaseActivity {
    private static String ARG_PHOTO = "arg_photo";

    private GamePhoto mGamePhoto;

    public static Intent createIntent(Context context, GamePhoto photo) {
        Intent intent = new Intent(context, PhotoDetailActivity.class);
        Bundle args = new Bundle();
        args.putSerializable(ARG_PHOTO, photo);
        intent.putExtras(args);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout);

        Bundle args = getIntent().getExtras();
        mGamePhoto = (GamePhoto) args.getSerializable(ARG_PHOTO);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, PhotoDetailFragment.newInstance(mGamePhoto))
                    .commit();
        }
    }
}
