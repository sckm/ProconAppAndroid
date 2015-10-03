package jp.gr.procon.proconapp.ui.fragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.gr.procon.proconapp.api.AuthApi;
import jp.gr.procon.proconapp.api.BaseApi;
import jp.gr.procon.proconapp.model.User;
import jp.gr.procon.proconapp.util.AppSharedPreference;

public class RegisterUserFragment extends BaseFragment {
    public interface OnCompleteRegisterUserListener {
        void onCompleteRegisterUser(String token);
    }


    private AuthApiAsyncTask mAuthApiAsyncTask;
    private OnCompleteRegisterUserListener mOnCompleteRegisterUserListener;

    public static RegisterUserFragment newInstance() {
        return new RegisterUserFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnCompleteRegisterUserListener) {
            mOnCompleteRegisterUserListener = (OnCompleteRegisterUserListener) activity;
        } else {
            throw new RuntimeException("activity must implement OnCompleteRegisterUserListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnCompleteRegisterUserListener = null;
    }

    @Override
    public void onStart() {
        super.onResume();

        String userToken = getUserToken();
        if (TextUtils.isEmpty(userToken)) {
            mAuthApiAsyncTask = new AuthApiAsyncTask();
            mAuthApiAsyncTask.execute();
        }
    }

    @Override
    public void onStop() {
        if (mAuthApiAsyncTask != null) {
            mAuthApiAsyncTask.cancel(true);
            mAuthApiAsyncTask = null;
        }
        super.onPause();
    }

    private class AuthApiAsyncTask extends AsyncTask<Void, Void, BaseApi<User>> {
        @Override
        protected BaseApi<User> doInBackground(Void... params) {
            return new AuthApi.PostNewUser().post();
        }

        @Override
        protected void onPostExecute(BaseApi<User> api) {
            super.onPostExecute(api);
            mAuthApiAsyncTask = null;
            if (isCancelled() || isDetached() || getActivity() == null) {
                return;
            }

            if (api.isSuccessful()) {
                AppSharedPreference.putString(getActivity(),
                        AppSharedPreference.PREFERENCE_USER_TOKEN, api.getResponseObj().getUserToken());
                mOnCompleteRegisterUserListener.onCompleteRegisterUser(api.getResponseObj().getUserToken());
            } else {
                // TODO error
            }
        }
    }
}
