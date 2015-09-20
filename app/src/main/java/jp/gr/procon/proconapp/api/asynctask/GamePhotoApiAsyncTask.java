package jp.gr.procon.proconapp.api.asynctask;

import android.os.AsyncTask;

import jp.gr.procon.proconapp.api.GamePhotoListApi;

public class GamePhotoApiAsyncTask extends AsyncTask<String, Void, GamePhotoListApi.GetRequest> {

    public interface GamePhotoApiListener {
        void onPreExecuteGamePhotoApi();

        void onPostExecuteGamePhotoApi(GamePhotoListApi.GetRequest api);

        void onCanceledGamePhotoApi();
    }

    private String mUserToken;
    private GamePhotoApiListener mListener;

    public GamePhotoApiAsyncTask(String userToken, GamePhotoApiListener listener) {
        mUserToken = userToken;
        mListener = listener;
    }

    public void execute(int count) {
        execute(Integer.toString(count));
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mListener != null) {
            mListener.onPreExecuteGamePhotoApi();
        }
    }

    @Override
    protected GamePhotoListApi.GetRequest doInBackground(String... params) {
        return new GamePhotoListApi.GetRequest(mUserToken).get(params[0]);
    }

    @Override
    protected void onPostExecute(GamePhotoListApi.GetRequest getRequest) {
        super.onPostExecute(getRequest);
        if (mListener != null) {
            mListener.onPostExecuteGamePhotoApi(getRequest);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (mListener != null) {
            mListener.onCanceledGamePhotoApi();
        }
    }
}
