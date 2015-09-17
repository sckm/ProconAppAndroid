package jp.gr.procon.proconapp.ui.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

import jp.gr.procon.proconapp.util.AppSharedPreference;

public class BaseFragment extends Fragment {

    private String mUserToken;

    protected String getUserToken() {
        return mUserToken;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mUserToken = AppSharedPreference.getString(activity, AppSharedPreference.PREFERENCE_USER_TOKEN);
    }
}
