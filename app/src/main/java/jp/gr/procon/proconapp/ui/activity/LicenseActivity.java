package jp.gr.procon.proconapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.ui.fragment.WebFragment;

public class LicenseActivity extends AppCompatActivity {
    public static Intent createIntent(Context context) {
        return new Intent(context, LicenseActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, WebFragment.newInstance("file:///android_asset/license.html"))
                    .commit();
        }
    }

}
