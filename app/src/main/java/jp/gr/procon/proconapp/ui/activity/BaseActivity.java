package jp.gr.procon.proconapp.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.util.AppSharedPreference;

public class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settingIntent = SettingActivity.createIntent(this);
                startActivity(settingIntent);
                return true;

            case R.id.action_program:
                Intent programIntent = ProgramActivity.createIntent(this);
                startActivity(programIntent);
                return true;

            case R.id.action_access:
                Intent accessIntent = AccessActivity.createIntent(this);
                startActivity(accessIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * @return ユーザートークンが登録済みなら登録されているトークン、登録されていないならnull
     */
    protected String getUserToken() {
        return AppSharedPreference.getString(this, AppSharedPreference.PREFERENCE_USER_TOKEN);
    }
}
