package jp.gr.procon.proconapp.ui.fragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import jp.gr.procon.proconapp.api.NoticeDetailApi;
import jp.gr.procon.proconapp.model.NoticeDetail;

public class ObtainNoticeDetailFragment extends BaseFragment {
    private static final String ARG_NOTICE_ID = "arg_notice_id";

    public interface OnObtainNewsDetailListener {
        void onObtainNewsDetail(NoticeDetail noticeDetail);

        void onFailedObtainNewsDetail();
    }

    private long mNoticeId;
    private NoticeDetail mNoticeDetail;
    private ApiAsyncTask mApiAsyncTask;

    private OnObtainNewsDetailListener mOnObtainNewsDetailListener;

    public static ObtainNoticeDetailFragment newInstance(long noticeId) {
        ObtainNoticeDetailFragment fragment = new ObtainNoticeDetailFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_NOTICE_ID, noticeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNoticeId = getArguments().getLong(ARG_NOTICE_ID);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mNoticeDetail == null) {
            startApiAsyncTask();
        }
    }

    @Override
    public void onPause() {
        if (mApiAsyncTask != null) {
            mApiAsyncTask.cancel(true);
            mApiAsyncTask = null;
        }
        super.onPause();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Fragment fragment = getParentFragment();
        if (fragment != null && fragment instanceof OnObtainNewsDetailListener) {
            mOnObtainNewsDetailListener = (OnObtainNewsDetailListener) fragment;
        } else if (activity instanceof OnObtainNewsDetailListener) {
            mOnObtainNewsDetailListener = (OnObtainNewsDetailListener) activity;
        } else {
            throw new RuntimeException("parent or activity must implement listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnObtainNewsDetailListener = null;
    }

    private void startApiAsyncTask() {
        if (mApiAsyncTask != null) {
            return;
        }

        mApiAsyncTask = new ApiAsyncTask();
        mApiAsyncTask.execute(mNoticeId);


    }

    private class ApiAsyncTask extends AsyncTask<Long, Void, NoticeDetailApi.GetRequest> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected NoticeDetailApi.GetRequest doInBackground(Long... params) {
            return new NoticeDetailApi.GetRequest(getUserToken()).get(Long.toString(params[0]));
        }

        @Override
        protected void onPostExecute(NoticeDetailApi.GetRequest api) {
            super.onPostExecute(api);
            if (isDetached() || getArguments() == null) {
                return;
            }

            if (api.isSuccessful()) {
                mNoticeDetail = api.getResponseObj();
                if (mOnObtainNewsDetailListener != null) {
                    mOnObtainNewsDetailListener.onObtainNewsDetail(mNoticeDetail);
                }
            } else {
                if (mOnObtainNewsDetailListener != null) {
                    mOnObtainNewsDetailListener.onFailedObtainNewsDetail();
                }
            }
        }


        @Override
        protected void onCancelled(NoticeDetailApi.GetRequest getRequest) {
            super.onCancelled(getRequest);
            if (mOnObtainNewsDetailListener != null) {
                mOnObtainNewsDetailListener.onFailedObtainNewsDetail();
            }
        }
    }
}
