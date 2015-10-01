package jp.gr.procon.proconapp.ui.activity;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.notification.NotificationConfig;
import jp.gr.procon.proconapp.ui.fragment.GameResultListFragment;
import timber.log.Timber;

public class GameResultListActivity extends BaseActivity {
    private static final String ARG_FROM_NOTIFICATION = "arg_from_notification";

    public static Intent createIntent(Context context) {
        return createIntent(context, false);
    }

    public static Intent createIntent(Context context, boolean fromNotification) {
        Intent intent = new Intent(context, GameResultListActivity.class);
        Bundle args = new Bundle();
        args.putBoolean(ARG_FROM_NOTIFICATION, fromNotification);
        intent.putExtras(args);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result_list);

        Bundle args = getIntent().getExtras();
        boolean isFromNotification = false;
        if (args != null) {
            isFromNotification = args.getBoolean(ARG_FROM_NOTIFICATION, false);
        }
        if (isFromNotification) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
            notificationManager.cancel(NotificationConfig.NOTIFICATION_ID_GAME_RESULT);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, GameResultListFragment.newInstance(isFromNotification))
                    .commit();
        }
    }
}
