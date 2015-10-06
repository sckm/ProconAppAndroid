package jp.gr.procon.proconapp.ui.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.microsoft.windowsazure.messaging.NotificationHub;
import com.microsoft.windowsazure.notifications.NotificationsManager;

import jp.gr.procon.proconapp.api.PushTokenApi;
import jp.gr.procon.proconapp.notification.NotificationConfig;
import jp.gr.procon.proconapp.notification.NotificationHandler;
import jp.gr.procon.proconapp.util.AppSharedPreference;
import timber.log.Timber;

// TODO Serviceで行う
public class RegisterTokenFragment extends BaseFragment {
    private GoogleCloudMessaging mGcm;
    private NotificationHub mHub;
    private RegisterAsyncTask mRegisterAsyncTask;

    public static RegisterTokenFragment newInstance() {
        return new RegisterTokenFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        NotificationsManager.handleNotifications(getActivity(), NotificationConfig.SENDER_ID, NotificationHandler.class);
        registerWithNotificationHubs();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerWithNotificationHubs();
    }

    @Override
    public void onStop() {
        stopAsyncTask();
        super.onStop();
    }

    private void startAsyncTask() {
        if (mRegisterAsyncTask != null) {
            return;
        }

        mRegisterAsyncTask = new RegisterAsyncTask(getActivity());
        mRegisterAsyncTask.execute();
    }

    private void stopAsyncTask() {
        if (mRegisterAsyncTask != null) {
            mRegisterAsyncTask.cancel(true);
        }
        mRegisterAsyncTask = null;
    }

    @SuppressWarnings("unchecked")
    private void registerWithNotificationHubs() {
        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (isAvailable != ConnectionResult.SUCCESS) {
            Timber.d("Google play services not available " + GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE);
            return;
        }

        String registeredId = AppSharedPreference.getString(getActivity(), AppSharedPreference.PREFERENCE_GCM_REGISTER_ID);
        if (TextUtils.isEmpty(registeredId)) {
            if (mGcm == null) {
                mGcm = GoogleCloudMessaging.getInstance(getActivity());
            }
            startAsyncTask();
        } else {
            Timber.d("registerWithNotificationHubs: already registered id=" + registeredId);
        }
    }

    private class RegisterAsyncTask extends AsyncTask<Object, Void, PushTokenApi.PutRequest> {
        private Context mContext;
        private String mRegisterId;

        public RegisterAsyncTask(Context context) {
            mContext = context;
        }

        @Override
        protected PushTokenApi.PutRequest doInBackground(Object... params) {
            try {
                mRegisterId = mGcm.register(NotificationConfig.SENDER_ID);
                Timber.d("Registered Successfully Id=" + mRegisterId);

                return new PushTokenApi.PutRequest(getUserToken()).put(mRegisterId);
            } catch (Exception e) {
                e.printStackTrace();
                Timber.d("Exception: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(PushTokenApi.PutRequest api) {
            super.onPostExecute(api);
            if (isCancelled() || isDetached() || getActivity() == null) {
                return;
            }

            if (api.isSuccessful()) {
                AppSharedPreference.putString(mContext, AppSharedPreference.PREFERENCE_GCM_REGISTER_ID, mRegisterId);
                Timber.d("Registered to api server: " + mRegisterId);
            }
        }
    }
}
