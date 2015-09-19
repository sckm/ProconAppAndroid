package jp.gr.procon.proconapp.api.asynctask;

import android.os.AsyncTask;

import jp.gr.procon.proconapp.api.NoticeListApi;

public class NoticeApiAsyncTask extends AsyncTask<String, Void, NoticeListApi.GetRequest> {

    public interface NoticeApiListener {
        void onPreExecuteNoticeApi();

        void onPostExecuteNoticeApi(NoticeListApi.GetRequest api);

        void onCanceledNoticeApi();
    }

    private String mUserToken;
    private NoticeApiListener mListener;

    public NoticeApiAsyncTask(String userToken, NoticeApiListener listener) {
        mUserToken = userToken;
        mListener = listener;
    }

    public void execute(int page) {
        execute(page, 3);
    }

    public void execute(int page, int count) {
        execute(Integer.toString(page), Integer.toString(count));
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mListener != null) {
            mListener.onPreExecuteNoticeApi();
        }
    }

    @Override
    protected NoticeListApi.GetRequest doInBackground(String... params) {
        return new NoticeListApi.GetRequest(mUserToken).get(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(NoticeListApi.GetRequest getRequest) {
        super.onPostExecute(getRequest);
        if (mListener != null) {
            mListener.onPostExecuteNoticeApi(getRequest);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (mListener != null) {
            mListener.onCanceledNoticeApi();
        }
    }
}
