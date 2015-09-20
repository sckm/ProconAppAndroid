package jp.gr.procon.proconapp.api.asynctask;

import android.os.AsyncTask;

import jp.gr.procon.proconapp.api.GameResultListApi;
import jp.gr.procon.proconapp.model.GameResult;

public class GameResultApiAsyncTask extends AsyncTask<String, Void, GameResultListApi.GetRequest> {

    public interface GameResultApiListener {
        void onPreExecuteGameResultApi();

        void onPostExecuteGameResultApi(GameResultListApi.GetRequest api);

        void onCanceledGameResultApi();
    }

    private String mUserToken;
    private GameResultApiListener mListener;

    public GameResultApiAsyncTask(String userToken, GameResultApiListener listener) {
        mUserToken = userToken;
        mListener = listener;
    }

    public void execute(int count) {
        execute(count, GameResultListApi.GetRequest.FILTER_ALL);
    }

    public void execute(int count, String filter) {
        execute(Integer.toString(count), filter);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mListener != null) {
            mListener.onPreExecuteGameResultApi();
        }
    }

    @Override
    protected GameResultListApi.GetRequest doInBackground(String... params) {
        return new GameResultListApi.GetRequest(mUserToken).get(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(GameResultListApi.GetRequest getRequest) {
        super.onPostExecute(getRequest);
        if (mListener != null) {
            mListener.onPostExecuteGameResultApi(getRequest);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (mListener != null) {
            mListener.onCanceledGameResultApi();
        }
    }
}
